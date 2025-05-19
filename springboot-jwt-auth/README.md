# Spring Boot JWT Authentication

This project is a Spring Boot application that implements JWT (JSON Web Token) authentication. It provides a secure way to handle user authentication and authorization using JWT tokens.

## Features

- User registration and login
- JWT token generation and validation
- Secure RESTful API endpoints
- Spring Security integration

## Technologies Used

- Spring Boot
- Spring Security
- Spring Data JPA
- H2 Database (for development and testing)
- Maven

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven

### Installation

1. Clone the repository:

   ```
   git clone https://github.com/yourusername/springboot-jwt-auth.git
   ```

2. Navigate to the project directory:

   ```
   cd springboot-jwt-auth
   ```

3. Build the project using Maven:

   ```
   mvn clean install
   ```

### Running the Application

To run the application, use the following command:

```
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

### API Endpoints

- **POST /api/auth/register**: Register a new user.
- **POST /api/auth/login**: Authenticate a user and return a JWT token.

### Running Tests

To run the tests, use the following command:

```
mvn test
```

## License

This project is licensed under the MIT License. See the LICENSE file for details.