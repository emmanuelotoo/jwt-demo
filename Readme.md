# JWT Demo

A RESTful API built with Spring Boot 3.5.6 that demonstrates JWT (JSON Web Token) authentication and authorization. This application implements secure user authentication with access tokens and refresh tokens, providing a complete authentication flow including signup, login, token refresh, and logout functionality.


### Tech Stack

- **Java 21**
- **Maven**

### Running the Application

1. Clone the repository:
   ```cmd
   git clone <repository-url>
   cd jwt-demo
   ```

2. Build the project:
   ```cmd
   mvnw.cmd clean install
   ```

3. Run the application:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

The application will start on `http://localhost:8080`

## How to Use

### API Endpoints

#### Public Endpoints (No Authentication Required)

##### 1. User Registration
```http
POST /api/auth/signup
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:**
```
User registered successfully
```

##### 2. User Login
```http
POST /api/auth/sign-in
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
}
```

##### 3. Refresh Access Token
```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
}
```

**Response:**
```json
{
  "New Access token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "New Refresh token": "660e8400-e29b-41d4-a716-446655440001"
}
```

##### 4. Logout
```http
POST /api/auth/logout
Content-Type: application/json

{
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
}
```

**Response:**
```
Logged out successfully
```

##### 5. Public Test Endpoint
```http
GET /api/test/all
```

**Response:**
```
Public Content.
```

#### Protected Endpoints (Authentication Required)

##### User Test Endpoint
```http
GET /api/test/user
Authorization: Bearer <your_access_token>
```

**Response:**
```
User Content.
```

### Authentication Flow

1. **Register**: Create a new user account with name, email, and password
2. **Login**: Authenticate with email and password to receive access and refresh tokens
3. **Access Resources**: Use the access token in the Authorization header as `Bearer <token>`
4. **Refresh**: When the access token expires, use the refresh token to get a new access token
5. **Logout**: Invalidate the refresh token when the user wants to logout