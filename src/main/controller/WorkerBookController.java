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
import main.model.account.Admin;
import main.model.account.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

// We need:
// Timeslots
// Seat
// Max for given seat and timeslot.

// Every time you pick a choice the remaining choices should narrow down based on the possibilities

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.Date;

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

        // set default values
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

    /* Make booking method
        Push booking into db
     */
    public void MakeBooking(ActionEvent event) throws SQLException
    {
        System.out.println("MAKE BOOKING:");
        System.out.println(fxDate.getValue());
        System.out.println(fxChoiceTime.getValue());
        System.out.println(fxChoiceDuration.getValue());
        System.out.println(fxChoiceSeat.getValue());

        LocalDate date = fxDate.getValue();
        String strTime = (String) fxChoiceTime.getValue();
        String strDuration = (String) fxChoiceDuration.getValue();
        String strSeat = (String) fxChoiceSeat.getValue();
        //System.out.println("TIMESTR: "+strTime);

        //if (strTime.equals("None") || strDuration.equals("None") || strSeat.equals("None"))
        {
            //System.out.println("Booking not complete");
        }
        //else
        {
            System.out.println("Writing booking to db");
            pushBooking(date,strTime,strDuration,strSeat);

        }

/*
        if (username.isEmpty() || password.isEmpty())
        {
            fxFeedback.setText("no empty");
        }
        else {
            fxFeedback.setText("Make acc obj");
            // write account to db
            if (isRegister(username, password))
            {
                fxFeedback.setText("ALREADY EXISTS");
            }
            else
            {
                //fxFeedback.setText("MAKE ACK");
                pushAccount(username,password);
                fxFeedback.setText("Ack should exist now");
            }
        } */


    }

    public boolean isValidBooking()
    {
        return true;
    }

    public boolean pushBooking(LocalDate date, String strTime, String strDuration, String strSeat) throws SQLException
    {
        System.out.println("Pushing booking to db");

        PreparedStatement preparedStatement = null;
        String query = "insert into booking (date, hour, duration, userid)"
                +" VALUES(?,?,?,0)";

        java.sql.Date sqlDate = java.sql.Date.valueOf( date );

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, sqlDate);
        preparedStatement.setString(2, strTime);
        preparedStatement.setString(3, strDuration);

        //Statement statement = connection.createStatement();
        int insertCount = preparedStatement.executeUpdate();

        return true;

    }

}
