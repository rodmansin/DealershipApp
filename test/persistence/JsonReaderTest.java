package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Dealership d = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDealership() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDealership.json");
        try {
            Dealership d = reader.read();
            assertEquals("RODMAN'S DEALERSHIP", d.getName());
            assertEquals(0, d.getSize());
            assertEquals(0, d.getCarsSold());
            assertEquals(0, d.getRevenue());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDealership() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDealership.json");
        try {
            Dealership d = reader.read();
            assertEquals("RODMAN'S DEALERSHIP", d.getName());
            assertEquals(2, d.getSize());
            assertEquals(0, d.getCarsSold());
            assertEquals(0, d.getRevenue());
            List<Vehicle> vehicles = d.getVehicles();           
            assertEquals(2, vehicles.size());
            checkVehicle("Cadillac Escalade", "SUV", 2023, 90000, 0, vehicles.get(0));
            checkVehicle("Nissan GT-R", "Sports Car", 2010, 60000, 100000, vehicles.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
