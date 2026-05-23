# EcommerceApi - Product CRUD API
Lab 7 - WS101 | Varela Unay

## Project Overview
A RESTful API for product management built with Spring Boot. Supports full CRUD operations, filtering, validation, and global exception handling. Data is stored in-memory using a List.

## Setup Instructions
1. *Prerequisites*: Java 17+, Gradle
2. *Clone repo*: git clone <your-repo-link>
3. *Run application*: 
   ```bash
   ./gradlew bootRun


## Database Schema
- **Table: products**
    - id: INT, PRIMARY KEY, AUTO_INCREMENT
    - name: VARCHAR(255)
    - price: DECIMAL(10,2)
    - description: TEXT


## API Endpoints
| Method | Endpoint | Description |
| --- | --- | --- |
| GET | /api/products | Get all products from database |
| POST | /api/products | Add new product |
| GET | /api/products/{id} | Get product by ID |

## Screenshots
### Database Table
![Database Table](docs/screenshots/db-table.png)

### Browser Console - Successful Fetch
![Console Fetch](docs/screenshots/console-fetch.png)

   

//BAGO LAB 9
# EcommerceApi - Session-Based Authentication

WS101 Laboratory Activity: Implementing session-based authentication, validation, and error handling in Spring Boot.

## Security Architecture

This application uses *Session-Based Authentication* with Spring Security.

*How it works:*
1. User submits credentials to /login via formLogin()
2. Spring Security validates credentials against database using BCryptPasswordEncoder
3. If valid, server creates a session and stores user details in HttpSession
4. Server sends JSESSIONID cookie to client browser
5. Browser automatically includes JSESSIONID in all subsequent requests
6. Spring Security validates the session ID on each request to authorize access
7. CSRF is currently disabled per instructor requirements for this lab

*Key Components:*
- SecurityConfig.java - Defines authentication rules and session management
- UserDetailsServiceImpl.java - Loads user data from database
- User entity - Stores username, hashed password, and roles

## Validation Rules

Validation is implemented using jakarta.validation annotations on entities and DTOs.

*Product Entity:*
- name: Not blank, 3-100 characters
- description: Not blank, max 500 characters
- price: Not null, must be greater than 0
- stock: Not null, must be 0 or positive

*RegisterRequest DTO:*
- username: Not blank, 3-20 characters
- password: Not blank, minimum 6 characters
- role: Not blank

Invalid requests return 400 Bad Request with detailed field-level error messages from GlobalExceptionHandler.

## API Reference

| Method | Endpoint | Auth Required | Roles | Description |
| --- | --- | --- |
| POST | /api/v1/auth/register | No | - | Register new user |
| GET | /api/v1/auth/check | Yes | Any | Check if user is logged in |
| POST | /login | No | - | Form login endpoint |
| POST | /logout | Yes | Any | Logout and invalidate session |
| GET | /api/v1/products | No | - | Get all products |
| GET | /api/v1/products/{id} | No | - | Get product by ID |
| POST | /api/v1/products | Yes | ADMIN, SELLER | Create new product |
| PUT | /api/v1/products/{id} | Yes | ADMIN, SELLER | Update product |
| DELETE | /api/v1/products/{id} | Yes | ADMIN | Delete product |

*Error Responses:*
- 401 Unauthorized: No session or invalid credentials. Frontend redirects to /login.html
- 403 Forbidden: Valid session but insufficient role permissions
- 400 Bad Request: Validation failed. Returns list of specific field errors
- 404 Not Found: Resource does not exist

## Frontend Integration

Static HTML files in src/main/resources/static/:
- login.html - Login form
- signup.html - Registration form
- products.html - Protected product listing
- solution.js - Global fetch wrapper handling 401/403 errors

All protected pages use checkAuth() from solution.js to verify session on page load.

## Running the Application

1. Configure application.properties with MySQL credentials
2. Run: ./gradlew bootRun
3. Access: http://localhost:8080/login.html

## Technologies Used
- Spring Boot 3.x
- Spring Security 6
- Spring Data JPA
- MySQL
- Jakarta Validation
- Gradle   
