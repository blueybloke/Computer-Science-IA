package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point of the application. Begins the application and opens the server browser.
 */
public class PaintGame extends Application {

    public ClientManager clientManager;
    static PaintGame mainInstance = new PaintGame();

    public static PaintGame getInstance() {
        return mainInstance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        clientManager = new ClientManager();
        clientManager.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}