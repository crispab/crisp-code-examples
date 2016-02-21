package se.crisp.examples.mockito;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public interface CourseService {

    List<Course> findCoursesBetweenDates(LocalDateTime from, LocalDateTime tom);
}
