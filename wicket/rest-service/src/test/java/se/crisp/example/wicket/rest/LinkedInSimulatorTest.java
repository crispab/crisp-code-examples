package se.crisp.example.wicket.rest;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class LinkedInSimulatorTest {

    @Test
    public void renders() throws Exception {
        WicketTester tester = new WicketTester(new RestService());

        PageParameters parameters = new PageParameters();
        tester.startPage(new LinkedInSimulator(parameters));
    }
}
