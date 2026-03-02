## API Gateway (Workspace Hero)

The central entry point for the Workspace Hero microservices ecosystem. It is responsible for **request routing** to internal services.

Note: The Gateway does **not** perform JWT validation/authorization. Authentication and RBAC are enforced in downstream services (User Service / Booking Service).

---

## Tech Stack

- **Java 17 & Spring Boot 3.4.2**
- **Spring Cloud Gateway (Reactive)**
- **Spring WebFlux**

---

## Key Responsibilities

### 1) Dynamic Routing
Routes traffic to the appropriate microservice based on URL patterns:

- `/users/**` → `user-service`
- `/booking/**` → `booking-service`

### 2) Edge service / API entry point
Acts as a single entry point for clients and keeps internal service addresses hidden behind one public endpoint.

---

## Security Model (important)

- The Gateway **does not parse or validate JWT**.
- The Gateway **does not enrich requests** with identity headers like `X-User-Id` / `X-User-Role`.
- Each internal service validates JWT and applies authorization rules independently (Spring Security + `@PreAuthorize`).

---

## Environment Variables / Configuration

Typical configuration values:

- `USER_SERVICE_URL` — Address of the user microservice
- `BOOKING_SERVICE_URL` — Address of the booking microservice

Example:

```properties
user-service.url=http://user-service:8081
booking-service.url=http://booking-service:8082
```