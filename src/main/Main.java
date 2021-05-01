package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ui/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        Parent test;
        test = FXMLLoader.load(getClass().getResource("ui/test.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Test page");
        stage.setScene(new Scene(test, 800, 600));
        stage.show();
        //stage.show();
        // Hide this current window (if this is what you want)
        //((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
