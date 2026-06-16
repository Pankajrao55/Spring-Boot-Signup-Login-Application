# Spring Boot Auth App 🔐
Login + Signup with Spring Boot, Thymeleaf, MySQL

---

## ✅ Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8+

---

## 🚀 Steps to Run

### Step 1 — MySQL Setup
Open MySQL and run:
```sql
CREATE DATABASE authdb;
```

### Step 2 — Update Password
Open `src/main/resources/application.properties` and change:
```
spring.datasource.username=root
spring.datasource.password=yourpassword   ← apna password daalo
```

### Step 3 — Run the app
```bash
mvn spring-boot:run
```

### Step 4 — Open in browser
```
http://localhost:8080
```

---

## 📁 Project Structure
```
src/main/java/com/auth/
├── AuthApplication.java          ← Entry point
├── config/
│   └── SecurityConfig.java       ← BCrypt + Security rules
├── controller/
│   └── AuthController.java       ← Login/Signup/Dashboard routes
├── model/
│   └── User.java                 ← DB entity
├── repository/
│   └── UserRepository.java       ← DB queries
└── service/
    └── UserService.java          ← Business logic

src/main/resources/
├── templates/
│   ├── login.html                ← Login page
│   ├── signup.html               ← Signup page
│   └── dashboard.html            ← After login
└── application.properties        ← DB config
```

---

## 🔗 URLs
| URL         | Method | Description        |
|-------------|--------|--------------------|
| /           | GET    | Redirects to login |
| /login      | GET    | Login form         |
| /login      | POST   | Submit login       |
| /signup     | GET    | Signup form        |
| /signup     | POST   | Submit signup      |
| /dashboard  | GET    | After login        |
| /logout     | GET    | Logout             |

---

## 🔒 Security
- Passwords stored as **BCrypt hash** (never plain text)
- Session-based authentication
- Duplicate username/email validation
