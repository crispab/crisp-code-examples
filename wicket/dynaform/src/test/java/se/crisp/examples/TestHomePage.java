package se.crisp.examples;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
    }

    @Test
    public void homepageRendersSuccessfully() {
        tester.startPage(HomePage.class);

        tester.assertRenderedPage(HomePage.class);
    }

    @Test
    public void has_link_to_DynamicFormDemo() {
        tester.startPage(HomePage.class);

        tester.assertComponent(HomePage.LINK, Link.class);

        tester.clickLink(HomePage.LINK);

        tester.assertRenderedPage(DynamicFormDemo.class);
    }
}
