package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A class to define the main logic for the client side.
 * Can be used to add extra functionality/Client-side logic.
 */
public class ClientManager extends Thread {

    //Initialization related variables
    private Thread clientListener;
    private FXMLLoader loader;

    /**
     * Create a new ClientManager on the localhost
     * with the default port. Used when creating a new server.
     */
    public ClientManager() throws IOException {
        Stage primaryStage = PaintGame.getPStage();
        loader = startGUI(primaryStage);
        int defaultPortNumber = 45665;
        clientListener = new ClientListener(defaultPortNumber, loader);
        clientListener.start();
    }

    /**
     * Create a new ClientManager on a set port and host.
     * @param host Specifies the host to connect to.
     * @param portNumber Specifies the port to connect via.
     * @throws IOException Will throw an IOException if it fails to connect.
     */
    public ClientManager(String host, int portNumber) throws IOException {
        loader = startGUI(PaintGame.getPStage());
        clientListener = new ClientListener(host, portNumber, loader);
        clientListener.start();
    }

    /**
     * Used to load the GUI.
     * @param primaryStage The stage to be loaded.
     * @throws IOException Will throw an IOException if something goes wrong.
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

