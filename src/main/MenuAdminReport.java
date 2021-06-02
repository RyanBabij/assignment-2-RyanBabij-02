package main;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.model.interfaces.GUI;

import static java.lang.Thread.sleep;

public class MenuAdminReport implements GUI {

    boolean failState;

    Parent test;
    Stage stage = new Stage();

    @FXML
    private Label fxMainLabel;

    private int updateCounter;

    public MenuAdminReport(int sizeX, int sizeY)
    {
        updateCounter = 0;

        failState=false;
        try
        {
            test = FXMLLoader.load(getClass().getResource("ui/MenuAdminReport.fxml"));
            stage.setTitle("Admin report");
            stage.setScene(new Scene(test, sizeX, sizeY));
        }
        catch (Exception e)
        {
            System.out.println("TestMenu failed.");
            failState=true;
        }

    }

    @FXML
    public void initialize()
    {
        fxMainLabel.setText("AAA\nBBB");
    }

    public void init()
    {
    }

    public void show()
    {
        if (failState)
        {
            System.out.println("TestMenu failed more.");
            return;
        }
        stage.show();
    }

    public void hide()
    {
        stage.hide();
    }

    public boolean isFailState()
    {
        return failState;
    }

}
