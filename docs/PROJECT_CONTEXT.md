# LogiSense - Project Context

## What It Is
A shipment management and tracking backend for small and mid-size logistics companies. It replaces Excel sheets, WhatsApp messages, and phone calls used to run daily deliveries.

## Problems Solved
- **Orders scattered in Excel:** Missing delivery records and fragmented order intake.
- **Vehicles assigned by phone:** Leads to double bookings, scheduling conflicts, and idle vehicles.
- **Customer uncertainty:** Customers constantly calling dispatchers to ask where a parcel is.
- **Unnoticed delays:** Delivery delays discovered only after customer complaints arrive.
- **No visible performance metrics:** Lack of data on delivery times, fleet utilization, and on-time rates.

## Goal
A resume project, fully finished and deployed. It is **NOT** intended for sale. Finishing a small, high-quality version beats a half-built large one.

## Users and Roles
- **ADMIN:** Full system access, management of dispatcher and driver accounts.
- **DISPATCHER:** Fleet management, manual and automated shipment assignment, monitoring.
- **DRIVER:** View assigned shipments and update status via action buttons.
- **CUSTOMER:** Self-registration, order placement, and tracking own orders.
- **Public:** Tracking by tracking number requires no login or authentication.

## Layered Scope

### Layer 1 (MVP)
- Authentication and role-based access control (RBAC).
- Fleet management (vehicles and drivers).
- Customer order creation and management.
- Shipments with manual assignment to vehicles and drivers.
- Driver status button updates (state progression).
- Public tracking endpoint by tracking number.
- Scheduled alerts for overdue shipments.
- KPI dashboard endpoint.
- Auto-dispatch (smallest available vehicle that fits order weight plus a free driver).
- Unit and integration tests.
- Docker and Docker Compose configuration.
- CI with GitHub Actions.
- Cloud deployment and project README.

### Layer 2
- Redis caching for high-frequency queries.
- Email notifications for delivery milestones.
- WebSocket live tracking and a GPS simulator script.

### Layer 3
- Python FastAPI ETA prediction service with graceful fallback.
- Simple frontend user interface.

### Explicitly Out of Scope
- Billing and payment processing
- Multi-tenancy
- Complex route optimization algorithms
- Real hardware GPS integration
- Warehouse inventory management

## Working Rules for Every AI Session
1. **Read all 5 docs** in `docs/` before doing anything.
2. **Do ONE task only** per session.
3. **Inspect existing code before changing it.** Never recreate existing functionality. Never overwrite working code without understanding its dependencies.
4. **Run the tests (`mvn test`)** and show the real output. Never claim completion without verification.
5. **Update `TASK_STATUS.md`** (and other docs if decisions changed) at the end of every task.
6. **Do not silently skip a requirement.** If something cannot be done, say so.
7. **Keep explanations beginner-friendly.** The developer is learning Java and Spring Boot.
8. **Ask before making any architectural decision** not already recorded in `TECHNICAL_DECISIONS.md`.
