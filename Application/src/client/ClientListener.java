package client;

import controllers.mainController;
import javafx.fxml.FXMLLoader;

import java.io.*;
import java.net.Socket;

public class ClientListener extends Thread {

    Socket socket;
    static DataOutputStream out;
    DataInputStream in;
    String host = "localhost";
    int portNumber;
    mainController mc;


    /**
     * Used when starting the client as a server.
     * @param p Port to start the client listener with.
     * @param loader The FXMLLoader used to access the mainController
     * @throws IOException Thrown if there is an issue opening the socket.
     */
    public ClientListener(int p, FXMLLoader loader) throws IOException {
        host = "localhost";
        portNumber = p;
        mc = loader.getController();
    }

    /**
     * Used when starting the client as a client, connecting to a remote host.
     * @param h Host to connect to, often localhost.
     * @param p Used to pass a specific port. Usually the default, 45665.
     * @param loader The FXMLLoader used to access the mainController
     * @throws IOException Thrown if there is an issue opening the socket.
     */
    public ClientListener(String h, int p, FXMLLoader loader) throws IOException {
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
     * @param host
     * @param portNumber
     * @return Returns a socket to be used when fixing the input and output streams.
     * @throws IOException Thrown if there is an issue opening the socket.
     */
    Socket AttemptConnection (String host, int portNumber) throws IOException {
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

            socket = AttemptConnection(host, portNumber);

            //Start input and output streams, and bind them to the socket
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            //Update loop TODO: These are both looping correctly and don't block. mainController is also sending the right byte array through, although stopping quickly.
            while(true) {
                recieveScreenUpdate(in);
                //sendScreenUpdate(out);
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
        out.write(message.length);
        out.write(message);
        out.flush();
    }

    /**
     * A helper method for recieving updates from other clients. First reads the packet length and then if that is
     * greater than zero, it will use the message to write to call the method to update the graphicscontext.
     * @param in Takes a DataInputStream to pull the packet from.
     * @throws IOException Might throw an IOException because it is pulling from a DataInputStream.
     */
    public void recieveScreenUpdate(DataInputStream in) throws IOException {

        if (in.available() > 0) { //TODO: No bytes are coming in so the contents of this if statement won't run.
            int messageLength = in.readInt(); //Gets the length of the incoming screen. This line blocks until an int (4 bytes) is read.
            System.out.println("Message recieved with length: " + messageLength);


            //If the message is longer than 0 bytes, parse it and tell the mainController to write it to the GraphicsContext
            if (messageLength > 0) {
                System.out.println("Beggining message processing...");
                byte[] message = new byte[messageLength];
                in.readFully(message, 0, message.length);
                System.out.println("Message read.");
                mc.setGraphicsContextFromByteArray(message);
                System.out.println("Message processed!");
            }
        }
    }
}