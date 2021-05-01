package main.model.account;

import main.model.booking.Booking;
import main.model.interfaces.Account;

import java.util.Vector;

public class Admin {

    String getReport()
    {
        return null;
    }
    boolean alterBooking()
    {
        return false;
    }
    boolean deleteBooking(Booking booking)
    {
        return false;
    }
    boolean deleteAccount(Account account)
    {
        return false;
    }

    Vector<Booking> getAllBookings()
    {
        return null;
    }
    Vector<Account> getAllAccounts()
    {
        return null;
    }
}
