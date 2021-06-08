package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import main.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Vector;

import static java.lang.Thread.sleep;

public class AdminMainController
{
    @FXML
    private Label fxMainLabel;

    Connection connection;

    Vector <String> vSeatName = new Vector <String> ();


    public AdminMainController()
    {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    @FXML
    public void initialize() throws SQLException {

        if (isDbConnected())
        {
            System.out.println("DB connected");
        }
        else
        {
            System.out.println("DB not connected");
        }

        fxMainLabel.setText("CURRENT BOOKING LIST GOES HERE.");

        loadTodayBookings();

        loadSeats();

        String strSeat = "";
        for (int i=0;i<vSeatName.size();++i)
        {
            strSeat+=vSeatName.get(i)+"\n";
        }

        fxMainLabel.setText(strSeat);

        // get current date
        // list all bookings which fill this date
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

    /* Admin Report method
    Build and display admin report
     */
    public void Report(ActionEvent event){

        System.out.println("REPORT");

        // Build and show report menu

        MenuAdminReport menuAdminReport = new MenuAdminReport(640,480);
        menuAdminReport.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    // Logout admin and return to login screen
    public void Logout(ActionEvent event)
    {
        System.out.println("LOGOUT");
        Main.primaryStage.show();
        // hide this window.
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void Lockdown(ActionEvent event){

        System.out.println("LOCKDOWN");

        // Build and show lockdown menu
        MenuAdminLockdown menuAdminLockdown = new MenuAdminLockdown(640,480);
        menuAdminLockdown.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    // load all of bookings into list
    void loadTodayBookings() throws SQLException
    {
        java.util.Date date =
                java.util.Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String output = "Current bookings:\n";



        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from booking where date = ?";
        try
        {
            System.out.println("Querying bookings");

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, sqlDate);

            resultSet = preparedStatement.executeQuery();

            //vBookingToday.clear();
            //vBookingIDToday.clear();
            while (resultSet.next()) // if there's a hit
            {
                java.util.Date strDate = resultSet.getDate("date");
                int hour = resultSet.getInt("hour");
                int duration = resultSet.getInt("duration");
                //boolean isAdmin = resultSet.getBoolean("isAdmin");
                int bookingID = resultSet.getInt("id");

                output += strDate.toString()+" "+hour+" "+duration+"\n";

                //vBookingToday.add(strDate.toString()+" "+hour+" "+duration);
                //vBookingIDToday.add(bookingID);
            }
        }
        catch (Exception e)
        {
        }
        finally
        {
            preparedStatement.close();
            resultSet.close();
        }

        //fxBookingListToday.getItems().clear();
        //for (int i=0;i<vBookingToday.size();++i)
        {
            //fxBookingListToday.getItems().add(vBookingToday.get(i));
        }

        fxMainLabel.setText(output);
    }

    void loadSeats() throws SQLException {
        vSeatName.clear();
        //Vector<String> vSeatName = new Vector<String>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from seat";
        try
        {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) // if there's a hit
            {
                int sid = resultSet.getInt("sid");
                String seatName = resultSet.getString("seatName");

                //if (vUnavailableSeats.contains(sid) == false)
                {
                    vSeatName.add(seatName);
                }
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

}
