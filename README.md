
# College Admission Management System (Java, JDBC, MySQL)

**Objective:** Manage student applications, course allocation, and merit lists.

**Deliverables included:** DB schema, user guide, sample output files, full Java Maven project with DAO layers and export capabilities (CSV + PDF).

---

## Project structure (in the zip)
```
CollegeAdmissionSystem/
  pom.xml
  README.md
  schema.sql
  sample_data.sql
  src/main/java/com/college/...
  src/main/resources/db.properties
  output/ (sample generated files)
```
---

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
   (Use password `adm@123` or change the credentials in `src/main/resources/db.properties`.)

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

If you'd like a GUI (Swing/JavaFX) or a web UI (Spring Boot + Thymeleaf or React), tell me and I will extend the project to include that and provide deployment notes for a server (Tomcat or Spring Boot standalone jar).
