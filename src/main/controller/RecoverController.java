package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import main.SQLConnection;
import main.model.account.Admin;
import main.model.account.Worker;
import main.model.interfaces.Account;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class RecoverController implements Initializable
{
    @FXML
    private TextField fxUsername;
    @FXML
    private TextField fxAnswer1;
    @FXML
    private TextField fxAnswer2;
    @FXML
    private Label fxFeedback;
    @FXML
    private Label fxQuestion1;
    @FXML
    private Label fxQuestion2;

    Connection connection;

    public RecoverController()
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

    @FXML
    public void initialize()
    {
    }
    /* Admin Report method
    Build and display admin report
     */
    public void Recover(ActionEvent event){

        System.out.println("Recovering:");

        System.out.println(fxUsername.getText());
        System.out.println(fxAnswer1.getText());
        System.out.println(fxAnswer2.getText());

        // go back to login menu
        fxFeedback.setText("Recovery successful. Go back and login");

    }

    public void GetQuestions (ActionEvent event)
    {

        System.out.println("Retrieve questions from db");

        String username = fxUsername.getText();

        if (username.isEmpty())
        {
            fxFeedback.setText("Please enter the username you wish to recover.");
        }
        else
        {
            Account account = getAccountByUsername(username);
            fxFeedback.setText("Retrieve questions or account not found.");
        }

        //System.out.println(fxUsername.getText());
        //System.out.println(fxAnswer1.getText());
        //System.out.println(fxAnswer2.getText());

        // go back to login menu
        //fxFeedback.setText("Recovery successful. Go back and login");

        //return null;

    }

    public Account getAccountByUsername(String username)
    {
        // search db by username here.


        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from user where email = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) // if there's a hit
            {
                String question1 = resultSet.getString("question1");
                String question2 = resultSet.getString("question2");

                fxQuestion1.setText(question1);
                fxQuestion2.setText(question2);

                Admin account = new Admin (username,"A","A","A","A", "A");


            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            //preparedStatement.close();
            //resultSet.close();
        }

        return null;
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
