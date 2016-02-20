package se.crisp.examples.mockito;

import java.util.List;

import static java.util.Collections.emptyList;

public class CourseList {

    private final CourseService courseService;

    public CourseList(CourseService courseService) {
        this.courseService = courseService;
    }


    public List<Course> currentCourses() {
        return emptyList();
    }
}
