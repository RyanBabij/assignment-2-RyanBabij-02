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
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

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

    @FXML
    private ListView<String> myListView;



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
        fxChoiceTime.getItems().add("0900");
        fxChoiceTime.getItems().add("1000");
        fxChoiceTime.getItems().add("1100");
        fxChoiceTime.getItems().add("1200");
        fxChoiceTime.getItems().add("1300");
        fxChoiceTime.getItems().add("1400");
        fxChoiceTime.getItems().add("1500");
        fxChoiceTime.getItems().add("1600");

        // maximum booking duration is 8 hours.
        // booking cannot extend beyond 5pm closing time,
        // so a booking at 4pm can only be 1 hour for example.
        fxChoiceDuration.getItems().add("None");
        fxChoiceDuration.getItems().add("1");
        fxChoiceDuration.getItems().add("2");
        fxChoiceDuration.getItems().add("3");
        fxChoiceDuration.getItems().add("4");
        fxChoiceDuration.getItems().add("5");
        fxChoiceDuration.getItems().add("6");
        fxChoiceDuration.getItems().add("7");
        fxChoiceDuration.getItems().add("8");


        // set default values
        fxChoiceTime.setValue("None");
        fxChoiceDuration.setValue("None");
        fxChoiceSeat.setValue("None");
        // Set default date to today.
        fxDate.setValue(LocalDate.now()  );

    }

    // Logout worker and return to login screen
    public void Back(ActionEvent event)
    {
        System.out.println("BACK");
        // back to worker menu
        MenuWorkerMain menuWorkerMain = new MenuWorkerMain(640,480, Main.worker);
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

        java.util.Date date =
                java.util.Date.from(fxDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String strTime = (String) fxChoiceTime.getValue();
        String strDuration = (String) fxChoiceDuration.getValue();
        String strSeat = (String) fxChoiceSeat.getValue();

        if (strTime.equals("None") || strDuration.equals("None") || strSeat.equals("None"))
        {
            System.out.println("Booking not complete");
        }
        else
        {
            //if (fxChoiceSeat.getItems().add(seatName);)
            // date hour duration

            int time = (Integer.parseInt((String) fxChoiceTime.getValue())/100)-9;
            int duration = Integer.parseInt((String) fxChoiceDuration.getValue());

            Vector <String> vSeatName = getAvailableSeats(sqlDate,time,duration);

            for (int i=0;i<vSeatName.size();++i)
            {
                if (vSeatName.get(i).equals(strSeat))
                {
                    System.out.println("Writing booking to db");
                    pushBooking(sqlDate,strTime,strDuration,strSeat);
                    return;
                }
            }
            System.out.println("Couldn't find available booking slot for this seat.");
            fxFeedback.setText("Couldn't find available booking slot for this seat.");

        }
    }

    // Find seats available on this date, time and duration
    public void FindSeats() throws SQLException
    {
        if (fxChoiceTime.getValue().equals("None") || fxChoiceDuration.getValue().equals("None"))
        {
            System.out.println("Please fill out date, time and duration.");
            return;
        }
        System.out.println("Find available seats.");

        //LocalDate date = fxDate.getValue();
        java.util.Date date =
                java.util.Date.from(fxDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        int time = (Integer.parseInt((String) fxChoiceTime.getValue())/100)-9;
        int duration = Integer.parseInt((String) fxChoiceDuration.getValue());

        System.out.println("Pushing date "+date);

        Vector <String> vSeatName = getAvailableSeats(date,time,duration);

        for (int i=0;i<vSeatName.size();++i)
        {
            fxChoiceSeat.getItems().add(vSeatName.get(i));
        }


    }

    public boolean pushBooking(Date date, String strTime, String strDuration, String strSeat) throws SQLException
    {
        // convert strtime into hours from 9am.
        int iTime = (Integer.parseInt(strTime) / 100) - 9;
        int iDuration = Integer.parseInt(strDuration);
        //int sid = Integer.parseInt(strSeat);
        // we need to convert strSeat into sid.

        System.out.println("Pushing booking to db");
        System.out.println("uid: "+Main.worker.uid);

        PreparedStatement preparedStatement = null;
        String query = "insert into booking (date, hour, duration, userid, seatid)"
                +" VALUES(?,?,?,?,?)";

        java.sql.Date sqlDate = java.sql.Date.valueOf(String.valueOf(date));

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, sqlDate);
        preparedStatement.setInt(2, iTime);
        preparedStatement.setInt(3, iDuration);
        preparedStatement.setInt(4, Main.worker.uid);
        preparedStatement.setInt(5, getSeatId(strSeat));

        //Statement statement = connection.createStatement();
        int insertCount = preparedStatement.executeUpdate();

        return true;
    }

    public int getSeatId(String seatName) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from seat";
        try
        {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) // if there's a hit
            {
                String dbSeatName = resultSet.getString("seatName");
                if (dbSeatName.equals(seatName))
                {
                    return resultSet.getInt("sid");
                }
            }
        }
        catch (Exception e)
        {
            return 0;
        }
        finally
        {
            preparedStatement.close();
            resultSet.close();
        }
        return 0;
    }

    // this should return a vector for portability
    public Vector <String> getAvailableSeats(Date date, int hour, int duration) throws SQLException
    {
        System.out.println("Getting available seats for " + date + " " + hour + " " + duration);
        // find seats which aren't booked during this period

        fxChoiceSeat.getItems().clear();
        fxChoiceSeat.getItems().add("None");

        System.out.println("Date: "+date);

        Vector <Integer> vUnavailableSeats = new Vector <Integer> ();
        {
            // get all bookings to compare to seats
            PreparedStatement preparedStatement = null;
            ResultSet resultSet=null;
            String query = "select * from booking where date = ?";

            try
            {
                preparedStatement = connection.prepareStatement(query);
                // setObject autoconverts objects to appropriate datatype
                preparedStatement.setObject(1, date);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) // if there's a hit
                {
                    System.out.println("Matching booking date found.");

                    // we need to check if this booking makes the seat unavailable for the given timeslot.
                    // hour starts at 0 for 0900, and 7 for 1600.
                    int startHour = resultSet.getInt("hour");
                    int endHour = startHour + resultSet.getInt("duration");
                    int bookingEndHour = hour+duration;

                    System.out.println("db hours: "+startHour+" "+endHour);
                    System.out.println("bk hours: "+hour+" "+bookingEndHour);


                    if ((hour<=endHour && bookingEndHour>=endHour)
                            || hour<=startHour && bookingEndHour>=startHour )
                    {
                        //booking for this seat is not possible.
                        // push this seatid to the unavailable list.
                        vUnavailableSeats.add(resultSet.getInt("seatid"));
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

        Vector<String> vSeatName = new Vector<String>();

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
                boolean seatActive = resultSet.getBoolean("active");

                if (vUnavailableSeats.contains(sid) == false && seatActive)
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

        return vSeatName;
    }
}
