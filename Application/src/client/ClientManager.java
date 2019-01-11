package client;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * A class to define the main gameplay inputs the player can perform such as drawing onto the stage.
 */
public class ClientManager implements Runnable {

    boolean myTurn = false;

    //Create a seperate thread for the ClientManager to run on.
    private Thread t;


    /**
     *
     */
    public ClientManager() {

    }

    /**
     * A method used to begin the ClientManager thread and class when called.
     */
    public void start() {
        t = new Thread(this, "ClientManager");
        System.out.println("Starting thread with ID: "+t.getId());
        t.start();
    }


    public void run() {

        while (true) {

        }
    }
}

