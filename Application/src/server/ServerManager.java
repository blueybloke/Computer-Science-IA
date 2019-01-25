package server;


public class ServerManager extends Thread {

    // Variables
    private final Thread serverListener;

    /**
     * Initializes the server manager on the default port.
     */
    public ServerManager() {
        //Create the server thread object that manages connections and transferring data.
        //Standard port number for the game.
        int defaultPortNumber = 45665;
        serverListener = new ServerListener(defaultPortNumber);
    }

    /**
     * Initializes the server manager on the passed port.
     * @param portNumber The port to start the server on.
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


