/**
 * Book My Stay App
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Demonstrates safe cancellation of confirmed bookings using
 * stack-based rollback and inventory restoration.
 *
 * @author Sheik Rizwan
 * @version 10.1
 */

import java.util.*;

// Reservation class
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;
    boolean active;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }
}

// Inventory Service
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Booking History
class BookingHistory {

    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.reservationId, r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }

    public void markCancelled(String id) {
        if (reservations.containsKey(id)) {
            reservations.get(id).active = false;
        }
    }
}

// Cancellation Service
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              InventoryService inventory) {

        Reservation reservation = history.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation failed: Reservation does not exist.");
            return;
        }

        if (!reservation.active) {
            System.out.println("Cancellation failed: Reservation already cancelled.");
            return;
        }

        // Record released room ID
        rollbackStack.push(reservation.roomId);

        // Restore inventory
        inventory.incrementRoom(reservation.roomType);

        // Update booking history
        history.markCancelled(reservationId);

        System.out.println("Reservation cancelled successfully.");
        System.out.println("Released Room ID: " + reservation.roomId);
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (Recently Released Rooms):");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

// Main class (name unchanged)
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Booking Cancellation");
        System.out.println(" Version 10.1");
        System.out.println("===================================");

        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService();

        // Simulated confirmed bookings
        Reservation r1 = new Reservation("RES101", "Alice", "Single Room", "SI-001");
        Reservation r2 = new Reservation("RES102", "Bob", "Double Room", "DO-001");

        history.addReservation(r1);
        history.addReservation(r2);

        // Guest cancels booking
        cancelService.cancelBooking("RES101", history, inventory);

        // Attempt invalid cancellation
        cancelService.cancelBooking("RES999", history, inventory);

        cancelService.displayRollbackStack();

        inventory.displayInventory();
    }
}