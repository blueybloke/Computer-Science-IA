package server;

import java.io.*;
import java.net.Socket;

@SuppressWarnings("InfiniteLoopStatement")
class ClientModel extends Thread {

    private DataInputStream in;
    private DataOutputStream out;

    /**
     * A class that is used to represent a single client and manage its connection.
     * @param sock Client socket to be connected via.
     * @throws IOException
     */
    public ClientModel(Socket sock) throws IOException {
        Socket socket = sock;

        //Connection succeeded!
        System.out.println("Server-side connection opened successfully on " +
                socket.getInetAddress() +
                " on port " + socket.getLocalPort() +
                " with " + socket.getRemoteSocketAddress()+". Running on Thread "+this.getId());

        //Open streams to transfer data to clients
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        //Start thread
        this.start();
    }

    @Override
    public void run() {

        //Begin the connection loop
        //noinspection InfiniteLoopStatement,InfiniteLoopStatement
        while (true) try {
            //If there is input coming through
            if (in.available() > 0) {
                //Store the input stream's contents in an ArrayList
                System.out.println("Bytes found, beginning redirection!");
                int bufferLength = in.readInt();

                byte[] buffer = new byte[bufferLength];
                in.readNBytes(buffer,0, bufferLength);
                System.out.println("Buffer stored!");

                //Iterate over all the output streams(Clients) that need writing to.
                for (DataOutputStream output : ServerListener.outputs) {
                    System.out.println("Re-directing...");
                    //Redirect it!
                    output.writeInt(bufferLength);
                    output.write(buffer);
                    //Flush the output stream
                    output.flush();
                }
                System.out.println("Redirect complete!");
            }
        } catch (IOException e) {
            System.out.println("Error in data forwarding: \n" + e);
        }

    }

    public DataOutputStream getOut() {
        return out;
    }

}
