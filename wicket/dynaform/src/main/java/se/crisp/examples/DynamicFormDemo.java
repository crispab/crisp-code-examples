package se.crisp.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2012-12-05
 * Time: 11:08
 */
public class DynamicFormDemo extends WebPage {
    public static final String FORM = "form";
    public static final String SPEC = "spec";

    public DynamicFormDemo() {
        add(new DynamicFormSetupForm(FORM));
    }

    private class DynamicFormSetupForm extends Form<Void> {
        private String spec = "";

        public DynamicFormSetupForm(String id) {
            super(id);
            add(new TextArea<String>(SPEC, new PropertyModel<String>(this, SPEC)));
        }

        @Override
        protected void onSubmit() {
            setResponsePage(new ShowForm(spec, new DomainObject()));
        }
    }

    private class DomainObject implements Serializable {
        String firstName = "";
        String lastName = "";
        Integer bornYear = 1900;

        @Override
        public String toString() {
            return String.format("%s, %s born %d", lastName, firstName, bornYear);
        }
    }
}
