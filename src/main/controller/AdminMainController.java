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
import java.util.Calendar;
import java.util.Vector;

import static java.lang.Thread.sleep;

public class AdminMainController
{
    @FXML
    private Label fxMainLabel;

    Connection connection;

    // vector of all seat names
    Vector <String> vSeatName = new Vector <String> ();
    // vector of bookings associated with each seat
    Vector <String> vBooking = new Vector <String> ();

    // username and seat name of each active booking
    Vector <String> vBookingUser = new Vector <String> ();
    Vector <String> vBookingSeat = new Vector <String> ();


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

        // load list of active bookings today
        loadTodayBookings();
        // load list of seat names
        loadSeats();

        // output the information

        String strSeat = "";

        java.util.Date date =
                java.util.Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        // get current hour so we can find any bookings active on this hour
        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        // 9am is 0 with the current system, so we need to remove 9 hours.
        currentHour-=9;


        strSeat+="Current bookings for "+sqlDate+" hour "+currentHour+":\n";


        for (int i=0;i<vSeatName.size();++i)
        {
            for (int i2=0;i2<vBookingSeat.size();++i2)
            {
                if (vBookingSeat.get(i2).equals(vSeatName.get(i)))
                {
                    // this seat has an active booking.
                    vBooking.set(i,"Currently booked by "+vBookingUser.get(i2));
                }
            }

            strSeat+=vSeatName.get(i)+": "+vBooking.get(i)+"\n";
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

        // get current hour so we can find any bookings active on this hour
        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        // 9am is 0 with the current system, so we need to remove 9 hours.
        currentHour-=9;

        String output = "Current bookings for "+sqlDate+":\n";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        // get all of today's bookings
        String query = "select * from booking INNER JOIN seat ON booking.seatid = seat.sid "
        + "INNER JOIN user ON booking.userid = user.id where date = ?";
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
                int seatID = resultSet.getInt("seatid");
                int userID = resultSet.getInt("userid");
                String seatName = resultSet.getString("seatname");
                String userName = resultSet.getString("email");

                output += strDate.toString()+" "+hour+" "+duration+"\n";

                System.out.println("Found booking: "+strDate+" "+hour+" "+duration);

                // check if this booking is currently active
                if ( currentHour >= hour && currentHour <= hour+duration)
                {
                    System.out.println("This booking is active by user "+userName+" on seat "+seatName);
                    vBookingUser.add(userName);
                    vBookingSeat.add(seatName);
                }

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
        vBooking.clear();
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
                    vBooking.add("Not booked");
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
