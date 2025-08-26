package com.college.service;

import com.college.dao.ApplicationDAO;
import com.college.dao.CourseDAO;
import com.college.dao.StudentDAO;
import com.college.dao.impl.ApplicationDAOImpl;
import com.college.dao.impl.CourseDAOImpl;
import com.college.dao.impl.StudentDAOImpl;
import com.college.model.Application;
import com.college.model.Course;


import java.util.*;
import java.util.stream.Collectors;

public class AdmissionService {

    private final StudentDAO studentDAO = new StudentDAOImpl();
    private final CourseDAO courseDAO = new CourseDAOImpl();
    private final ApplicationDAO applicationDAO = new ApplicationDAOImpl();

    /**
     * Allocation logic:
     * For each course:
     *   - fetch all applications for that course
     *   - filter out those whose student marks < course.cutoff
     *   - sort by student marks desc (meritScore)
     *   - pick top 'seats' and mark them ADMITTED; others REJECTED
     */
    public void allocateSeats() throws Exception {
        List<Course> courses = courseDAO.getAll();
        for (Course course : courses) {
            List<Application> appls = applicationDAO.getByCourseId(course.getId());
            // join with student marks (meritScore already provided in application but let's trust it)
            List<Application> eligible = appls.stream()
                    .filter(a -> a.getMeritScore() >= course.getCutoff())
                    .sorted(Comparator.comparingDouble(Application::getMeritScore).reversed())
                    .collect(Collectors.toList());

            int seats = course.getSeats();
            for (int i = 0; i < eligible.size(); i++) {
                Application a = eligible.get(i);
                if (i < seats) {
                    applicationDAO.updateStatus(a.getId(), "ADMITTED");
                } else {
                    applicationDAO.updateStatus(a.getId(), "REJECTED");
                }
            }
            // Mark others (below cutoff) as REJECTED
            Set<Integer> eligibleIds = eligible.stream().map(Application::getId).collect(Collectors.toSet());
            for (Application a : appls) {
                if (!eligibleIds.contains(a.getId())) {
                    applicationDAO.updateStatus(a.getId(), "REJECTED");
                }
            }
        }
    }
}
