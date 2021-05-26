package main;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.model.interfaces.GUI;

import static java.lang.Thread.sleep;

public class MenuAdminLockdown implements GUI {

    boolean failState;

    Parent test;
    Stage stage = new Stage();

    @FXML
    private Label welcomeText;

    private int updateCounter;

    public MenuAdminLockdown(int sizeX, int sizeY)
    {
        updateCounter = 0;

        failState=false;
        try
        {
            test = FXMLLoader.load(getClass().getResource("ui/AdminConfirmLockdown.fxml"));
            stage.setTitle("Confirm lockdown");
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
        welcomeText.setText("AAA");
        initBackgroundThread();
    }

    public void init()
    {
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
                            welcomeText.setText(Integer.toString(updateCounter));
                        }
                    });
                    try {
                        sleep(1000);
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

    // Logout worker and return to login screen
    public void Back(ActionEvent event)
    {
        System.out.println("BACK");
        // back to worker menu
        MenuAdminMain menuAdminMain = new MenuAdminMain(640,480);
        menuAdminMain.show();
        // hide this window.
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
