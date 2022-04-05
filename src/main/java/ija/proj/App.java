package ija.proj;

import ija.proj.uml.ClassDiagram;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    public static Scene scene;
    public static ClassDiagram classDiagram;
    public Integer idCounter = 1;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("UMLEditor"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        classDiagram = new ClassDiagram("Diagram");
        launch();
    }

}