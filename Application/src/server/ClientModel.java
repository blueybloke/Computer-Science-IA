package server;

import java.io.*;
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
    private void AttemptConnection() {

        try {
            //TODO: As it currently is, the clients are each only receiving data from themselves and sending it back to themselves.
            //TODO: Ensure that data is being synced to ALL clients. Manage out from ServerListener?
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

    //Input stream getter
    public DataInputStream getIn() {
        return in;
    }

    //Output stream getter
    public DataOutputStream getOut() {
        return out;
    }

    boolean isConnected() {
        if (clientSocket != null) {
            return clientSocket.isConnected();
        }
        else {
            return false;
        }
    }

    @Override
    public void run() {
        AttemptConnection();
    }
}
