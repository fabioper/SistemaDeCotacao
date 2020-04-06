package br.edu.infnet.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public final void start(Stage stage) throws IOException {
        var scene = new Scene(loadView());
        scene.getStylesheets().add("/styles/Styles.css");
        initializeStage(stage, scene);
    }

    private void initializeStage(Stage stage, Scene scene) {
        stage.setTitle("Sistema de Cotações");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private Parent loadView() throws IOException {
        var view = getClass().getResource("/views/TelaPrincipal.fxml");
        return (Parent) FXMLLoader.load(view);
    }
}
