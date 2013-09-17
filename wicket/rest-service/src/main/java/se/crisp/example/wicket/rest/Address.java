package se.crisp.example.wicket.rest;

import org.apache.wicket.util.io.IClusterable;

public class Address implements IClusterable {
    private static Address nullObject = new Address();
    private String street = "";
    private String postcode = "";
    private String city = "";
    private String country = "";

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public static Address nullObject() {
        return nullObject;
    }
}