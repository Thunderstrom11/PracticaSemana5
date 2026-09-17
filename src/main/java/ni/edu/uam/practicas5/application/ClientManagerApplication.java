package ni.edu.uam.practicas5.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientManagerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientManagerApplication.class.getResource("/ni/edu/uam/practicas5/fxml/inicio-sesion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 420, 340);
        stage.setTitle("Client Manager - Inicio de Sesion");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
