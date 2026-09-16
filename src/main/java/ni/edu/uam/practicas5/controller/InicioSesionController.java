package ni.edu.uam.practicas5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InicioSesionController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
