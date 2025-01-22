package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

//The application representing the dealership
public class DealershipAppGUI extends JFrame {
private static final String JSON_STORE = "./data/dealership.json";
private Dealership dealership;
private JsonWriter jsonWriter;
private JsonReader jsonReader;
private CardLayout cardLayout;
private JPanel mainPanel;
private JTextArea statisticsArea;
private JTextArea viewVehicleListArea;

public DealershipAppGUI() {
    dealership = new Dealership("RODMAN'S DEALERSHIP", 0, 0);
    jsonWriter = new JsonWriter(JSON_STORE);
    jsonReader = new JsonReader(JSON_STORE);
    
    setTitle("Dealership System");
    setSize(1000, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);

    addHomePage();
    addViewStatisticsPage();
    addAddVehiclePage();
    addRemoveVehiclePage();
    addSellVehiclePage();
    addViewVehiclesPage();

    add(mainPanel);
    setVisible(true);
}

private void addHomePage() {
    JPanel homePanel = new JPanel(new GridLayout(0, 1));
    ImageIcon carImage = new ImageIcon("./src/main/ui/car.png");
    JLabel imageLabel = new JLabel(carImage, SwingConstants.CENTER);
    imageLabel.setBorder(new EmptyBorder(20, 0, 0, 0));
    homePanel.add(imageLabel); 
    homePanel.add(new JLabel("Welcome to the dealership system!", SwingConstants.CENTER));
    JButton viewVehiclesButton = new JButton("View Vehicles");
    viewVehiclesButton.addActionListener(e -> {
        updateVehicleList(viewVehicleListArea, "ALL");
        cardLayout.show(mainPanel, "ViewVehicles");
    });
    homePanel.add(viewVehiclesButton);
    
    JButton addButton = new JButton("Add Vehicle");
    addButton.addActionListener(e -> cardLayout.show(mainPanel, "AddVehicle"));
    homePanel.add(addButton);

    JButton removeButton = new JButton("Remove Vehicle");
    removeButton.addActionListener(e -> {
        updateVehicleList(viewVehicleListArea, "ALL");  
        cardLayout.show(mainPanel, "RemoveVehicle"); 
    });
    homePanel.add(removeButton);
    
    JButton sellButton = new JButton("Sell Vehicle");
    sellButton.addActionListener(e -> {
        updateVehicleList(viewVehicleListArea, "ALL");  
        cardLayout.show(mainPanel, "SellVehicle"); 
    });
    homePanel.add(sellButton);

    JButton viewStatsButton = new JButton("View Statistics");
    viewStatsButton.addActionListener(e -> {
        updateStatistics();
        cardLayout.show(mainPanel, "ViewStatistics");
    });
    homePanel.add(viewStatsButton);

    JButton saveButton = new JButton("Save Dealership");
    saveButton.addActionListener(e -> saveDealership());
    homePanel.add(saveButton);

    JButton loadButton = new JButton("Load Dealership");
    loadButton.addActionListener(e -> loadDealership());
    homePanel.add(loadButton);

    JButton resetButton = new JButton("Reset");
    resetButton.addActionListener(e -> reset());
    homePanel.add(resetButton);

    JButton quitButton = new JButton("Quit");
    quitButton.addActionListener(e -> System.exit(0));
    homePanel.add(quitButton);

    mainPanel.add(homePanel, "Home");
}

private void addViewVehiclesPage() {
    JPanel viewVehiclesPanel = new JPanel(new BorderLayout());
    
    viewVehicleListArea = new JTextArea();
    viewVehicleListArea.setEditable(false);
    viewVehiclesPanel.add(new JScrollPane(viewVehicleListArea), BorderLayout.CENTER);
    
    JPanel filterPanel = new JPanel(new GridLayout(1, 5));
    
    JButton viewAllButton = new JButton("View All Vehicles");
    viewAllButton.addActionListener(e -> updateVehicleList(viewVehicleListArea, "ALL"));
    filterPanel.add(viewAllButton);
    
    JButton viewSupercarsButton = new JButton("View Supercars");
    viewSupercarsButton.addActionListener(e -> updateVehicleList(viewVehicleListArea, "Supercar"));
    filterPanel.add(viewSupercarsButton);
    
    JButton viewSportsCarsButton = new JButton("View Sports Cars");
    viewSportsCarsButton.addActionListener(e -> updateVehicleList(viewVehicleListArea, "Sports Car"));
    filterPanel.add(viewSportsCarsButton);
    
    JButton viewSUVsButton = new JButton("View SUVs");
    viewSUVsButton.addActionListener(e -> updateVehicleList(viewVehicleListArea, "SUV"));
    filterPanel.add(viewSUVsButton);
    
    JButton viewMotorcyclesButton = new JButton("View Motorcycles");
    viewMotorcyclesButton.addActionListener(e -> updateVehicleList(viewVehicleListArea, "Motorcycle"));
    filterPanel.add(viewMotorcyclesButton);
    
    viewVehiclesPanel.add(filterPanel, BorderLayout.NORTH);
    
    JButton backButton = new JButton("Back to Menu");
    backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
    viewVehiclesPanel.add(backButton, BorderLayout.SOUTH);
    
    mainPanel.add(viewVehiclesPanel, "ViewVehicles");
}

private void addViewStatisticsPage() {
    JPanel statsPanel = new JPanel(new BorderLayout());
    statisticsArea = new JTextArea();
    statisticsArea.setEditable(false);
    statsPanel.add(statisticsArea, BorderLayout.CENTER);

    JButton backButton = new JButton("Back to Menu");
    backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
    statsPanel.add(backButton, BorderLayout.SOUTH);

    mainPanel.add(statsPanel, "ViewStatistics");
}

private void addAddVehiclePage() {
    JPanel addVehiclePanel = new JPanel(new GridLayout(0, 2));

    JTextField nameField = new JTextField();
    addVehiclePanel.add(new JLabel("Name of Vehicle:"));
    addVehiclePanel.add(nameField);

    JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Supercar", "Sports Car", "SUV", "Motorcycle"});
    addVehiclePanel.add(new JLabel("Type:"));
    addVehiclePanel.add(typeComboBox);

    JTextField yearField = new JTextField();
    addVehiclePanel.add(new JLabel("Year of Production:"));
    addVehiclePanel.add(yearField);

    JTextField priceField = new JTextField();
    addVehiclePanel.add(new JLabel("Price ($):"));
    addVehiclePanel.add(priceField);

    JTextField mileageField = new JTextField();
    addVehiclePanel.add(new JLabel("Mileage (km):"));
    addVehiclePanel.add(mileageField);

    JButton addButton = new JButton("Add Vehicle");
    addButton.addActionListener(e -> {
        String name = nameField.getText();
        String type = (String) typeComboBox.getSelectedItem();
        int year = Integer.parseInt(yearField.getText());
        double price = Double.parseDouble(priceField.getText());
        double mileage = Double.parseDouble(mileageField.getText());
        dealership.addVehicle(name, type, year, price, mileage);
        JOptionPane.showMessageDialog(this, name + " has been added to the dealership.");
    });

    JButton backButton = new JButton("Back to Menu");
    backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

    addVehiclePanel.add(addButton);
    addVehiclePanel.add(backButton);

    mainPanel.add(addVehiclePanel, "AddVehicle");
}

private void addRemoveVehiclePage() {
    
    JPanel removeVehiclePanel = new JPanel(new BorderLayout());

    JTextArea vehicleListArea = new JTextArea();
    vehicleListArea.setEditable(false);
    removeVehiclePanel.add(new JScrollPane(vehicleListArea), BorderLayout.CENTER);
    
    updateVehicleList(vehicleListArea, "ALL");

    JButton refreshButton = new JButton("Refresh");
    refreshButton.addActionListener(e -> updateVehicleList(vehicleListArea, "ALL"));

    JButton removeButton = new JButton("Remove Vehicle");
    removeButton.addActionListener(e -> {
        String input = JOptionPane.showInputDialog(this, "Enter vehicle number to remove:");
        if (input != null) {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < dealership.getSize()) {
                dealership.removeVehicle(index);
                JOptionPane.showMessageDialog(this, "Vehicle has been removed.");
                updateVehicleList(vehicleListArea, "ALL");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid selection.");
            }
        }
    });

    JButton backButton = new JButton("Back to Menu");
    backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(refreshButton, BorderLayout.WEST);
    topPanel.add(backButton, BorderLayout.EAST);

    removeVehiclePanel.add(topPanel, BorderLayout.NORTH);
    removeVehiclePanel.add(removeButton, BorderLayout.SOUTH);

    mainPanel.add(removeVehiclePanel, "RemoveVehicle");

}

private void addSellVehiclePage() {
    JPanel sellVehiclePanel = new JPanel(new BorderLayout());

    JTextArea vehicleListArea = new JTextArea();
    vehicleListArea.setEditable(false);
    sellVehiclePanel.add(new JScrollPane(vehicleListArea), BorderLayout.CENTER);

    JButton refreshButton = new JButton("Refresh");
    refreshButton.addActionListener(e -> updateVehicleList(vehicleListArea, "ALL"));

    JButton sellButton = new JButton("Sell Vehicle");
    sellButton.addActionListener(e -> {
        String input = JOptionPane.showInputDialog(this, "Enter vehicle number to sell:");
        if (input != null) {
            int index = Integer.parseInt(input) - 1;  
            if (index >= 0 && index < dealership.getSize()) {
                dealership.sellVehicle(index);
                JOptionPane.showMessageDialog(this, "Vehicle has been sold.");
                updateVehicleList(vehicleListArea, "ALL");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid selection.");
            }
        }
    });

    // Back Button to go back to the home menu
    JButton backButton = new JButton("Back to Menu");
    backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

    // Panel for the buttons (Refresh, Sell, Back)
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(refreshButton, BorderLayout.WEST);
    topPanel.add(backButton, BorderLayout.EAST);

    // Add components to sellVehiclePanel
    sellVehiclePanel.add(topPanel, BorderLayout.NORTH);
    sellVehiclePanel.add(sellButton, BorderLayout.SOUTH);

    // Add Sell Vehicle page to mainPanel
    mainPanel.add(sellVehiclePanel, "SellVehicle");

    // Refresh vehicle list on page load
    updateVehicleList(vehicleListArea, "ALL");
}

private void updateStatistics() {
    statisticsArea.setText("Cars in dealership: " + dealership.getSize() +
                           "\nCars sold: " + dealership.getCarsSold() +
                           "\nRevenue: $" + dealership.getRevenue());
}

private void updateVehicleList(JTextArea vehicleListArea, String type) {
StringBuilder sb = new StringBuilder();
ArrayList<Vehicle> vehicles;

if (type.equals("ALL")) {
    vehicles = dealership.getVehicles();
} else {
    vehicles = dealership.getVehiclesByType(type);
}

for (int i = 0; i < vehicles.size(); i++) {
    sb.append("(").append(i + 1).append(") ").append(vehicles.get(i).toString()).append("\n");
}
vehicleListArea.setText(sb.toString());
}
private void saveDealership() {
    try {
        jsonWriter.open();
        jsonWriter.write(dealership);
        jsonWriter.close();
        JOptionPane.showMessageDialog(this, "Dealership saved to " + JSON_STORE);
    } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE);
    }
}

private void loadDealership() {
    try {
        dealership = jsonReader.read();
        JOptionPane.showMessageDialog(this, "Loaded dealership from " + JSON_STORE);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE);
    }
}

private void reset() {
    dealership.reset();
    JOptionPane.showMessageDialog(this, "Dealership has been reset.");
}
}

