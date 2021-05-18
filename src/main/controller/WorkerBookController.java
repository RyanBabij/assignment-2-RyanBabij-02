package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.Main;
import main.MenuWorkerMain;
import main.SQLConnection;

import java.sql.Connection;
import java.time.LocalDateTime;

// We need:
// Timeslots
// Seat
// Max for given seat and timeslot.

// Every time you pick a choice the remaining choices should narrow down based on the possibilities

import java.time.LocalDate;

import java.time.LocalDateTime;

public class WorkerBookController
{

    @FXML
    private ListView fxBookingList;
    @FXML
    private ChoiceBox fxChoiceTime;
    @FXML
    private ChoiceBox fxChoiceDuration;
    @FXML
    private ChoiceBox fxChoiceSeat;
    @FXML
    private DatePicker fxDate;
    @FXML
    private Label fxFeedback;

    Connection connection;

    public WorkerBookController()
    {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    @FXML
    public void initialize()
    {
        if (isDbConnected()){
            fxFeedback.setText("Connected");
        }else {
            fxFeedback.setText("Not Connected");
        }

        fxChoiceTime.getItems().add("None");
        fxChoiceDuration.getItems().add("None");
        fxChoiceDuration.getItems().add("Choice 2");
        fxChoiceDuration.getItems().add("Choice 3");
        fxChoiceSeat.getItems().add("None");
        fxChoiceSeat.getItems().add("1A");
        fxChoiceSeat.getItems().add("1B");
        fxChoiceSeat.getItems().add("1C");

        fxChoiceTime.setValue("None");
        fxChoiceDuration.setValue("None");
        fxChoiceSeat.setValue("None");

        fxDate.setValue(LocalDate.now()  );
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

    // Push the booking into the db
    public void MakeBooking(ActionEvent event)
    {
        System.out.println("MAKE BOOKING:");
        System.out.println(fxDate.getValue());
        System.out.println(fxChoiceTime.getValue());
        System.out.println(fxChoiceDuration.getValue());
        System.out.println(fxChoiceSeat.getValue());
    }

}
