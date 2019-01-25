package server;

import java.io.IOException;

public class ServerManager extends Thread {

    // Variables
    private final Thread serverListener;
    GameState gs = GameState.WAITING_TO_START;
    ClientModel currentTurn;

    //Standard port number for the game.
    private final int defaultPortNumber = 45665;

    /**
     * Initalizes the server manager on the default port.
     */
    public ServerManager() {
        //Create the server thread object that manages connections and transferring data.
        serverListener = new ServerListener(defaultPortNumber);
    }

    /**
     * Initalizes the server manager on the passed port.
     * @param portNumber
     */
    public ServerManager(int portNumber) {
        //Create the server thread object that manages connections and transferring data.
        serverListener = new ServerListener(portNumber);
    }

    /**
     * Start the server networking component.
     */
    public void startServer() {
        serverListener.start();
    }
}


