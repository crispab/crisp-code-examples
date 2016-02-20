package se.crisp.examples.mockito.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.ResultSet;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {

    @Mock
    private CourseDatabase courseDatabase;

    @Before
    public void setUp() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(courseDatabase.executeSQL(anyString())).thenReturn(resultSet);
    }

    @Test
    public void should_call_database() throws Exception {
        CourseServiceImpl courseService = new CourseServiceImpl(courseDatabase);

        courseService.listCourses();

        verify(courseDatabase).executeSQL(anyString());
    }

    @Test
    public void investigate_sql_string() throws Exception {
        CourseServiceImpl courseService = new CourseServiceImpl(courseDatabase);

        courseService.listCourses();

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(courseDatabase).executeSQL(captor.capture());
        System.out.println(captor.getAllValues());
    }
}