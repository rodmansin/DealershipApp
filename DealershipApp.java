package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import javax.swing.event.SwingPropertyChangeSupport;
import model.*;

//The application representing the dealership
public class DealershipApp {
    private static final String JSON_STORE = "./data/dealership.json";
    private Dealership dealership;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the dealership app
    public DealershipApp() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runDealership();
    }

    // MODIFIES: this
    // EFFECTS: prompts user for input on an action then processes it
    private void runDealership() {
        System.out.println("Welcome to the dealership system!");
        dealership = new Dealership("RODMAN'S DEALERSHIP", 0, 0);
        String option = "";
        do {
            System.out.println("\n--- " + dealership.getName() + " ---");
            displayMenu();
            input = new Scanner(System.in);
            option = input.nextLine().toLowerCase();
            selectMenuOption(option);
        } while (!option.equals("q"));
    }

    private void selectMenuOption(String option) {
        if (option.equals("a")) {
            viewVehiclesMenu();
        } else if (option.equals("b")) {
            addVehicleMenu();
        } else if (option.equals("c")) {
            removeVehicleMenu();
        } else if (option.equals("d")) {
            sellVehicleMenu();
        } else if (option.equals("e")) {
            viewStatistics();
        } else if (option.equals("f")) {
            reset();
        } else if (option.equals("g")) {
            saveDealership();
        } else if (option.equals("h")) {
            loadDealership();
        } else if (option.equals("q")) {
            System.out.println("Exiting dealership system.");
        } else {
            System.out.println("Invalid option, please enter one of the listed options: ");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nDEALERSHIP MAIN MENU");
        System.out.println("Please select one of the following options below by entering its letter: ");
        System.out.println("(a) View vehicles");
        System.out.println("(b) Add vehicle listing");
        System.out.println("(c) Remove vehicle listing");
        System.out.println("(d) Sell vehicle");
        System.out.println("(e) View statistics");
        System.out.println("(f) Reset");
        System.out.println("(g) Save dealership");
        System.out.println("(h) Load dealership");
        System.out.println("(q) Quit");
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter in details of a vehicle, then adds a vehicle
    // with those attributes into dealership
    private void addVehicleMenu() {
        System.out.println("\nADD VEHICLE MENU");
        System.out.println("Name of Vehicle: ");
        String name = input.nextLine();
        System.out.println("Type: \n(a) Supercar\n" + "(b) Sports Car\n" + "(c) SUV\n" + "(d) Motorcycle");
        String type = input.nextLine();
        type = inputVehicleType(type);
        System.out.println("Year of Production: ");
        int year = input.nextInt();
        System.out.println("Price($): ");
        double price = input.nextDouble();
        System.out.println("Mileage(km): ");
        double mileage = input.nextDouble();
        dealership.addVehicle(name, type, year, price, mileage);
        System.out.println(name + " has been added to the dealership.");
    }

    // MODIFIES: this
    // EFFECTS: displays a list of all vehicles, then prompts user to select a
    // vehicle and removes it.
    private void removeVehicleMenu() {
        System.out.println("\nREMOVE VEHICLE MENU");
        System.out.println("Select a vehicle to remove by entering its corresponding number (0 to exit): ");
        printVehicles(dealership.getVehicles());
        int index = input.nextInt();
        while (index < 0 || index > dealership.getSize()) {
            System.out.println("Invalid number, enter one in range: ");
            index = input.nextInt();
        }
        if (index != 0) {
            index--; // User enters one number higher due to list format, decrement for actual index
                     // value
            dealership.removeVehicle(index);
        }
        System.out.println("Vehicle has been removed from dealership.");
    }

    // MODIFIES: this
    // EFFECTS: displays a list of all vehicles, then prompts user to select a
    // vehicle to confirm sale from dealership
    private void sellVehicleMenu() {
        System.out.println("\nSELL VEHICLE MENU");
        System.out.println("Select a vehicle to sell by entering its corresponding number (0 to exit): ");
        printVehicles(dealership.getVehicles());
        int index = input.nextInt();
        while (index < 0 || index > dealership.getSize()) {
            System.out.println("Invalid number, enter one in range: ");
            index = input.nextInt();
        }
        if (index != 0) {
            index--; // User enters one number higher due to list format, decrement for actual index
            dealership.sellVehicle(index);
        }
        System.out.println("Congratulations on sale! Statistics have been updated and car has been removed.");
    }

    // REQUIRES: vehicles != null
    // EFFECTS: prints a list of vehicles
    private void viewVehiclesMenu() {
        System.out.println("\nVIEW VEHICLES MENU\nWhat vehicles would you like to view?");
        System.out.println("(a) All vehicles\n(b) Vehicles by year\n(c) Vehicles by type\n(d) Return To Menu");
        String option;
        do {
            option = input.nextLine().toLowerCase();
            if (option.equals("a")) {
                if (dealership.getVehicles().isEmpty()) {
                    System.out.println("Dealership contains no cars at this moment.");
                } else {
                    System.out.println("\nALL DEALERSHIP VEHICLES");
                    printVehicles(dealership.getVehicles());
                }
            } else if (option.equals("b")) {
                viewVehiclesByYear();
            } else if (option.equals("c")) {
                viewVehiclesByType();
            } else if (option.equals("d")) {
                System.out.println("Returning to main menu.");
            } else {
                System.out.println("Invalid option, please enter one of the given options: ");
                option = input.nextLine().toLowerCase();
            }
        } while (!(option.equals("a") || option.equals("b") || option.equals("c") || option.equals("d")));
    }

    // EFFECTS: prompts user to input a year, then prints out a list of vehicles in
    // the dealership produced in that year
    private void viewVehiclesByYear() {
        System.out.println("\nEnter year: ");
        int year = input.nextInt();
        if (dealership.getVehiclesByYear(year).isEmpty()) {
            System.out.println("Sorry, there are no cars in the dealership produced in that year.");
        } else {
            System.out.println("VEHICLES PRODUCED IN " + year + ": ");
            printVehicles(dealership.getVehiclesByYear(year));
        }
    }

    // EFFECTS: prompts user for an input for a vehicle type, then prints out a list
    // of vehicles of that type in the dealership
    private void viewVehiclesByType() {
        System.out.println("\nSelect a type below: ");
        System.out.println("(a) Supercar\n(b) Sports Car\n(c) SUV\n(d) Motorcycle\n(e) Exit");
        String type = input.nextLine().toLowerCase();
        type = inputVehicleType(type);
        ArrayList<Vehicle> vehiclesOfType = dealership.getVehiclesByType(type);
        if (vehiclesOfType.isEmpty()) {
            System.out.println("There are no vehicles of this type.");
        } else {
            System.out.println(type + " VEHICLES: ");
            printVehicles(vehiclesOfType);
        }
    }

    // REQUIRES: vehicles != null
    // EFFECTS: prints a list of vehicles
    private void printVehicles(ArrayList<Vehicle> vehicles) {
        System.out.println("---------------------");
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.println("(" + (i + 1) + ") " + vehicles.get(i).toString());
            System.out.println("---------------------");
        }
    }

    // EFFECTS: takes in a user selected option for type of vehicle, then returns
    // the corresponding string representation of type
    private String inputVehicleType(String option) {
        String vehicleType = "";
        do {
            if (option.equals("a")) {
                vehicleType = "Supercar";
            } else if (option.equals("b")) {
                vehicleType = "Sports Car";
            } else if (option.equals("c")) {
                vehicleType = "SUV";
            } else if (option.equals("d")) {
                vehicleType = "Motorcycle";
            } else {
                System.out.println("Invalid option, enter one of the given options: ");
                option = input.nextLine();
            }
        } while (!(option.equals("a") || option.equals("b") || option.equals("c") || option.equals("d")));
        return vehicleType;
    }

    // EFFECTS: prints dealership's cars, cars sold, and revenue
    private void viewStatistics() {
        System.out.println("\nDEALERSHIP STATISTICS");
        System.out.println("Cars in dealership: " + dealership.getSize());
        System.out.println("Cars sold: " + dealership.getCarsSold());
        System.out.println("Revenue: $" + dealership.getRevenue());
    }

    private void reset() {
        System.out.println("RESET COMPLETE: Vehicles have been cleared and statistics have been set to 0.");
        dealership.reset();
    }

    // EFFECTS: saves the dealership to file
    private void saveDealership() {
        try {
            jsonWriter.open();
            jsonWriter.write(dealership);
            jsonWriter.close();
            System.out.println("Saved " + dealership.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads dealership from file
    private void loadDealership() {
        try {
            dealership = jsonReader.read();
            System.out.println("Loaded " + dealership.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}