package main;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import static java.lang.Thread.sleep;

import main.model.account.Worker;
import main.model.interfaces.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class MenuWorkerMain
{
    Worker worker = null;

    boolean failState;

    Parent test;
    Stage stage = new Stage();

    private int updateCounter;

    public MenuWorkerMain(int sizeX, int sizeY, Worker worker)
    {
        this.worker = worker;
        updateCounter = 0;

        failState=false;
        try
        {
            //Parent test;
            test = FXMLLoader.load(getClass().getResource("ui/MenuWorkerMain.fxml"));
            //Stage stage = new Stage();

            if (worker!=null)
            {
                stage.setTitle("Welcome, " +worker.getEmail() + " (uid "+worker.uid+")");
            }
            else
            {
                stage.setTitle("Worker main menu");
            }

            //stage.setTitle("Worker main menu");
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

    // This thread runs periodically in the background to update the GUI if necessary.
    private void initBackgroundThread()
    {
        // Define the thread

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                while (true) {
                    //String userInput = input.nextLine();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ++updateCounter;
                            //welcomeText.setText(Integer.toString(updateCounter));
                        }
                    });
                    try {
                        sleep(1000);
                        //fxBookingList.getItems().add("VVV");
                    }
                    catch (Exception e)
                    {
                        System.out.println("sleep exception");
                    }
                }
            }
        };

        // Start the thread
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
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
