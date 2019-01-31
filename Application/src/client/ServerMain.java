package client;

import javafx.application.Application;
import javafx.stage.Stage;
import server.ServerManager;

import java.io.IOException;

public class ServerMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Set some variables
        PaintGame.setPStage(primaryStage);
        ServerManager serverManager;
        ClientManager clientManager;

        try {
            //Start the server
            serverManager = new ServerManager();
            serverManager.startServer();


            //Start the Client
            clientManager = new ClientManager();
            clientManager.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
