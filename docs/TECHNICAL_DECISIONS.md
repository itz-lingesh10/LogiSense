# LogiSense - Technical Decisions

## Decisions Table

| Decision | Choice | Reason |
| :--- | :--- | :--- |
| **Architecture** | Modular monolith | Simplest design that is realistic for a solo beginner project. |
| **Language and build** | Java 21 as compile target (`release 21`), Spring Boot 3.x, Maven | Modern LTS baseline with widespread community and library support. |
| **Database** | PostgreSQL via Docker Compose, with Flyway migrations | Reliable relational database with controlled schema evolution (no Hibernate auto-DDL). |
| **Authentication** | JWT with Spring Security, roles `ADMIN`, `DISPATCHER`, `DRIVER`, `CUSTOMER` | Stateless authentication appropriate for REST APIs with clear role-based access control. |
| **Driver model** | Driver is a `User` with role `DRIVER` plus a linked `Driver` profile | Clean separation of authentication/credentials from domain fleet attributes. |
| **Driver updates** | Driver taps status buttons (API calls); no GPS in Layer 1 | Keeps the MVP workflow simple and reliable without requiring GPS hardware simulation. |
| **Assignment** | Manual first; auto-dispatch added in Phase 8 reusing the same service; rule = smallest available vehicle whose capacity fits the order weight, plus any available driver | Progressive development complexity; solidifies assignment rules before automating them. |
| **Customers** | `CUSTOMER` role with self-registration; tracking by number is public | Seamless onboarding for customers and zero-friction tracking visibility for recipients. |
| **API design** | DTOs separate from entities, global exception handler, Swagger via springdoc | Strict data contract isolation, unified error handling, and interactive documentation. |
| **Testing** | JUnit 5 and Mockito for services, Testcontainers for integration tests | Comprehensive unit isolation and production-like PostgreSQL testing during integration. |
| **Frontend** | None in Layer 1; tested via Swagger UI | Keeps complete focus on backend business logic and API correctness in Layer 1. |

## Environment
- **Operating System:** Windows 11
- **Shell:** Windows PowerShell 5.1
- **Build & Runtime:** Maven 3.9.16 (JDK 24 installed on machine, project targets Java 21 bytecode)
- **Docker Tooling:** Use `docker-compose` with a hyphen because the `docker compose` plugin is not linked on this machine
- **Version Control:** Git identity already configured in the repository

## Open Questions
- Deployment host not chosen yet (will evaluate free and low-cost options during Phase 9).
