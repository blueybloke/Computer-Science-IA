package client;

import java.io.IOException;

/**
 * A class to define the main gameplay inputs the player can perform such as drawing onto the stage.
 */
public class ClientManager extends Thread {

    //Initalization related variables
    Thread clientListener;
    int defaultPortNumber = 45665;

    //ClientModel game logic variables.
    boolean myTurn = false;

    /**
     * Create a new ClientManager on the localhost with the default port. Used when creating a new server.
     */
    public ClientManager() throws IOException {
        clientListener = new ClientListener(defaultPortNumber);
    }

    /**
     * Create a new ClientManager on a set port and host.
     * @param host
     * @param portNumber
     * @throws IOException
     */
    public ClientManager(String host, int portNumber) throws IOException {
        clientListener = new ClientListener(host, portNumber);
    }

    /**
     * Used to handle client side game logic.
     */
    @Override
    public void run() {
        System.out.println("Starting ClientManager thread with ID: " + this.getId());
    }
}

