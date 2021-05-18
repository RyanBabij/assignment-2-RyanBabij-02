package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import main.Main;
import main.MenuWorkerMain;

public class WorkerBookController
{

    @FXML
    private ListView fxBookingList;

    public WorkerBookController()
    {
    }

    @FXML
    public void initialize()
    {
    }

    // Logout worker and return to login screen
    public void Back(ActionEvent event){

        System.out.println("BACK");
        // back to worker menu
        MenuWorkerMain menuWorkerMain = new MenuWorkerMain(640,480);
        menuWorkerMain.show();
        // hide this window.
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void MakeBooking(ActionEvent event)
    {
        System.out.println("MAKE BOOKING");
    }

}
