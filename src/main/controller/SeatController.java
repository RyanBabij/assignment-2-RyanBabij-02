package main.controller;

import main.model.seat.Seat;

import java.util.Date;

/*
    Aka an office. Typically there should only be one per application.
 */

public class SeatController {

    Seat[][] aSeat;

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
