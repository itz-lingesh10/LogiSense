# LogiSense - Technical Decisions

## Decisions Table

| Decision | Choice | Reason |
| :--- | :--- | :--- |
| **Architecture** | Modular monolith | Simplest design that is realistic for a solo beginner project. |
| **Language and build** | Java 21 as compile target (`release 21`), Spring Boot 4.1.x, Maven | 3.5 no longer receives free security patches and 4.0 loses free support on 31 Dec 2026; 4.1 is the supported line. |
| **Spring Boot 4 Conventions** | Verify 4.1 artifact names and imports against official docs; all tasks must pass `mvn test` | AI-generated code often assumes Spring Boot 3.x conventions (e.g. renamed starters like `spring-boot-starter-webmvc` and test modularization). |
| **Database** | PostgreSQL via Docker Compose, with Flyway migrations | Reliable relational database with controlled schema evolution (no Hibernate auto-DDL). |
| **Authentication** | JWT with Spring Security, roles `ADMIN`, `DISPATCHER`, `DRIVER`, `CUSTOMER` | Stateless authentication appropriate for REST APIs with clear role-based access control. |
| **Driver model** | Driver is a `User` with role `DRIVER` plus a linked `Driver` profile | Clean separation of authentication/credentials from domain fleet attributes. |
| **Driver updates** | Driver taps status buttons (API calls); no GPS in Layer 1 | Keeps the MVP workflow simple and reliable without requiring GPS hardware simulation. |
| **Assignment** | Manual first; auto-dispatch added in Phase 8 reusing the same service; rule = smallest available vehicle whose capacity fits the order weight, plus any available driver | Progressive development complexity; solidifies assignment rules before automating them. |
| **Customers** | `CUSTOMER` role with self-registration; tracking by number is public | Seamless onboarding for customers and zero-friction tracking visibility for recipients. |
| **API design** | DTOs separate from entities, global exception handler, Swagger via springdoc | Strict data contract isolation, unified error handling, and interactive documentation. |
| **Testing** | JUnit 5 and Mockito for services, Testcontainers for integration tests | Comprehensive unit isolation and production-like PostgreSQL testing during integration. |
| **Frontend** | None in Layer 1; tested via Swagger UI | Keeps complete focus on backend business logic and API correctness in Layer 1. |
| **User Roles** | One role per user stored as a string with a `CHECK` constraint (`ADMIN`, `DISPATCHER`, `DRIVER`, `CUSTOMER`) | Simple, strict validation directly in the database without complex join tables. |
| **Primary Keys** | `BIGINT GENERATED ALWAYS AS IDENTITY` | 64-bit auto-incrementing safety preventing integer overflow without manual sequence management. |
| **Email Uniqueness** | Emails stored as given with a case-insensitive unique index (`lower(email)`) | Prevents duplicate accounts with differing letter case; service layer normalizes casing in Task 2.1. |
| **Schema Evolution** | Flyway-only migrations with `spring.jpa.hibernate.ddl-auto=validate` | Reproducible, version-controlled schema evolution; Hibernate validates schema against entities. |
| **Environment Config** | `.env` loaded through `spring.config.import=optional:file:.env[.properties]` | Keeps credentials out of source control while natively loading local secrets. |
| **JPA Open-in-View** | `spring.jpa.open-in-view=false` | Closes DB sessions at service boundaries, preventing lazy loading queries in the web/controller tier. |
| **Database Testing** | Testcontainers PostgreSQL 17 for all DB-backed tests via `@ServiceConnection` | True integration parity in temporary ephemeral containers; tests never touch the dev database. |
| **JVM Timezone** | UTC forced across runtime, Maven Surefire, and Testcontainers (`user.timezone=UTC`) | Guarantees consistent ISO-8601 UTC timestamp handling across PostgreSQL 17 TIMESTAMPTZ columns and tests. |
| **Error Response Format** | Unified `ErrorResponse` record (timestamp, status, error, message, path, fieldErrors) | Consistent error structure for all API responses; internal implementation details and stack traces are shielded on 500. |
| **Exception Handling Strategy** | `@RestControllerAdvice` in `common.exception` package | Maps custom business exceptions (`ResourceNotFoundException` -> 404, `ConflictException` -> 409, `BusinessRuleException` -> 422) and Spring framework exceptions. |
| **OpenAPI / Swagger** | `springdoc-openapi-starter-webmvc-ui` (version `3.2.1` for Spring Boot 4.x) | Interactive Swagger UI available at `/swagger-ui/index.html` and OpenAPI specification at `/v3/api-docs`. |

## Environment
- **Operating System:** Windows 11
- **Shell:** Windows PowerShell 5.1
- **Build & Runtime:** Maven 3.9.16 (JDK 24 installed on machine, project targets Java 21 bytecode)
- **Docker Tooling:** Use `docker-compose` with a hyphen because the `docker compose` plugin is not linked on this machine
- **Version Control:** Git identity already configured in the repository

## Open Questions
- Deployment host not chosen yet (will evaluate free and low-cost options during Phase 9).
