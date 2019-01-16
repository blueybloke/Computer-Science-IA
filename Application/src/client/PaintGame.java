package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.ServerManager;

import java.io.IOException;
import java.util.List;

/**
 * Entry point of the application. Begins the application and opens the server browser.
 */
public class PaintGame extends Application {

    static Stage pStage;
    ClientManager clientManager;
    ServerManager serverManager;

    /**
     * A getter for the primary stage.
     * @return
     */
    public static Stage getpStage() {
        return pStage;
    }

    /**
     * The start method for the application main thread.
     * @param primaryStage The default stage to load.
     * @throws Exception If something goes wrong in starting the JavaFX side of things.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Store the primary stage
        pStage = primaryStage;

        /**
         * This chunk of code is used to determine how to start the program.
         */
        List<String> args = getParameters().getRaw();
        if (args.size() > 0) {
            if (args.size() > 0) {
                switch (args.get(0)) { //Check whether in Server or Client mode
                    case "ServerMode": try { //Try to start as a Server with a custom port.
                        //Start the server
                        serverManager = new ServerManager(Integer.parseInt(args.get(1)));
                        serverManager.startServer();


                        //Start the Client
                        clientManager = new ClientManager("localhost",Integer.parseInt(args.get(1)));
                        clientManager.start();
                        break;
                    } catch (IndexOutOfBoundsException e) { //Try to start as a server with default port.
                        System.out.println("No port entered, starting with default. ");
                        //Start the server
                        serverManager = new ServerManager();
                        serverManager.startServer();


                        //Start the Client
                        clientManager = new ClientManager();
                        clientManager.start();
                        break;
                    }

                    case "ClientMode": try { //Try to start as a client with a custom port.
                        //Start the Client
                        clientManager = new ClientManager("localhost", Integer.parseInt(args.get(1)));
                        clientManager.start();
                        break;
                    } catch (IndexOutOfBoundsException e) { //Try to start as a client with default port
                        System.out.println("No port entered, starting with default. ");
                        //Start the Client
                        clientManager = new ClientManager();
                        clientManager.start();
                        break;
                    }
                    //Start in headless mode
                    case "HeadlessMode": try {
                        System.out.println("Starting in headless mode with custom port");
                        serverManager = new ServerManager(Integer.parseInt(args.get(1)));
                        serverManager.startServer();
                        break;
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Starting in headless mode with default port");
                        serverManager = new ServerManager();
                        serverManager.startServer();
                        break;
                    }
                }
            }
        } else {
            //TODO: If this part of the code is reached, it is starting with the launcher.
            System.out.println(" === PROGRAM STARTED AS LAUNCHER === \n Exiting...");
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}