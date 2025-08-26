package com.college.dao;

import com.college.model.Application;
import java.util.List;

public interface ApplicationDAO {
    int addApplication(Application a) throws Exception;
    List<Application> getAll() throws Exception;
    List<Application> getByCourseId(int courseId) throws Exception;
    void updateStatus(int applicationId, String status) throws Exception;
}
