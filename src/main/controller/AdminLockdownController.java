package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import main.Main;
import main.MenuAdminMain;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AdminLockdownController
{
    Connection connection;

    @FXML
    private Label fxMainLabel;

    public AdminLockdownController()
    {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
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

        boolean lockdownState = !getLockdownState();

        if (lockdownState)
        {
            fxMainLabel.setText("Current mode: Lockdown");
        }
        else
        {
            fxMainLabel.setText("Current mode: Normal");
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

    public void Lockdown() throws SQLException {
        System.out.println("Activate lockdown here");

        // for now we will simply cycle every second seat between active and inactive.
        boolean lockdownState = !getLockdownState();
        System.out.println("Lockdown state: "+lockdownState);
        lockdownSeats(lockdownState);
    }

    // check db to see if we are currently in lockdown.
    // for this we simply check to see if a seat is in lockdown
    boolean getLockdownState() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from seat where canLockdown = true";
        try
        {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) // if there's a hit
            {
                // return the lockdown state of this seat
                boolean seatActive = resultSet.getBoolean("active");
                return seatActive;
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
        return false;
    }

    // load all of this user's bookings from db
    void lockdownSeats(boolean activeMode) throws SQLException
    {
        //System.out.println("Cycle seat active.");

        PreparedStatement preparedStatement = null;
        String query = "UPDATE seat SET active = ? WHERE canLockdown = true";
        try
        {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, activeMode);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
        }
        finally
        {
            preparedStatement.close();
        }

        if (!activeMode)
        {
            fxMainLabel.setText("Current mode: Lockdown");
        }
        else
        {
            fxMainLabel.setText("Current mode: Normal");
        }
    }

}
