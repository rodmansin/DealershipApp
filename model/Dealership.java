package model;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Vehicle;

//Represents a dealership that will contain an array list of vehicles
public class Dealership {
    private String name;
    private ArrayList<Vehicle> vehicles;
    private int size;
    private int carsSold;
    private double revenue;

    // EFFECTS: constructs a dealership
    public Dealership(String name, int carsSold, double revenue) {
        this.name = name;
        vehicles = new ArrayList<Vehicle>();
        size = 0;
        this.carsSold = carsSold;
        this.revenue = revenue;
    }

    // REQUIRES: name != null, type != null, price >= 0, and mileage >= 0
    // MODIFIES: this
    // EFFECTS: adds a new vehicle to dealership and increments size by 1
    public void addVehicle(String name, String type, int year, double price, double mileage) {
        Vehicle vehicle = new Vehicle(name, type, year, price, mileage);
        vehicles.add(vehicle);
        size++;
    }

    // MODIFIES: this
    // EFFECTS: removes a vehicle from dealership and decrements size by 1
    public void removeVehicle(int index) {
        vehicles.remove(index);
        size--;
    }

    // MODIFIES: this
    // EFFECTS: Adds car price to revenue, increments cars sold by 1, and removes
    // vehicle from dealership
    public void sellVehicle(int index) {
        revenue += vehicles.get(index).getPrice();
        carsSold++;
        removeVehicle(index);
    }

    public void reset() {
        revenue = 0;
        carsSold = 0;
        vehicles = new ArrayList<Vehicle>();
    }

    // EFFECTS: returns amount of vehicles in relationship
    public String getName() {
        return name;
    }

    // EFFECTS: returns amount of vehicles in relationship
    public int getSize() {
        return size;
    }

    // EFFECTS: returns total amount of vehicles sold
    public int getCarsSold() {
        return carsSold;
    }

    // EFFECTS: returns total amount of revenue generated through sales
    public double getRevenue() {
        return revenue;
    }

    // EFFECTS: returns all vehicles in dealership
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    // EFFECTS: returns all vehicles in dealership that were produced in a specific
    //          year
    public ArrayList<Vehicle> getVehiclesByYear(int year) {
        ArrayList<Vehicle> yearVehicles = new ArrayList<Vehicle>();
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getYear() == year) {
                yearVehicles.add(vehicles.get(i));
            }
        }
        return yearVehicles;
    }

    // EFFECTS: returns all vehicles in dealership that are of one type
    public ArrayList<Vehicle> getVehiclesByType(String type) {
        ArrayList<Vehicle> typeVehicles = new ArrayList<Vehicle>();
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getType().equals(type)) {
                typeVehicles.add(vehicles.get(i));
            }
        }
        return typeVehicles;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("carsSold", carsSold);
        json.put("revenue", revenue);
        json.put("vehicles", vehiclesToJson());
        return json;
    }

    // EFFECTS: returns things in this dealership as a JSON array
    private JSONArray vehiclesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Vehicle v: vehicles) {
            jsonArray.put(v.toJson());
        }
        return jsonArray;
    }

}
