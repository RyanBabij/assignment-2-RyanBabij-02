package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import main.Main;
import main.MenuWorkerMain;
import main.SQLConnection;
import main.model.account.Admin;
import main.model.account.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;

public class WorkerAccountDetailsController
{
    @FXML
    private Label fxFeedback;

    @FXML
    private TextField fxUsername;
    @FXML
    private TextField fxQuestion1;
    @FXML
    private TextField fxAnswer1;
    @FXML
    private TextField fxQuestion2;
    @FXML
    private TextField fxAnswer2;
    @FXML
    private TextField fxPassword;

    Connection connection;

    @FXML
    private ListView<String> myListView;



    public WorkerAccountDetailsController()
    {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
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
        if (isDbConnected()){
            fxFeedback.setText("Connected");
        }else {
            fxFeedback.setText("Not Connected");
        }

        updateForm();
    }

    // Logout worker and return to login screen
    public void Back(ActionEvent event)
    {
        System.out.println("BACK");
        // back to worker menu
        MenuWorkerMain menuWorkerMain = new MenuWorkerMain(640,480, Main.worker);
        menuWorkerMain.show();
        // hide this window.
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    // Update the account with the new details in the form
    public void Update(ActionEvent event) throws SQLException {
        System.out.println("Update details.");

        String username = fxUsername.getText();
        String password = fxPassword.getText();
        String question1 = fxQuestion1.getText();
        String answer1 = fxAnswer1.getText();
        String question2 = fxQuestion2.getText();
        String answer2 = fxAnswer2.getText();

        System.out.println("Update username to "+username);
        System.out.println("Update password to "+password);
        System.out.println("Update q1 to "+question1);
        System.out.println("Update a1 to "+answer1);
        System.out.println("Update q2 to "+question2);
        System.out.println("Update a2 to "+answer2);

        PreparedStatement preparedStatement = null;
        String query = "UPDATE user SET password = ? WHERE id = ?";
        try
        {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2,Main.worker.uid );
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
        }
        finally
        {
            preparedStatement.close();
        }

        updateForm();
    }

    public void updateForm()
    {
        fxUsername.setText(Main.worker.getEmail());
        fxQuestion1.setText(Main.worker.getQuestion1());
        fxAnswer1.setText(Main.worker.getAnswer1());
        fxQuestion2.setText(Main.worker.getQuestion2());
        fxAnswer2.setText(Main.worker.getAnswer2());
        fxPassword.setText(Main.worker.getPassword());
    }
}