package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import main.SQLConnection;
import main.model.account.Admin;
import main.model.account.Worker;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RegisterController implements Initializable
{
    @FXML
    private TextField fxUsername;
    @FXML
    private PasswordField fxPassword;
    @FXML
    private TextField fxQuestion1;
    @FXML
    private TextField fxAnswer1;
    @FXML
    private TextField fxQuestion2;
    @FXML
    private TextField fxAnswer2;
    @FXML
    private Label fxFeedback;

    Connection connection;

    public RegisterController()
    {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (isDbConnected()){
            fxFeedback.setText("Connected");
        }else {
            fxFeedback.setText("Not Connected");
        }

    }

    public boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    /* Account register method
    Push account to db
     */
    public void Register(ActionEvent event) throws SQLException {

        System.out.println("Registering:");

        String username = fxUsername.getText();
        String password = fxPassword.getText();
        String question1 = fxQuestion1.getText();
        String question2 = fxQuestion2.getText();
        String answer1 = fxAnswer1.getText();
        String answer2 = fxAnswer2.getText();

        if (username.isEmpty() || password.isEmpty())
        {
            fxFeedback.setText("no empty");
        }
        else {
            fxFeedback.setText("Make acc obj");
            // write account to db
            if (isRegister(username, password))
            {
                fxFeedback.setText("ALREADY EXISTS");
            }
            else
            {
                //fxFeedback.setText("MAKE ACK");
                pushAccount(username,password, question1, answer1, question2, answer2);
                fxFeedback.setText("Ack should exist now");
            }
        }

        System.out.println(fxUsername.getText());
        System.out.println(fxPassword.getText());

        System.out.println(fxQuestion1.getText());
        System.out.println(fxAnswer1.getText());
        System.out.println(fxQuestion2.getText());
        System.out.println(fxAnswer2.getText());

        // if registration is success. Print feedback.

        // go back to login menu
        //fxFeedback.setText("Registration successful. Go back and login");

    }

    public boolean isRegister(String user, String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from user where email = ? and password= ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) // if there's a hit
            {
                boolean isAdmin = resultSet.getBoolean("isAdmin");

                // build the account object

                if (isAdmin)
                {
                    Admin admin = new Admin("a","a","a","a","a","a");

                    System.out.println("ADMIN");
                    //this.isAdmin=true;
                }
                else
                {
                    Worker worker = new Worker("a","a","a","a","a","a");

                    System.out.println("Not admin");
                    //this.isAdmin=false;
                }
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }

    }

    public boolean pushAccount(String username, String password, String question1, String answer1,
                               String question2, String answer2) throws SQLException {

        PreparedStatement preparedStatement = null;
        String query = "insert into user (email, password, question1, answer1, question2, answer2, isAdmin)"
                +" VALUES(?,?,?,?,?,?,false)";

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, question1);
        preparedStatement.setString(4, answer1);
        preparedStatement.setString(5, question2);
        preparedStatement.setString(6, answer2);

        //Statement statement = connection.createStatement();
        int insertCount = preparedStatement.executeUpdate();

        return true;
    }

    public void Back(ActionEvent event)
    {

        System.out.println("Back");



        Parent test;
        Stage stage = new Stage();

        try
        {
            Main.primaryStage.show();
            //Parent test;
            //test = FXMLLoader.load(getClass().getResource("ui/MenuLogin.fxml"));

            //System.out.println("FXML LOADED");
            //Stage stage = new Stage();
            ///stage.setTitle("Login");
            //stage.setScene(new Scene(test, 640, 480));
            //stage.show();
            //stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (Exception e)
        {
            System.out.println("TestMenu failed.");
        }
        // hide this window.
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
