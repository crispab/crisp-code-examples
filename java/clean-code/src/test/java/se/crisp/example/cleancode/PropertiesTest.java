package se.crisp.example.cleancode;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class PropertiesTest {

    public static final String SOME_KEY = "some key";
    public static final String SOME_VALUE = "some value";
    public static final String SOME_DEFAULT = "some default";

    @Test
    public void initially_empty() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(SOME_KEY, SOME_VALUE);

        assertThat(properties.getProperty(SOME_KEY), is(SOME_VALUE));
    }

    @Test
    public void properties_with_no_value_returns_null() throws Exception {
        Properties properties = new Properties();

        assertThat(properties.getProperty(SOME_KEY), is(nullValue()));
    }

    @Test
    public void get_property_with_default_value() throws Exception {
        Properties properties = new Properties();

        assertThat(properties.getProperty(SOME_KEY, SOME_DEFAULT), is(SOME_DEFAULT));
    }

    @Test
    public void properties_with_default_values() throws Exception {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty(SOME_KEY, SOME_VALUE);
        Properties properties = new Properties(defaultProperties);

        assertThat(properties.getProperty(SOME_KEY), is(SOME_VALUE));
    }
}