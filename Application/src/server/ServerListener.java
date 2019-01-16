package server;

import com.sun.security.ntlm.Client;

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
    ClientModel localClient;
    ClientModel remoteClient;

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
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT); //Create the server socket
            serverSocket.setSoTimeout(0); //Sets connection timeout to infinity
            System.out.println("Created server socket on port " + serverSocket.getLocalPort());

            //Open two client connections on seperate threads. TODO: Refactor next line so localClient won't start in headless mode.
            localClient = new ClientModel(serverSocket);
            localClient.start();
            remoteClient = new ClientModel(serverSocket);
            remoteClient.start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
