package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientModel extends Thread {

    Socket clientSocket;
    ServerSocket serverSocket;
    DataInputStream in;
    DataOutputStream out;
    public ClientModel(ServerSocket sock) {
        serverSocket = sock;
    }

    /**
     * Attempts to connect with a client. Will loop until this is done successfully, or five attempts are unsuccessful.
     */
    void AttemptConnection() {

        try {
            //Attempt a connection with a client
            clientSocket = serverSocket.accept();   //THIS LINE WILL BLOCK UNTIL CLIENT CONNECTS
            System.out.println("Server-side connection opened successfully on " +
                    serverSocket.getInetAddress() +
                    " on port " + serverSocket.getLocalPort() +
                    " with " + clientSocket.getRemoteSocketAddress()+". Running on Thread "+this.getId());

            //Open streams to transfer data to clients
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error! Failed to connect to client! Trying again...");
            this.AttemptConnection(); //Try again.
        }


    }

    public DataInputStream getClientSocket() {
        return in;
    }

    @Override
    public void run() {
        AttemptConnection();
        try {
            in.transferTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
