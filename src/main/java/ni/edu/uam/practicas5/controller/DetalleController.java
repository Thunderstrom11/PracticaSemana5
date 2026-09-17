package ni.edu.uam.practicas5.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ni.edu.uam.practicas5.model.Cliente;

public class DetalleController {
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblTipo;
    @FXML
    private Label lblCiudad;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblSolicitud;
    @FXML
    private Label lblCarpeta;
    @FXML
    private ImageView imgCliente;
    @FXML
    private ListView<String> lstServicios;

    @FXML
    private void initialize(){
        Cliente cliente = Cliente.seleccionado; // recoge el dato   que dejo la consulta
        if (cliente == null) return;

        //mostrar los detalles
        lblNombre.setText(cliente.getNombreCompleto());
        lblTipo.setText(cliente.getTipoCliente());
        lblCiudad.setText(cliente.getCiudad());
        lblFecha.setText(cliente.getFechaNacimiento() == null ?  "" : cliente.getFechaNacimiento().toString()); //if en una linea
        lblSolicitud.setText(cliente.getTipoSolicitud());
        lblCarpeta.setText(cliente.getRutaCarpeta() == null ? "No seleccionado" : cliente.getRutaCarpeta());

        if (cliente.getRutaFotografia() != null){
            imgCliente.setImage(new Image(cliente.getRutaFotografia()));
        }

        String servicios = cliente.getServicios();
        if(servicios != null && !servicios.isBlank()){
            lstServicios.setItems(FXCollections.observableArrayList(servicios.split(", ")));
        }

    }

    @FXML
    private void cerrar(){
        Stage stage = (Stage) lblNombre.getScene().getWindow();
        stage.close();
    }
}
