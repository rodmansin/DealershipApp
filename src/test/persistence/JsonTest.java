package persistence;

import model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkVehicle(String name, String type, int year, double price, double mileage, Vehicle vehicle) {
        assertEquals(name, vehicle.getName());
        assertEquals(type, vehicle.getType());
        assertEquals(year, vehicle.getYear());
        assertEquals(price, vehicle.getPrice());
        assertEquals(mileage, vehicle.getMileage());
    }
}
