package ni.edu.uam.practicas5.model;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Cliente {
    private String nombres;
    private String apellidos;
    private String tipoCliente;
    private String ciudad;
    private LocalDate fechaNacimiento;
    private String tipoSolicitud;
    private String servicios;
    private String rutaFotografia;

    //paso de datos entre ventanas. registro agrega y la consulta lee.
    public static final List<Cliente> registrados = new ArrayList<>(); //lista de objetos cliente

    //este es el cliente que el usuario selecciona en la tabla de consulta
    // el detalle lo va a leer aca para mostrar los datos.
    public static Cliente seleccionado;

    public String getNombreCompleto(){
        return nombres + " " + apellidos;
    }

}
