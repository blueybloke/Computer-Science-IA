package server;

import com.sun.security.ntlm.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientModel extends Thread {

    Socket clientSocket;
    ServerSocket serverSocket;
    public ClientModel(ServerSocket sock) {
        serverSocket = sock;
    }

    /**
     * Attempts to connect with a client. Will loop until this is done successfully, or five attempts are unsuccessful.
     */
    void AttemptConnection() {

        int failedAttempts = 0;
        while (failedAttempts < 5) {
            try {
                //Attempt a connection with a client
                clientSocket = serverSocket.accept();   //THIS LINE WILL BLOCK UNTIL CLIENT CONNECTS
                System.out.println("Server-side connection opened successfully on " +
                        serverSocket.getInetAddress() +
                        " on port " + serverSocket.getLocalPort() +
                        " with " + clientSocket.getRemoteSocketAddress());
            } catch (IOException e) {
                failedAttempts++;
                System.out.println("Error! Failed to connect to client! "
                        + failedAttempts + " unsuccessful attempts. Retrying...");
            }
        }
    }
    @Override
    public void run() {
        AttemptConnection();
    }
}
