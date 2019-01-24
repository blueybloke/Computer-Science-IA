package server;

import client.ClientManager;
import java.io.*;
import java.net.ServerSocket;

/**
 * A main class to manage the server side of the game. This creates a server when instantiated, and handles sending
 * updates to the server client.
 */
public class ServerListener extends Thread {

    private int SERVER_PORT;
    private int serverBufferLength = 0;
    private int remoteBufferLength = 0;
    private int localBufferLength = 0;
    private byte[] serverBuffer = new byte[0];
    private ClientModel localModel;
    private ClientModel remoteModel;

    public ServerListener(int port) {
        SERVER_PORT = port;
    }

    @Override
    public void run() {
        try {

            // Displaying the thread that is running
            System.out.println("Server Thread " +
                    Thread.currentThread().getId() +
                    " is running");

            //Create a new ServerSocket
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT); //Create the server socket
            serverSocket.setSoTimeout(0); //Sets connection timeout to infinity
            System.out.println("Created server socket on port " + serverSocket.getLocalPort());

            //Open data streams to forward packets between clients. CLIENT1 --> SERVER ---> CLIENT2
            //Open client connections on separate threads. TODO: Refactor next line so localClient won't start in headless mode.
                localModel = new ClientModel(serverSocket);
                localModel.start(); //Will block until connection approved.
                remoteModel = new ClientModel(serverSocket);
                remoteModel.start();

                while (localModel.isConnected() && remoteModel.isConnected()) { //Block until connection is made.
                    //Do nothing
                }
                System.out.println("Starting refresher...");
                while (true) {

                    //Store the buffer lengths of the input streams.
                    localBufferLength = localModel.getIn().readInt();
                    remoteBufferLength = remoteModel.getIn().readInt();

                    //Start by piping the local model's stream.
                    if (serverBufferLength != localBufferLength) {
                        serverBufferLength =  localBufferLength; //If the buffer length of the server is different...
                        localModel.getIn().readNBytes(serverBuffer, 0, serverBufferLength); //Update the server.
                    }

                    //Start by piping the local model's stream.
                    if (serverBufferLength != remoteBufferLength) {
                        serverBufferLength =  remoteBufferLength; //If the buffer length of the server is different...
                        remoteModel.getIn().readNBytes(serverBuffer, 0, serverBufferLength); //Update the server.
                    }

                    //Push the updated serverBuffer to output streams.
                    localModel.getOut().writeInt(serverBufferLength);
                    localModel.getOut().write(serverBuffer);

                    remoteModel.getOut().writeInt(serverBufferLength);
                    remoteModel.getOut().write(serverBuffer);
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
