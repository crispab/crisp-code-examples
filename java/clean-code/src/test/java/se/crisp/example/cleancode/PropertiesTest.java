package se.crisp.example.cleancode;


import org.junit.Test;

import java.io.*;
import java.util.*;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class PropertiesTest {

    public static final String SOME_KEY = "somekey";
    public static final String SOME_KEY_2 = "somekey2";
    public static final String SOME_KEY_3 = "somekey3";
    public static final String SOME_VALUE = "some value";
    public static final String SOME_DEFAULT = "some default";
    private static final String SOME_DEFAULT_KEY = "some default key";
    private static final int BUFFER_SIZE = 25000;
    public static final String LISTING_PROPERTIES = "-- listing properties --";
    private static final String SOME_LONG_VALUE = "012345678901234567890123456789012345678901234567890123456789";
    public static final String EMPTY_STRING = "";
    private static final String COMMENTS = "some comments to consider.";

    @Test
    public void initially_empty() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(SOME_KEY, SOME_VALUE);

        assertThat(properties.getProperty(SOME_KEY), is(SOME_VALUE));
        assertThat(properties.stringPropertyNames().size(), is(1));
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
    public void list_all_property_keys_using_string_names() throws Exception {
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
    public void load_from_input_stream_with_equals() throws Exception {
        Properties properties = new Properties();

        String someKeyValue = format("%s=%s%n%s=%s",
                SOME_KEY, SOME_VALUE,
                SOME_KEY_2, SOME_DEFAULT);
        byte[] bytes = someKeyValue.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        properties.load(inputStream);

        assertThat(properties.getProperty(SOME_KEY), is(SOME_VALUE));
        assertThat(properties.getProperty(SOME_KEY_2), is(SOME_DEFAULT));
    }

    @Test
    public void load_from_input_stream_with_empty_key() throws Exception {
        Properties properties = new Properties();

        InputStream inputStream = new ByteArrayInputStream(SOME_KEY.getBytes());
        properties.load(inputStream);

        assertThat(properties.getProperty(SOME_KEY), is(EMPTY_STRING));
    }

    @Test
    public void load_from_input_stream_with_colon() throws Exception {
        Properties properties = new Properties();

        String someKeyValue = format("%s:%s%n%s:%s",
                SOME_KEY, SOME_VALUE,
                SOME_KEY_2, SOME_DEFAULT);
        byte[] bytes = someKeyValue.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        properties.load(inputStream);

        assertThat(properties.getProperty(SOME_KEY), is(SOME_VALUE));
        assertThat(properties.getProperty(SOME_KEY_2), is(SOME_DEFAULT));
    }

    @Test
    public void load_from_input_stream_with_back_slash() throws Exception {
        Properties properties = new Properties();

        String someKeyValue = format("HI\\:%s:%s%n%s:%s",
                SOME_KEY, SOME_VALUE,
                SOME_KEY_2, SOME_DEFAULT);
        byte[] bytes = someKeyValue.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        properties.load(inputStream);

        assertThat(properties.getProperty("HI:" + SOME_KEY), is(SOME_VALUE));
        assertThat(properties.getProperty(SOME_KEY_2), is(SOME_DEFAULT));
    }

    @Test
    public void load_from_input_stream_with_leading_white_space() throws Exception {
        Properties properties = new Properties();

        String someKeyValue = format(" %s:%s%n\t%s:%s%n \f%s = %s",
                SOME_KEY, SOME_VALUE,
                SOME_KEY_2, SOME_DEFAULT,
                SOME_KEY_3, SOME_LONG_VALUE);
        byte[] bytes = someKeyValue.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        properties.load(inputStream);

        assertThat(properties.getProperty(SOME_KEY), is(SOME_VALUE));
        assertThat(properties.getProperty(SOME_KEY_2), is(SOME_DEFAULT));
        assertThat(properties.getProperty(SOME_KEY_3), is(SOME_LONG_VALUE));
    }

    @Test
    public void load_from_input_stream_with_embedded_white_space() throws Exception {
        Properties properties = new Properties();

        String someKeyWithSpace = "some\\ key";
        String someKeyValue = someKeyWithSpace + "=" + SOME_VALUE;
        byte[] bytes = someKeyValue.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        properties.load(inputStream);

        assertThat(properties.getProperty("some key"), is(SOME_VALUE));
    }

    @Test
    public void load_from_input_stream_with_comment() throws Exception {
        Properties properties = new Properties();

        String someKeyValue = format("#%s:%s%n%s:%s",
                SOME_KEY, SOME_VALUE,
                SOME_KEY_2, SOME_DEFAULT);
        byte[] bytes = someKeyValue.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        properties.load(inputStream);

        assertThat(properties.getProperty(SOME_KEY), is(nullValue()));
        assertThat(properties.getProperty(SOME_KEY_2), is(SOME_DEFAULT));
    }

    @Test
    public void load_from_input_stream_with_multi_line_value() throws Exception {
        Properties properties = new Properties();

        String someKeyValue = format("%s:\\%n%s,\\%n%s,\\%n\\%s",
                SOME_KEY, SOME_VALUE,
                SOME_DEFAULT,
                SOME_LONG_VALUE);
        byte[] bytes = someKeyValue.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        properties.load(inputStream);

        assertThat(properties.getProperty(SOME_KEY), is(format("%s,%s,%s", SOME_VALUE, SOME_DEFAULT, SOME_LONG_VALUE)));
    }

    // Writing

    @Test
    public void list_properties_on_print_stream() throws Exception {
        Properties properties = propertiesWithTwoProperties();

        ByteArrayOutputStream sink = new ByteArrayOutputStream(BUFFER_SIZE);
        properties.list(new PrintStream(sink));

        ByteArrayOutputStream expected = getExpectedTwoProperties(true, LISTING_PROPERTIES);
        assertThat(sink.toString(), is(expected.toString()));
    }

    @Test
    public void list_properties_on_print_writer() throws Exception {
        Properties properties = propertiesWithTwoProperties();

        ByteArrayOutputStream sink = new ByteArrayOutputStream(BUFFER_SIZE);
        properties.list(new PrintWriter(sink));

        ByteArrayOutputStream expected = new ByteArrayOutputStream(BUFFER_SIZE);
        PrintWriter writer = new PrintWriter(expected);
        writer.println(LISTING_PROPERTIES);
        writer.println(SOME_KEY + "=" + SOME_VALUE);
        writer.println(SOME_KEY_2 + "=" + SOME_LONG_VALUE.substring(0, 37) + "...");

        assertThat(sink.toString(), is(expected.toString()));
    }

    @Test
    public void store_properties_on_output_stream() throws Exception {
        Properties properties = propertiesWithTwoProperties();

        ByteArrayOutputStream sink = new ByteArrayOutputStream(BUFFER_SIZE);
        properties.store(sink, COMMENTS);

        ByteArrayOutputStream expected = getExpectedTwoProperties(false, "#" + COMMENTS, "#" + new Date());
        assertThat(sink.toString(), is(expected.toString()));
    }

    private Properties propertiesWithTwoProperties() {
        Properties properties = new Properties();
        properties.setProperty(SOME_KEY, SOME_VALUE);
        properties.setProperty(SOME_KEY_2, SOME_LONG_VALUE);
        return properties;
    }

    private ByteArrayOutputStream getExpectedTwoProperties(boolean shortening, String... prelude) {
        ByteArrayOutputStream expected = new ByteArrayOutputStream(BUFFER_SIZE);
        PrintStream stream = new PrintStream(expected);
        for(String line : prelude) {
            stream.println(line);
        }
        stream.println(SOME_KEY + "=" + SOME_VALUE);
        if (!shortening) {
            stream.println(SOME_KEY_2 + "=" + SOME_LONG_VALUE);
        } else {
            stream.println(SOME_KEY_2 + "=" + SOME_LONG_VALUE.substring(0, 37) + "...");
        }
        return expected;
    }
}