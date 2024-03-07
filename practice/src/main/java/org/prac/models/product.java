package org.prac.models;

public class product {
    private final String name;
    private final double price;

    public product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
