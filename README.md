# SA-App (Sentiment Analysis Application)

This application is a sentiment analysis application, which gives users the possibility to write some text.
The given text will be analyzed and classified as a sentiment such as bad, happy or neutral.

## Table of Contents

1. [Features](#features)
2. [Tech Stack](#tech-stack)
3. [Structure of the Project](#structure-of-the-project)
4. [Getting Started](#getting-started)
    1. [Requirements](#requirements)
    2. [Installation](#installation)
    3. [Configuration](#configuration)
5. [Docker](#docker)
6. [API Documentation](#api-documentation)
7. [CI/CD](#cicd)
8. [Architecture](#architecture)
9. [Contributing](#contributing)
10. [License](#license)

## Features

- Client management (create, search by ID)
- Emotion tracking with three sentiment types: HAPPY, MIDDLE, BAD
- RESTful API with JSON responses
- Swagger/OpenAPI documentation
- Input validation
- Global exception handling

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.1**
- **MariaDB** - Database for persistent storage
- **H2** - In-memory database for testing
- **Lombok** - Reduces boilerplate code (constructors, getters, setters)
- **Docker** - Runtime environment for database, frontend, and backend API
- **Thymeleaf** - Template engine for the frontend
- **SpringDoc OpenAPI** - API documentation
- **GitLab CI/CD** - Pipeline for merge requests and deployment

## Structure of the Project

This is a multi-module Maven project:

```
sa-app/
├── pom.xml                    # Parent POM with dependency management
├── sa-app-api/                # Backend REST API module
│   ├── pom.xml
│   └── src/main/java/com/jekdev/saappapi/
│       ├── controller/        # REST controllers
│       ├── service/           # Business logic
│       ├── repositories/      # Data access layer
│       ├── entities/          # JPA entities
│       ├── dto/               # Data transfer objects
│       ├── mapper/            # Entity-DTO mappers
│       ├── errorhandling/     # Exception handlers
│       └── base/              # Enums and base classes
├── sa-app-frontend/           # Frontend module (Thymeleaf)
│   ├── pom.xml
│   └── src/main/resources/
└── pipeline/                  # GitLab CI/CD configuration
```

## Getting Started

### Requirements

- Java 21 or higher
- Maven 3.9+
- Docker and Docker Compose (for database)

### Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd sa-app
   ```

2. Start the database using Docker:
   ```bash
   docker compose -f sa-app-api/src/main/resources/docker-compose.yml up -d
   ```

3. Build the project:
   ```bash
   ./mvnw clean install
   ```

4. Run the API:
   ```bash
   ./mvnw -pl sa-app-api spring-boot:run
   ```

### Configuration

The application can be configured via `application.properties`:

| Property | Description | Default |
|----------|-------------|---------|
| `spring.application.name` | Application name | `sa-app-api` |
| `spring.jpa.hibernate.ddl-auto` | Database schema generation | `update` |
| `server.servlet.context-path` | API context path | `/api` |
| `springdoc.api-docs.path` | OpenAPI docs path | `/api-docs` |

Database connection is configured for MariaDB on port `3307`.

## Docker

The project includes a `docker-compose.yml` for local development:

```bash
docker compose -f sa-app-api/src/main/resources/docker-compose.yml up -d
```

Services:
- **MariaDB** - Port 3307 (user: `spring`, password: `spring`, database: `spring_db`)
- **Adminer** - Port 8180 (database management UI)

## API Documentation

Once the application is running, access the API documentation at:
- Swagger UI: `http://localhost:8080/api/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api/api-docs`

### Endpoints

**Client Controller** (`/api/client`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/create` | Create a new client |
| GET | `/search/{id}` | Get client by ID |

**Emotion Controller** (`/api/emotions`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/create` | Create a new emotion |
| GET | `/all` | Get all emotions |
| DELETE | `/delete/{id}` | Delete an emotion |

## CI/CD

The project uses GitLab CI/CD with the following stages:

1. **prepare** - Setup and preparation
2. **test** - Run tests
3. **build** - Compile and package
4. **sast** - Static application security testing
5. **publish** - Publish artifacts

Pipeline configuration is in `.gitlab-ci.yml` and `pipeline/build.gitlab-ci.yml`.

## Architecture

The application follows a layered architecture:

```
┌─────────────────────────────────────┐
│           Controllers               │  ← REST endpoints
├─────────────────────────────────────┤
│            Services                 │  ← Business logic
├─────────────────────────────────────┤
│           Repositories              │  ← Data access (JPA)
├─────────────────────────────────────┤
│            Database                 │  ← MariaDB
└─────────────────────────────────────┘
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Merge Request

## License

This project is proprietary. All rights reserved.
