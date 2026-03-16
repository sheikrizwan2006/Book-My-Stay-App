/**
 * Book My Stay App
 * Use Case 7: Add-On Service Selection
 *
 * This program demonstrates how optional services can be added
 * to an existing reservation without modifying booking or inventory logic.
 *
 * @author Sheik Rizwan
 * @version 7.1
 */

import java.util.*;

// Add-On Service class
class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println(serviceName + " - $" + cost);
    }
}

// Manager class to handle add-on services
class AddOnServiceManager {

    // Map<ReservationID, List<Service>>
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println("Added service '" + service.getServiceName() +
                "' to reservation " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");

        for (AddOnService s : services) {
            s.displayService();
        }
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// Main class (do not change name)
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Add-On Services");
        System.out.println(" Version 7.1");
        System.out.println("===================================");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Example reservation ID
        String reservationId = "RES-101";

        // Available services
        AddOnService breakfast = new AddOnService("Breakfast", 20);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 40);
        AddOnService spa = new AddOnService("Spa Access", 50);

        // Guest selects services
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, airportPickup);
        manager.addService(reservationId, spa);

        // Display selected services
        manager.displayServices(reservationId);

        // Calculate total additional cost
        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Add-On Cost: $" + totalCost);
    }
}