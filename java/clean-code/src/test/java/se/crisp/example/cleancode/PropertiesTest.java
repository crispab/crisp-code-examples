package se.crisp.example.cleancode;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PropertiesTest {

    public static final String SOME_KEY = "some key";
    public static final String SOME_VALUE = "some value";

    @Test
    public void initially_empty() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(SOME_KEY, SOME_VALUE);

        assertThat(properties.getProperty(SOME_KEY), is(SOME_VALUE));
    }
}