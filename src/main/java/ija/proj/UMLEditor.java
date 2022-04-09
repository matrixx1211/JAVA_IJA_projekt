package ija.proj;

import java.io.IOException;

import ija.proj.uml.UMLAttribute;
import ija.proj.uml.UMLClass;
import ija.proj.uml.UMLClassifier;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UMLEditor extends App{
    private String activeObjName = null;
    UMLClass activeObj;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

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
    private TextField newAttributeName;
    @FXML
    private TextField newAttributeType;
    @FXML
    private ChoiceBox newAttributeAccess;


    @FXML
    private TableView attributesTable;
    @FXML
    private TableColumn attributesTableAccess;
    @FXML
    private TableColumn attributesTableName;
    @FXML
    private TableColumn attributesTableType;
    @FXML
    private TableColumn attributesTableAction;

    @FXML
    private void gotoUMLClass() throws IOException {
        App.setRoot("UMLshit");
    }
    @FXML
    private void changeClassName() throws IOException {
        String newName = newClassName.getText();
        if (newName.isEmpty()) {
            newName = " ";
        }
        classDiagram.changeClassName(activeObjName, newName);
        ((Label) detail.lookup("#detailText")).setText("Detail of "+newName);
        TitledPane classTable = ((TitledPane) main.lookup("#"+ activeObjName.replaceAll("\\s+","€")));
        classTable.setText(newName);
        classTable.setId(newName.replaceAll("\\s+","€"));

        // změny ID pro attributes a operations
        VBox attributes = ((VBox) main.lookup(("#"+activeObjName+"Attributes").replaceAll("\\s+","€")));
        attributes.setId((newName+"Attributes").replaceAll("\\s+","€"));

        VBox operations = ((VBox) main.lookup(("#"+activeObjName+"Operations").replaceAll("\\s+","€")));
        operations.setId((newName+"Operations").replaceAll("\\s+","€"));

        activeObjName = newName;
    }
    @FXML
    private void addAttribute() throws IOException {
        UMLClassifier type = classDiagram.classifierForName(newAttributeType.getText());
        UMLAttribute attr = new UMLAttribute(newAttributeName.getText(), type, newAttributeAccess.getSelectionModel().getSelectedItem().toString());
        activeObj.addAttribute(attr);
        VBox attributes = ((VBox) main.lookup(("#"+activeObjName+"Attributes").replaceAll("\\s+","€")));
        Text attribute = new Text(newAttributeName.getText());
        attributes.getChildren().add(attribute);
        System.out.println(type);

    }
    @FXML
    private void addOperation() throws IOException {

    }
    @FXML
    private void clickedInterfaceBtn() throws IOException {
        createClass(true);
    }
    @FXML
    private void clickedClassBtn() throws IOException {
        createClass(false);
    }

    private void createClass(Boolean isInterface) throws IOException {
        String name = className.getText();
        if (name.isEmpty())
        {
            name = " ";
        }

        UMLClass newObj = classDiagram.createClass(name, idCounter, false);
        if (newObj != null) {
            // vytvoření listu
            VBox vbox = new VBox();

            if (!isInterface) {
                // atributy
                VBox attributes = new VBox();
                vbox.getChildren().add(attributes);
                attributes.setId((name+"Attributes").replaceAll("\\s+","€"));

                // separátor
                vbox.getChildren().add(new Separator());
            }

            // metody
            VBox operations = new VBox();
            vbox.getChildren().add(operations);
            operations.setId((name+"Operations").replaceAll("\\s+","€"));

            // vytvoření tabulky Třídy
            TitledPane titledPane = new TitledPane(name, vbox);
            titledPane.setId(name.replaceAll("\\s+","€"));
            titledPane.setPrefSize(-1, -1);
            titledPane.setLayoutX(10*idCounter);
            titledPane.setLayoutY(10*idCounter);
            titledPane.setCollapsible(false);
            titledPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    activeObjName = titledPane.getId();
                    Label label = new Label("My Label");
                    ((Label) detail.lookup("#detailText")).setText("Detail of "+titledPane.getText()); //! toto mě stálo 5 hodin života
                    ((TextField) detail.lookup("#newClassName")).setText(titledPane.getText());
                    activeObjName = titledPane.getText();
                    ((Button) detail.lookup("#addAttributeBtn")).setDisable(false);
                    ((Button) detail.lookup("#addOperationBtn")).setDisable(false);
                    ((TitledPane) detail.lookup("#classDetailPane")).setDisable(false);
                    if (isInterface) {
                        ((TitledPane) detail.lookup("#attributesPane")).setDisable(true);
                        ((TitledPane) detail.lookup("#attributesPane")).setExpanded(false);
                    }
                    else {
                        ((TitledPane) detail.lookup("#attributesPane")).setDisable(false);
                    }
                    activeObj = classDiagram.getObject(activeObjName);
                    ((TitledPane) detail.lookup("#operationsPane")).setDisable(false);
                    orgSceneX = event.getSceneX();
                    orgSceneY = event.getSceneY();
                    orgTranslateX = ((TitledPane)(event.getSource())).getTranslateX();
                    orgTranslateY = ((TitledPane)(event.getSource())).getTranslateY();
                }
            });

            // pohyb tabulky při táhnutí myší
            titledPane.setOnMouseDragged(new EventHandler <MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event) {
                    double offsetX = event.getSceneX() - orgSceneX;
                    double offsetY = event.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((TitledPane)(event.getSource())).setTranslateX(newTranslateX);
                    ((TitledPane)(event.getSource())).setTranslateY(newTranslateY);
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
