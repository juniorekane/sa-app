# SA-App (Sentiment Analysis Application)

SA-App is a multi-module Spring Boot project with:

- `sa-app-api`: REST backend for clients and emotions
- `sa-app-frontend`: Thymeleaf UI to test all backend features end-to-end

Emotion labels are not manually selected anymore. They are resolved by an external sentiment API and stored with a confidence score.

## Current Scope

### Backend (`sa-app-api`)

- Create, list, and search clients
- Create, list, and delete emotions
- External sentiment lookup (label + score) during emotion creation
- JSON error handling (`409`, `400`, `502`)
- OpenAPI/Swagger endpoints

### Frontend (`sa-app-frontend`)

- Entry page at `http://localhost:8081/ui/client`
- UI flows for:
  - create client
  - list all clients
  - find single client by ID
  - create emotion (text + client email)
  - list all emotions
  - delete emotion by ID
- Dedicated error page for backend/API failures

## Tech Stack

- Java 21
- Spring Boot 4.0.1
- Spring MVC + Thymeleaf
- Spring Data JPA
- MariaDB (local profile)
- Lombok
- SpringDoc OpenAPI
- Maven (multi-module)
- GitLab CI/CD + Jib

## Project Structure

```text
sa-app/
├── pom.xml
├── sa-app-api/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/jekdev/saappapi/
│       │   ├── base/
│       │   ├── controller/
│       │   ├── dto/
│       │   ├── entities/
│       │   ├── errorhandling/
│       │   ├── mapper/
│       │   ├── repositories/
│       │   ├── service/
│       │   └── utils/
│       └── resources/
│           ├── application.properties
│           ├── application-local.properties
│           └── docker-compose.yml
├── sa-app-frontend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/jekdev/saappfrontend/
│       │   ├── config/
│       │   ├── controller/
│       │   ├── dto/
│       │   ├── errorhandling/
│       │   └── service/
│       └── resources/
│           ├── application.properties
│           └── templates/
└── pipeline/
    └── jobs/
```

## Configuration

### Backend properties (`sa-app-api`)

| Property | Description | Default |
|---|---|---|
| `server.servlet.context-path` | API base path | `/api` |
| `springdoc.api-docs.path` | OpenAPI JSON path | `/api-docs` |
| `sentiment.api.base-url` | External sentiment base URL | `https://router.huggingface.co/hf-inference` |
| `sentiment.api.model-path` | Provider model path | `/models/distilbert/distilbert-base-uncased-finetuned-sst-2-english` |
| `sentiment.api.token` | Provider token property | `${SENTIMENT_API_TOKEN:}` |

### Backend profile for DB (`local`)

`application-local.properties` contains:

- `spring.datasource.url=jdbc:mariadb://localhost:3307/spring_db`
- `spring.datasource.username=spring`
- `spring.datasource.password=spring`

Without the `local` profile (or another datasource config), the API does not start because no datasource URL is defined.

### Frontend backend target

Frontend calls backend via `RestClient` base URL:

- `http://localhost:8080`

If your backend runs on another host/port, adjust:

- `sa-app-frontend/src/main/java/com/jekdev/saappfrontend/config/ApiClientConfig.java`

## Local Run

### 1) Start MariaDB (required for the `local` profile run command below)

```bash
docker compose -f sa-app-api/src/main/resources/docker-compose.yml up -d
```

Provided services:

- MariaDB on `localhost:3307`
- Adminer on `http://localhost:8180`

### 2) Set sentiment API token

Linux/macOS:

```bash
export SENTIMENT_API_TOKEN='hf_your_token'
```

Windows (PowerShell):

```powershell
$env:SENTIMENT_API_TOKEN='hf_your_token'
```

IntelliJ:

- Open Run Configuration for `SaAppApiApplication`
- Add environment variable `SENTIMENT_API_TOKEN=hf_your_token`
- Add VM option `-Dspring.profiles.active=local` (or set active profile in IDE)

### 3) Run backend

```bash
mvn -pl sa-app-api spring-boot:run -Dspring-boot.run.profiles=local
```

### 4) Run frontend (second terminal)

```bash
mvn -pl sa-app-frontend spring-boot:run
```

### 5) Open UI

- Frontend entry page: `http://localhost:8081/ui/client`
- Swagger UI: `http://localhost:8080/api/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/api/api-docs`

## Backend API Endpoints

All backend endpoints are under `/api` (context path).

### Client endpoints

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/client/create` | Create a client |
| `GET` | `/api/client/find_all` | List all clients |
| `GET` | `/api/client/search/{id}` | Get one client by ID |

Create client payload:

```json
{
  "email": "client@example.com"
}
```

### Emotion endpoints

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/emotions/create` | Create emotion and resolve sentiment externally |
| `GET` | `/api/emotions/all` | List all emotions |
| `DELETE` | `/api/emotions/delete/{id}` | Delete one emotion |

Create emotion payload:

```json
{
  "text": "I really like this product.",
  "client": {
    "email": "client@example.com"
  }
}
```

Note: `type` is no longer a required input field. The backend fills `type` and `score` from the sentiment provider response.

## Frontend Routes

| Method | Path | Description |
|---|---|---|
| `GET` | `/ui/client` | Entry page |
| `POST` | `/ui/new/client` | Create client |
| `GET` | `/ui/client/all` | Show all clients |
| `GET` | `/ui/client/find?id={id}` | Show one client |
| `GET` | `/ui/emotions` | Open create-emotion page |
| `POST` | `/ui/emotions/create` | Submit emotion creation |
| `GET` | `/ui/emotions/all` | Show all emotions |
| `POST` | `/ui/emotions/delete` | Delete emotion by ID |

## CI/CD Overview

Pipeline stages (`.gitlab-ci.yml`):

1. `prepare`
2. `build`
3. `test`
4. `analyze`
5. `security`
6. `package`
7. `deploy`
8. `release`

Job definitions are located in:

- `pipeline/jobs/maven-build.gitlab-ci.yml`
- `pipeline/jobs/test.gitlab-ci.yml`
- `pipeline/jobs/analyze.gitlab-ci.yml`
- `pipeline/jobs/security.gitlab-ci.yml`
- `pipeline/jobs/package.gitlab-ci.yml`
- `pipeline/jobs/deploy.gitlab-ci.yml`

Container images are built with Jib in package jobs. Registry reachability depends on runner network access.

## Troubleshooting

- `502 Bad Gateway` with message about missing token:
  - Set `SENTIMENT_API_TOKEN` in your run environment.
- `502 Bad Gateway` with provider `404 Not Found`:
  - Verify `sentiment.api.base-url` and `sentiment.api.model-path`.
- Jib `Network is unreachable` in CI:
  - Usually a runner-to-registry connectivity issue, not a Java build failure.

## License

This project is proprietary. All rights reserved.
