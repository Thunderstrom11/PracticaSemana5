package ni.edu.uam.practicas5.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import ni.edu.uam.practicas5.util.SceneManager;


import java.io.IOException;

public class MenuPrincipalController {

    @FXML
    private void abrirLogin(){
        try {
            SceneManager.abrirVentana(
                    "/ni/edu/uam/practicas5/view/Login.fxml",
                    "Login");
        }catch (IOException e){
            new Alert(Alert.AlertType.ERROR,"Error al abrir el login").showAndWait();

        }
    }
    @FXML
    private void abrirConsulta(){

    }

    @FXML
    private void abrirRegistro(){

    }

    @FXML
    private void mostrarInformacionProyecto(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText(null);
        alert.setContentText("Sistema de Registro y consulta de solicitudes de sus clientes \n - Programa desarrollado por Diego Chamendy y José Salgado " );
        alert.showAndWait();
    }



    @FXML
    private void cerrarApp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Desea cerrar la aplicacion ?", ButtonType.OK, ButtonType.CANCEL);
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) Platform.exit();
    }


}
