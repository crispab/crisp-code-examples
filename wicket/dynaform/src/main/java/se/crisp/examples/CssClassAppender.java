package se.crisp.examples;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;

/**
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2012-12-05
 * Time: 15:56
 */
public class CssClassAppender extends Behavior {

    static final String CLASS = "class";
    private String classToAppend = "error";

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        FormComponent<?> formComponent = (FormComponent<?>) component;
        if(! formComponent.isValid()) {
            String cssClass = tag.getAttribute(CLASS);
            if(cssClass == null) {
                tag.put(CLASS, classToAppend);
            } else {
                tag.put(CLASS, cssClass + " " + classToAppend);
            }
        } else {
            tag.remove(CLASS);
        }
    }

}
