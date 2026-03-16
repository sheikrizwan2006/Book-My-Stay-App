/**
 * Book My Stay App
 * Use Case 12: Data Persistence & System Recovery
 *
 * Demonstrates saving system state to a file using serialization
 * and restoring it when the application restarts.
 *
 * @author Sheik Rizwan
 * @version 12.1
 */

import java.io.*;
import java.util.*;

// Reservation class
class Reservation implements Serializable {

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// System state containing inventory + booking history
class SystemState implements Serializable {

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState() {
        inventory = new HashMap<>();
        bookingHistory = new ArrayList<>();
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state to file
    public void save(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state.");
        }
    }

    // Load state from file
    public SystemState load() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();
            System.out.println("System state restored from file.");
            return state;

        } catch (Exception e) {

            System.out.println("No previous data found. Starting fresh system.");
            return new SystemState();
        }
    }
}

// Main class (name unchanged)
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Data Persistence");
        System.out.println(" Version 12.1");
        System.out.println("===================================");

        PersistenceService persistence = new PersistenceService();

        // Load previous system state
        SystemState state = persistence.load();

        // If system started fresh, initialize inventory
        if (state.inventory.isEmpty()) {
            state.inventory.put("Single Room", 2);
            state.inventory.put("Double Room", 1);
            state.inventory.put("Suite Room", 1);
        }

        // Simulated booking
        Reservation r1 = new Reservation("RES201", "Alice", "Single Room");

        state.bookingHistory.add(r1);

        int available = state.inventory.get("Single Room");
        state.inventory.put("Single Room", available - 1);

        // Display current state
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : state.inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("\nBooking History:");
        for (Reservation r : state.bookingHistory) {
            r.display();
        }

        // Save state before shutdown
        persistence.save(state);
    }
}