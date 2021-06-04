package main.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.Main;
import main.MenuAdminMain;
import main.SQLConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Vector;

public class AdminReportController
{
    @FXML
    private Label fxMainLabel;
    @FXML
    private ListView fxBookingList;
    @FXML
    private ListView fxBookingListToday;

    Connection connection;

    private Vector<String> vBooking = new Vector <String> ();
    private Vector <Integer> vBookingID = new Vector <Integer> ();

    private Vector<String> vBookingToday = new Vector <String> ();
    private Vector <Integer> vBookingIDToday = new Vector <Integer> ();

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

        //Creating the Statement object
        Statement stmt = connection.createStatement();
        //Query to get the number of rows in a table
        String query = "select count(*) from booking";
        //Executing the query
        ResultSet rs = stmt.executeQuery(query);
        //Retrieving the result
        rs.next();
        int count = rs.getInt(1);

        fxMainLabel.setText("Admin report:\nTotal bookings: "+count+"\nBookings for today: "+getTodayBookings()+"\nNumber of users: "+getTotalUsers());

        loadBookings();
        loadTodayBookings();
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

    int getTodayBookings() throws SQLException {
        java.util.Date date =
                java.util.Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select count(*) from booking where date = ?";

        try
        {
            preparedStatement = connection.prepareStatement(query);
            // setObject autoconverts objects to appropriate datatype
            preparedStatement.setObject(1, date);
            resultSet = preparedStatement.executeQuery();

            //Retrieving the result
            resultSet.next();
            return resultSet.getInt(1);
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

        return 0;
    }
    int getTotalUsers() throws SQLException
    {
        //Creating the Statement object
        Statement stmt = connection.createStatement();
        //Query to get the number of rows in a table
        String query = "select count(*) from user";
        //Executing the query
        ResultSet rs = stmt.executeQuery(query);
        //Retrieving the result
        rs.next();
        return rs.getInt(1);
    }

    // load all of bookings into list
    void loadBookings() throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from booking";
        try
        {
            System.out.println("Querying bookings");

            preparedStatement = connection.prepareStatement(query);
            //preparedStatement.setInt(1, Main.worker.uid);

            resultSet = preparedStatement.executeQuery();

            vBooking.clear();
            vBookingID.clear();
            while (resultSet.next()) // if there's a hit
            {
                java.util.Date strDate = resultSet.getDate("date");
                int hour = resultSet.getInt("hour");
                int duration = resultSet.getInt("duration");
                //boolean isAdmin = resultSet.getBoolean("isAdmin");
                int bookingID = resultSet.getInt("id");

                vBooking.add(strDate.toString()+" "+hour+" "+duration);
                vBookingID.add(bookingID);
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

        fxBookingList.getItems().clear();
        for (int i=0;i<vBooking.size();++i)
        {
            fxBookingList.getItems().add(vBooking.get(i));
        }
    }

    // load all of bookings into list
    void loadTodayBookings() throws SQLException
    {
        java.util.Date date =
                java.util.Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());


        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from booking where date = ?";
        try
        {
            System.out.println("Querying bookings");

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, sqlDate);

            resultSet = preparedStatement.executeQuery();

            vBookingToday.clear();
            vBookingIDToday.clear();
            while (resultSet.next()) // if there's a hit
            {
                java.util.Date strDate = resultSet.getDate("date");
                int hour = resultSet.getInt("hour");
                int duration = resultSet.getInt("duration");
                //boolean isAdmin = resultSet.getBoolean("isAdmin");
                int bookingID = resultSet.getInt("id");

                vBookingToday.add(strDate.toString()+" "+hour+" "+duration);
                vBookingIDToday.add(bookingID);
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

        fxBookingListToday.getItems().clear();
        for (int i=0;i<vBookingToday.size();++i)
        {
            fxBookingListToday.getItems().add(vBookingToday.get(i));
        }
    }

    public void DeleteFromAll() throws SQLException
    {
        System.out.println("Delete from all");

        ObservableList selectedIndices = fxBookingList.getSelectionModel().getSelectedIndices();

        for(Object o : selectedIndices)
        {
            System.out.println("Delete index: "+o);
            System.out.println("Delete booking id: "+vBookingID.get((int)o));
            deleteBooking(vBookingID.get((int)o));
            //System.out.println("o = " + o + " (" + o.getClass() + ")");
        }
        loadBookings();
        loadTodayBookings();
    }
    public void DeleteFromToday() throws SQLException {
        System.out.println("Delete from today");

        ObservableList selectedIndices = fxBookingListToday.getSelectionModel().getSelectedIndices();

        for(Object o : selectedIndices)
        {
            System.out.println("Delete index: "+o);
            System.out.println("Delete booking id: "+vBookingIDToday.get((int)o));
            deleteBooking(vBookingIDToday.get((int)o));
            // System.out.println("o = " + o + " (" + o.getClass() + ")");
        }
        loadBookings();
        loadTodayBookings();
    }


    void deleteBooking(int index) throws SQLException
    {
        String sql = "DELETE FROM booking WHERE id = ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);

        // set the corresponding param
        pstmt.setInt(1, index);
        // execute the delete statement
        pstmt.executeUpdate();
    }

}

