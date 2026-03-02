# Workspace Hero | User Service

This is the **User Service** for the Workspace Hero project. It handles **registration**, **login**, **JWT issuing**, **user profiles**, and **balance operations**.

---

## Features

- **User Registration**: Create a new account with `ROLE_USER`.
- **Manager Registration**: Create a manager account with `ROLE_MANAGER`.
- **Secure Login**: Authentication via email + password, returns a **JWT**.
- **Password Hashing**: Passwords are encrypted using **BCrypt**.
- **Role-based Access**: Some endpoints are restricted to managers.
- **Balance management**:
    - top-up by id
    - deduct **for current user** (`/users/me/deduct`) based on JWT identity

---

## Tech Stack

- **Java 17 / Spring Boot 3.4.2**
- **Spring Security + JWT**
- **Spring Data JPA + PostgreSQL**
- **Lombok**
- **Docker**

---

## Setup

### Database
Run PostgreSQL on port **5432**.

### Configuration (example)

Keep secrets out of Git. Use `.env` for production.

```properties
jwt.secret=CHANGE_ME
jwt.expiration=86400000

spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=root
```

---

## API Endpoints

### Public
- `POST /users/register/user` — Create a regular user
- `POST /users/register/manager` — Create a manager
- `POST /users/login` — Get JWT token

### Protected (requires JWT)

#### Manager-only
- `GET /users` — List all users
- `GET /users/{id}` — Get user details by id

#### Current user balance (USER or MANAGER)
- `POST /users/me/deduct?amount=...` — Deduct balance for the current user (identity is taken from JWT)

#### Balance by id (optional / internal)
- `POST /users/{id}/top-up?amount=...` — Top up balance by user id