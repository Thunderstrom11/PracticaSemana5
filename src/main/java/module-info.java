module ni.edu.uam.practicas5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.practicas5 to javafx.fxml;
    exports ni.edu.uam.practicas5;
}