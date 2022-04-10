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

    // levá část
    @FXML private TextField className;

    // střed
    @FXML private AnchorPane main;

    // pravá část
    @FXML private AnchorPane detail;
    @FXML private Label detailText;

    @FXML private TitledPane classDetailPane;
    @FXML private TextField newClassName;

    @FXML private TitledPane attributesPane;
    @FXML private ChoiceBox newAttributeAccess;
    @FXML private TextField newAttributeName;
    @FXML private TextField newAttributeType;
    @FXML private Button addAttributeBtn;

    @FXML private TitledPane operationsPane;
    @FXML private Button addOperationBtn;

    @FXML private ChoiceBox newOperationAccess;

    @FXML
    private Label leftStatusLabel;
    @FXML
    private Label rightStatusLabel;

    @FXML private void gotoUMLClass() throws IOException {
        App.setRoot("UMLshit");
    }
    @FXML private void changeClassName() throws IOException {
        String newName = newClassName.getText();
        if (newName.isEmpty()) {
            newName = " ";
        }
        if (classDiagram.changeClassName(activeObjName, newName) == true) {
            detailText.setText("Detail of "+newName);
            TitledPane classTable = ((TitledPane) main.lookup("#"+ activeObjName.replaceAll("\\s+","€")));
            classTable.setText(newName);
            classTable.setId(newName.replaceAll("\\s+","€"));

            // změny ID pro attributes a operations
            if (!activeObj.isInterface()) {
                VBox attributes = ((VBox) main.lookup(("#"+activeObjName+"Attributes").replaceAll("\\s+","€")));
                attributes.setId((newName+"Attributes").replaceAll("\\s+","€"));
            }

            VBox operations = ((VBox) main.lookup(("#"+activeObjName+"Operations").replaceAll("\\s+","€")));
            operations.setId((newName+"Operations").replaceAll("\\s+","€"));

            activeObjName = newName;
        } else {
            leftStatusLabel.setText("Name \""+newName+"\" already exists!");
        }
        newClassName.setText(activeObjName);
    }

    @FXML private void addAttribute() throws IOException {
        String access = newAttributeAccess.getSelectionModel().getSelectedItem().toString();
        String name = newAttributeName.getText();
        String type = newAttributeType.getText();
        if (name.isEmpty() || type.isEmpty()) {
            leftStatusLabel.setText("Attribute name or type not entered.");
        }
        else {
            UMLClassifier classifier = classDiagram.findClassifier(type);
            if (classifier == null){
                classifier = new UMLClassifier(type, true);
                classDiagram.classifiers.add(classifier);
            }
            UMLAttribute attr = new UMLAttribute(newAttributeName.getText(), classifier, access);
            if (activeObj.addAttribute(attr)){
                VBox attributes = ((VBox) main.lookup(("#"+activeObjName+"Attributes").replaceAll("\\s+","€")));
                Text attribute = new Text(access + " - " + name + ":" + type);
                attributes.getChildren().add(attribute);
            }
        }
    }
    @FXML private void addOperation() throws IOException {

    }
    @FXML private void clickedInterfaceBtn() throws IOException {
        createClass(true);
    }
    @FXML private void clickedClassBtn() throws IOException {
        createClass(false);
    }

    /**
     * Metoda, která vytváří třídu nebo rozhraní podle parametru isInterface.
     * Provádí se kontroly, jestli již neexistuje objekt se stejným jménem.
     *
     * @param isInterface nastavuje příznak rozhraní
     */
    private void createClass(Boolean isInterface) {
        String name = className.getText();
        if (name.isEmpty())
        {
            leftStatusLabel.setText("No name entered!");
        } else {
            UMLClass newObj = classDiagram.createClass(name, idCounter, isInterface);
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
                        detailText.setText("Detail of "+titledPane.getText());
                        newClassName.setText(titledPane.getText());
                        activeObjName = titledPane.getText();

                        addAttributeBtn.setDisable(false);
                        addOperationBtn.setDisable(false);

                        classDetailPane.setDisable(false);
                        classDetailPane.setExpanded(true);
                        if (isInterface) {
                            attributesPane.setDisable(true);
                            attributesPane.setExpanded(false);
                        }
                        else {
                            attributesPane.setDisable(false);
                            attributesPane.setExpanded(true);
                        }
                        activeObj = classDiagram.getObject(activeObjName);

                        operationsPane.setDisable(false);
                        operationsPane.setExpanded(true);

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
            }
        }
    }
}
