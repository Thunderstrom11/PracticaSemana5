package ni.edu.uam.practicas5.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ni.edu.uam.practicas5.model.Cliente;
import ni.edu.uam.practicas5.util.AlertsUtils;

import java.io.File;
import java.time.LocalDate;

public class RegistroClientesController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private ComboBox<String> cmbTipoCliente;
    @FXML private ComboBox<String> cmbCiudad;
    @FXML private DatePicker dtpFechaNacimiento;
    @FXML private RadioButton chkCotizacion;
    @FXML private RadioButton chkReclamo;
    @FXML private ToggleGroup tipoCliente;
    @FXML private CheckBox chxbRetiro;
    @FXML private CheckBox chxbSoporte;
    @FXML private ImageView imgCliente;
    @FXML private Label lblRuta;
    @FXML private Button btnBorrar;

    private boolean modoEdicion;
    private Cliente clienteAEditar;
    private String rutaFotografia;
    private String rutaCarpetaSeleccionada;

    @FXML
    public void initialize() {
        cmbTipoCliente.setItems(FXCollections.observableArrayList("Personal", "Empresarial"));
        cmbCiudad.setItems(FXCollections.observableArrayList("Managua", "Masaya", "Granada", "Matagalpa"));
        dtpFechaNacimiento.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate fecha, boolean empty) {
                super.updateItem(fecha, empty);
                setDisable(empty || fecha.isAfter(LocalDate.now()));
            }
        });

        Platform.runLater(this::prepararModoEdicion);
    }

    @FXML
    private void guardarCliente() {
        if (modoEdicion) { guardarEdicion(); return; }
        if (!validarCampos()) return;

        if (confirmarRegistro()) {
            Cliente nuevoCliente = new Cliente(
                    txtNombre.getText().trim(),
                    txtApellido.getText().trim(),
                    cmbTipoCliente.getValue(),
                    textoCiudad(),
                    dtpFechaNacimiento.getValue(),
                    textoTipoSolicitud(),
                    serviciosSeleccionados(),
                    rutaFotografia,
                    rutaCarpetaSeleccionada);

            Cliente.registrados.add(nuevoCliente);
            AlertsUtils.showInfo("Exito", "Cliente registrado correctamente.");
            limpiarCampos();
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().isBlank()) {
            AlertsUtils.showAlert("Validacion", "Los nombres no pueden estar vacios.");
            return false;
        }
        if (txtApellido.getText().isBlank()) {
            AlertsUtils.showAlert("Validacion", "Los apellidos no pueden estar vacios.");
            return false;
        }
        if (cmbTipoCliente.getValue() == null) {
            AlertsUtils.showAlert("Validacion", "Debe seleccionar un tipo de cliente.");
            return false;
        }
        if (textoCiudad().isBlank()) {
            AlertsUtils.showAlert("Validacion", "Debe ingresar una ciudad.");
            return false;
        }
        if (dtpFechaNacimiento.getValue() == null) {
            AlertsUtils.showAlert("Validacion", "Debe seleccionar la fecha de nacimiento.");
            return false;
        }
        if (tipoCliente.getSelectedToggle() == null) {
            AlertsUtils.showAlert("Validacion", "Debe seleccionar un tipo de solicitud.");
            return false;
        }
        return true;
    }

    private boolean confirmarRegistro() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmar registro");
        dialog.setHeaderText("Verifique los datos antes de guardar");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        grid.add(new Label("Nombres:"), 0, 0);
        grid.add(new Label(txtNombre.getText().trim()), 1, 0);
        grid.add(new Label("Apellidos:"), 0, 1);
        grid.add(new Label(txtApellido.getText().trim()), 1, 1);
        grid.add(new Label("Tipo de cliente:"), 0, 2);
        grid.add(new Label(cmbTipoCliente.getValue()), 1, 2);
        grid.add(new Label("Ciudad:"), 0, 3);
        grid.add(new Label(textoCiudad()), 1, 3);
        grid.add(new Label("Fecha de nacimiento:"), 0, 4);
        grid.add(new Label(String.valueOf(dtpFechaNacimiento.getValue())), 1, 4);
        grid.add(new Label("Tipo de solicitud:"), 0, 5);
        grid.add(new Label(textoTipoSolicitud()), 1, 5);
        grid.add(new Label("Servicios:"), 0, 6);
        grid.add(new Label(serviciosSeleccionados()), 1, 6);
        grid.add(new Label("Carpeta:"), 0, 7);
        grid.add(new Label(rutaCarpetaSeleccionada == null ? "No seleccionada" : rutaCarpetaSeleccionada), 1, 7);

        dialog.getDialogPane().setContent(grid);
        return dialog.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private String textoCiudad() {
        String texto = cmbCiudad.getEditor().getText();
        if (texto == null || texto.isBlank()) {
            Object valor = cmbCiudad.getValue();
            return valor == null ? "" : valor.toString().trim();
        }
        return texto.trim();
    }

    private String textoTipoSolicitud() {
        Toggle toggle = tipoCliente.getSelectedToggle();
        return toggle == null ? "" : ((RadioButton) toggle).getText();
    }

    private String serviciosSeleccionados() {
        StringBuilder servicios = new StringBuilder();
        if (chxbRetiro.isSelected()) servicios.append("Plan de retiro");
        if (chxbSoporte.isSelected()) {
            if (!servicios.isEmpty()) servicios.append(", ");
            servicios.append("Soporte Técnico");
        }
        return servicios.isEmpty() ? "Ninguno" : servicios.toString();
    }

    @FXML
    private void seleccionarImg() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccionar fotografia");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg"));
        File archivo = chooser.showOpenDialog(txtNombre.getScene().getWindow());
        if (archivo != null) {
            rutaFotografia = archivo.toURI().toString();
            imgCliente.setImage(new Image(rutaFotografia));
        }
    }

    @FXML
    private void reiniciarImg() {
        rutaFotografia = null;
        imgCliente.setImage(null);
    }

    @FXML
    private void seleccionarCarpeta() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Seleccionar carpeta");
        File carpeta = chooser.showDialog(txtNombre.getScene().getWindow());
        if (carpeta != null) {
            rutaCarpetaSeleccionada = carpeta.getAbsolutePath();
            lblRuta.setText(rutaCarpetaSeleccionada);
            AlertsUtils.showInfo("Carpeta seleccionada", rutaCarpetaSeleccionada);
        }
    }

    @FXML
    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        cmbTipoCliente.setValue(null);
        cmbCiudad.setValue(null);
        cmbCiudad.getEditor().clear();
        dtpFechaNacimiento.setValue(null);
        chxbRetiro.setSelected(false);
        chxbSoporte.setSelected(false);
        chkCotizacion.setSelected(false);
        chkReclamo.setSelected(false);
        rutaFotografia = null;
        rutaCarpetaSeleccionada = null;
        imgCliente.setImage(null);
        lblRuta.setText("");
    }

    private void prepararModoEdicion() {
        if (Cliente.seleccionado == null) return;
        modoEdicion = true;
        clienteAEditar = Cliente.seleccionado;
        llenarCamposDesde(clienteAEditar);
        btnBorrar.setVisible(true);
        btnBorrar.setDisable(false);
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.setTitle("Client Manager - Editar Cliente");
    }

    private void llenarCamposDesde(Cliente cliente) {
        txtNombre.setText(cliente.getNombres());
        txtApellido.setText(cliente.getApellidos());
        cmbTipoCliente.setValue(cliente.getTipoCliente());
        if (cliente.getCiudad() != null) {
            cmbCiudad.getEditor().setText(cliente.getCiudad());
            cmbCiudad.setValue(cliente.getCiudad());
        }
        dtpFechaNacimiento.setValue(cliente.getFechaNacimiento());
        if (cliente.getTipoSolicitud() != null) {
            for (Toggle toggle : tipoCliente.getToggles()) {
                RadioButton radio = (RadioButton) toggle;
                if (radio.getText().equalsIgnoreCase(cliente.getTipoSolicitud())) {
                    radio.setSelected(true);
                }
            }
        }
        String servicios = cliente.getServicios();
        if (servicios != null) {
            chxbRetiro.setSelected(servicios.contains("Plan de retiro"));
            chxbSoporte.setSelected(servicios.contains("Soporte"));
        }
        rutaFotografia = cliente.getRutaFotografia();
        if (rutaFotografia != null) imgCliente.setImage(new Image(rutaFotografia));
        rutaCarpetaSeleccionada = cliente.getRutaCarpeta();
        if (rutaCarpetaSeleccionada != null) lblRuta.setText(rutaCarpetaSeleccionada);
    }

    // aplica los cambios sobre el mismo objeto de la lista (no crea uno nuevo)
    @FXML
    private void guardarEdicion() {
        if (!validarCampos()) return;
        if (!confirmarRegistro()) return;
        clienteAEditar.setNombres(txtNombre.getText().trim());
        clienteAEditar.setApellidos(txtApellido.getText().trim());
        clienteAEditar.setTipoCliente(cmbTipoCliente.getValue());
        clienteAEditar.setCiudad(textoCiudad());
        clienteAEditar.setFechaNacimiento(dtpFechaNacimiento.getValue());
        clienteAEditar.setTipoSolicitud(textoTipoSolicitud());
        clienteAEditar.setServicios(serviciosSeleccionados());
        clienteAEditar.setRutaFotografia(rutaFotografia);
        clienteAEditar.setRutaCarpeta(rutaCarpetaSeleccionada);
        AlertsUtils.showInfo("Exito", "Cliente actualizado correctamente.");
        salirRegistroClientes();
    }

    @FXML
    private void borrarCliente() {
        if (AlertsUtils.showConfirmation("Confirmar eliminacion",
                "Desea eliminar el cliente '" + clienteAEditar.getNombreCompleto() + "'?")) {
            Cliente.registrados.remove(clienteAEditar);
            AlertsUtils.showInfo("Exito", "Cliente eliminado correctamente.");
            salirRegistroClientes();
        }
    }

    @FXML
    private void salirRegistroClientes() {
        Cliente.seleccionado = null;
        modoEdicion = false;
        clienteAEditar = null;
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }
}
