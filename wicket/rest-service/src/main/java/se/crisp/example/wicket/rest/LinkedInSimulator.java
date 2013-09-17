package se.crisp.example.wicket.rest;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LinkedInSimulator extends WebPage {

    public static final String RESPONSE_TYPE = "response_type";
    public static final String CLIENT_ID = "client_id";
    public static final String SCOPE = "scope";
    public static final String STATE = "state";
    public static final String REDIRECT_URI = "redirect_uri";
    private static final String[] parameterNames = {
            RESPONSE_TYPE,
            CLIENT_ID,
            SCOPE,
            STATE,
            REDIRECT_URI
    };
    private static final String FORM = "form";

    Map<String, String> parameterMap = new HashMap<>();

    LinkedInSimulatorData data = new LinkedInSimulatorData();

    public LinkedInSimulator(PageParameters parameters) {
        for (String parameterName : parameterNames) {
            parameterMap.put(parameterName, parameters.get(parameterName).toString());
        }
        add(new LinkedInSimulatorForm(FORM, new PropertyModel<LinkedInSimulatorData>(this, "data")));
    }

    private class LinkedInSimulatorForm extends Form<LinkedInSimulatorData> {

        private static final String EMAIL = "email";
        private static final String PASSWORD = "password";

        public LinkedInSimulatorForm(String id, IModel<LinkedInSimulatorData> dataIModel) {
            super(id, dataIModel);
            add(new TextField<>(EMAIL, new PropertyModel<String>(dataIModel, EMAIL)));
            add(new PasswordTextField(PASSWORD, new PropertyModel<String>(dataIModel, PASSWORD)));
        }

        @Override
        protected void onSubmit() {
            setResponsePage(new RedirectPage(createResponseUrl()));
        }

        private String createResponseUrl() {
            return parameterMap.get(REDIRECT_URI) + "?code=" + generateCode() + "&state=" + parameterMap.get(STATE);
        }

        private String generateCode() {
            return UUID.randomUUID().toString();
        }
    }

    private class LinkedInSimulatorData implements Serializable {
        String email = "";
        String password = "";
    }
}
