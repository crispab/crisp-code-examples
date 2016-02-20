package se.crisp.examples.mockito.service;

import java.sql.ResultSet;

public interface CourseDatabase {
    ResultSet executeSQL(String sql);
}
