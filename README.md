# Kanban API

RESTful API for a Kanban SaaS system built with Java 21, Spring Boot 3, Spring Security (JWT), JPA and PostgreSQL. Clean architecture with multi-tenant data isolation.

## 🚀 Technologies

- **Java 21**
- **Spring Boot 3.4.5**
- **Spring Security** — JWT Authentication (Access + Refresh Token)
- **Spring Data JPA** — Hibernate ORM
- **PostgreSQL 16**
- **Docker & Docker Compose**
- **Lombok**
- **Maven**

---

## 📋 Features

- ✅ User registration and login with JWT
- ✅ Access Token (15 min) + Refresh Token (7 days)
- ✅ Multi-tenant data isolation (users only access their own data)
- ✅ Board CRUD
- ✅ Column CRUD with position ordering
- ✅ Task CRUD with drag & drop support (move between columns)
- ✅ Comments on tasks
- ✅ Audit logging (created, updated, deleted)
- ✅ Global exception handler with standardized error responses
- ✅ Soft delete on all entities
- ✅ Bean Validation on all requests
- ✅ CORS configured for Angular frontend

---

## 🏗️ Architecture

```
com.devdogstudio.kanban_api
├── audit/          → Audit logging
├── config/         → Security and CORS configuration
├── controller/     → REST endpoints
├── dto/
│   ├── request/    → Request DTOs
│   └── response/   → Response DTOs
├── entity/         → JPA entities
├── enums/          → Enumerators
├── exception/      → Custom exceptions and global handler
├── repository/     → JPA repositories
├── security/       → JWT filter and service
└── service/        → Business logic
```

---

## ⚙️ Prerequisites

- Java 21 ([Adoptium](https://adoptium.net))
- Maven 3.9+
- Docker & Docker Compose ([docker.com](https://www.docker.com/get-started))

---

## 🐳 Running the project

### 1. Clone the repository

```bash
git clone https://github.com/marcofavero3/kanban-api.git
cd kanban-api
```

### 2. Start the database

```bash
docker-compose up -d
```

This starts:
- **PostgreSQL** on port `5432`
- **pgAdmin** on port `5050` → `http://localhost:5050`
  - Email: `admin@kanban.com`
  - Password: `admin`

### 3. Run the application

```bash
./mvnw spring-boot:run
```

API available at: `http://localhost:8080`

---

## 🔐 Authentication

All endpoints except `/auth/**` require a Bearer token.

```
Authorization: Bearer <accessToken>
```

### Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | Login and get tokens |
| POST | `/auth/refresh` | Refresh access token |

---

## 📡 API Endpoints

### Boards
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/boards` | List all boards |
| GET | `/boards/{id}` | Get board by ID |
| POST | `/boards` | Create board |
| PUT | `/boards/{id}` | Update board |
| DELETE | `/boards/{id}` | Delete board |

### Columns
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/boards/{boardId}/columns` | List columns |
| GET | `/boards/{boardId}/columns/{id}` | Get column |
| POST | `/boards/{boardId}/columns` | Create column |
| PUT | `/boards/{boardId}/columns/{id}` | Update column |
| DELETE | `/boards/{boardId}/columns/{id}` | Delete column |

### Tasks
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/boards/{boardId}/columns/{columnId}/tasks` | List tasks |
| GET | `/boards/{boardId}/columns/{columnId}/tasks/{id}` | Get task |
| POST | `/boards/{boardId}/columns/{columnId}/tasks` | Create task |
| PUT | `/boards/{boardId}/columns/{columnId}/tasks/{id}` | Update task |
| PATCH | `/boards/{boardId}/tasks/{id}/move` | Move task between columns |
| DELETE | `/boards/{boardId}/columns/{columnId}/tasks/{id}` | Delete task |

### Comments
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/tasks/{taskId}/comments` | List comments |
| POST | `/tasks/{taskId}/comments` | Add comment |
| DELETE | `/tasks/{taskId}/comments/{id}` | Delete comment |

### Audit
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/audit` | List audit logs |

---

## 🛡️ Security

- Passwords hashed with **BCrypt**
- JWT signed with **HMAC-SHA512**
- Ownership validation on every request
- Soft delete — no data is physically removed
- `@SQLRestriction` automatically filters deleted records

---

## 🌱 Git Flow

```
main       → stable, production-ready code
develop    → integration branch
feature/x  → individual features
```

---

## 👨‍💻 Developer

**Marco Antonio Favero Junior**
Full-Stack Developer — Java Spring Boot & Angular
[Dev Dog Studio](https://devdogstudio.com.br)