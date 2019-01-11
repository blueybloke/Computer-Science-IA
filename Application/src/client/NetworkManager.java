package client;

import java.net.Socket;

/**
 * A class used to manage networking.
 */
public class NetworkManager implements Runnable {

    Socket socket;

    Thread t;
    /**
     * A class used to manage networking.
     * @param port Used to specify the port to open the server on.
     */
    public NetworkManager(int port) {
        socket = new Socket();
    }

    public void start() { t.start(); }

    public void run() {

    }
}
