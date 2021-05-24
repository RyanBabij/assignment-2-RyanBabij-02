package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import main.Main;

import static java.lang.Thread.sleep;

public class AdminMainController
{
    public AdminMainController()
    {
    }

    @FXML
    public void initialize()
    {
    }
    /* Admin Report method
    Build and display admin report
     */
    public void Report(ActionEvent event){

        System.out.println("REPORT");

        // Build and show report menu
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

        // Build and show report menu
    }

}
