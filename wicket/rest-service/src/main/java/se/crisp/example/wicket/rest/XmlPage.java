package se.crisp.example.wicket.rest;

import org.apache.wicket.markup.MarkupType;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import java.util.List;

public class XmlPage extends WebPage {

    public XmlPage() {
        add(new PersonsListView("persons", ExampleData.getPersons()));
    }

    @Override
    public MarkupType getMarkupType() {
        return new MarkupType("xml", "text/xml");
    }

    private static final class PersonsListView extends ListView<Person> {

        public PersonsListView(String id, List<Person> list) {
            super(id, list);
        }

        @Override
        protected void populateItem(ListItem<Person> item) {
            Person person = item.getModelObject();
            item.add(new Label("firstName", person.getName()));
            item.add(new Label("lastName", person.getLastName()));
            WebMarkupContainer address = new WebMarkupContainer("address");
            address.add(new Label("street",     person.getAddress().getStreet()));
            item.add(address);
        }
    }
}