# LogiSense - Development Roadmap

## Phase 0: Setup
- **0.1 Environment check and repo:** OS detection, required tool verification, and base repository skeleton. *(Done)*
- **0.2 Continuity docs:** Create project memory and guideline documents.

## Phase 1: Foundation
- **1.1 Spring Boot skeleton:** Initialize Spring Boot application, configure Docker Compose for PostgreSQL, implement health check endpoint.
- **1.2 Flyway and first migration:** Configure Flyway migration baseline and create initial schema migration for `users`.
- **1.3 Common utilities:** Global exception handler, unified error response structure, and OpenAPI / Swagger documentation via springdoc.

## Phase 2: Auth and Roles
- **2.1 User management:** User entity, repository, and registration / login endpoints with secure password hashing.
- **2.2 JWT security:** JWT generation, token validation, and Spring Security authentication filter.
- **2.3 RBAC and security tests:** Role-based access rules (ADMIN, DISPATCHER, DRIVER, CUSTOMER), admin endpoints to create dispatcher/driver accounts, security test suite.

## Phase 3: Fleet
- **3.1 Vehicles CRUD:** Vehicle entity, repository, service, and management endpoints (capacity, status).
- **3.2 Driver profiles CRUD:** Driver profile entity linked to DRIVER user account, status management, and REST endpoints.

## Phase 4: Orders
- **4.1 Order entity and migration:** Database schema migration and JPA entity for customer orders.
- **4.2 Order service and controller:** Endpoints for order placement; customer views own orders, dispatcher views all orders, with pagination.
- **4.3 Order status rules and tests:** State validation rules and test coverage.

## Phase 5: Shipments and Manual Assignment
- **5.1 Shipment and tracking-event entities:** Schema migration and domain models for shipments and tracking audit logs.
- **5.2 Assignment service:** Manual assignment logic validating vehicle capacity and driver availability, tracking number generation within a single database transaction.
- **5.3 Driver status updates:** Driver status progression state machine (`PICKED_UP`, `IN_TRANSIT`, `DELIVERED`) recording tracking events.
- **5.4 Delivery completion:** Freeing vehicle and driver back to `AVAILABLE` upon successful delivery.

## Phase 6: Public Tracking
- **6.1 Public tracking endpoint:** `GET /api/track/{trackingNumber}` allowing unauthenticated tracking queries.

## Phase 7: Alerts and Dashboard
- **7.1 Overdue alerts:** Alert entity and scheduled background job checking for overdue shipments.
- **7.2 KPI dashboard:** Analytics endpoint aggregating key logistics metrics (totals, delivered, delayed, on-time percentage).

## Phase 8: Auto-Dispatch
- **8.1 Auto-dispatch service:** Matching algorithm selecting the smallest available vehicle that fits order weight plus a free driver, reusing Phase 5 assignment logic.
- **8.2 Trigger on order creation:** Attempt automatic assignment upon new order submission.
- **8.3 Scheduled retry and release re-run:** Retry unassigned orders on a timer and immediately whenever a vehicle/driver is freed.
- **8.4 Concurrency test:** Concurrency tests to prevent double-booking under simultaneous dispatch requests.

## Phase 9: Quality and Deployment
- **9.1 Test gaps and coverage:** Unit and integration test suite verification.
- **9.2 Containerization:** Production `Dockerfile` and multi-container `docker-compose.yml`.
- **9.3 CI Pipeline:** Automated build and test workflows using GitHub Actions.
- **9.4 Deployment:** Deploy application to a free hosting platform (verifying current available options).
- **9.5 Documentation:** Comprehensive production README.

---

## Layer 2 (Detail Later)
- **10 Redis caching:** Cache frequently queried tracking lookups and KPI statistics.
- **11 Email notifications:** Automated notifications to customers on shipment status transitions.
- **12 Live tracking:** WebSocket-based real-time tracking stream and GPS coordinates simulator script.

## Layer 3 (Detail Later)
- **13 FastAPI ETA model:** Python microservice providing machine-learning or heuristic ETA predictions.
- **14 Spring Boot integration:** Client integration with the Python ETA service including fallback logic.
- **15 Frontend:** Lightweight web UI for customer order submission, dispatcher dashboard, and driver action screen.
