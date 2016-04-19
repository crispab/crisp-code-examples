package se.crisp.example.cleancode;


import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class PropertiesTest {

    public static final String SOME_KEY = "some key";
    public static final String SOME_KEY_2 = "some key 2";
    public static final String SOME_VALUE = "some value";
    public static final String SOME_DEFAULT = "some default";
    private static final String SOME_DEFAULT_KEY = "some default key";
    private static final int BUFFER_SIZE = 25000;
    public static final String LISTING_PROPERTIES = "-- listing properties --";
    private static final String SOME_LONG_VALUE = "012345678901234567890123456789012345678901234567890123456789";

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

    @Test
    public void list_properties_on_print_stream() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(SOME_KEY, SOME_VALUE);
        properties.setProperty(SOME_KEY_2, SOME_LONG_VALUE);

        ByteArrayOutputStream sink = new ByteArrayOutputStream(BUFFER_SIZE);
        properties.list(new PrintStream(sink));

        ByteArrayOutputStream expected = new ByteArrayOutputStream(BUFFER_SIZE);
        PrintStream stream = new PrintStream(expected);
        stream.println(LISTING_PROPERTIES);
        stream.println(SOME_KEY + "=" + SOME_VALUE);
        stream.println(SOME_KEY_2 + "=" + SOME_LONG_VALUE.substring(0, 37) + "...");

        assertThat(sink.toString(), is(expected.toString()));
    }

    @Test
    public void list_properties_on_print_writer() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(SOME_KEY, SOME_VALUE);
        properties.setProperty(SOME_KEY_2, SOME_LONG_VALUE);

        ByteArrayOutputStream sink = new ByteArrayOutputStream(BUFFER_SIZE);
        properties.list(new PrintWriter(sink));

        ByteArrayOutputStream expected = new ByteArrayOutputStream(BUFFER_SIZE);
        PrintWriter writer = new PrintWriter(expected);
        writer.println(LISTING_PROPERTIES);
        writer.println(SOME_KEY + "=" + SOME_VALUE);
        writer.println(SOME_KEY_2 + "=" + SOME_LONG_VALUE.substring(0, 37) + "...");

        assertThat(sink.toString(), is(expected.toString()));
    }
}