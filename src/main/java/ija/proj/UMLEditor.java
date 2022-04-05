package ija.proj;

import java.io.IOException;

import ija.proj.uml.ClassDiagram;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UMLEditor extends App{
    private String activeTable = null;
    @FXML
    private Label detailText;
    @FXML
    private Button addAttributesBtn;
    @FXML
    private Button addMethodBtn;
    @FXML
    private AnchorPane main;
    @FXML
    private AnchorPane detail;

    @FXML
    private void gotoUMLClass() throws IOException {
        App.setRoot("UMLshit");
    }
    @FXML
    private void addAttribute() throws IOException {

    }
    @FXML
    private void addMethod() throws IOException {

    }
    @FXML
    private void createClass() throws IOException {

        classDiagram = new ClassDiagram("Diagram");
        if (classDiagram.createClass("Class", idCounter) != null) {
            // vytvoření listu
            VBox vbox = new VBox();

            // atributy
            VBox attributes = new VBox();
            vbox.getChildren().add(attributes);

            // separátor
            vbox.getChildren().add(new Separator());

            // metody
            VBox methods = new VBox();
            vbox.getChildren().add(methods);

            // vytvoření tabulky Třídy
            TitledPane titledPane = new TitledPane("Class", vbox);
            titledPane.setId(String.valueOf(idCounter));
            titledPane.setPrefSize(-1, -1);
            titledPane.setLayoutX(10*idCounter);
            titledPane.setLayoutY(10*idCounter);
            titledPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    activeTable = titledPane.getId();
                    Label label = new Label("My Label");
                    ((Label) detail.lookup("#detailText")).setText("Detail tabulky "+titledPane.getText()); //! toto mě stálo 5 hodin života
                    ((Button) detail.lookup("#addAttributesBtn")).setDisable(false);
                    ((Button) detail.lookup("#addMethodBtn")).setDisable(false);
                }
            });

            // vložení panu do mainu
            main.getChildren().add(titledPane);

            // zvednutí id pro další class
            this.idCounter++;
        }

        System.out.println(classDiagram.getName());
        System.out.println(classDiagram.classes.get(0).getName());
        System.out.println(classDiagram.classes.get(0).getId());
    }
}
