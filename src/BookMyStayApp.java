/**
 * Book My Stay App
 * Use Case 8: Booking History & Reporting
 *
 * This program stores confirmed reservations in a booking history
 * and allows the admin to generate reports from that history.
 *
 * @author Sheik Rizwan
 * @version 8.1
 */

import java.util.*;

// Reservation class
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// Booking History class
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Get booking history
    public List<Reservation> getHistory() {
        return history;
    }
}

// Report Service
class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n===== Booking History Report =====");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            r.displayReservation();
        }

        System.out.println("\nTotal Bookings: " + reservations.size());
    }
}

// Main class (do not change name)
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Booking History");
        System.out.println(" Version 8.1");
        System.out.println("===================================");

        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulated confirmed reservations
        Reservation r1 = new Reservation("RES101", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES102", "Bob", "Double Room");
        Reservation r3 = new Reservation("RES103", "Charlie", "Suite Room");

        // Store bookings in history
        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);

        // Admin requests report
        reportService.generateReport(bookingHistory.getHistory());
    }
}