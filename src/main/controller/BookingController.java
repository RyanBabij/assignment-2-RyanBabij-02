package main.controller;

import main.model.booking.Booking;
import main.model.interfaces.Account;

import java.util.Date;
import java.util.Vector;

public class BookingController
{
    Vector<Booking> vBooking;

    // create a Booking object from the parameters
    Booking create(Date date, int hour, int duration)
    {
        return null;
    }
    // get all Bookings in system
    Vector <Booking> getAll()
    {
        return null;
    }
    // get all Bookings linked to Account
    Vector <Booking> getAccountBookings (Account account)
    {
        return null;
    }
}
