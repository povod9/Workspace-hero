## Workspace Hero — Microservices Ecosystem
Workspace Hero is a distributed platform designed to manage office workspaces, desks, and meeting rooms. It features a modern microservices architecture with centralized authentication, automated scheduling, and inter-service communication.

## System Architecture
* **The project is built using a Monorepo structure, managed by a parent Maven project.**

* **API Gateway: The entry point. Handles JWT validation, role-based access control (RBAC), and request routing.**

* **User Service: Manages user profiles, authentication (JWT generation), and financial balances.**

* **Booking Service: Handles workspace availability, reservations, and automated status management.**


## Features & Logic
 Centralized Security

* **JWT Authentication: Users log in via User-Service. All subsequent requests pass through the Gateway.**

* **Header Propagation: The Gateway extracts user identity and injects it into X-User-Id and X-User-Role headers for internal services.**

* **RBAC: Specific actions (like creating workspaces) are restricted to the MANAGER role at the Gateway level.**

## Automated Booking Lifecycle
* **Dynamic Status: Workspaces automatically switch to BUSY upon a successful reservation.**

* **Booking Finalizer: A background scheduler in the Booking-Service monitors expired bookings and resets workspaces to FREE.**

## Financial Integration
Atomic Transactions: The Booking-Service communicates with the User-Service via Feign Clients to verify and deduct funds before confirming a reservation.