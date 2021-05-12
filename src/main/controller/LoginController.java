package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.WorkerMainMenu;
import main.model.LoginModel;
import main.AdminMainMenu;

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

                // There's no need to create the AdminController, JavaFX will create it.
                AdminMainMenu adminMainMenu = new AdminMainMenu(640,480);
                adminMainMenu.show();

                WorkerMainMenu workerMainMenu = new WorkerMainMenu(640,480);
                workerMainMenu.show();

            }else{
                isConnected.setText("Invalid credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
