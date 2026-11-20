# LogiSense

Logistics management platform built with Spring Boot (work in progress)

## Status

Phase 1: foundation in progress

## Run Locally

1. **Configure Environment:**
   Copy the example environment file to `.env`:
   ```powershell
   Copy-Item .env.example .env
   ```

2. **Start Database:**
   Start PostgreSQL 17 using Docker Compose:
   ```powershell
   docker-compose up -d
   ```

3. **Run Application:**
   Start the Spring Boot backend using the Maven wrapper:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

4. **Verify Health:**
   Check the application status:
   - Endpoint: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
   - PowerShell probe:
     ```powershell
     Invoke-RestMethod http://localhost:8080/actuator/health
     ```
