package se.crisp.examples;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2012-12-05
 * Time: 11:49
 */
public class ShowFormTest {
    static final String VALUE = "42";
    private WicketTester tester;
    private static final String SOME_PROPERTY = "someProperty";
    private static final String SOME_INT_PROPERTY = "intProperty";
    private static final String SOME_LABEL = "some label";
    private static final String LINE_END = ShowForm.LINE_END;
    private static final String SAMPLE_SPEC = SOME_LABEL + ":string:" + SOME_PROPERTY + LINE_END;
    private static final String ONE_INTEGER_SPEC = SOME_LABEL + ":integer:" + SOME_INT_PROPERTY + LINE_END;
    private DomainObjectForTesting object;
    private static final String NOT_A_NUMBER = "not a number";

    @Before
    public void setUp()
    {
        tester = new WicketTester(new WicketApplication());
        object = new DomainObjectForTesting();
    }

    @Test
    public void hookUp() {
        tester.startPage(new ShowForm(SAMPLE_SPEC, object));
    }

    @Test
    public void a_spec_with_one_integer_field() {
        tester.startPage(new ShowForm(ONE_INTEGER_SPEC, object));
        tester.assertComponent(ShowForm.FORM, Form.class);
        tester.assertComponent(ShowForm.FORM + ":" + ShowForm.FIELDS + ":0:" + ShowForm.LABEL, Label.class);
        tester.assertComponent(ShowForm.FORM + ":" + ShowForm.FIELDS + ":0:" + ShowForm.FIELD, TextField.class);
    }

    @Test
    public void on_submit_the_domain_object_should_be_updated() {
        tester.startPage(new ShowForm(ONE_INTEGER_SPEC, object));
        FormTester formTester = tester.newFormTester(ShowForm.FORM);
        formTester.setValue(ShowForm.FIELDS + ":0:" + ShowForm.FIELD, VALUE);
        formTester.submit();

        assertEquals(VALUE, String.valueOf(object.intProperty));
    }

    @Test
    public void on_submit_go_to_result_page() {
        tester.startPage(new ShowForm(ONE_INTEGER_SPEC, object));
        FormTester formTester = tester.newFormTester(ShowForm.FORM);
        formTester.setValue(ShowForm.FIELDS + ":0:" + ShowForm.FIELD, VALUE);
        formTester.submit();
        tester.assertRenderedPage(FormSubmissionResult.class);
    }

    @Test
    public void on_submit_validate_integer_field() {
        tester.startPage(new ShowForm(ONE_INTEGER_SPEC, object));
        FormTester formTester = tester.newFormTester(ShowForm.FORM);
        formTester.setValue(ShowForm.FIELDS + ":0:" + ShowForm.FIELD, NOT_A_NUMBER);
        formTester.submit();
        tester.assertRenderedPage(ShowForm.class);
    }
    
    private class DomainObjectForTesting {
        public String someProperty = "";
        public Integer intProperty = new Integer(-1);
    }
}
