package main.controller;

import main.model.seat.Seat;

/*
    Aka an office. Typically there should only be one per application.
 */

public class SeatController {

    Seat[][] aSeat;

    public SeatController (int x, int y)
    {
        aSeat = new Seat [x][y];
    }
}
