package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.awt.*;

/**
 * A class to define the view controller for the main view. Used to handle actions given by the user.
 */
public class mainController {

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

    public void initalize() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        canvas.setOnMouseDragged(mouseEvent -> {
            System.out.println("Dragged.");
            double mouseX = mouseEvent.getX();
            double mouseY = mouseEvent.getY();

            graphicsContext.setFill(colorPicker.getValue());
            graphicsContext.fillOval(mouseX,mouseY,100,100);
        });
    }

    /**
     * View controller event method. Defines behaviour of the save button.
     * @param actionEvent
     */
    public void onSave(ActionEvent actionEvent) {
    }

    /**
     * View controller event method. Defines behaviour of the quit button.
     * @param actionEvent
     */
    public void onQuit(ActionEvent actionEvent) {
        Platform.exit();
    }


    public void canvasMouseDragged(MouseEvent mouseEvent) {
    }

    public void canvasEntered(MouseEvent mouseEvent) {
    }

    public void canvasExited(MouseEvent mouseEvent) {
    }

    public void canvasMouseReleased(MouseEvent mouseEvent) {
    }
}
