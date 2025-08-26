package com.college.dao.impl;

import com.college.dao.CourseDAO;
import com.college.model.Course;
import com.college.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public int addCourse(Course c) throws Exception {
        String sql = "INSERT INTO courses (code, name, seats, cutoff) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getCode());
            ps.setString(2, c.getName());
            ps.setInt(3, c.getSeats());
            ps.setDouble(4, c.getCutoff());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    @Override
    public Course getById(int id) throws Exception {
        String sql = "SELECT id, code, name, seats, cutoff FROM courses WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Course(rs.getInt("id"), rs.getString("code"), rs.getString("name"), rs.getInt("seats"), rs.getDouble("cutoff"));
                }
            }
        }
        return null;
    }

    @Override
    public List<Course> getAll() throws Exception {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT id, code, name, seats, cutoff FROM courses";
        try (Connection conn = DBUtil.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Course(rs.getInt("id"), rs.getString("code"), rs.getString("name"), rs.getInt("seats"), rs.getDouble("cutoff")));
            }
        }
        return list;
    }
}
