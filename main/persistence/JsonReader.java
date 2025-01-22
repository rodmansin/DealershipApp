package persistence;

import model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.*;

//Reads data from source file and its JSON objects
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads dealership from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Dealership read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDealership(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses dealership from JSON object and returns it
    private Dealership parseDealership(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int carsSold = jsonObject.getInt("carsSold");
        double revenue = jsonObject.getDouble("revenue");
        Dealership d = new Dealership(name, carsSold, revenue);
        addVehicles(d, jsonObject);
        return d;
    }

    // MODIFIES: d
    // EFFECTS: parses vehicles from JSON object and adds them to dealership
    private void addVehicles(Dealership d, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("vehicles");
        for (Object json : jsonArray) {
            JSONObject nextVehicle = (JSONObject) json;
            addVehicle(d, nextVehicle);
        }
    }

    // MODIFIES: d
    // EFFECTS: parses vehicle from JSON object and adds it to dealership
    private void addVehicle(Dealership d, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        int year = jsonObject.getInt("year");
        double price = jsonObject.getDouble("price");
        double mileage = jsonObject.getDouble("mileage");
        d.addVehicle(name, type, year, price, mileage);
    }
}
