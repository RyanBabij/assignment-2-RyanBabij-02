package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.model.account.Worker;

public class Main extends Application {

    public static Stage primaryStage;
    public static Worker worker;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("ui/OldLogin.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("ui/MenuLogin.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        this.primaryStage = primaryStage;
        primaryStage.show();



    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
