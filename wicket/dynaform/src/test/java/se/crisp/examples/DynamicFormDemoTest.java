package se.crisp.examples;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2012-12-05
 * Time: 11:06
 */
public class DynamicFormDemoTest {

    private WicketTester tester;
    private static final String SAMPLE_SPEC = "some label:string:someProperty";

    @Before
    public void setUp()
    {
        tester = new WicketTester(new WicketApplication());
    }

    @Test
    public void hookUp() {
        tester.startPage(DynamicFormDemo.class);
    }

    @Test
    public void on_form_submit_go_to_show_form_page() {
        tester.startPage(DynamicFormDemo.class);
        FormTester formTester = tester.newFormTester(DynamicFormDemo.FORM);
        formTester.submit();
        tester.assertRenderedPage(ShowForm.class);
    }

    @Test
    public void submitting_with_a_form_spec() {
        tester.startPage(DynamicFormDemo.class);
        FormTester formTester = tester.newFormTester(DynamicFormDemo.FORM);
        formTester.setValue(DynamicFormDemo.SPEC, SAMPLE_SPEC);
        formTester.submit();
        tester.assertRenderedPage(ShowForm.class);
    }
}
