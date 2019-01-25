package controllers;

import client.ClientListener;

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class to define the view controller for the main view.
 * Used to handle actions given by the user.
 */
public class mainController implements Initializable {

    @FXML
    ColorPicker colorPicker;
    @FXML
    Label ipAddressLbl;
    @FXML
    Label turnLbl;
    @FXML
    Label statusLbl;
    @FXML
    CheckBox readyCheckBox;
    @FXML
    Canvas canvas;

    //Local variables
    private byte[] currentSnapshot = new byte[0];
    private double mouseX;
    private double mouseY;
    private GraphicsContext graphicsContext; //Set to be static since we only want one graphics context per client

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphicsContext = canvas.getGraphicsContext2D();
        initializePen();
    }

    /**
     * Used to bind drawing.
     */
    private void initializePen() {

        //Set variables for color etc.
        graphicsContext.setStroke(colorPicker.getValue());
        graphicsContext.lineTo(mouseX, mouseY);
        graphicsContext.stroke();

        //Bind the mouse-drag being released to end the stroke and clear mouse x/y
        canvas.setOnMousePressed(e -> {
            System.out.println("pressed!");
            graphicsContext.beginPath();
        });

        //Handle mouse being dragged and create a stroke
        canvas.setOnMouseDragged(e -> {

            //Update the mouse position.
            mouseX = e.getX();
            mouseY = e.getY();

            //Update the color
            graphicsContext.setStroke(colorPicker.getValue());

            //Draw the line if it is the client's turn
            graphicsContext.lineTo(mouseX, mouseY);
            graphicsContext.stroke();
        });

        //Handle updating the graphics context when mouse is released
        canvas.setOnMouseReleased(e -> {
            try {
                currentSnapshot = this.graphicsContextToByteArray();
                System.out.println("Mouse Release Callback reached");
                System.out.println("Current snapshot size: " + currentSnapshot.length);
                ClientListener.sendScreenUpdate(ClientListener.getOut(),this);
            } catch (IOException e1) {
                System.out.println("Failed to snapshot graphics context.");
            }
        });
    }

    /**
     * A getter for the current byte[] representing the graphicsContext, updated each mouse release.
     * @return Returns a byte[] representing the current snapshot taken from the canvas.
     */
    public byte[] getCurrentSnapshot() {
        return currentSnapshot;
    }


    /**
     * A helper method that converts a graphicsContext to an ImageIO image, and then into a byte array to be serialized
     * Over the network.
     */
    private byte[] graphicsContextToByteArray() throws IOException {
        //Get a snapshot of the graphics context
        Image snappedImage = graphicsContext.getCanvas().snapshot(null,null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(SwingFXUtils.fromFXImage(snappedImage, null), "png", baos);
        baos.flush();
        System.out.println("Pre-networked message length: " + baos.toByteArray().length+"\n");
        for (int i = 0; i < baos.toByteArray().length; i++) {
            System.out.print(", "+baos.toByteArray()[i]+", ");
        }
        return baos.toByteArray();
    }

    /**
     * A helper method that places an image onto the graphics context after converting it from a byte array.
     */
    public void setGraphicsContextFromByteArray(byte[] message) {
        //Parse the image from the given byte[] and draw it to the graphics context
        Image img = new Image(new ByteArrayInputStream(message));
        graphicsContext.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());
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
