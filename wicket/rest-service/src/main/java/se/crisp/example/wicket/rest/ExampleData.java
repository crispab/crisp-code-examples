package se.crisp.example.wicket.rest;

import java.util.ArrayList;
import java.util.List;

public class ExampleData {

    private static final List<Person> personsDB;

    static {
        personsDB = new ArrayList<Person>();
        Address address = new Address();
        address.setStreet("Some street");
        personsDB.add(new Person("Fritz", "Fritzel", address));
        personsDB.add(new Person("Ghan", "Phariounimn", null));
        personsDB.add(new Person("Jan", "Klaasen", null));
        personsDB.add(new Person("Hank", "Plaindweller", null));
    }

    public static final List<Person> getPersons() {
        return personsDB;
    }
}
