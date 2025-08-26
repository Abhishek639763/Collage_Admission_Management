package com.college.dao;

import com.college.model.Student;
import java.util.List;

public interface StudentDAO {
    int addStudent(Student s) throws Exception;
    Student getById(int id) throws Exception;
    List<Student> getAll() throws Exception;
}
