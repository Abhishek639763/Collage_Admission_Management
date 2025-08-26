package com.college;

import com.college.dao.CourseDAO;
import com.college.dao.StudentDAO;
import com.college.dao.ApplicationDAO;
import com.college.dao.impl.CourseDAOImpl;
import com.college.dao.impl.StudentDAOImpl;
import com.college.dao.impl.ApplicationDAOImpl;
import com.college.export.Exporter;
import com.college.model.Course;
import com.college.model.Student;
import com.college.model.Application;
import com.college.service.AdmissionService;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    private static final StudentDAO studentDAO = new StudentDAOImpl();
    private static final CourseDAO courseDAO = new CourseDAOImpl();
    private static final ApplicationDAO applicationDAO = new ApplicationDAOImpl();
    private static final AdmissionService admissionService = new AdmissionService();

    public static void main(String[] args) {
        System.out.println("=== College Admission Management System ===");
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Register Student");
            System.out.println("2. Add Course (admin)");
            System.out.println("3. List Students");
            System.out.println("4. List Courses");
            System.out.println("5. Apply for Course");
            System.out.println("6. Run Allocation (admit/reject)"); 
            System.out.println("7. Export admission list (CSV + PDF)"); 
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1": registerStudent(sc); break;
                    case "2": addCourse(sc); break;
                    case "3": listStudents(); break;
                    case "4": listCourses(); break;
                    case "5": applyForCourse(sc); break;
                    case "6": runAllocation(); break;
                    case "7": exportFiles(); break;
                    case "8": System.out.println("Goodbye."); 
                    System.exit(0); 
                    break;
                    default: 
                    	System.out.println("Invalid choice.");
                    	break;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void registerStudent(Scanner sc) throws Exception {
        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Marks (percentage): ");
        String marksInput = sc.nextLine().trim();
        if (marksInput.endsWith("%")) {
            marksInput = marksInput.substring(0, marksInput.length() - 1); // remove %
        }
        double marks = Double.parseDouble(marksInput);

        Student s = new Student(name, email, marks);
        int id = studentDAO.addStudent(s);
        System.out.println("Registered student id: " + id);
    }


    private static void addCourse(Scanner sc) throws Exception {
        System.out.print("Course code: "); 
        String code = sc.nextLine();
        System.out.print("Course name: "); 
        String name = sc.nextLine();
        System.out.print("Seats: "); 
        int seats = Integer.parseInt(sc.nextLine());
        System.out.print("Cutoff (percentage): "); 
        double cutoff = Double.parseDouble(sc.nextLine());
        Course c = new Course(code, name, seats, cutoff);
        int id = courseDAO.addCourse(c);
        System.out.println("Added course id: " + id);
    }

    private static void listStudents() throws Exception {
        List<Student> list = studentDAO.getAll();
        System.out.println("Students:");
        for (Student s : list) System.out.println(s);
    }

    private static void listCourses() throws Exception {
        List<Course> list = courseDAO.getAll();
        System.out.println("Courses:");
        for (Course c : list) System.out.println(c);
    }

    private static void applyForCourse(Scanner sc) throws Exception {
        System.out.print("Enter student id: "); 
        int sid = Integer.parseInt(sc.nextLine());
        System.out.print("Enter course id: "); 
        int cid = Integer.parseInt(sc.nextLine());
        // fetch student to get marks
        Student s = studentDAO.getById(sid);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        Application a = new Application(sid, cid, s.getMarks());
        int id = applicationDAO.addApplication(a);
        System.out.println("Application submitted with id: " + id);
    }

    private static void runAllocation() throws Exception {
        System.out.println("Running allocation...");
        admissionService.allocateSeats();
        System.out.println("Allocation complete.");
    }

    private static void exportFiles() throws Exception {
        String csvOut = "output/admission_list.csv";
        String pdfOut = "output/admission_list.pdf";
        java.nio.file.Path outDir = java.nio.file.Paths.get("output");
        if (!java.nio.file.Files.exists(outDir)) java.nio.file.Files.createDirectories(outDir);
        Exporter.exportToCSV(csvOut);
        Exporter.exportToPDF(pdfOut);
        System.out.println("Exported:"); System.out.println(csvOut); System.out.println(pdfOut);
    }
}
