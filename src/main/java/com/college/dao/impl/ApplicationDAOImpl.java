package com.college.dao.impl;

import com.college.dao.ApplicationDAO;
import com.college.model.Application;
import com.college.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAOImpl implements ApplicationDAO {

    @Override
    public int addApplication(Application a) throws Exception {
        String sql = "INSERT INTO applications (student_id, course_id, merit_score, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getStudentId());
            ps.setInt(2, a.getCourseId());
            ps.setDouble(3, a.getMeritScore());
            ps.setString(4, a.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    @Override
    public List<Application> getAll() throws Exception {
        List<Application> list = new ArrayList<>();
        String sql = "SELECT id, student_id, course_id, merit_score, status FROM applications";
        try (Connection conn = DBUtil.getConnection(); 
        		PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Application(rs.getInt("id"), rs.getInt("student_id"), rs.getInt("course_id"), rs.getDouble("merit_score"), rs.getString("status")));
            }
        }
        return list;
    }

    @Override
    public List<Application> getByCourseId(int courseId) throws Exception {
        List<Application> list = new ArrayList<>();
        String sql = "SELECT id, student_id, course_id, merit_score, status FROM applications WHERE course_id = ?";
        try (Connection conn = DBUtil.getConnection(); 
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Application(rs.getInt("id"), rs.getInt("student_id"), rs.getInt("course_id"), rs.getDouble("merit_score"), rs.getString("status")));
                }
            }
        }
        return list;
    }

    @Override
    public void updateStatus(int applicationId, String status) throws Exception {
        String sql = "UPDATE applications SET status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, applicationId);
            ps.executeUpdate();
        }
    }
}
