package model;

import ui.DealershipApp;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDealership {
    private Dealership dealership;

    @BeforeEach
    void runBefore() {
        dealership = new Dealership("RODMAN'S DEALERSHIP", 0, 0);
    }

    @Test
    void testConstructor() {
        assertEquals(0, dealership.getSize());
        assertEquals(0, dealership.getCarsSold());
        assertEquals(0, dealership.getRevenue());
    }

    @Test
    void testGetVehicles() {
        assertEquals(0, dealership.getVehicles().size());
        dealership.addVehicle("Cadillac Escalade", "SUV", 2023, 90000, 0);
        assertEquals(1, dealership.getVehicles().size());
    }

    @Test
    void testAddVehicle() {
        assertEquals(0, dealership.getSize());
        dealership.addVehicle("Cadillac Escalade", "SUV", 2023, 90000, 0);
        assertEquals(1, dealership.getSize());
    }

    @Test
    void testRemoveVehicle() {
        dealership.addVehicle("Cadillac Escalade", "SUV", 2023, 90000, 0);
        assertEquals(1, dealership.getSize());
        dealership.removeVehicle(0);
        assertEquals(0, dealership.getSize());
    }

    @Test
    void testSellVehicle() {
        dealership.addVehicle("Cadillac Escalade", "SUV", 2023, 90000, 0);
        assertEquals(1, dealership.getSize());
        assertEquals(0, dealership.getCarsSold());
        assertEquals(0, dealership.getRevenue());
        dealership.sellVehicle(0);
        assertEquals(0, dealership.getSize());
        assertEquals(1, dealership.getCarsSold());
        assertEquals(90000, dealership.getRevenue());
    }

    @Test
    void testVehiclesByYear() {
        assertTrue(dealership.getVehiclesByYear(2023).isEmpty());
        dealership.addVehicle("Cadillac Escalade", "SUV", 2023, 90000, 0);
        assertEquals(1, dealership.getVehiclesByYear(2023).size());
        dealership.addVehicle("Nissan GT-R", "Sports Car", 2010, 60000, 100000);
        assertEquals(1, dealership.getVehiclesByYear(2023).size());
    }

    @Test
    void testVehiclesByType() {
        assertTrue(dealership.getVehiclesByType("SUV").isEmpty());
        dealership.addVehicle("Cadillac Escalade", "SUV", 2023, 90000, 0);
        assertEquals(1, dealership.getVehiclesByType("SUV").size());
        dealership.addVehicle("Nissan GT-R", "Sports Car", 2010, 60000, 100000);
        assertEquals(1, dealership.getVehiclesByType("SUV").size());
    }

    @Test
    void testReset() {
        dealership.addVehicle("Cadillac Escalade", "SUV", 2023, 90000, 0);
        dealership.sellVehicle(0);
        assertEquals(1, dealership.getCarsSold());
        assertEquals(90000, dealership.getRevenue());
        dealership.reset();
        assertEquals(0, dealership.getCarsSold());
        assertEquals(0, dealership.getRevenue());
        dealership.addVehicle("Cadillac Escalade", "SUV", 2023, 90000, 0);
        dealership.reset();
        assertTrue(dealership.getVehicles().isEmpty());
    }
}
