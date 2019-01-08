package client;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * A class to define the main gameplay inputs the player can perform such as drawing onto the stage.
 */
public class PlayerManager implements Runnable {

    mainController mc;
    GraphicsContext gc;
    Canvas canvas;
    boolean myTurn = false;

    //Create a seperate thread for the PlayerManager to run on.
    private Thread t;


    /**
     *
     * @param mainController Takes the mainController.
     */
    public PlayerManager(mainController mainController) {

        this.mc = mainController;
        gc = this.mc.canvas.getGraphicsContext2D();
        canvas = gc.getCanvas();
    }

    /**
     * A method used to begin the PlayerManager thread and class when called.
     */
    public void start() {
        t = new Thread(this, "PlayerManager");
        System.out.println("Starting thread with ID: "+t.getId());
        this.run();
    }


    public void run() {

        //Handle mousedrag being released to end the stroke and clear mouse x/y
        canvas.setOnMousePressed(e -> {
            if (myTurn) {
                System.out.println("pressed!");
                gc.beginPath();
            }
        });

    }

    public void performTurn() {

        //Set variables for color etc.
        gc.setStroke(mc.colorPicker.getValue());
        gc.lineTo(mc.mouseX, mc.mouseY);
        gc.stroke();

        //Handle mouse being dragged and create a stroke.
        canvas.setOnMouseDragged(e -> {

            //Update the mouse position.
            mc.mouseX = e.getX();
            mc.mouseY = e.getY();

            //Update the color
            gc.setStroke(mc.colorPicker.getValue());

            //Draw the line if it is the client's turn
            if (myTurn) {
                gc.lineTo(mc.mouseX, mc.mouseY);
                gc.stroke();
            }
        });


    }
}
