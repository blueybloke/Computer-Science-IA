package client;

import controllers.mainController;
import javafx.fxml.FXMLLoader;

import java.io.*;
import java.net.Socket;

/**
 * The networking component of the client-side.
 * This runs on a separate thread and manages sending
 * and receiving data from the server, as well as connecting.
 */
public class ClientListener extends Thread {

    private static DataOutputStream out;
    private final String host;
    private final int portNumber;
    private final mainController mc;


    /**
     * Used when starting the client as a server.
     * @param p Port to start the client listener with.
     * @param loader The FXMLLoader used to access the mainController
     */
    public ClientListener(int p, FXMLLoader loader){
        host = "localhost";
        portNumber = p;
        mc = loader.getController();
    }

    /**
     * Used when starting the client as a client, connecting to a remote host.
     * @param h Host to connect to, often localhost.
     * @param p Used to pass a specific port. Usually the default, 45665.
     * @param loader The FXMLLoader used to access the mainController
     */
    public ClientListener(String h, int p, FXMLLoader loader) {
        host = h;
        portNumber = p;
        mc = loader.getController();
    }

    /**
     * A getter for the DataOutputStream.
     * @return Returns a static DataOutputStream instance.
     */
    public static DataOutputStream getOut() {
        return out;
    }

    /**
     * A method used to attempt a connection to a host. Will block until connection is made.
     * @param host The host used to connect to.
     * @param portNumber The port to connect via.
     * @return Returns a socket to be used when fixing the input and output streams.
     * @throws IOException Thrown if there is an issue opening the socket.
     */
    private Socket AttemptConnection(String host, int portNumber) throws IOException {
        Socket sock;
        sock = new Socket(host, portNumber);
        if (sock.isConnected())
            System.out.println("Client-side connection made to host " +
                    sock.getInetAddress() +
                    " on port " +
                    sock.getPort() + " with " + sock.getRemoteSocketAddress());

        return sock;
    }

    @Override
    public void run() {
        try {

            System.out.println("Starting ClientListener thread on "+this.getId());

            Socket socket = AttemptConnection(host, portNumber);

            //Start input and output streams, and bind them to the socket
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            //Update loop
            //noinspection InfiniteLoopStatement (THIS LINE USED BY IDE)
            while(true) {
                receiveScreenUpdate(in);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //=============== This next part would need to be refactored to support different types of packets ===============

    /**
     * A helper method for sending screen updates to other clients. Calls the mainController to pull a byte[] of the
     * graphicsContext and then writes it to the given DataOutputStream, prefixed by a single int value which identifies
     * the length of the message (it is assumed the graphics context won't output the same length packet each time).
     * @param out Takes a DataOutputStream to send the screen update through.
     * @param mc Takes a mainController instance to get the snapshot from.
     * @throws IOException Can throw IOException since it is writing to a stream.
     */
    public static void sendScreenUpdate(DataOutputStream out, mainController mc) throws IOException {

        byte[] message = mc.getCurrentSnapshot();
        System.out.println("Message sent with length: " + message.length);
        out.writeInt(message.length);
        out.write(message);
        out.flush();
    }

    /**
     * A helper method for receiving updates from other clients. First reads the packet length and then if that is
     * greater than zero, it will use the message to write to call the method to update the graphicsContext.
     * @param in Takes a DataInputStream to pull the packet from.
     * @throws IOException Might throw an IOException because it is pulling from a DataInputStream.
     */
    private void receiveScreenUpdate(DataInputStream in) throws IOException {

        if (in.available() > 0) {
            int messageLength = in.readInt(); //Gets the length of the incoming screen. This line blocks until an int (4 bytes) is read.
            System.out.println("Message received with length: " + messageLength);

            //If the message is longer than 0 bytes, parse it and tell the mainController to write it to the GraphicsContext
            if (messageLength > 0) {
                System.out.println("Beginning message processing...");
                byte[] buffer = new byte[messageLength];
                System.out.println("Receiving message array built.");
                in.readNBytes(buffer, 0, messageLength);
                System.out.println("Message read.");
                mc.setGraphicsContextFromByteArray(buffer);
                System.out.println("Message processed!");
            }
        }
    }
}