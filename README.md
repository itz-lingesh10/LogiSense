# LogiSense

Logistics management platform built with Spring Boot (work in progress)

## Status

Phase 1: foundation in progress

## Run Locally

> **Prerequisites:** Docker Desktop must be running. Automated tests use Testcontainers to spin up an ephemeral PostgreSQL instance, and local application execution requires the database container.

1. **Configure Environment:**
   The `.env` file must exist for database credentials and application startup. Copy the example file:
   ```powershell
   Copy-Item .env.example .env
   ```

2. **Run Tests:**
   Execute all unit and integration tests (requires Docker Desktop running for Testcontainers):
   ```powershell
   .\mvnw.cmd test
   ```

3. **Start Database:**
   Start PostgreSQL 17 using Docker Compose (must be started before running the app):
   ```powershell
   docker-compose up -d
   ```

4. **Run Application:**
   Start the Spring Boot backend using the Maven wrapper:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

5. **Verify Health:**
   Check the application status and database connectivity:
   - Endpoint: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
   - PowerShell probe:
     ```powershell
     Invoke-RestMethod http://localhost:8080/actuator/health
     ```
