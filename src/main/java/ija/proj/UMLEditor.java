package ija.proj;

import java.io.IOException;

import ija.proj.uml.ClassDiagram;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class UMLEditor extends App{
    @FXML
    private Button gotoUMLEditor;
    @FXML
    private AnchorPane main;
    @FXML
    private void gotoUMLClass() throws IOException {
        App.setRoot("UMLshit");
    }
    @FXML
    private void createClass() throws IOException {

        classDiagram = new ClassDiagram("Diagram");
        if (classDiagram.createClass("Class", idCounter) != null) {
            this.idCounter++;
            TitledPane titledPane = new TitledPane();
            titledPane.setPrefSize(-1, -1);
            titledPane.parentProperty();
            titledPane.setLayoutX(10*idCounter);
            titledPane.setLayoutY(10*idCounter);
            main.getChildren().add(titledPane);
        }

        System.out.println(classDiagram.getName());
        System.out.println(classDiagram.classes.get(0).getName());
        System.out.println(classDiagram.classes.get(0).getId());
    }
}
