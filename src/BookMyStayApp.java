/**
 * Book My Stay App
 * Use Case 3: Centralized Room Inventory Management
 *
 * This program demonstrates how room availability can be managed
 * using a centralized HashMap instead of scattered variables.
 *
 * @author Sheik Rizwan
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

// Inventory class to manage room availability
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor to initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Method to get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Method to display inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Inventory Setup (Version 3.1)\n");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nChecking availability of Single Room:");
        System.out.println("Available: " + inventory.getAvailability("Single Room"));

        System.out.println("\nUpdating availability of Single Room to 4...");
        inventory.updateAvailability("Single Room", 4);

        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();
    }
}