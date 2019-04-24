package client;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The entry point for the client version of the application.
 */
public class ClientMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Set some variables
        PaintGame.setPStage(primaryStage);
        ClientManager clientManager;

        try {
            //Start the Client
            clientManager = new ClientManager();
            clientManager.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
