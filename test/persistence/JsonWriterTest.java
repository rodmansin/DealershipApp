package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Dealership d = new Dealership("RODMAN'S DEALERSHIP", 0, 0);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDealership() {
        try {
            Dealership d = new Dealership("RODMAN'S DEALERSHIP", 0, 0);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDealership.json");
            writer.open();
            writer.write(d);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyDealership.json");
            d = reader.read();
            assertEquals("RODMAN'S DEALERSHIP", d.getName());
            assertEquals(0, d.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDealership() {
        try {
            Dealership d = new Dealership("RODMAN'S DEALERSHIP", 0, 0);
            d.addVehicle("Cadillac Escalade", "SUV", 2023, 90000, 0);
            d.addVehicle("Nissan GT-R", "Sports Car", 2010, 60000, 100000);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDealership.json");
            writer.open();
            writer.write(d);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDealership.json");
            d = reader.read();
            assertEquals("RODMAN'S DEALERSHIP", d.getName());
            List<Vehicle> vehicles = d.getVehicles();
            assertEquals(2, vehicles.size());
            checkVehicle("Cadillac Escalade", "SUV", 2023, 90000, 0, vehicles.get(0));
            checkVehicle("Nissan GT-R", "Sports Car", 2010, 60000, 100000, vehicles.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
