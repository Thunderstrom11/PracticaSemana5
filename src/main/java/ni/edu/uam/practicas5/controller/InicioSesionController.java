package ni.edu.uam.practicas5.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ni.edu.uam.practicas5.util.AlertsUtils;
import ni.edu.uam.practicas5.util.SceneManager;

import java.io.IOException;

public class InicioSesionController {
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField pwdContrasena;

    private String USUARIO_VALIDO = "admin";
    private String CLAVE_VALIDA = "1234";

    @FXML
    private void initialize() { // enter para ir al siguiente campo
        txtUsuario.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                pwdContrasena.requestFocus();
            }
        });
    }

    //boton ingresar
    @FXML
    private void iniciarSesion(ActionEvent event) {
        String usuario = txtUsuario.getText().trim();
        String clave = pwdContrasena.getText();

        if (usuario.isEmpty() || clave.isEmpty()) {
            AlertsUtils.showAlert("Campos incompletos", "Completa el usuario y contrasena");
            return;
        }

        //not
        if (!usuario.equals(USUARIO_VALIDO) || !clave.equals(CLAVE_VALIDA)) {
            AlertsUtils.showInfo("Acceso denegado", "Usuario o contrasena incorrectos");
            pwdContrasena.clear();
            pwdContrasena.requestFocus();
            return;
        }
        AlertsUtils.showInfo("Bienvenido", "Bienvenido, " + usuario + ".");

        try {
            Stage stage = (Stage) txtUsuario.getScene().getWindow(); //cualquier elemento toma el window
            stage.close();
            SceneManager.abrirVentana("/ni/edu/uam/practicas5/fxml/menu-principal.fxml", "Client Manager - Menu Principal");
        } catch (IOException e) { //errores de lectura del fxml
            AlertsUtils.showError("Error", "No fue posible abrir el menu principal");
        }
    }

    @FXML
    private void salir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Desea salir del programa?", ButtonType.OK, ButtonType.CANCEL);

        if (AlertsUtils.showConfirmation("Confirmar salida", "Desea salir del programa?")) {
            Platform.exit();
        }
    }
}
