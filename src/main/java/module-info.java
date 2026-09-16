module ni.edu.uam.practicas5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens ni.edu.uam.practicas5 to javafx.fxml;
    exports ni.edu.uam.practicas5;
    exports ni.edu.uam.practicas5.application;
    opens ni.edu.uam.practicas5.application to javafx.fxml;
    exports ni.edu.uam.practicas5.controller;
    opens ni.edu.uam.practicas5.controller to javafx.fxml;
}