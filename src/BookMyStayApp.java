/**
 * Book My Stay App
 * UC1 + UC2 Implementation
 *
 * UC1: Application Entry & Welcome Message
 * UC2: Basic Room Types & Static Availability
 *
 * @author Sheik Rizwan
 * @version 2.1
 */

// Abstract Room class
abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq.ft");
        System.out.println("Price     : $" + price);
    }
}

// Single Room class
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100.0);
    }
}

// Double Room class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180.0);
    }
}

// Suite Room class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 500, 300.0);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        // UC1: Welcome Message
        System.out.println("===================================");
        System.out.println("        Welcome to Book My Stay");
        System.out.println("      Hotel Booking System v2.1");
        System.out.println("===================================");

        // UC2: Room Initialization
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        System.out.println("\nRoom Details & Availability\n");

        single.displayRoomDetails();
        System.out.println("Available : " + singleAvailability);
        System.out.println("-----------------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available : " + doubleAvailability);
        System.out.println("-----------------------------------");

        suite.displayRoomDetails();
        System.out.println("Available : " + suiteAvailability);
        System.out.println("-----------------------------------");

        System.out.println("Application execution completed.");
    }
}