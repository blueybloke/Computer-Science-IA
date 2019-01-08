package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class to define the view controller for the main view. Used to handle actions given by the user.
 */
public class mainController implements Initializable {

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

    //Mouse pos
    double mouseX;
    double mouseY;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Get the graphicsContext of the canvas and store mouse x and y
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();



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
