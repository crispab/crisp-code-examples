package se.crisp.example.wicket.rest;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

public class RestService extends WebApplication {
    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init() {
        super.init();
        mountPage("/xml", XmlPage.class);
        mountPage("/authorization", LinkedInSimulator.class);
        getMarkupSettings().setStripWicketTags(true);
    }
}
