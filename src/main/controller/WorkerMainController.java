package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import main.Main;

import static java.lang.Thread.sleep;

public class WorkerMainController
{

    @FXML
    private ListView fxBookingList;

    public WorkerMainController()
    {
        //fxBookingList.getItems().add("AAAAA");
    }

    @FXML
    public void initialize()
    {
        fxBookingList.getItems().add("BBBB");
    }

    // Logout worker and return to login screen
    public void Logout(ActionEvent event){

        System.out.println("LOGOUT");
        Main.primaryStage.show();
        // hide this window.
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void Booking(ActionEvent event)
    {
        System.out.println("BOOKING");
        fxBookingList.getItems().add("CCC");
    }

    public void ManageAccount(ActionEvent event)
    {
        System.out.println("MANAGE ACCOUNT");
    }

}
