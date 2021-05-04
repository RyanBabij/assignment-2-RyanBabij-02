package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestMenu {

    boolean failState;

    Parent test;
    Stage stage = new Stage();
    TestMenu(int sizeX, int sizeY)
    {
        failState=false;
        try
        {
            //Parent test;
            test = FXMLLoader.load(getClass().getResource("ui/test.fxml"));
            //Stage stage = new Stage();
            stage.setTitle("Test page");
            stage.setScene(new Scene(test, sizeX, sizeY));
            //stage.show();
            //stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (Exception e)
        {
            System.out.println("TestMenu failed.");
            failState=true;
        }

    }

    void show()
    {
        if (failState)
        {
            System.out.println("TestMenu failed more.");
            return;
        }
        stage.show();
    }

}
