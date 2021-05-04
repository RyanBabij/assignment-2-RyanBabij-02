package main.model.seat;

import main.model.booking.Booking;

import java.util.Vector;

public class Seat {

    // All bookings on this Seat.
    Vector<Booking> vBooking;

    // return all bookings on this Seat
    Vector <Booking> getAllBookings()
    {
        return vBooking;
    }
    // clear Bookings vector on this Seat
    boolean clear()
    {
        vBooking.clear();
        return true;
    }
}
