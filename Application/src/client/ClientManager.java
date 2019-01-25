package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A class to define the main gameplay inputs the player can perform such as drawing onto the stage.
 */
class ClientManager extends Thread {

    //Initalization related variables
    private Thread clientListener;
    private final int defaultPortNumber = 45665;
    private FXMLLoader loader;
    private Stage primaryStage;

    /**
     * Create a new ClientManager on the localhost with the default port. Used when creating a new server.
     */
    public ClientManager() throws IOException {
        primaryStage = PaintGame.getpStage();
        loader = startGUI(primaryStage);
        clientListener = new ClientListener(defaultPortNumber, loader);
        clientListener.start();
    }

    /**
     * Create a new ClientManager on a set port and host.
     * @param host
     * @param portNumber
     * @throws IOException
     */
    public ClientManager(String host, int portNumber) throws IOException {
        loader = startGUI(PaintGame.getpStage());
        clientListener = new ClientListener(host, portNumber, loader);
        clientListener.start();
    }

    /**
     * Used to load the GUI.
     * @param primaryStage
     * @throws IOException
     */
    private FXMLLoader startGUI(Stage primaryStage) throws IOException {
        //Start the view
        FXMLLoader l = new FXMLLoader(getClass().getResource("/views/main.fxml"));
        Parent root = l.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        return l;
    }

    /**
     * Used to handle client side game logic.
     */
    @Override
    public void run() {
        System.out.println("Starting ClientManager thread with ID: " + this.getId());
    }
}

