module ni.edu.uam.practicas5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;



    exports ni.edu.uam.practicas5;
    exports ni.edu.uam.practicas5.application;
    exports ni.edu.uam.practicas5.model;
    exports ni.edu.uam.practicas5.controller;
    exports ni.edu.uam.practicas5.util;


    opens ni.edu.uam.practicas5 to javafx.fxml;
    opens ni.edu.uam.practicas5.application to javafx.fxml;
    opens ni.edu.uam.practicas5.controller to javafx.fxml;
    opens ni.edu.uam.practicas5.model to javafx.base;
}