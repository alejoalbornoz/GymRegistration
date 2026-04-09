# 🏋️ Gym Registration API

A REST API for managing gym client registrations, memberships, and user accounts built with Spring Boot and MySQL.

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.2.x**
- **Spring Security + JWT**
- **Spring Data JPA**
- **MySQL**
- **Lombok**
- **Maven**

---

## ⚙️ Configuration

### application.properties

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/gym_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false

# JWT
jwt.secret=your_secret_key
jwt.expiration=86400000

# Scheduler
spring.task.scheduling.pool.size=5
```

---

## 🗄️ Database Setup

Create the database in MySQL:

```sql
CREATE DATABASE gymregistration;
```

Create the admin user directly in the database. The password must be encrypted with BCrypt (use [bcrypt-generator.com](https://bcrypt-generator.com) with 10 rounds):

```sql
INSERT INTO user (email, password, role, client_id)
VALUES (
    'admin@gym.com',
    '$2a$10$your_bcrypt_hashed_password',
    'ADMIN',
    NULL
);
```

---

## 🔐 Authentication

The API uses **JWT (JSON Web Tokens)** for authentication.

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

> **Note:** When a client is registered, a `User` account is automatically created with the client's DNI as the default password.

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
| `ACTIVE` | Currently active (1 month from creation) |
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

## 🚀 Running the Project

### Prerequisites

- Java 17
- Maven
- MySQL

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/your-username/gym-registration-api.git

# 2. Configure application.properties with your MySQL credentials

# 3. Create the database
mysql -u root -p -e "CREATE DATABASE gym_db;"

# 4. Run the application
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

---

## 🐳 Docker (Coming Soon)

Docker Compose support is currently being added. It will include:

- MySQL container
- Spring Boot app container

---

## 📬 Postman

To test the API with Postman efficiently:

1. Create an environment called `GymAPI`
2. Add a **Post-response script** to the Login request:

```javascript
const response = pm.response.json();
pm.environment.set("token", response.token);
```

3. Use `Bearer {{token}}` in the Authorization header of all protected requests

---

## 📄 License

This project is for educational purposes.
