Workspace Hero | User Service
This is the user management service for the Workspace Hero project. It handles registration, login, and roles (USER/MANAGER) using Spring Security and JWT
-----------------------------------------------------------------------------------
Features
User Registration: Create a new account with ROLE_USER.

Manager Registration: Create a manager account with ROLE_MANAGER.

Secure Login: Authentication via email and password, returning a JWT token.

Password Hashing: All passwords are encrypted using BCrypt.

Role-based Access: Some endpoints are restricted to Managers only.

-----------------------------------------------------------------------------------

## Tech Stack
* **Java 17 / Spring Boot 3.4.2**

* **Spring Security + JWT** 

* **Spring Data JPA + PostgreSQL**

* **Lombok**

* **Docker**

-----------------------------------------------------------------------------------

## Setup
Database: Run PostgreSQL on port 5432

Properties

jwt.secret=awgnkjnajef3u2oejf84fkldsnvneshguio5483o8gjldsnvkjrg58lkngslknvr

jwt.expiration=86400000

spring.datasource.url=jdbc:postgresql://localhost:5432/postgres

spring.datasource.username=postgres

spring.datasource.password=root

spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration

-----------------------------------------------------------------------------------

## API Endpoints
Public
POST /users/register_user - Create a regular user.

POST /users/register_manager - Create a manager.

POST /users/login - Get your JWT token.

Protected only for manager
GET /users - Get list of all users.

GET /users/{id} - Get user details by ID.

-----------------------------------------------------------------------------------
