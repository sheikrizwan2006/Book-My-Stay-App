/**
 * Book My Stay App
 * Use Case 9: Error Handling & Validation
 *
 * This program demonstrates validation and error handling
 * using custom exceptions to prevent invalid bookings.
 *
 * @author Sheik Rizwan
 * @version 9.1
 */

import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {

    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Validator class
class InvalidBookingValidator {

    private Map<String, Integer> inventory;

    public InvalidBookingValidator() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void validateReservation(Reservation reservation) throws InvalidBookingException {

        // Validate room type
        if (!inventory.containsKey(reservation.roomType)) {
            throw new InvalidBookingException("Invalid room type: " + reservation.roomType);
        }

        // Validate availability
        if (inventory.get(reservation.roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + reservation.roomType);
        }

        // Update inventory safely
        inventory.put(reservation.roomType, inventory.get(reservation.roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main class (do not change name)
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Error Handling");
        System.out.println(" Version 9.1");
        System.out.println("===================================");

        InvalidBookingValidator validator = new InvalidBookingValidator();

        // Example reservations
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Luxury Room"); // invalid
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        Reservation[] reservations = {r1, r2, r3};

        for (Reservation r : reservations) {

            try {
                validator.validateReservation(r);
                System.out.println("Booking successful for " + r.guestName +
                        " (" + r.roomType + ")");

            } catch (InvalidBookingException e) {
                System.out.println("Booking failed for " + r.guestName +
                        " : " + e.getMessage());
            }
        }

        validator.displayInventory();
    }
}