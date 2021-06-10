package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import main.MenuAdminMain;

public class AdminLockdownController
{
    public AdminLockdownController()
    {
    }

    @FXML
    public void initialize()
    {
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
    }

}
