package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.Main;
import main.MenuWorkerBook;
import main.SQLConnection;
import main.model.account.Admin;
import main.model.account.Worker;
import main.model.booking.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import static java.lang.Thread.sleep;

public class WorkerMainController
{
    public Worker worker = null;

    private Vector <String> vBooking = new Vector <String> ();

    Connection connection;

    @FXML
    private ListView fxBookingList;
    @FXML
    private Label fxFeedback;

    public WorkerMainController()
    {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    @FXML
    public void initialize() throws SQLException
    {
        if (isDbConnected())
        {
            fxFeedback.setText("Connected");
        }
        else
        {
            fxFeedback.setText("Not Connected");
        }

        // populate list view with bookings
        System.out.println("Loading bookings by uid: "+Main.worker.uid);
        loadBookings(Main.worker.uid);
        System.out.println(vBooking.size()+" matches");

        for (int i=0;i<vBooking.size();++i)
        {
            fxBookingList.getItems().add(vBooking.get(i));
        }
    }

    public boolean isDbConnected()
    {
        try
        {
            return !connection.isClosed();
        }
        catch(Exception e)
        {
            return false;
        }
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

        // populate worker bookings here

        fxBookingList.getItems().add("POPULATE BOOKINGS HERE");

        MenuWorkerBook menuWorkerBook = new MenuWorkerBook(640,480, worker);
        menuWorkerBook.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    // load all of this user's bookings from db
    void loadBookings(int userID) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from booking where userid = ?";
        try
        {
            System.out.println("Querying bookings");

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Main.worker.uid);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) // if there's a hit
            {
                System.out.println("next booking");
                //resultSet.getString("label");
                Date strDate = resultSet.getDate("date");
                int hour = resultSet.getInt("hour");
                int duration = resultSet.getInt("duration");
                //boolean isAdmin = resultSet.getBoolean("isAdmin");

                // build the account object

                vBooking.add(strDate.toString()+" "+hour+" "+duration);
                //return true;
            }
            else
            {
                System.out.println("no more bookings");
                //return false;
            }
        }
        catch (Exception e)
        {
            //return false;
        }
        finally
        {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public void ManageAccount(ActionEvent event)
    {
        System.out.println("MANAGE ACCOUNT");
    }

}
