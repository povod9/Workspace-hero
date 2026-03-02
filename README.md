# Workspace Hero — Microservices Ecosystem

Workspace Hero is a distributed platform designed to manage office workspaces, desks, and meeting rooms. It uses a microservices architecture with centralized authentication, inter-service communication, and time-based reservation logic.

## System Architecture

The project is built as a **monorepo** managed by a parent Maven project and includes:

- **API Gateway**: Single entry point for clients. Responsible for routing requests to internal services.
- **User Service**: Manages authentication (JWT generation), user profiles, and financial balances.
- **Booking Service**: Handles workspace availability, reservations, and booking-related logic.

## Features & Logic

### Security and Authentication (JWT)

- **JWT Authentication**: Users authenticate via **User-Service**, which issues a JWT.
- **Service-level authorization**: The **Gateway** does **not** validate or parse JWT claims. Each downstream service validates JWT and enforces authorization rules.
- **No identity headers**: The platform does **not** rely on `X-User-Id` / `X-User-Role` propagation. User identity is derived from the JWT in each service (via Spring Security context).
- **RBAC**: Role-based restrictions (e.g., `MANAGER`-only endpoints) are enforced at the **service level** using Spring Security / `@PreAuthorize`.

### Booking & Availability (time-based)

- **Time-based availability**: A workspace is considered busy only for the time interval of an active booking.
- **Automated lifecycle**: A scheduler may be used to finalize/expire bookings, but “busy now” can always be computed dynamically from booking time ranges.

### Financial Integration

- **Atomic booking flow**: The Booking-Service charges the user before persisting a reservation.
- **Current-user deduction**: Booking-Service calls User-Service using a JWT-protected endpoint:
    - `POST /users/me/deduct?amount=...`
      User-Service determines the user from the JWT (no userId is passed from Booking-Service).
- **Authorization propagation**: Booking-Service forwards the incoming `Authorization: Bearer ...` header to User-Service via Feign configuration/interceptor.