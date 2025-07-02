# SportyShoes Admin Portal

A prototype admin dashboard for **SportyShoes**, a company transitioning from a physical retail presence to an online store.

---

## 🏦 Project Overview

This Spring Boot application provides the **admin side** of the sportyshoes.com e-commerce portal. The goal is to showcase key features to stakeholders for budget approval of full-scale development.

---

## ✅ Features Implemented

### 🔑 Admin Authentication

* Secure admin login
* Password change functionality
* Logout and session management

### 💼 Product Management

* View all products
* Add new product with category selection
* Delete existing products

### 🌎 Category Management

* Seeded "Running" and "Casual" categories

### 📄 Purchase Reports

* View all purchases
* Filter by date and product category

### 👥 User Management

* View all registered users
* Search users by name or email

---

## ⚡ Tech Stack

* **Backend**: Java, Spring Boot, Spring MVC, JPA/Hibernate
* **Database**: MySQL
* **Frontend**: Thymeleaf templates (HTML, minimal CSS)
* **Build Tool**: Maven
* **Version Control**: Git, GitHub

---

## 📅 Data Initialization

Upon application start:

* Default admin (`admin:admin123`) is created
* Two test users (Alice and Bob) are seeded
* Two categories and sample products
* Two purchases associated with users/products

---

## 🔍 Run Locally

```bash
git clone https://github.com/your-username/sportyshoes-admin.git
cd sportyshoes-admin
```

Ensure you have MySQL running and `application.properties` configured with your DB credentials.

Then run:

```bash
./mvnw spring-boot:run
```

Visit: `http://localhost:8080/admin/login`

---

## 📁 Folder Structure Highlights

```
src/
 ├── main/java/com/sportyshoes/
 │   ├── controller/      --> MVC controllers
 │   ├── model/           --> Entity classes
 │   ├── repository/      --> JPA Repositories
 └── resources/templates/   --> Thymeleaf HTML files
```

---

## 🌟 Author

**Milind Jain**
Full Stack Java Developer

---
