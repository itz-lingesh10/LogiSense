# LogiSense - Architecture

## Architecture Style
- **Modular Monolith:** A single Spring Boot application running against one PostgreSQL database. No microservices.
- **Inter-module Communication:** Modules interact by directly calling each other's service classes. A controller must never access another module's repository directly.

## Module Layering
Every module follows a strict 3-tier layering pattern:
`Controller -> Service -> Repository`
- **DTOs (Data Transfer Objects):** Completely separated from persistent database entities. Requests and responses use DTOs to avoid leaking domain entities to API consumers.

## Package Structure
- **Base Package:** `com.logisense`
- **Sub-packages per Module:**
  - `com.logisense.auth`
  - `com.logisense.user`
  - `com.logisense.fleet`
  - `com.logisense.order`
  - `com.logisense.shipment`
  - `com.logisense.tracking`
  - `com.logisense.alert`
  - `com.logisense.analytics`
  - `com.logisense.common`
  - `com.logisense.config`

## Module Responsibilities
- **auth:** User registration, login authentication, JWT token generation and validation, role extraction.
- **user:** User account entities and profiles.
- **fleet:** Vehicle management (cargo capacity, status `AVAILABLE`/`BUSY`) and Driver profiles (linked to a user account with role `DRIVER`, status `AVAILABLE`/`BUSY`).
- **order:** Customer order intake and management (pickup address, delivery address, weight, status).
- **shipment:** Assigning orders to vehicles and drivers, generating unique tracking numbers, status workflow progression, tracking event recording, and dispatch coordination.
- **tracking:** Public read-only tracking endpoint looked up by tracking number.
- **alert:** Scheduled background job that detects and flags overdue shipments.
- **analytics:** Aggregate KPI metrics (total orders, delivered count, delayed shipments, on-time delivery percentage).
- **common:** Global exception handler, unified error responses, and shared utility classes.

## Planned Entities
- `users`
- `vehicles`
- `drivers`
- `orders`
- `shipments`
- `tracking_events`
- `alerts`

## Planned Status Values (Proposed)
*(To be confirmed when each specific module is implemented)*
- **Order:** `PENDING`, `ASSIGNED`, `DELIVERED`, `CANCELLED`
- **Shipment:** `ASSIGNED`, `PICKED_UP`, `IN_TRANSIT`, `DELIVERED`
- **Vehicle & Driver:** `AVAILABLE`, `BUSY`

## Core Workflow
1. **Order Creation:** A customer registers and submits an order (status: `PENDING`).
2. **Assignment:** Dispatcher assigns the order (manual in Phase 5, automated in Phase 8) to an available vehicle and driver that fit the required capacity.
3. **Shipment Creation:** A shipment is created with a generated tracking number; the assigned vehicle and driver status changes to `BUSY`.
4. **Delivery Progression:** The driver updates status via action buttons (`PICKED_UP` -> `IN_TRANSIT` -> `DELIVERED`). Each transition is stored as a record in `tracking_events`.
5. **Completion & Release:** When marked `DELIVERED`, the vehicle and driver status returns to `AVAILABLE`, and any waiting pending orders are retried for dispatch.
6. **Public Tracking:** The customer can query the public tracking endpoint at any time using their tracking number.
7. **Overdue Alerts:** A background scheduler periodically flags overdue shipments as alerts.

## Security Architecture
- **Mechanism:** Stateless authentication using JSON Web Tokens (JWT) passed in the `Authorization: Bearer <token>` header.
- **Authorization:** Role-Based Access Control (RBAC) configured per endpoint.
- **Status Codes:**
  - `401 Unauthorized`: Missing, expired, or invalid token.
  - `403 Forbidden`: Authenticated user lacks the required role for the requested endpoint.
