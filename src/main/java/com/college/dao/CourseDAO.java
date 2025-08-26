package com.college.dao;

import com.college.model.Course;
import java.util.List;

public interface CourseDAO {
    int addCourse(Course c) throws Exception;
    Course getById(int id) throws Exception;
    List<Course> getAll() throws Exception;
}
