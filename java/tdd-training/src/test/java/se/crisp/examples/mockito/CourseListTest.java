package se.crisp.examples.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CourseListTest {

    @Mock
    CourseService courseService;

    @Test
    public void should_use_course_service() throws Exception {
        CourseList courseList = new CourseList(courseService);

        courseList.currentCourses();

        verify(courseService).findCoursesBetweenDates(isA(OffsetDateTime.class), isA(OffsetDateTime.class));
    }

    @Test
    public void current_courses_is_within_6_months() {
        CourseList courseList = new CourseList(courseService);

        courseList.currentCourses();

        // Problem here. What shall we do?
        // LocalDateTime months = LocalDateTime.now().plusMonths(6);
        //verify(courseService).findCoursesBetweenDates(OffsetDateTime.now(), OffsetDateTime.from(months));
    }
}