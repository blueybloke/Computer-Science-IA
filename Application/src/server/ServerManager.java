package server;

public class ServerManager implements Runnable {

    // Variables
    Thread serverThread;
    GameState gs = GameState.WAITING_TO_START;
    Client currentTurn;

    public ServerManager() {

        serverThread = new Thread();

    }

    @Override
    public void run() {

    }
}
