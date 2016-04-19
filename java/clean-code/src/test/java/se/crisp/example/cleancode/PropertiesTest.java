package se.crisp.example.cleancode;


import org.junit.Test;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class PropertiesTest {

    public static final String SOME_KEY = "some key";
    public static final String SOME_VALUE = "some value";
    public static final String SOME_DEFAULT = "some default";
    private static final String SOME_DEFAULT_KEY = "some default key";

    @Test
    public void initially_empty() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(SOME_KEY, SOME_VALUE);

        assertThat(properties.getProperty(SOME_KEY), is(SOME_VALUE));
    }

    @Test
    public void properties_with_no_value_returns_null() throws Exception {
        Properties properties = new Properties();

        String actual = properties.getProperty(SOME_KEY);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void get_property_with_default_value_fall_back() throws Exception {
        Properties properties = new Properties();

        String actual = properties.getProperty(SOME_KEY, SOME_DEFAULT);

        assertThat(actual, is(SOME_DEFAULT));
    }

    @Test
    public void get_property_with_default_value() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(SOME_KEY, SOME_VALUE);

        String actual = properties.getProperty(SOME_KEY, SOME_DEFAULT);

        assertThat(actual, is(SOME_VALUE));
    }

    @Test
    public void properties_with_default_values() throws Exception {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty(SOME_KEY, SOME_VALUE);
        Properties properties = new Properties(defaultProperties);

        assertThat(properties.getProperty(SOME_KEY), is(SOME_VALUE));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void list_all_property_keys_including_default() throws Exception {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty(SOME_DEFAULT_KEY, SOME_DEFAULT);
        Properties properties = new Properties(defaultProperties);
        properties.setProperty(SOME_KEY, SOME_VALUE);

        Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
        Set<String> actualSet = new HashSet<>(Collections.list(enumeration));

        Set<String> expectedSet = new HashSet<String>() {{
            add(SOME_DEFAULT_KEY);
            add(SOME_KEY);
        }};

        assertThat(actualSet, is(expectedSet));
    }

    @Test
    public void list_all_property_keys_and_value_including_default() throws Exception {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty(SOME_DEFAULT_KEY, SOME_DEFAULT);
        Properties properties = new Properties(defaultProperties);
        properties.setProperty(SOME_KEY, SOME_VALUE);

        Set<String> actualSet = properties.stringPropertyNames();

        Set<String> expectedSet = new HashSet<String>() {{
            add(SOME_DEFAULT_KEY);
            add(SOME_KEY);
        }};

        assertThat(actualSet, is(expectedSet));
    }
}