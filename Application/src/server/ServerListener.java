package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A main class to manage the server side of the game. This creates a server when instantiated, and handles sending
 * updates to the server client.
 */
public class ServerListener extends Thread {

    private int SERVER_PORT;
    private Socket clientSocket;

    public ServerListener(int port) {
        SERVER_PORT = port;
    }

    @Override
    public void run() {
        try {

            // Displaying the thread that is running
            System.out.println ("Server Thread " +
                    Thread.currentThread().getId() +
                    " is running");

            //Create a new ServerSocket
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Created server socket on port " + serverSocket.getLocalPort());

            //Attempt a connection with a client
            clientSocket = serverSocket.accept();             //THIS LINE WILL BLOCK UNTIL CLIENT CONNECTS
            System.out.println("Connection opened on " +
                    serverSocket.getInetAddress() +
                    " on port "+serverSocket.getLocalPort());

            //Forwards output from the OutputStream out to the socket's outgoing connection.
            OutputStream out = clientSocket.getOutputStream();

            // TODO: Refactor the input stream so that input is handled by the ClientListener.

            while(true) {
                //TODO: Refactor the ServerListener loop
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
