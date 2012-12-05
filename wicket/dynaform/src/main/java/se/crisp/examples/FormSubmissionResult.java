package se.crisp.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2012-12-05
 * Time: 14:12
 */
public class FormSubmissionResult extends WebPage {
    public FormSubmissionResult(Object o) {
        add(new Label("result", o.toString()));
    }
}
