## API Gateway (Workspace Hero)
The central entry point for the Workspace Hero microservices ecosystem. It handles security, routing, and user context propagation across the cluster.

## Tech Stack
* **Java 17 & Spring Boot 3.4.2**

* **Spring Cloud Gateway (Reactive)**

* **JJWT (Java JWT) (Token parsing and validation)**

* **Spring WebFlux**

## Key Responsibilities
1. Centralized Authentication
   Validates incoming JWT tokens using a shared secret key.

Automatically rejects requests with expired or malformed tokens before they reach internal services.

2. User Context Propagation (Header Enrichment)
   After successful validation, the Gateway extracts claims from the JWT and injects them into the HTTP headers for downstream services:

X-User-Id: The unique database ID of the user.

X-User-Email: The user's email address.

X-User-Role: The user's security role (e.g., USER, MANAGER).

3. Role-Based Access Control 
   Implements path-based security checks in the AuthenticationFilter.

Example: Restricts access to /booking/workspace/create strictly to users with the MANAGER role.

4. Dynamic Routing
   Routes traffic to the appropriate microservices based on URL patterns:

* /users/** → user-service 

* /booking/** → booking-service

## Security Configuration
The Gateway uses a custom AuthenticationFilter to intercept every request.

Validation Logic:

Check for Authorization header with Bearer prefix.

Verify JWT signature and expiration date.

Extract user identity and role.

Perform path-level authority check (e.g., Manager-only routes).

## Environment Variables
Required for the Gateway to function correctly:

JWT_SECRET — Must match the secret used by user-service for signing.

USER_SERVICE_URL — Address of the user microservice.

BOOKING_SERVICE_URL — Address of the booking microservice.