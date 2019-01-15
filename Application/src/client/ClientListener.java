package client;

import controllers.mainController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientListener extends Thread {

    private Socket socket;

    /**
     * Used when starting the client as a server.
     * @throws IOException
     */
    public ClientListener(int portNumber) throws IOException {
        socket = new Socket("localhost",portNumber); //Attempt a connection with the localhost server.
        if (socket.isConnected())
            System.out.println("Connection made to host " +
                    socket.getInetAddress() +
                    " on port " +
                    socket.getPort());
    }

    /**
     * Used when starting the client as a client, connecting to a remote host.
     * @param host Host to connect to, often localhost.
     * @param portNumber Used to pass a specific port. Usually the default, 45665.
     * @throws IOException Thrown if there is an issue opening the socket.
     */
    public ClientListener(String host, int portNumber) throws IOException {
        socket = new Socket(host, portNumber);
    }


    @Override
    public void run() {
        try {

            // TODO: Create a method that detects images from the InputStream, maybe by using a byte packet that describes the length of the following image byte[]

            InputStream in  = socket.getInputStream();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: refactor this method so that screen updates are only sent by the client.
    public static void sendScreenUpdate(OutputStream out) throws IOException {
        out.write(mainController.graphicsContextToByteArray());
    }
}
