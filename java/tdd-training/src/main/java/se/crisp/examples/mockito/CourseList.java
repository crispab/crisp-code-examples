package se.crisp.examples.mockito;

import java.time.OffsetDateTime;
import java.util.List;

public class CourseList {

    private final CourseService courseService;

    public CourseList(CourseService courseService) {
        this.courseService = courseService;
    }


    public List<Course> currentCourses() {
        return courseService.findCoursesBetweenDates(OffsetDateTime.now(), OffsetDateTime.now());
    }
}
