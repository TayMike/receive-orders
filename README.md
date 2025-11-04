# Fase4

Postgraduate course completion work.

## Overview

This project is a Spring Boot application developed as part of a postgraduate course completion work. It uses Clean Architecture principles and connects to a MySQL database for production and development environments, with an in-memory H2 database for testing.

## Prerequisites

- **Docker** and **Docker Compose** installed.

- Java 21 (for local development or testing).

- Maven (for building the JAR and running tests).

- A `target/*.jar` file built from the project (e.g., `mvn clean package`).

- A `.env` file in the project root with the following variables:

  ```env
  # Production
  DB_URL="jdbc:mysql://mysql:3306/mydatabase"
  DB_USERNAME="myuser"
  DB_PASSWORD="secret"
  EMAIL_USERNAME="YOUR_EMAIL_USERNAME"
  EMAIL_PASSWORD="YOUR_EMAIL_PASSWORD"
  
  # Development
  DEV_DB_URL="jdbc:mysql://mysql:3306/mydatabase"
  DEV_DB_USERNAME="myuser"
  DEV_DB_PASSWORD="secret"
  
  # Test
  TEST_DB_URL="jdbc:h2:mem:testdb"
  TEST_DB_USERNAME="sa"
  TEST_DB_PASSWORD=""
  ```

- Ensure `.env` is added to `.gitignore` to avoid committing sensitive data.

## Running the Application with Docker Compose

### Build the Docker Images

Build the images for the application:

```bash
docker-compose build
```

### Run in Production Mode

Start the application with the `prod` profile (uses `application-prod.yml`):
Ensure that src/main/resources/application.yml has configured with `prod` profile settings.:

```bash
docker-compose up --build app
```

- Runs the MySQL database and the application on port `8080`.
- To run in the background: `docker-compose up -d --build app`.

Swagger Doc
- http://localhost:8080/swagger-ui/index.html

### Run in Development Mode

Start the application with the `dev` profile (uses `application-dev.yml`):
Ensure that src/main/resources/application.yml has configured with `dev` profile settings.:

```bash
docker-compose up --build app-dev
```

- Runs the MySQL database and the application on port `8081`.
- To run in the background: `docker-compose up -d --build app-dev`.

Swagger Doc
- http://localhost:8081/swagger-ui/index.html

### Stop and Remove Containers

Stop and remove containers (preserves MySQL data):

```bash
docker-compose down
```

To also remove the MySQL data volume (resets the database):

```bash
docker-compose down -v
```

### View Logs

Check logs for production:

```bash
docker-compose logs -f app
```

Check logs for development:

```bash
docker-compose logs -f app-dev
```

### Run Performance Tests

Run the application with the `dev` profile to execute Gatling performance tests
Ensure that src/main/resources/application.yml has configured with `dev` profile settings.:

```bash
mvn spring-boot:run
mvn clean gatling:test -Pperformance
```

### Run Unit Tests

```bash
mvn test
```

## Docker Compose Configuration

The `docker-compose.yml` defines:

- `mysql`: MySQL database with `mydatabase` (used by `prod` and `dev` profiles).
- `redis`: Redis service for caching.
- `kafka`: Kafka service for messaging.
- `zookeeper`: Zookeeper service for Kafka.
- `app`: Production service on port `8080` (uses `SPRING_PROFILES_ACTIVE=prod`).
- `app-dev`: Development service on port `8081` (uses `SPRING_PROFILES_ACTIVE=dev`).

## Notes

- Ensure ports `8080`, `8081`, `3306`, `6379`, `9092` and `2181` are free on your host.
- In production, remove the `3306:3306` port mapping from `mysql` in `docker-compose.yml` for security.