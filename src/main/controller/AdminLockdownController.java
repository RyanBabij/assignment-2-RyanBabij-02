package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import main.MenuAdminMain;
import main.SQLConnection;

import java.sql.Connection;

public class AdminLockdownController
{
    Connection connection;

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
    public void initialize()
    {
        if (isDbConnected())
        {
            System.out.println("DB connected");
        }
        else
        {
            System.out.println("DB not connected");
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

    public void Lockdown()
    {
        System.out.println("Activate lockdown here");

        // for now we will simply cycle every second seat between active and inactive.
    }

}
