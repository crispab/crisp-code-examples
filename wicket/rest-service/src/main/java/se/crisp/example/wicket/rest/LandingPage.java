package se.crisp.example.wicket.rest;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LandingPage extends WebPage {

    private static final String PARAMETERS = "parameters";

    public LandingPage(PageParameters parameters) {
        add(new Label(PARAMETERS, parameters.toString()));
    }
}
