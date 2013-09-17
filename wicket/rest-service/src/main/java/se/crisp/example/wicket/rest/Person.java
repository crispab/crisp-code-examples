package se.crisp.example.wicket.rest;

import org.apache.wicket.util.io.IClusterable;

import java.util.Date;

public class Person implements IClusterable {
    private String name;
    private String lastName;
    private Date dateOfBirth;
    private Address address;

    public Person(String name, String lastName, Address address) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }


    public Address getAddress() {
        return address == null ? Address.nullObject() : address;
    }
}
