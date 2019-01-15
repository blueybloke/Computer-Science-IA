package controllers;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class to define the view controller for the main view. Used to handle actions given by the user.
 */
public class mainController implements Initializable , Runnable {

    @FXML
    public ColorPicker colorPicker;
    @FXML
    public Label ipAddressLbl;
    @FXML
    public Label turnLbl;
    @FXML
    public Label statusLbl;
    @FXML
    public CheckBox readyCheckBox;
    @FXML
    public Canvas canvas;

    //Singleton variable
    public mainController mc;

    //Local variables
    double mouseX;
    double mouseY;
    Thread t;
    static GraphicsContext graphicsContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphicsContext = canvas.getGraphicsContext2D();
        this.start();
    }

    public void start() {
        t = new Thread(this, "ClientManager");
        System.out.println("Starting thread with ID: "+t.getId());
        t.start();
    }

    public void run() {
        initalizePen(); // Begin the Pen
    }

    /**
     * Used to bind drawing.
     */
    public void initalizePen() {

        //Set variables for color etc.
        graphicsContext.setStroke(colorPicker.getValue());
        graphicsContext.lineTo(mouseX, mouseY);
        graphicsContext.stroke();

        //Bind the mousedrag being released to end the stroke and clear mouse x/y
        canvas.setOnMousePressed(e -> {
            if (true) {
                System.out.println("pressed!");
                graphicsContext.beginPath();
            }
        });

        //Handle mouse being dragged and create a stroke.
        canvas.setOnMouseDragged(e -> {

            //Update the mouse position.
            mouseX = e.getX();
            mouseY = e.getY();

            //Update the color
            graphicsContext.setStroke(colorPicker.getValue());

            //Draw the line if it is the client's turn
            if (true) {
                graphicsContext.lineTo(mouseX, mouseY);
                graphicsContext.stroke();
            }
        });
    }

    /**
     * A helper method that converts a graphicsContext to an ImageIO image, and then into a byte array to be serialized
     * Over the network.
     */
    public static byte[] graphicsContextToByteArray() throws IOException {

        //Get a snapshot of the graphics context
        Image snappedImage = graphicsContext.getCanvas().snapshot(null,null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(SwingFXUtils.fromFXImage(snappedImage, null), "jpg", baos);
        baos.flush();
        return baos.toByteArray();
    }


    /**
     * A helper method that places an image onto the graphics context after converting it from a byte array.
     */
    public static void setGraphicsContextFromByteArray() {

    }

    public void canvasMouseDragged(MouseEvent mouseEvent) {
    }

    public void canvasEntered(MouseEvent mouseEvent) {
    }

    public void canvasMouseReleased(MouseEvent mouseEvent) {
    }

    public void canvasExited(MouseEvent mouseEvent) {
    }

    public void onSave(ActionEvent actionEvent) {
    }

    public void onQuit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
