package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerManager {

    // Variables
    Thread serverThread;
    GameState gs = GameState.WAITING_TO_START;
    Client currentTurn;

    //Standard port number for the game.
    int portNumber = 45665;


    public ServerManager() {

        //Create the server thread object that manages connections and transferring data.
        serverThread = new ServerListener(portNumber);
    }

    /**
     * Start the server process,
     */
    public void startServer() {
        serverThread.start();
    }
}

