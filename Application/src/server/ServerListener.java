package server;

import java.io.*;
import java.net.ServerSocket;
import java.util.HashSet;

/**
 * The networking component of the Server-side. This
 * class is responsible for starting the server,
 * accepting connections, and redirecting/syncing
 * traffic between clients.
 */
public class ServerListener extends Thread {

    private final int SERVER_PORT;

    //A HashSet that holds the DataOutputStreams to be written to.
    public static final HashSet<DataOutputStream> outputs = new HashSet<>();

    /**
     * Constructor for the ServerListener. Binds the relevant port.
     * @param port Port to start the server on.
     */
    public ServerListener(int port) {
        SERVER_PORT = port;
    }


    //This will run as a new thread, and is used to accept new client connections.
    @Override
    public void run() {
        //Catch IOExceptions
        try (var listenerSocket = new ServerSocket(SERVER_PORT)) {

            // Displaying the thread that is running
            System.out.println("Server Thread " +
                    Thread.currentThread().getId() +
                    " is running");

            //Create a new ServerSocket
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
