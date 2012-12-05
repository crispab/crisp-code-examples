package se.crisp.examples;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2012-12-05
 * Time: 11:23
 */
public class ShowForm extends WebPage {
    static final String LINE_END = ";";
    static final String FIELD_DELIMIT = ":";
    static final String FORM = "form";
    static final String LABEL = "label";
    static final String FIELD = "inputField";
    static final String FIELDS = "fields";
    static final String FEEDBACK = "feedback";
    static final String INTEGER_TYPE = "integer";
    static final String SUBMIT_BUTTON = "ajax-button";
    private final FeedbackPanel feedback;

    public ShowForm(String specification, Object object) {
        feedback = new FeedbackPanel(FEEDBACK);
        feedback.setOutputMarkupId(true);
        add(feedback);
        ShowFormForm form = new ShowFormForm(FORM, specification, object);
        add(form);
        //AjaxFormValidatingBehavior.addToAllFormComponents(form, "onkeyup", Duration.ONE_SECOND);
    }

    private class FieldSpec implements Serializable {
        String label;
        String type;
        String property;

        public FieldSpec(String fieldSpec) {
            String[] split = fieldSpec.split(FIELD_DELIMIT);
            if (split.length == 3) {
                label = split[0];
                type = split[1];
                property = split[2];
            }
        }
    }

    private class ShowFormForm extends Form<Void> {
        public ShowFormForm(String id, String specification, Object object) {
            super(id);
            setDefaultModel(new CompoundPropertyModel<Object>(object));
            addFieldsAccordingToSpecification(specification, object);
            add(createAjaxSubmitButton());
        }

        @Override
        protected void onSubmit() {
            Object o = getDefaultModelObject();
            setResponsePage(new FormSubmissionResult(o));
        }

        private void addFieldsAccordingToSpecification(String specification, final Object object) {
            List<FieldSpec> fieldList = parseSpecification(specification);

            add(new ListView<FieldSpec>(FIELDS, fieldList) {
                @Override
                protected void populateItem(ListItem<FieldSpec> item) {
                    FieldSpec fieldSpec = item.getModelObject();
                    item.add(new Label(LABEL, fieldSpec.label));
                    item.add(createInputField(fieldSpec, object));
                }
            });
        }

        private FormComponent createInputField(FieldSpec fieldSpec, Object object) {
            FormComponent field;
            if (fieldSpec.type.equalsIgnoreCase(INTEGER_TYPE)) {
                field = new TextField<Integer>(FIELD, new PropertyModel<Integer>(object, fieldSpec.property));
            } else {
                field = new TextField<String>(FIELD, new PropertyModel<String>(object, fieldSpec.property));
            }
            field.setOutputMarkupId(true);
            field.setRequired(true);
            field.setLabel(Model.of(fieldSpec.label));
            field.add(new CssClassAppender());
            return field;
        }

        private List<FieldSpec> parseSpecification(String specification) {
            List<FieldSpec> result = new ArrayList<FieldSpec>();
            if (null != specification) {
                String[] fieldSpecs = specification.split(LINE_END);
                for (String fieldSpec : fieldSpecs) {
                    result.add(new FieldSpec(fieldSpec));
                }
            }
            return result;
        }

        private AjaxButton createAjaxSubmitButton() {
            return new AjaxButton(SUBMIT_BUTTON, this) {
                @Override
                protected void onError(final AjaxRequestTarget target, Form<?> form) {
                    form.visitChildren(new IVisitor<Component, Object>() {
                        @Override
                        public void component(Component object, IVisit<Object> visit) {
                            if (object instanceof FormComponent) {
                                FormComponent fc = (FormComponent) object;
                                target.add(fc);
                            }
                        }
                    });
                    target.add(feedback);
                }

            };
        }
    }
}
