package ija.proj;

import ija.proj.uml.ClassDiagram;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    public static Scene scene;
    public static ClassDiagram classDiagram;
    public Integer idCounter = 1;
    public static ObservableList<String> accessibilityList = FXCollections.observableArrayList();


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("UMLEditor"));
        stage.setScene(scene);
        stage.setTitle("UML Editor");
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
        accessibilityList.add("+");
        accessibilityList.add("-");
        accessibilityList.add("#");
        accessibilityList.add("~");
        launch();
    }

}