package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestVehicle {
    Vehicle testSupercar;

    @BeforeEach
    void runBefore() {
        testSupercar = new Vehicle("Lamborghini Huracan", "Supercar", 2024, 260000, 0);
    }

    @Test
    void testConstructor() {
        assertEquals("Lamborghini Huracan", testSupercar.getName());
        assertEquals("Supercar", testSupercar.getType());
        assertEquals(2024, testSupercar.getYear());
        assertEquals(260000, testSupercar.getPrice());
        assertEquals(0, testSupercar.getMileage());
    }

    // SETTER AND GETTER METHOD TESTS
    @Test
    void testSetName() {
        assertEquals("Lamborghini Huracan", testSupercar.getName());
        testSupercar.setName("Porsche Macan");
        assertEquals("Porsche Macan", testSupercar.getName());
    }

    @Test
    void testSetType() {
        assertEquals("Supercar", testSupercar.getType());
        testSupercar.setType("Motorcycle");
        assertEquals("Motorcycle", testSupercar.getType());
    }

    @Test
    void testSetYear() {
        assertEquals(2024, testSupercar.getYear());
        testSupercar.setYear(2025);
        assertEquals(2025, testSupercar.getYear());
    }

    @Test
    void testSetPrice() {
        assertEquals(260000, testSupercar.getPrice());
        testSupercar.setPrice(70000);
        assertEquals(70000, testSupercar.getPrice());
    }

    @Test
    void testSetMileage() {
        assertEquals(0, testSupercar.getMileage());
        testSupercar.setMileage(10000);
        assertEquals(10000, testSupercar.getMileage());
    }

    @Test
    void testDescription() {
        assertEquals("Lamborghini Huracan\nType: Supercar\nYear: 2024\nPrice: 260000.0\nMileage: 0.0",
                testSupercar.toString());
    }

}
