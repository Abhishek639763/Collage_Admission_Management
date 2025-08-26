package com.college.dao.impl;

import com.college.dao.StudentDAO;
import com.college.model.Student;
import com.college.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public int addStudent(Student s) throws Exception {
        String sql = "INSERT INTO students (name, email, marks) VALUES (?, ?, ?)";
        try (Connection c = DBUtil.getConnection(); 
        		PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setDouble(3, s.getMarks());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    @Override
    public Student getById(int id) throws Exception {
        String sql = "SELECT id, name, email, marks FROM students WHERE id = ?";
        try (Connection c = DBUtil.getConnection();
        		PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getDouble("marks"));
                }
            }
        }
        return null;
    }

    @Override
    public List<Student> getAll() throws Exception {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT id, name, email, marks FROM students";
        try (Connection c = DBUtil.getConnection();
        		PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getDouble("marks")));
            }
        }
        return list;
    }
}
