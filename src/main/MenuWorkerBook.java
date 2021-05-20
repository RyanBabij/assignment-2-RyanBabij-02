package main;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.model.account.Worker;

import static java.lang.Thread.sleep;

public class MenuWorkerBook
{
    Worker worker = null;

    boolean failState;

    Parent test;
    Stage stage = new Stage();

    private int updateCounter;

    public MenuWorkerBook(int sizeX, int sizeY, Worker worker)
    {
        this.worker = worker;
        updateCounter = 0;

        failState=false;
        try
        {
            test = FXMLLoader.load(getClass().getResource("ui/MenuBook.fxml"));
            stage.setTitle("Make a booking");
            stage.setScene(new Scene(test, sizeX, sizeY));
        }
        catch (Exception e)
        {
            System.out.println("TestMenu failed.");
            failState=true;
        }

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
