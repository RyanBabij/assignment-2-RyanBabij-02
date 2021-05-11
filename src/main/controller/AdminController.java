package main.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.model.LoginModel;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController
{


    //public LoginModel loginModel = new LoginModel();
    @FXML
    private Label welcomeText;

    @FXML
    public void initialize()
    {
        welcomeText.setText("AAA");

        Platform.runLater(new Runnable() {
            @Override public void run() {
                welcomeText.setText("AAA2");
            }
        });
    }

    @FXML
    public void setText(String str)
    {
        //welcomeText.setText(str);
    }

}
