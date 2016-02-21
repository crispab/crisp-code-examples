package se.crisp.examples.mockito.service;

import se.crisp.examples.mockito.Course;
import se.crisp.examples.mockito.CourseService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CourseServiceImpl implements CourseService {

    private final CourseDatabase courseDatabase;

    public CourseServiceImpl(CourseDatabase courseDatabase) {
        this.courseDatabase = courseDatabase;
    }

    public List<Course> listCourses() {
        ResultSet resultSet = courseDatabase.executeSQL("SELECT * FROM COURSE C WHERE TEACHER IN (SELECT ID FROM TEACHERS WHERE COMPANY=CRISP AND ACTIVE=TRUE)");
        return fromResultSet(resultSet);
    }

    @Override
    public List<Course> findCoursesBetweenDates(LocalDateTime from, LocalDateTime tom) {
        return null;
    }

    private List<Course> fromResultSet(ResultSet resultSet) {
        List<Course> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(createCourse(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Course createCourse(ResultSet resultSet) {
        return new Course(resultSet);
    }
}
