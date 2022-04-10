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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UMLEditor extends App{
    private String activeObjName = null;
    private UMLClass activeObj;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    private String activeAttrName;

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
    @FXML private VBox attributesList;
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
                Text attribute = new Text(access + " <-> " + name + ":" + type);
                attribute.setId(name+"Attr");
                attributes.getChildren().add(attribute);
                HBox row = new HBox();
                row.setId((name+"Row").replaceAll("\\s+","€"));
                // výběrový box
                    // přistupnost
                    VBox accessCol = new VBox();
                    ChoiceBox<String> accessColChoiceBox = new ChoiceBox();
                    accessColChoiceBox.setDisable(true);
                    accessColChoiceBox.setItems(accessibilityList);
                    accessColChoiceBox.setValue(access);
                    accessCol.getChildren().add(accessColChoiceBox);
                    row.getChildren().add(accessCol);
                // texty
                    // jméno
                    VBox nameCol = new VBox();
                    TextField nameColText = new TextField(name);
                    nameColText.setDisable(true);
                    nameCol.getChildren().add(nameColText);
                    row.getChildren().add(nameCol);
                    // typ
                    VBox typeCol = new VBox();
                    TextField typeColText = new TextField(type);
                    typeColText.setDisable(true);
                    typeCol.getChildren().add(typeColText);
                    row.getChildren().add(typeCol);
                // tlačítka
                    // editace
                    VBox editCol = new VBox();
                    Button editColBtn = new Button("Edit");
                    editColBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            ChoiceBox c1 = ((ChoiceBox) ((VBox) row.getChildren().get(0)).getChildren().get(0));
                            TextField c2 = ((TextField) ((VBox) row.getChildren().get(1)).getChildren().get(0));
                            TextField c3 = ((TextField) ((VBox) row.getChildren().get(2)).getChildren().get(0));
                            Button c4 = ((Button) ((VBox) row.getChildren().get(3)).getChildren().get(0));

                            if (c1.isDisabled()) {
                                // staré hodnoty
                                String oldC1Value = c1.getSelectionModel().getSelectedItem().toString();
                                String oldC2Value = c2.getText();
                                String oldC3Value = c3.getText();

                                // povolím úpravu hodnot + signalizace na tlačítku
                                c1.setDisable(false);
                                c2.setDisable(false);
                                c3.setDisable(false);
                                c4.setText("Save");


                                // upravím hodnoty a dělám věci


                            }

                            //!TODO urobít zítra :), coz mozek need spánek
                            System.out.println(c1 + " " + c2 + " " + c3);
                        }
                    });
                    editCol.getChildren().add(editColBtn);
                    row.getChildren().add(editCol);
                    // mazání
                    VBox removeCol = new VBox();
                    Button removeColBtn = new Button("Remove");
                    removeColBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            String id = row.getId(); //((HBox)((VBox) ((Button) event.getSource()).getParent()).getParent()).getId();
                            //System.out.println();

                            attributesList.getChildren().remove(attributesList.lookup("#"+id));
                            attributes.getChildren().remove(attributes.lookup("#"+name+"Attr"));
                            activeObj.removeAttr(attr);
                        }
                    });
                    removeCol.getChildren().add(removeColBtn);
                    row.getChildren().add(removeCol);

                attributesList.getChildren().add(attributes.getChildren().size()-1, row);
                System.out.println(attributesList.getChildren().size()-1);
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
