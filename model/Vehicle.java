package model;

import org.json.JSONObject;

//Represents a vehicle along with its attributes: name, type, year, price, and mileage
public class Vehicle {
    private String name;
    private String type;
    private int year;
    private double price;
    private double mileage;

    // REQUIRES: name has a non-zero length and price >= 0 and mileage >= 0;
    // EFFECTS: constructs a vehicle with name, type, year, price, and mileage
    public Vehicle(String name, String type, int year, double price, double mileage) {
        this.name = name;
        this.type = type;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
    }

    // SETTER METHODS
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    // GETTER METHODS
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public double getMileage() {
        return mileage;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("type", type);
        json.put("year", year);
        json.put("price", price);
        json.put("mileage", mileage);
        return json;
    }

    // EFFECTS: returns a description of vehicle's attributes
    public String toString() {
        return (name + "\nType: " + type + "\nYear: " + year + "\nPrice: " + price + "\nMileage: " + mileage);
    }
}
