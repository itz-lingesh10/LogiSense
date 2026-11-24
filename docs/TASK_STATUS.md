# LogiSense - Task Status

## Current Task
- **Task 2.1: User entity and register/login with password hashing** (TODO)

## Phase Status
- **Phase 0 (Setup):** COMPLETED
- **Phase 1 (Foundation):** COMPLETED
- **Phase 2 (Auth & Users):** NOT STARTED

## Task Table

| Task | Description | Status | Notes |
| :--- | :--- | :---: | :--- |
| **0.1** | Environment check and repo | **DONE** | Repo created, identity set |
| **0.2** | Continuity docs | **DONE** | 5 continuity documents created and committed |
| **1.1** | Spring Boot skeleton, Docker Compose for PostgreSQL, health endpoint | **DONE** | Spring Boot 4.1.2, Postgres 17 compose, Actuator health verified |
| **1.2** | Flyway and first migration (users) | **DONE** | PostgreSQL connection, Flyway V1 migration for users, Testcontainers tests passing |
| **1.3** | Global exception handler, error response, Swagger | **DONE** | Global exception handler with unified ErrorResponse, 3 custom business exceptions, Swagger UI via springdoc 3.2.1, 17 tests passing |
| **2.1** | User entity and register/login with password hashing | **TODO** | |
| **2.2** | JWT generation and filter | **TODO** | |
| **2.3** | Role rules, admin creates dispatcher and driver accounts, security tests | **TODO** | |
| **3.1** | Vehicles CRUD | **TODO** | |
| **3.2** | Driver profiles CRUD | **TODO** | |
| **4.1** | Order entity and migration | **TODO** | |
| **4.2** | Order service and controller (customer sees own orders, dispatcher sees all, pagination) | **TODO** | |
| **4.3** | Order status rules and tests | **TODO** | |
| **5.1** | Shipment and tracking-event entities | **TODO** | |
| **5.2** | Assignment service (capacity and availability checks, tracking number, one transaction) | **TODO** | |
| **5.3** | Driver status updates (state machine, tracking events) | **TODO** | |
| **5.4** | Free vehicle and driver on delivery | **TODO** | |
| **6.1** | Public tracking endpoint `GET /api/track/{trackingNumber}` | **TODO** | |
| **7.1** | Alert entity and scheduled overdue check | **TODO** | |
| **7.2** | KPI endpoint | **TODO** | |
| **8.1** | Dispatch service (smallest fitting vehicle plus free driver, reusing Phase 5 assignment logic) | **TODO** | |
| **8.2** | Trigger on order creation | **TODO** | |
| **8.3** | Scheduled retry and re-run when a vehicle is freed | **TODO** | |
| **8.4** | Concurrency test | **TODO** | |
| **9.1** | Test gaps and coverage | **TODO** | |
| **9.2** | Dockerfile and full Docker Compose | **TODO** | |
| **9.3** | GitHub Actions CI | **TODO** | |
| **9.4** | Deployment to a free host (verify current options first) | **TODO** | |
| **9.5** | README | **TODO** | |
| **Layer 2** | Redis caching, email notifications, WebSocket live tracking and GPS simulator | **TODO** | Detail later |
| **Layer 3** | FastAPI ETA model, Spring Boot integration with fallback, simple frontend | **TODO** | Detail later |

## Known Risks
- Docker Desktop must be running during builds and tests because integration tests use Testcontainers.
- Actuator health currently shows component details (`management.endpoint.health.show-details=always`). Will be restricted in Phase 2.

## Last Updated
2026-11-24
