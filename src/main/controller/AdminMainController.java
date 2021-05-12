package main.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.TestMenu;
import main.model.LoginModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

}
