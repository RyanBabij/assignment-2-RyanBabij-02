package main.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import main.*;
import main.model.LoginModel;
import main.model.account.Worker;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (loginModel.isDbConnected()){
            isConnected.setText("Connected");
        }else {
            isConnected.setText("Not Connected");
        }
    }

    /* login Action method
       check if user input is the same as database.
     */
    public void Login(ActionEvent event){

        try {
            if (loginModel.isLogin(txtUsername.getText(),txtPassword.getText())){

                isConnected.setText("Logged in successfully");

                // hide this window.
                ((Node)(event.getSource())).getScene().getWindow().hide();

                if(loginModel.isAdmin)
                {
                    // open admin menu
                    // There's no need to create the AdminController, JavaFX will create it.
                    MenuAdminMain menuAdminMain = new MenuAdminMain(640,480);
                    menuAdminMain.show();

                    //MenuAdminReport menuAdminReport = new MenuAdminReport(640,480);
                    //menuAdminReport.show();
                }
                else
                {
                    //Worker testWorker = new Worker("a","a","a","a,","a","a,");
                    // open worker menu
                    MenuWorkerMain menuWorkerMain = new MenuWorkerMain(640,480, loginModel.worker);
                    menuWorkerMain.show();
                }

            }else{
                isConnected.setText("Invalid credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /* Register Action method
   redirect to register menu
    */
    public void Register(ActionEvent event)
    {
        // hide this window.
        ((Node)(event.getSource())).getScene().getWindow().hide();

        System.out.println("Register");

        // open worker menu
        MenuAccountRegister menuRegister = new MenuAccountRegister(640,480);
        menuRegister.show();
    }

    /* Register Action method
   redirect to register menu
    */
    public void Recover(ActionEvent event)
    {
        // hide this window.
        ((Node)(event.getSource())).getScene().getWindow().hide();

        System.out.println("Recover");

        // open worker menu
        MenuAccountRecover menuRecover = new MenuAccountRecover(640,480);
        menuRecover.show();
    }
}
