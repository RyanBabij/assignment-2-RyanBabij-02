package main.controller;

import main.model.seat.Seat;

import java.util.Date;

/*
    Aka an office/workspace. Typically there should only be one per application.
 */

public class SeatController {

    Seat[][] aSeat;

    // initialize the workspace with the required seating rows and columns
    // currently you are limited to a square workspace but in future you could do fancy stuff by marking seats as
    // disabled if you wanted to create non-square areas.
    public SeatController (int x, int y)
    {
        // initialize the array of Seats
        aSeat = new Seat [x][y];
    }

    boolean isAvailableFor (Date date, int time, int duration)
    {
        // check safety
        // check availability
        return false;
    }

    void lockdown()
    {
        // enable lockdown, move/delete bookings
    }
}
