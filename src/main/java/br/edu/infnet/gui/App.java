package br.edu.infnet.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public final void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/TelaInicial.fxml"));
        var scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sistema de Cotações");
        stage.show();
    }
}
