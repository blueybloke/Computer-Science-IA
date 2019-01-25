package server;

import java.io.*;
import java.net.ServerSocket;
import java.util.HashSet;

/**
 * A main class to manage the server side of the game. This creates a server when instantiated, and handles sending
 * updates to the server client.
 */
class ServerListener extends Thread {

    private final int SERVER_PORT;

    public static final HashSet<DataOutputStream> outputs = new HashSet<>();

    public ServerListener(int port) {
        SERVER_PORT = port;
    }

    @Override
    public void run() {
        try (var listenerSocket = new ServerSocket(SERVER_PORT)) {

            // Displaying the thread that is running
            System.out.println("Server Thread " +
                    Thread.currentThread().getId() +
                    " is running");

            //Create a new ServerSocket
             //Create the server socket
            listenerSocket.setSoTimeout(0); //Sets connection timeout to infinity
            System.out.println("Created server socket on port " + listenerSocket.getLocalPort());

            //This will loop allowing multiple clients to connect, and then add their out streams to the master list.
            //noinspection InfiniteLoopStatement
            while (true) {
                outputs.add(new ClientModel(listenerSocket.accept()).getOut());
                System.out.println("Added a new client to list. New length: "+outputs.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
