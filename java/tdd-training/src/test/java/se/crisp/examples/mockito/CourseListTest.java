package se.crisp.examples.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.OffsetDateTime;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;

@RunWith(MockitoJUnitRunner.class)
public class CourseListTest {

    @Mock
    CourseService courseService;

    @Test
    public void should_use_course_service() throws Exception {
        CourseList courseList = new CourseList(courseService);

        courseList.currentCourses();

        Mockito.verify(courseService).findCoursesBetweenDates(isA(OffsetDateTime.class), isA(OffsetDateTime.class));
    }

    @Test
    public void current_courses_is_within_6_months() {

    }
}