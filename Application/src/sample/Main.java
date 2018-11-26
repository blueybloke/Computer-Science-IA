package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        Stage newStage = new Stage();
        newStage.setTitle("Another stage");
        Parent anotherRoot = FXMLLoader.load(getClass().getResource("sample.fxml"));
        newStage.setScene(new Scene(anotherRoot, 200, 275));
        newStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
