/**
 * Book My Stay App
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * Demonstrates thread-safe booking processing using synchronized methods
 * to prevent race conditions and double room allocation.
 *
 * @author Sheik Rizwan
 * @version 11.1
 */

import java.util.*;

// Reservation class
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Inventory Service (thread-safe)
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    // Critical section
    public synchronized boolean allocateRoom(String roomType, String guestName) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available <= 0) {
            System.out.println("Booking failed for " + guestName +
                    " (No " + roomType + " available)");
            return false;
        }

        inventory.put(roomType, available - 1);

        System.out.println("Room allocated to " + guestName +
                " | Room Type: " + roomType +
                " | Remaining: " + inventory.get(roomType));

        return true;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory State:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Booking Processor Thread
class BookingProcessor extends Thread {

    private Queue<Reservation> queue;
    private InventoryService inventory;

    public BookingProcessor(Queue<Reservation> queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation reservation;

            // synchronized queue access
            synchronized (queue) {

                if (queue.isEmpty()) {
                    break;
                }

                reservation = queue.poll();
            }

            if (reservation != null) {
                inventory.allocateRoom(reservation.roomType, reservation.guestName);
            }
        }
    }
}

// Main class (name unchanged)
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Concurrent Booking");
        System.out.println(" Version 11.1");
        System.out.println("===================================");

        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulated guest booking requests
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Single Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room"));
        bookingQueue.add(new Reservation("David", "Double Room"));
        bookingQueue.add(new Reservation("Eva", "Suite Room"));

        InventoryService inventory = new InventoryService();

        // Multiple threads simulating concurrent guests
        BookingProcessor t1 = new BookingProcessor(bookingQueue, inventory);
        BookingProcessor t2 = new BookingProcessor(bookingQueue, inventory);
        BookingProcessor t3 = new BookingProcessor(bookingQueue, inventory);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();
    }
}