package ija.proj;

import java.io.IOException;

import ija.proj.uml.UMLClass;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class UMLEditor extends App{
    private String activeTableName = null;

    @FXML
    private Label detailText;
    @FXML
    private TextField className;
    @FXML
    private TextField newClassName;
    @FXML
    private Button addAttributeBtn;
    @FXML
    private Button addOperationBtn;
    @FXML
    private AnchorPane main;
    @FXML
    private AnchorPane detail;

    @FXML
    private void gotoUMLClass() throws IOException {
        App.setRoot("UMLshit");
    }
    @FXML
    private void changeClassName() throws IOException {
        //TODO: upravit věci protože mezera not gut
        String newName = newClassName.getText();
        classDiagram.changeClassName(activeTableName, newName);
        ((Label) detail.lookup("#detailText")).setText("Detail of "+newName);
        TitledPane classTable = ((TitledPane) main.lookup("#"+activeTableName));
        classTable.setText(newName);
        classTable.setId(newName.trim());
        System.out.println(newName);
        System.out.println(activeTableName);
        activeTableName = newName;

    }
    @FXML
    private void addAttribute() throws IOException {

    }
    @FXML
    private void addOperation() throws IOException {

    }
    @FXML
    private void createClass() throws IOException {
        String name = className.getText();
        UMLClass newObj = classDiagram.createClass(name, idCounter);
        if (newObj != null) {
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
            TitledPane titledPane = new TitledPane(name, vbox);
            titledPane.setId(String.valueOf(name));
            titledPane.setPrefSize(-1, -1);
            titledPane.setLayoutX(10*idCounter);
            titledPane.setLayoutY(10*idCounter);
            titledPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    activeTableName = titledPane.getId();
                    Label label = new Label("My Label");
                    ((Label) detail.lookup("#detailText")).setText("Detail of "+titledPane.getText()); //! toto mě stálo 5 hodin života
                    ((TextField) detail.lookup("#newClassName")).setText(titledPane.getText());
                    activeTableName = titledPane.getText();
                    ((Button) detail.lookup("#addAttributeBtn")).setDisable(false);
                    ((Button) detail.lookup("#addOperationBtn")).setDisable(false);
                    ((TitledPane) detail.lookup("#attributesPane")).setDisable(false);
                    ((TitledPane) detail.lookup("#operationsPane")).setDisable(false);
                }
            });

            // vložení panu do mainu
            main.getChildren().add(titledPane);

            // zvednutí id pro další class
            this.idCounter++;

            //TODO: SMAZAT
            System.out.println(classDiagram.getName());
            System.out.println(classDiagram.classes.get(0).getName());
            System.out.println(classDiagram.classes.get(0).getId());
        }


    }
}
