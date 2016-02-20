package se.crisp.examples.mockito;

import java.time.OffsetDateTime;
import java.util.List;

public interface CourseService {

    List<Course> findCoursesBetweenDates(OffsetDateTime from, OffsetDateTime tom);
}
