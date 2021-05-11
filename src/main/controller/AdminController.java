package main.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.model.LoginModel;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class AdminController
{


    //public LoginModel loginModel = new LoginModel();
    @FXML
    private Label welcomeText;

    private int updateCounter;

    public AdminController()
    {
        updateCounter=0;
    }

    @FXML
    public void initialize()
    {
        welcomeText.setText("AAA");

        // This seems to be a way to safely update GUI elements without causing exceptions from
        // trying to change things prior to JavaFX initializing them.
        /*
        Platform.runLater(new Runnable() {
            @Override public void run() {
                welcomeText.setText("AAA2");

                ++updateCounter;
                welcomeText.setText(Integer.toString(updateCounter));

                try {
                    sleep(100);
                }
                catch (Exception e)
                {
                    System.out.println("sleep exception");
                }
            }
        });
         */

        initInputThread();
    }

    private void initInputThread() {

        //Scanner input = new Scanner(System.in);

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

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    @FXML
    public void setText(String str)
    {
        //welcomeText.setText(str);
    }

}
