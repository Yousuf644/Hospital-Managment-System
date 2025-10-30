# Hospital Management System

![Java](https://img.shields.io/badge/Java-17-orange) ![MySQL](https://img.shields.io/badge/MySQL-8.0-blue) ![Build](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-darkgreen) ![License](https://img.shields.io/badge/License-MIT-lightgrey)

> A clean, console-based **Hospital Management System** written in **Java** using **JDBC / MySQL**. Manage patients and doctors — add, view, update, delete, search and count — with neat boxed table outputs.

---

## Table of contents
1. [Features](#features)  
2. [Prerequisites](#prerequisites)  
3. [Quick setup — step by step (recommended)](#quick-setup)  
4. [Database schema (SQL)](#database-schema-sql)  
5. [Run the app (detailed)](#run-the-app-detailed)  
6. [Common troubleshooting](#common-troubleshooting)  
7. [Useful commands (git + run)](#useful-commands-git--run)  
8. [Project structure](#project-structure)  
9. [Contributing](#contributing)  
10. [License & Author](#license--author)

---

## Features
- ✅ Add / View / Update / Delete Patients  
- ✅ Search patients by name (supports partial match)  
- ✅ Count total patients  
- ✅ Add / View / Update / Delete Doctors  
- ✅ Search doctors by name (partial match)  
- ✅ Count total doctors  
- ✅ Nicely formatted boxed output for lists and searches

---

## Prerequisites
- Java JDK 17+ (JDK 24 also works if you use that locally)  
- MySQL Server (local or remote)  
- MySQL Connector/J (JAR) — add to project dependencies  
- IntelliJ IDEA (recommended) or any Java IDE  
- Git (for version control)

---

## Quick setup — step by step

Follow these steps one by one to set up and run the project:

1. **Clone or copy the project**
   ```bash
   git clone https://github.com/Yousuf644/Hospital-Managment-System.git
   cd Hospital-Managment-System
   ```

2. **Install MySQL and create the database**
   - Launch MySQL client (Workbench or CLI) and run the SQL from the next section.

3. **Add the JDBC driver**
   - Download `mysql-connector-j` (if not already) and add the JAR to the project:
     - In IntelliJ: *File → Project Structure → Modules → Dependencies → + → JARs or directories* → select the connector jar.

4. **Configure DB credentials**
   ```java
   static final String JDBC_URL = "jdbc:mysql://localhost:3306/hospital";
   static final String USER = "root";
   static final String PASSWORD = "mohd@"; // change to your MySQL password
   ```

5. **Build & run**
   - In IntelliJ: *Run → Run 'HospitalManagementSystem'*  
   - Or via terminal (compiled `out` classes):  
     ```bash
     javac -cp ".;path\to\mysql-connector-j.jar" src\HospitalManagementSystem\*.java
     java -cp ".;path\to\mysql-connector-j.jar;out\production\<module>" HospitalManagementSystem.HospitalManagementSystem
     ```

---

## Database schema (SQL)

```sql
CREATE DATABASE IF NOT EXISTS hospital;
USE hospital;

CREATE TABLE IF NOT EXISTS patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    gender VARCHAR(10),
    disease VARCHAR(100),
    contact VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100),
    experience INT
);
```

---

## Common troubleshooting

**1️⃣ `Access denied for user 'root'@'localhost'`**  
→ Confirm your MySQL credentials and plugin settings.

**2️⃣ `Unknown database 'hospital'`**  
→ Run the provided `CREATE DATABASE` commands.

**3️⃣ `git not recognized` on Windows**  
→ Install Git and ensure its path (e.g., `C:\Program Files\Git\bin`) is in system environment variables.

---

## Project structure
```
Hospital-Management-System/
├─ src/
│  └─ HospitalManagementSystem/
│     ├─ HospitalManagementSystem.java
│     ├─ Patient.java
│     └─ Doctor.java
├─ lib/
│  └─ mysql-connector-j-<version>.jar
├─ .gitignore
└─ README.md
```

---

## License & Author

**Author:** Mohammed Yousuf Furqan (`Yousuf644`)  
**License:** MIT
