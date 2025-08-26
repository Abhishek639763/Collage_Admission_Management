package com.college.model;

public class Application {
    private int id;
    private int studentId;
    private int courseId;
    private double meritScore;
    private String status; // PENDING, ADMITTED, REJECTED

    public Application() {}

    public Application(int id, int studentId, int courseId, double meritScore, String status) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.meritScore = meritScore;
        this.status = status;
    }

    public Application(int studentId, int courseId, double meritScore) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.meritScore = meritScore;
        this.status = "PENDING";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public double getMeritScore() { return meritScore; }
    public void setMeritScore(double meritScore) { this.meritScore = meritScore; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return id + " - sId:" + studentId + " cId:" + courseId + " score:" + meritScore + " status:" + status;
    }
}
