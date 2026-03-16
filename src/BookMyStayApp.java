/**
 * Book My Stay App
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * This program confirms booking requests by allocating unique room IDs
 * and updating inventory while preventing double-booking.
 *
 * @author Sheik Rizwan
 * @version 6.1
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

// Booking request queue (FIFO)
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

// Inventory Service
class InventoryService {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// Booking Service
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocationMap = new HashMap<>();

    public void processReservation(Reservation reservation, InventoryService inventory) {

        String roomType = reservation.roomType;

        if (!inventory.isAvailable(roomType)) {
            System.out.println("No rooms available for " + reservation.guestName);
            return;
        }

        // Generate unique room ID
        String roomId;
        do {
            roomId = roomType.substring(0,2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0,4);
        } while (allocatedRoomIds.contains(roomId));

        // Store room ID
        allocatedRoomIds.add(roomId);

        roomAllocationMap.putIfAbsent(roomType, new HashSet<>());
        roomAllocationMap.get(roomType).add(roomId);

        // Update inventory
        inventory.decrementRoom(roomType);

        // Confirm reservation
        System.out.println("Reservation Confirmed");
        System.out.println("Guest: " + reservation.guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Allocated Room ID: " + roomId);
        System.out.println("Remaining Rooms: " + inventory.getAvailability(roomType));
        System.out.println("----------------------------------");
    }
}

// Main class (name not changed)
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Room Allocation");
        System.out.println(" Version 6.1");
        System.out.println("===================================");

        BookingRequestQueue queue = new BookingRequestQueue();
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService();

        // Booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("David", "Single Room"));

        // Process requests FIFO
        while (queue.hasRequests()) {
            Reservation r = queue.getNextRequest();
            bookingService.processReservation(r, inventory);
        }
    }
}