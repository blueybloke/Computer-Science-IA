package client;

import java.net.Socket;

public class NetworkManager {

    Socket socket;
    public NetworkManager(int port) {
        socket = new Socket();
    }
}
