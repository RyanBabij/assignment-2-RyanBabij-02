package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import main.Main;
import main.MenuAdminMain;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminReportController
{
    @FXML
    private Label fxMainLabel;

    Connection connection;

    public AdminReportController()
    {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    @FXML
    public void initialize() throws SQLException
    {
        fxMainLabel.setText("Admin report:\nTotal bookings: X\nBookings for today: X\nNumber of users: X");

        if (isDbConnected())
        {
            System.out.println("DB connected");
        }
        else
        {
            System.out.println("DB not connected");
        }

        // populate list view with bookings
        //System.out.println("Loading bookings by uid: "+ Main.worker.uid);
        //loadBookings(Main.worker.uid);
        //System.out.println(vBooking.size()+" matches");

        //Creating the Statement object
        Statement stmt = connection.createStatement();
        //Query to get the number of rows in a table
        String query = "select count(*) from booking";
        //Executing the query
        ResultSet rs = stmt.executeQuery(query);
        //Retrieving the result
        rs.next();
        int count = rs.getInt(1);

        fxMainLabel.setText("Admin report:\nTotal bookings: "+count+"\nBookings for today: X\nNumber of users: X");
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
    public void Back(ActionEvent event)
    {
        System.out.println("BACK");
        // back to worker menu
        MenuAdminMain menuAdminMain = new MenuAdminMain(640,480);
        menuAdminMain.show();
        // hide this window.
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
