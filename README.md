
# College Admission Management System (Java, JDBC, MySQL)

**Objective:** Manage student applications, course allocation, and merit lists.

A **Java + MySQL based system** to manage student admissions, course allocations, and merit-based seat allotment.  

Built as a console application with options for student registration, course management, application submission, and automatic seat allocation.

---

**Deliverables included:** DB schema, user guide, sample output files, full Java Maven project with DAO layers and export capabilities (CSV + PDF).

---

## 🚀 Features

- 👨‍🎓 **Student Management**
  - Register students with name, email, and marks.
  - View all registered students.

- 📚 **Course Management**
  - Admin can add courses with code, name, available seats, and cutoff percentage.
  - View all available courses.

- 📝 **Applications**
  - Students can apply for courses.
  - Applications stored with student marks.

- 🎯 **Merit-based Allocation**
  - Automatically allocate seats based on cutoff and marks.
  - Reject students who do not meet the cutoff.

- 📤 **Export Results**
  - Generate **CSV** and **PDF** admission lists.

---

## 🛠️ Tech Stack

- **Java 17+**
- **MySQL 8+**
- **JDBC**
- **Maven**
- **OpenCSV / iText** (for exporting CSV/PDF)
- **Eclipse IDE** (recommended)

---

## Project structure 

CollegeAdmissionSystem/
│── pom.xml
│── schema.sql # Database schema
│── sample_data.sql # Sample data insert script
│── README.md
│── output/ # Exported CSV/PDF files
│
└── src/main/java/com/college/
│── MainApp.java # Entry point
│
├── dao/ # DAO Interfaces
├── dao/impl/ # DAO Implementations
├── model/ # Entity classes (Student, Course, Application)
├── service/ # Admission allocation logic
├── util/ # DBUtil for connection
└── export/ # Exporter for CSV/PDF
---

## Database Setup (SQL Schema + Sample Data)

Run the following SQL commands to set up the database manually:

```sql
-- Create database
CREATE DATABASE collage_db;

USE collage_db;

-- Students table
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    marks DOUBLE NOT NULL
);

-- Courses table
CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cutoff DOUBLE NOT NULL,
    seats INT NOT NULL
);

-- Applications table
CREATE TABLE applications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- Sample data
INSERT INTO students (name, email, marks) VALUES
('Alice Johnson', 'alice@example.com', 92.5),
('Bob Smith', 'bob@example.com', 85.0),
('Charlie Brown', 'charlie@example.com', 78.2);

INSERT INTO courses (name, cutoff, seats) VALUES
('Computer Science', 85.0, 2),
('Mechanical Engineering', 75.0, 3),
('Civil Engineering', 70.0, 2);

INSERT INTO applications (student_id, course_id) VALUES
(1, 1), (2, 1), (3, 2);


## Quick setup (step-by-step)

1. **Install Java 17 and Maven** on the server. Verify with:
   ```bash
   java -version
   mvn -v
   ```

2. **Install MySQL** (or MariaDB). Create a database and user:
   ```sql
   -- connect as root and run:
   CREATE DATABASE college_admission;
   CREATE USER 'admuser'@'localhost' IDENTIFIED BY 'adm@123';
   GRANT ALL PRIVILEGES ON college_admission.* TO 'admuser'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Run the SQL schema** to create tables:
   ```bash
   mysql -u admuser -p college_admission < schema.sql
   mysql -u admuser -p college_admission < sample_data.sql
   ```
   (Use password `` or change the credentials in `src/main/resources/db.properties`.)

4. **Configure DB connection** in `src/main/resources/db.properties` if needed:
   ```properties
   db.url=jdbc:mysql://localhost:3306/college_admission?useSSL=false&serverTimezone=UTC
   db.user=admuser
   db.password=adm@123
   ```

5. **Build the project**:
   ```bash
   mvn clean package
   ```
   This will create a jar-with-dependencies in `target/college-admission-1.0-SNAPSHOT-jar-with-dependencies.jar`.

6. **Run the program**:
   ```bash
   java -jar target/college-admission-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```
   Use the console menu to register students, add courses, apply, run allocation and export results (CSV and PDF).

7. **Output files**: After running allocation and exporting, you'll find files under `output/` (or wherever you provide path in the menu). Sample outputs are included in the zip under `output/`.

---

## Notes & design choices
- The project uses a layered structure: `model`, `dao` (interfaces), `dao.impl` (JDBC implementations), `service` (allocation logic), and `export` (CSV/PDF).
- Merit is calculated using the `marks` field on `Student` (treated as percentage). Courses have a `cutoff` percentage and `seats` integer.
- Allocation: for each course, applicants are filtered by cutoff and sorted by student marks (desc). Top `seats` are admitted.
- CSV and PDF exports are implemented (PDF via Apache PDFBox).

---
## Add Example Console Output
A small block showing what the menu looks like when the program runs (like:

=== College Admission Management System ===
1. Register Student
2. Add Course (Admin)
3. Apply for Course
4. Run Allocation
5. Export Results (CSV/PDF)
6. Exit

If you'd like a GUI (Swing/JavaFX) or a web UI (Spring Boot + Thymeleaf or React), tell me and I will extend the project to include that and provide deployment notes for a server (Tomcat or Spring Boot standalone jar).
