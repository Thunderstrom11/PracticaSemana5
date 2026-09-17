package ni.edu.uam.practicas5.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.ContextMenuEvent;
import ni.edu.uam.practicas5.model.Cliente;
import ni.edu.uam.practicas5.util.AlertsUtils;
import ni.edu.uam.practicas5.util.SceneManager;


import java.io.IOException;

public class MenuPrincipalController {

    @FXML
    private void abrirConsulta(){
        try {
            SceneManager.abrirVentana(
                    "/ni/edu/uam/practicas5/fxml/consulta-clientes.fxml",
                    "Client Manager - Consulta de Clientes");
        }catch (IOException e){
            AlertsUtils.showError("Error", "No fue posible Consultar la lista de clientes");

        }

    }

    @FXML
    private void abrirRegistro(){
        Cliente.seleccionado = null;
        try {
            SceneManager.abrirVentana(
                    "/ni/edu/uam/practicas5/fxml/registro-clientes.fxml",
                    "Client Manager - Registro de Clientes");
        }catch (IOException e){
            AlertsUtils.showError("Error", "No fue posible abrir el registro de clientes");

        }

    }

    @FXML
    private void mostrarInformacionProyecto(ActionEvent event){
        AlertsUtils.showInfo("Acerca de", "Sistema de Registro y consulta de solicitudes de sus clientes \n - Programa desarrollado por Diego Chamendy y José Salgado");
    }

    @FXML private ContextMenu menuContextual;
    @FXML
    private void mostrarMenuContextual(ContextMenuEvent evento) {
        menuContextual.show((Node) evento.getSource(), evento.getScreenX(), evento.getScreenY());
    }



    @FXML
    private void cerrarApp(){
        if (AlertsUtils.showConfirmation("Cerrar aplicacion", "Desea cerrar la aplicacion?")) {
            Platform.exit();
        }
    }


}
