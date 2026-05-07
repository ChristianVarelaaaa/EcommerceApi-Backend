// Global fetch wrapper para mahuli yung 401/403
async function apiFetch(url, options = {}) {
    // Default options
    const defaultOptions = {
        credentials: 'include', // Important para masama yung JSESSIONID cookie
        headers: {
            'Content-Type': 'application/json',
            ...options.headers
        },
        ...options
    };

    try {
        const response = await fetch(url, defaultOptions);

        // 401 Unauthorized - hindi naka login or expired session
        if (response.status === 401) {
            alert('Session expired. Please login again.');
            window.location.href = '/login.html';
            return null;
        }

        // 403 Forbidden - naka login pero walang permission
        if (response.status === 403) {
            alert('Access Denied. You do not have permission to access this resource.');
            return null;
        }

        // Check if response is ok
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || 'Request failed');
        }

        return response;

    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

// Helper function para sa GET requests
async function apiGet(url) {
    const response = await apiFetch(url);
    if (response) return response.json();
    return null;
}

// Helper function para sa POST requests
async function apiPost(url, data) {
    const response = await apiFetch(url, {
        method: 'POST',
        body: JSON.stringify(data)
    });
    if (response) return response.json();
    return null;
}

// Helper para check kung logged in ba
async function checkAuth() {
    try {
        const res = await fetch('/api/v1/auth/check', {credentials: 'include'});
        if (res.status === 401) {
            window.location.href = '/login.html';
            return false;
        }
        return true;
    } catch (e) {
        window.location.href = '/login.html';
        return false;
    }
}
