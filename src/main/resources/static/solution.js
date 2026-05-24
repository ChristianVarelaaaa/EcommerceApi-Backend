const API_BASE = 'http://localhost:8080';

// Kunin token sa localStorage
function getToken() {
  return localStorage.getItem('jwt_token');
}

// Global fetch wrapper
async function apiFetch(url, options = {}) {
  const fullUrl = API_BASE + url;
  const token = getToken();

  const defaultOptions = {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers
    },
    ...options
  };

  // Lagay token sa header kung meron
  if (token) {
    defaultOptions.headers['Authorization'] = 'Bearer ' + token;
  }

  try {
    const response = await fetch(fullUrl, defaultOptions);

    if (response.status === 401) {
      alert('Session expired. Please login again.');
      localStorage.removeItem('jwt_token');
      window.location.href = '/login.html';
      return null;
    }

    if (response.status === 403) {
      alert('Access Denied. You do not have permission.');
      return null;
    }

    if (!response.ok) {
      let errorData = { error: 'Request failed' };
      try {
        errorData = await response.json();
      } catch (e) {}
      throw new Error(errorData.error || `HTTP ${response.status}`);
    }

    return response;
  } catch (error) {
    console.error('API Error:', error);
    throw error;
  }
}

// Helper GET
async function apiGet(url) {
  const response = await apiFetch(url);
  if (response) return response.json();
  return null;
}

// Helper POST
async function apiPost(url, data) {
  const response = await apiFetch(url, {
    method: 'POST',
    body: JSON.stringify(data)
  });
  if (response) return response.json();
  return null;
}

// Login function - i-save token
async function login(username, password) {
  const res = await fetch(API_BASE + '/api/v1/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password })
  });

  if (!res.ok) {
    alert('Invalid username or password');
    return false;
  }

  const data = await res.json();
  localStorage.setItem('jwt_token', data.token);
  window.location.href = '/dashboard.html';
  return true;
}

// Logout
function logout() {
  localStorage.removeItem('jwt_token');
  window.location.href = '/login.html';
}

// Check kung logged in
async function checkAuth() {
  const token = getToken();
  if (!token) {
    window.location.href = '/login.html';
    return false;
  }

  try {
    const res = await fetch(API_BASE + '/api/v1/auth/check', {
      headers: { 'Authorization': 'Bearer ' + token }
    });
    
    if (res.status === 401) {
      logout();
      return false;
    }
    return true;
  } catch (e) {
    logout();
    return false;
  }
}