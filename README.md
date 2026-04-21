# 🏋️ Gym Registration API

A REST API for managing gym client registrations, memberships, and user accounts built with Spring Boot and MySQL.

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Security + JWT**
- **Spring Data JPA + Hibernate**
- **MySQL 8.0**
- **Lombok**
- **Swagger / OpenAPI**
- **Docker + Docker Compose**
- **Maven**

---

## ⚙️ Configuration

### application.properties

```properties
# App
spring.application.name=GymRegistration

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/gymregistration?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.open-in-view=false

# JWT
jwt.secret=your_secret_key
jwt.expiration=86400000

# Swagger
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
```

---

## 🚀 Running the Project

### Option 1 - Local

#### Prerequisites
- Java 17
- Maven
- MySQL running locally

#### Steps

```bash
# 1. Clone the repository
git clone https://github.com/alejoalbornoz/GymRegistration.git

# 2. Create the database in MySQL
CREATE DATABASE gymregistration;

# 3. Configure application.properties with your MySQL credentials

# 4. Run the application
cd GymRegistration
./mvnw spring-boot:run
```

---

### Option 2 - Docker

#### Prerequisites
- Docker Desktop

#### Steps

```bash
# 1. Compile the project
cd GymRegistration
mvn clean package -DskipTests

# 2. Go back to root and start containers
cd ..
docker-compose up --build
```

#### Stop containers

```bash
docker-compose down

# Stop and remove volumes (deletes the database)
docker-compose down -v
```

The API will be available at `http://localhost:8080`

---

## 🗄️ Database Setup

After running the app, insert the admin user directly in the database. The password must be encrypted with BCrypt (use [bcrypt-generator.com](https://bcrypt-generator.com) with 10 rounds):

```sql
INSERT INTO user (email, password, role, client_id)
VALUES (
    'admin@gym.com',
    '$2a$10$your_bcrypt_hashed_password',
    'ADMIN',
    NULL
);
```

For Docker, connect to the MySQL container:

```bash
docker exec -it gym_mysql mysql -u root gymregistration
```

---

## 🔐 Authentication

The API uses **JWT (JSON Web Tokens)** for authentication. Tokens expire after **24 hours**.

### Roles

| Role | Description |
|------|-------------|
| `ADMIN` | Full access: manage clients and memberships |
| `CLIENT` | Limited access: view and update own profile only |

### Login

```
POST /api/auth/login
Content-Type: application/json

{
    "email": "admin@gym.com",
    "password": "your_password"
}
```

Response:

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "role": "ADMIN"
}
```

Use the token in every subsequent request:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Logout

```
POST /api/auth/logout
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 📋 API Endpoints

### Auth

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `POST` | `/api/auth/login` | Public | Login and get JWT token |
| `POST` | `/api/auth/logout` | Authenticated | Invalidate JWT token |

### Clients (Admin only)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/clients` | Get all clients |
| `GET` | `/api/clients/{id}` | Get client by ID |
| `GET` | `/api/clients/dni/{dni}` | Get client by DNI |
| `POST` | `/api/clients` | Register new client |
| `PUT` | `/api/clients/{id}` | Update client data |
| `DELETE` | `/api/clients/{id}` | Delete client |

#### Create Client Request Body

```json
{
    "firstName": "Juan",
    "lastName": "Pérez",
    "dni": "12345678",
    "email": "juan@gmail.com",
    "birthDate": "1995-05-15"
}
```

> **Note:** When a client is registered, a `User` account is automatically created with the client's **DNI as the default password**.

### Memberships (Admin only)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/memberships` | Get all memberships |
| `GET` | `/api/memberships/client/{clientId}` | Get active membership by client |
| `POST` | `/api/memberships/client/{clientId}?type=UNLIMITED` | Create membership |
| `PUT` | `/api/memberships/{id}/renew` | Renew expired membership |
| `PUT` | `/api/memberships/{id}/pay` | Mark membership as paid |
| `DELETE` | `/api/memberships/{id}/cancel` | Cancel membership |

#### Membership Types

| Type | Description |
|------|-------------|
| `THREE_TIMES_WEEK` | Access limited to 3 times per week |
| `UNLIMITED` | Unlimited access |

#### Membership Status

| Status | Description |
|--------|-------------|
| `ACTIVE` | Currently active (1 month duration) |
| `EXPIRED` | Automatically expired after 1 month |

### User (Client only)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/user/me` | Get own profile |
| `PUT` | `/api/user/me` | Update own profile |
| `PUT` | `/api/user/me/password` | Change password |
| `DELETE` | `/api/user/me` | Delete own account |

---

## ⏰ Automatic Membership Expiration

A scheduled task runs every day at midnight and automatically sets memberships to `EXPIRED` if their expiration date has passed:

```java
@Scheduled(cron = "0 0 0 * * *")
public void checkExpiredMemberships()
```

---

## 📖 API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 📬 Postman Setup

To test the API with Postman efficiently:

1. Create an environment called `GymAPI`
2. Add a **Post-response script** to the Login request:

```javascript
const response = pm.response.json();
pm.environment.set("token", response.token);
```

3. Use `Bearer {{token}}` in the Authorization header of all protected requests — the token updates automatically after every login.

---

## 🐳 Docker Files

### Dockerfile

```dockerfile
FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/GymRegistration-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} gym_app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gym_app.jar"]
```

### docker-compose.yml

```yaml
services:
  mysql:
    image: mysql:8.0
    container_name: gym_mysql
    restart: always
    environment:
      MYSQL_DATABASE: gymregistration
      MYSQL_ALLOW_EMPTY_PASSWORD: "yes"
    ports:
      - "3307:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - gym_network
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 5

  app:
    build:
      context: ./GymRegistration
      dockerfile: Dockerfile
    container_name: gym_app
    restart: always
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/gymregistration?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: ""
      JWT_SECRET: clave_secreta_muy_larga_para_que_funcione_bien_gymapp
      JWT_EXPIRATION: 86400000
    depends_on:
      mysql:
        condition: service_healthy
    networks:
      - gym_network

volumes:
  mysql_data:

networks:
  gym_network:
    driver: bridge
```

---

## 📄 License

This project is for educational purposes.
