# 💰 Expense Tracker Backend API

A RESTful Expense Tracker Backend built using **Core Java**, **JDBC**, and **PostgreSQL**. This project provides APIs for managing users, income, expenses, and calculating savings without using any backend framework such as Spring Boot.

---

## 🚀 Features

- 👤 User Management
  - Add User
  - View Users
  - Update User
  - Delete User

- 💵 Income Management
  - Add Income
  - View Income
  - Update Income
  - Delete Income

- 💸 Expense Management
  - Add Expense
  - View Expenses
  - Update Expense
  - Delete Expense

- 📊 Savings Calculation
  - Calculates Total Income
  - Calculates Total Expense
  - Returns Overall Savings

- 🌐 REST API using Java HttpServer
- 🗄 PostgreSQL Database Integration
- 🔗 JDBC Connectivity
- 📦 JSON Request & Response Handling
- ✅ Tested using Postman

---

# 🛠 Tech Stack

- Java 17+
- JDBC
- PostgreSQL
- Java HttpServer
- REST API
- JSON
- Postman

---

# 📂 Project Structure

```
ExpenseTracker
│
├── src
│   ├── database
│   │      DatabaseConnection.java
│   │
│   ├── handler
│   │      UserHandler.java
│   │      IncomeHandler.java
│   │      ExpenseHandler.java
│   │      SavingsHandler.java
│   │
│   ├── manager
│   │      ExpenseManager.java
│   │
│   ├── model
│   │      User.java
│   │      Income.java
│   │      Expense.java
│   │      Savings.java
│   │
│   ├── Server.java
│   └── Main.java
│
├── db.properties
└── README.md
```

---

# 🗄 Database Schema

## Users

| Column | Type |
|---------|------|
| user_id | SERIAL PRIMARY KEY |
| user_name | VARCHAR(100) |

---

## Income

| Column | Type |
|---------|------|
| income_id | SERIAL PRIMARY KEY |
| user_id | INTEGER |
| amount | DOUBLE PRECISION |
| inc_source | VARCHAR(100) |
| income_date | DATE |
| income_time | TIME |

---

## Expense

| Column | Type |
|---------|------|
| expense_id | SERIAL PRIMARY KEY |
| user_id | INTEGER |
| amount_spent | DOUBLE PRECISION |
| category | VARCHAR(100) |
| description | TEXT |
| income_date | DATE |
| income_time | TIME |

---

# ⚙ Configuration

Create a `db.properties` file.

```properties
db.url=jdbc:postgresql://localhost:5432/expense
db.username=postgres
db.password=your_password
```

---

# ▶ Running the Project

### 1. Clone Repository

```bash
git clone https://github.com/yourusername/expense-tracker-backend.git
```

### 2. Open Project

Import the project into IntelliJ IDEA or Eclipse.

### 3. Create PostgreSQL Database

```sql
CREATE DATABASE expense;
```

Run the SQL scripts to create the required tables.

### 4. Configure Database

Update `db.properties`.

### 5. Run Server

Run

```
Server.java
```

Server starts at

```
http://localhost:8080
```

---

# 📡 REST API Endpoints

## User APIs

| Method | Endpoint | Description |
|----------|----------|-------------|
| GET | /users | Get All Users |
| POST | /users | Add User |
| PUT | /users | Update User |
| DELETE | /users | Delete User |

---

## Income APIs

| Method | Endpoint |
|----------|----------|
| GET | /income |
| POST | /income |
| PUT | /income |
| DELETE | /income |

---

## Expense APIs

| Method | Endpoint |
|----------|----------|
| GET | /expense |
| POST | /expense |
| PUT | /expense |
| DELETE | /expense |

---

## Savings API

| Method | Endpoint |
|----------|----------|
| GET | /savings?userId=1 |

Example Response

```json
{
    "totalIncome": 45000,
    "totalExpense": 17000,
    "savings": 28000
}
```

---

# 🧪 Testing

The APIs were tested using **Postman**.

Example:

```
GET http://localhost:8080/users
```

```
POST http://localhost:8080/income
```

```
GET http://localhost:8080/savings?userId=1
```

---

# 📚 Concepts Used

- Object-Oriented Programming (OOP)
- JDBC
- PostgreSQL
- REST APIs
- HTTP Methods (GET, POST, PUT, DELETE)
- JSON Parsing
- Layered Architecture
- PreparedStatement
- ResultSet
- SQL CRUD Operations
- Aggregate SQL Functions (SUM, COALESCE)

---

# 🔮 Future Improvements

- JWT Authentication
- Password Hashing (BCrypt)
- User Login & Registration
- React + TypeScript Frontend
- Docker Support
- Swagger/OpenAPI Documentation
- Unit Testing
- Logging & Exception Handling

---

# 👩‍💻 Author

**Melissa Sequeira**

Backend Developer | Java | Python | PostgreSQL | REST APIs
