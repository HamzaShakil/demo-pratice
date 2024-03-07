package org.prac.models;

public class checkOutInfo {
    private final String firstName;
    private final String lastName;
    private final String postalCode;

    public checkOutInfo(String firstName, String lastName, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
