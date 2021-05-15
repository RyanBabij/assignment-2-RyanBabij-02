package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;

public class RecoverController
{
    @FXML
    private TextField fxUsername;
    @FXML
    private TextField fxAnswer1;
    @FXML
    private TextField fxAnswer2;
    @FXML
    private Label fxFeedback;

    public RecoverController()
    {
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
