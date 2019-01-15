package server;

import java.io.IOException;

public class ServerManager extends Thread {

    // Variables
    Thread serverListener;
    GameState gs = GameState.WAITING_TO_START;
    ClientModel currentTurn;

    //Standard port number for the game.
    int defaultPortNumber = 45665;

    /**
     * Initalizes the server manager on the default port.
     */
    public ServerManager() throws IOException {
        //Create the server thread object that manages connections and transferring data.
        serverListener = new ServerListener(defaultPortNumber);
    }

    /**
     * Initalizes the server manager on the passed port.
     * @param portNumber
     */
    public ServerManager(int portNumber) throws IOException {
        //Create the server thread object that manages connections and transferring data.
        serverListener = new ServerListener(portNumber);
    }

    /**
     * Start the server networking component.
     */
    public void startServer() {
        serverListener.start();
    }

    @Override
    public void run() {

    }
}

