package ni.edu.uam.practicas5.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import ni.edu.uam.practicas5.model.Cliente;
import ni.edu.uam.practicas5.util.AlertsUtils;
import ni.edu.uam.practicas5.util.SceneManager;

import java.io.IOException;
import java.time.LocalDate;

public class ConsultaClientesController {

    @FXML private TableView<Cliente> tbvClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colTipo;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TableColumn<Cliente, LocalDate> colFechaNac;
    @FXML private TableColumn<Cliente, String> colTipoSolicitud;
    @FXML private TableColumn<Cliente, String> colFoto;
    @FXML private TableColumn<Cliente, String> colCarpeta;

    private final ObservableList<Cliente> clientes = FXCollections.observableList(Cliente.registrados);

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colFechaNac.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colTipoSolicitud.setCellValueFactory(new PropertyValueFactory<>("tipoSolicitud"));
        colCarpeta.setCellValueFactory(new PropertyValueFactory<>("rutaCarpeta"));
        colFoto.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getRutaFotografia()));
        colFoto.setCellFactory(_ -> new TableCell<>() {
            private final ImageView view = new ImageView();
            @Override
            protected void updateItem(String ruta, boolean empty) {
                super.updateItem(ruta, empty);
                if (empty || ruta == null) {
                    setGraphic(null);
                } else {
                    view.setFitHeight(40);
                    view.setFitWidth(40);
                    view.setPreserveRatio(true);
                    view.setImage(new Image(ruta, 40, 40, true, true, true));
                    setGraphic(view);
                }
            }
        });
        tbvClientes.setFixedCellSize(50);
        tbvClientes.setItems(clientes);
        tbvClientes.setRowFactory(tabla -> {
            TableRow<Cliente> fila = new TableRow<>();
            fila.setOnMouseClicked(evento -> {
                if (fila.isEmpty()) return;
                if (evento.getButton() == MouseButton.PRIMARY) {
                    abrirEdicion(fila.getItem());
                } else if (evento.getButton() == MouseButton.SECONDARY) {
                    tbvClientes.getSelectionModel().select(fila.getItem());
                }
            });
            return fila;
        });
    }

    private void abrirEdicion(Cliente cliente) {
        Cliente.seleccionado = cliente;
        try {
            SceneManager.abrirVentana(
                    "/ni/edu/uam/practicas5/fxml/registro-clientes.fxml",
                    "Client Manager - Editar Cliente");
        } catch (IOException e) {
            AlertsUtils.showError("Error", "No fue posible abrir el registro de clientes");
        }
        tbvClientes.refresh();
    }

    @FXML
    private void verDetalle() {
        Cliente cliente = tbvClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) {
            AlertsUtils.showAlert("Sin seleccion", "Seleccione primero un cliente de la tabla.");
            return;
        }
        Cliente.seleccionado = cliente;
        try {
            SceneManager.abrirVentana(
                    "/ni/edu/uam/practicas5/fxml/detalle-cliente.fxml",
                    "Client Manager - Detalle del Cliente");
        } catch (IOException e) {
            AlertsUtils.showError("Error", "No fue posible abrir el detalle del cliente");
        }
    }

    @FXML
    private void salirConsultaCliente() {
        ((Stage) tbvClientes.getScene().getWindow()).close();
    }
}
