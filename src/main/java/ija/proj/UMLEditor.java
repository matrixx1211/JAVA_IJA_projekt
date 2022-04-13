/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci kódu spojeného s GUI
 */

package ija.proj;

import java.io.*;
import java.util.List;

import com.google.gson.Gson;

import javafx.application.Application;
import ija.proj.uml.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UMLEditor extends App {
    private String activeObjName = null;
    private UMLClass activeObj;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    private String activeAttrName;
    public static final String OUTPUT_FILE = "out.json";

    // celá appka
    @FXML private VBox all;

    // levá část
    @FXML private TextField className;
    @FXML private Button deleteClassBtn;

    // střed
    @FXML private AnchorPane main;

    // pravá část
    @FXML private AnchorPane detail;
    @FXML private Label detailText;

    @FXML private TitledPane classDetailPane;
    @FXML private TextField newClassName;

    @FXML private TitledPane attributesPane;
    @FXML
    private VBox attributesList;
    @FXML
    private HBox attributesListAddRow;
    @FXML
    private ChoiceBox<String> newAttributeAccess;
    @FXML
    private TextField newAttributeName;
    @FXML
    private TextField newAttributeType;
    @FXML
    private Button addAttributeBtn;

    @FXML
    private TitledPane operationsPane;
    @FXML
    private VBox operationsList;
    @FXML ChoiceBox<String> newOperationAccess;
    @FXML TextField newOperationName;
    @FXML TextField newOperationReturnType;
    @FXML
    private Button addOperationBtn;
    @FXML
    private TitledPane relationsPane;
    @FXML
    private VBox relationsList;
    @FXML
    private ChoiceBox<String> newRelationType;
    @FXML
    private TextField newRelationName;
    @FXML
    private ChoiceBox<String> newRelationClass1;
    @FXML
    private ChoiceBox<String> newRelationClass2;
    @FXML
    private ChoiceBox<String> newRelationClass3;
    @FXML private Button addRelationBtn;

    @FXML
    private Label leftStatusLabel;
    @FXML
    private Label rightStatusLabel;

    @FXML
    public void newFile() throws IOException {
        classDiagram = new ClassDiagram("Diagram");
        App.setRoot("UMLEditor");
    }

    @FXML
    public void saveToFile() throws IOException {

        Gson gson = new Gson();
        Writer writer = new FileWriter("data/JSON.json");
        writer.write(gson.toJson(classDiagram));
        writer.close();
    }
    @FXML
    private void openFromFile() throws IOException {
        Gson gson = new Gson();
        newFile();
        Reader reader = new FileReader("data/JSON.json");
        classDiagram = gson.fromJson(reader, ClassDiagram.class);
        System.out.println(classDiagram.getName());
        //for (int i = 0; i < classDiagram.classes.size(); i++) {
            //drawclass(classDiagram.classes.get(i));
        //}
        //TODO Vykresleni
    }

    @FXML
    private void deleteClass() {
        classDiagram.deleteClass(activeObj);

        List<UMLRelation> relations = classDiagram.findAllRelationsOfClass(activeObjName);
        for (int i = 0; i < relations.size(); i++) {
            main.getChildren().removeAll(main.lookupAll(("#" + relations.get(i).getClass1() + "ß" + relations.get(i).getClass2() + "Line").replaceAll("\\s+", "€")));
            classDiagram.relations.remove(relations.get(i));
            relationsList.getChildren().remove(relationsList.lookup(("#" + relations.get(i).getClass1() + "ß" + relations.get(i).getClass2() + "RelRow").replaceAll("\\s+", "€")));
        }
        classList.remove(activeObjName);

        // odstranění záznamů atributů v detailu a v entitách
        VBox attributes = ((VBox) main.lookup(("#" + activeObjName + "Attributes").replaceAll("\\s+", "€")));
        attributesList.getChildren().removeAll(attributesList.lookup("#classAttributes"));
        attributes.getChildren().removeAll();
        main.getChildren().remove(main.lookup(("#" + activeObjName).replaceAll("\\s+", "€")));

        // nový položky v listu tříd
        newRelationClass1.setItems(classList);
        newRelationClass2.setItems(classList);
        newRelationClass3.setItems(classList);

        // obnovení detailu vpravo
        detailText.setText("Detail");

        addAttributeBtn.setDisable(true);
        addOperationBtn.setDisable(true);
        addRelationBtn.setDisable(true);

        classDetailPane.setDisable(true);
        classDetailPane.setExpanded(false);

        attributesPane.setDisable(true);
        attributesPane.setExpanded(false);

        operationsPane.setDisable(true);
        operationsPane.setExpanded(false);

        relationsPane.setDisable(true);
        relationsPane.setExpanded(false);

        deleteClassBtn.setDisable(true);

        // obnovení proměnných
        activeObj = null;
        activeObjName = null;
    }

    @FXML
    private void changeClassName() throws IOException {
        String newName = newClassName.getText();
        if (newName.isEmpty()) {
            newName = " ";
        }
        if (classDiagram.changeClassName(activeObjName, newName) == true) {
            detailText.setText("Detail of " + newName);
            TitledPane classTable = ((TitledPane) main.lookup("#" + activeObjName.replaceAll("\\s+", "€")));
            classTable.setText(newName);
            classTable.setId(newName.replaceAll("\\s+", "€"));

            // změny ID pro attributes a operations
            if (!activeObj.isInterface()) {
                VBox attributes = ((VBox) main.lookup(("#" + activeObjName + "Attributes").replaceAll("\\s+", "€")));
                attributes.setId((newName + "Attributes").replaceAll("\\s+", "€"));
            }

            VBox operations = ((VBox) main.lookup(("#" + activeObjName + "Operations").replaceAll("\\s+", "€")));
            operations.setId((newName + "Operations").replaceAll("\\s+", "€"));

            activeObjName = newName;
        } else {
            leftStatusLabel.setText("Name \"" + newName + "\" already exists!");
        }
        newClassName.setText(activeObjName);
    }

    @FXML
    private void addAttribute() throws IOException {
        String access = newAttributeAccess.getSelectionModel().getSelectedItem().toString();
        String name = newAttributeName.getText();
        String type = newAttributeType.getText();
        if (name.isEmpty() || type.isEmpty()) {
            leftStatusLabel.setText("Attribute name or type not entered.");
        } else {
            UMLClassifier classifier = classDiagram.findClassifier(type);
            if (classifier == null) {
                classifier = new UMLClassifier(type, true);
                classDiagram.classifiers.add(classifier);
            }
            UMLAttribute attr = new UMLAttribute(newAttributeName.getText(), classifier, access);
            if (activeObj.addAttribute(attr)) {
                VBox attributes = ((VBox) main.lookup(("#" + activeObjName + "Attributes").replaceAll("\\s+", "€")));
                Text attribute = new Text(access + " <-> " + name + ":" + type);
                System.out.println(name);
                attribute.setId(name + "Attr");
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
                        attributesList.getChildren().remove(attributesList.lookup("#"+(name+"Row").replaceAll("\\s+","€")));
                        attributes.getChildren().remove(attributes.lookup("#" + name + "Attr"));
                        activeObj.removeAttr(attr);
                    }
                });
                removeCol.getChildren().add(removeColBtn);
                row.getChildren().add(removeCol);

                attributesList.getChildren().add(attributes.getChildren().size() - 1, row);
                System.out.println(attributesList.getChildren().size() - 1);
            }
        }
    }

    @FXML private void addOperation() throws IOException {
        // vytvoření komponent
        VBox operationsCol = new VBox();
            HBox detailRow = new HBox();
                VBox accessCol = new VBox();
                    ChoiceBox<String> accessColChoiceBox = new ChoiceBox<String>();
                VBox nameCol = new VBox();
                    TextField nameColText = new TextField(newOperationName.getText());
                VBox returnTypeCol = new VBox();
                    TextField returnTypeColText = new TextField(newOperationReturnType.getText());
                VBox deleteCol = new VBox();
                    Button deleteColBtn = new Button("Delete");
            //TODO toto bude jinde
            HBox operationsRow = new HBox();
                VBox operationsNameCol = new VBox();
                VBox operationsTypeCol = new VBox();
            HBox addRow = new HBox();
                VBox addOperationNameCol = new VBox();
                    TextField addOperationNameColName = new TextField();
                VBox addOperationTypeCol = new VBox();
                    TextField addOperationTypeColType = new TextField();
                VBox addOperationBtnCol = new VBox();
                    Button addOperationBtn = new Button("Add");

        // přidání komponent do struktury
        operationsCol.getChildren().add(detailRow);
            detailRow.getChildren().add(accessCol);
                accessCol.getChildren().add(accessColChoiceBox);
            detailRow.getChildren().add(nameCol);
                nameCol.getChildren().add(nameColText);
            detailRow.getChildren().add(returnTypeCol);
                returnTypeCol.getChildren().add(returnTypeColText);
            detailRow.getChildren().add(deleteCol);
                deleteCol.getChildren().add(deleteColBtn);
        operationsCol.getChildren().add(operationsRow);
            operationsRow.getChildren().add(operationsNameCol);

            operationsRow.getChildren().add(operationsTypeCol);

        operationsCol.getChildren().add(addRow);
            addRow.getChildren().add(addOperationNameCol);
                addOperationNameCol.getChildren().add(addOperationNameColName);
            addRow.getChildren().add(addOperationTypeCol);
                addOperationTypeCol.getChildren().add(addOperationTypeColType);
            addRow.getChildren().add(addOperationBtnCol);
                addOperationBtnCol.getChildren().add(addOperationBtn);

        // TODO: 13.04.2022 Ládine prosím tě :)
        addOperationBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Děláš něco?");
                System.out.println(addOperationNameColName.getText());
                TextField operationName = new TextField(addOperationNameColName.getText()); //toto
                TextField operationType = new TextField(addOperationTypeColType.getText()); //toto
                operationsNameCol.getChildren().add(operationName);
                operationsTypeCol.getChildren().add(operationType);
            }});
        // nastavení dat
        accessColChoiceBox.setItems(accessibilityList);
        accessColChoiceBox.setValue("+");

        operationsList.getChildren().add(0, operationsCol);
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
        newAttributeAccess.setItems(accessibilityList);
        newAttributeAccess.setValue("+");
        newOperationAccess.setItems(accessibilityList);
        newOperationAccess.setValue("+");
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
                newObj.setPosition(10*idCounter, 10*idCounter);
                titledPane.setCollapsible(false);
                titledPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        attributesList.getChildren().remove(0, attributesList.getChildren().size()-1);
                        activeObjName = titledPane.getId();
                        Label label = new Label("My Label");
                        detailText.setText("Detail of "+titledPane.getText());
                        newClassName.setText(titledPane.getText());
                        activeObjName = titledPane.getText();

                        deleteClassBtn.setDisable(false);

                        addAttributeBtn.setDisable(false);
                        addOperationBtn.setDisable(false);
                        addRelationBtn.setDisable(false);

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

                        for (int i = 0; i < activeObj.attributes.size(); i++) {
                            VBox attributes = ((VBox) main.lookup(("#" + activeObjName + "Attributes").replaceAll("\\s+", "€")));
                            UMLAttribute attr = activeObj.attributes.get(i);

                            String access = attr.getAccessibility();
                            String name = attr.getName();
                            String type = attr.getType().getName();
                            HBox row = new HBox();
                            row.setId((name+"Row").replaceAll("\\s+","€"));
                            // výběrový box
                            // přistupnost
                            VBox accessCol = new VBox();
                            ChoiceBox<String> accessColChoiceBox = new ChoiceBox<String>();
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
                            // mazání
                            VBox removeCol = new VBox();
                            Button removeColBtn = new Button("Remove");
                            removeColBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    attributesList.getChildren().remove(attributesList.lookup("#"+(name+"Row").replaceAll("\\s+","€")));
                                    attributes.getChildren().remove(attributes.lookup("#" + name + "Attr"));
                                    activeObj.removeAttr(attr);
                                }
                            });
                            removeCol.getChildren().add(removeColBtn);
                            row.getChildren().add(removeCol);

                            attributesList.getChildren().add(attributesList.getChildren().size() - 1, row);
                        }

                        operationsPane.setDisable(false);
                        operationsPane.setExpanded(true);

                        relationsPane.setDisable(false);
                        relationsPane.setExpanded(true);

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

                        activeObj.setPosition(((TitledPane)(event.getSource())).getLayoutX()+newTranslateX, ((TitledPane)(event.getSource())).getLayoutY()+newTranslateY);

                        List<UMLRelation> relations = classDiagram.findAllRelationsOfClass(activeObjName);
                        for (int i = 0; i < relations.size(); i++) {
                            main.getChildren().removeAll(main.lookupAll(("#"+relations.get(i).getClass1()+"ß"+relations.get(i).getClass2()+"Line").replaceAll("\\s+","€")));
                            drawRelation(relations.get(i));
                        }
                    }
                });

                // vložení panu do mainu
                main.getChildren().add(titledPane);

                // zvednutí id pro další class
                this.idCounter++;
                //nastaveni listu pro vyber class na relaci
                classList.add(name);
                newRelationClass1.setItems(classList);
                newRelationClass2.setItems(classList);
                newRelationClass3.setItems(classList);
                newRelationClass1.setValue("");
                newRelationClass2.setValue("");
                newRelationClass3.setValue("");
            }
        }
    }

    @FXML private void addRelation() throws IOException {
        String name = ((TextField)(relationsPane.lookup("#newRelationName"))).getText();
        String type = ((ChoiceBox)(relationsPane.lookup("#newRelationType"))).getValue().toString();
        String class1 = ((ChoiceBox)(relationsPane.lookup("#newRelationClass1"))).getValue().toString();
        String class2 = ((ChoiceBox)(relationsPane.lookup("#newRelationClass2"))).getValue().toString();
        String class3 = ((ChoiceBox)(relationsPane.lookup("#newRelationClass3"))).getValue().toString();
        System.out.println(class1.length());
        if (class1.isEmpty() || class2.isEmpty()){
            //TODO chyba nezadane data
            return;
        }
        if (((class1 == class2) || (!class3.isEmpty())) && (type.compareTo("association") != 0) || (class1 == class3) || (class2 == class3)){
            //TODO chyba zle zadana relace
            return;
        }
        UMLRelation relation = classDiagram.createRelation(name, type, class1, class2, class3);
        if (relation == null){
            //TODO chyba relace jiz existuje
            return;
        }
        HBox row = new HBox();
        row.setId((class1+"ß"+class2+"RelRow").replaceAll("\\s+","€"));
        System.out.println(row.getId());
        // texty
        // typ
        VBox typeCol = new VBox();
        TextField typeColText = new TextField(type);
        typeColText.setDisable(true);
        typeCol.getChildren().add(typeColText);
        row.getChildren().add(typeCol);
        // jméno
        VBox nameCol = new VBox();
        TextField nameColText = new TextField(name);
        nameColText.setDisable(true);
        nameCol.getChildren().add(nameColText);
        row.getChildren().add(nameCol);
        // class1
        VBox class1Col = new VBox();
        TextField class1ColText = new TextField(class1);
        class1ColText.setDisable(true);
        class1Col.getChildren().add(class1ColText);
        row.getChildren().add(class1Col);
        // class2
        VBox class2Col = new VBox();
        TextField class2ColText = new TextField(class2);
        class2ColText.setDisable(true);
        class2Col.getChildren().add(class2ColText);
        row.getChildren().add(class2Col);
        // class3
        VBox class3Col = new VBox();
        TextField class3ColText = new TextField(class3);
        class3ColText.setDisable(true);
        class3Col.getChildren().add(class3ColText);
        row.getChildren().add(class3Col);
        // tlačítka
        // mazání
        VBox removeCol = new VBox();
        Button removeColBtn = new Button("Remove");
        removeColBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String id = row.getId();
                String class1 = ((TextField)(((VBox)(row.getChildren().get(2))).getChildren().get(0))).getText();
                String class2 = ((TextField)(((VBox)(row.getChildren().get(3))).getChildren().get(0))).getText();
                main.getChildren().removeAll(main.lookupAll(("#"+class1+"ß"+class2+"Line").replaceAll("\\s+","€")));
                classDiagram.relations.remove(classDiagram.findRelation(class1, class2));
                relationsList.getChildren().remove(relationsList.lookup("#"+id));
            }
        });
        removeCol.getChildren().add(removeColBtn);
        row.getChildren().add(removeCol);

        relationsList.getChildren().add(relationsList.getChildren().size()-1,row);

        drawRelation(relation);
    }

    public void drawRelation(UMLRelation relation) {
        String name = relation.getName();
        String type = relation.getType();
        String class1 = relation.getClass1();
        String class2 = relation.getClass2();
        String class3 = relation.getClass3();
        double x1 = (main.lookup(("#"+class1.replaceAll("\\s+","€")))).getLayoutX() + (main.lookup(("#"+class1.replaceAll("\\s+","€")))).getTranslateX();
        double y1 = (main.lookup(("#"+class1.replaceAll("\\s+","€")))).getLayoutY() + (main.lookup(("#"+class1.replaceAll("\\s+","€")))).getTranslateY();
        double x2 = (main.lookup(("#"+class2.replaceAll("\\s+","€")))).getLayoutX() + (main.lookup(("#"+class2.replaceAll("\\s+","€")))).getTranslateX();
        double y2 = (main.lookup(("#"+class2.replaceAll("\\s+","€")))).getLayoutY() + (main.lookup(("#"+class2.replaceAll("\\s+","€")))).getTranslateY();
        double h1 = (main.lookup(("#"+class1.replaceAll("\\s+","€")))).getBoundsInLocal().getHeight();
        double w1 = (main.lookup(("#"+class1.replaceAll("\\s+","€")))).getBoundsInLocal().getWidth();
        double h2 = (main.lookup(("#"+class2.replaceAll("\\s+","€")))).getBoundsInLocal().getHeight();
        double w2 = (main.lookup(("#"+class2.replaceAll("\\s+","€")))).getBoundsInLocal().getWidth();
        double x3;
        double y3;
        double h3;
        double w3;

        if (type.compareTo("association") == 0){
            if (class1 != class2){
                Line line1 = new Line(x1+w1/2, y1+h1/2, x2+w2/2, y1+h1/2);
                Line line2 = new Line(x2+w2/2, y1+h1/2, x2+w2/2, y2+h2/2);
                line1.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
                line2.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
                main.getChildren().add(0, line1);
                main.getChildren().add(0, line2);
                if (!class3.isEmpty()){
                    x3 = (main.lookup(("#"+class3.replaceAll("\\s+","€")))).getLayoutX() + (main.lookup(("#"+class3.replaceAll("\\s+","€")))).getTranslateX();
                    y3 = (main.lookup(("#"+class3.replaceAll("\\s+","€")))).getLayoutY() + (main.lookup(("#"+class3.replaceAll("\\s+","€")))).getTranslateY();
                    h3 = (main.lookup(("#"+class3.replaceAll("\\s+","€")))).getBoundsInLocal().getHeight();
                    w3 = (main.lookup(("#"+class3.replaceAll("\\s+","€")))).getBoundsInLocal().getWidth();
                    double x12 = ((x2+w2/2)+(x1+w1/2))/2;
                    Line line3 = new Line(x3+w3/2, y3+h3/2, x12, y3+h3/2);
                    Line line4 = new Line(x12, y3+h3/2, x12, y1+h1/2);
                    line3.getStrokeDashArray().addAll(10d, 4d);
                    line4.getStrokeDashArray().addAll(10d, 4d);
                    line3.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
                    line4.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
                    main.getChildren().add(0, line3);
                    main.getChildren().add(0, line4);
                }
            } else {
                Line line1 = new Line(x1, y1+h1/2, x1-30, y1+h1/2);
                Line line2 = new Line(x2-30, y1+h1/2, x2-30, y2-30);
                Line line5 = new Line(x1-30, y1-30, x2+w2/2, y1-30);
                Line line6 = new Line(x2+w2/2, y1-30, x2+w2/2, y2+h2/2);
                line1.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
                line2.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
                line5.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
                line6.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
                main.getChildren().add(0, line1);
                main.getChildren().add(0, line2);
                main.getChildren().add(0, line5);
                main.getChildren().add(0, line6);
                if (!class3.isEmpty()){
                    x3 = (main.lookup(("#"+class3.replaceAll("\\s+","€")))).getLayoutX() + (main.lookup(("#"+class3.replaceAll("\\s+","€")))).getTranslateX();
                    y3 = (main.lookup(("#"+class3.replaceAll("\\s+","€")))).getLayoutY() + (main.lookup(("#"+class3.replaceAll("\\s+","€")))).getTranslateY();
                    h3 = (main.lookup(("#"+class3.replaceAll("\\s+","€")))).getBoundsInLocal().getHeight();
                    w3 = (main.lookup(("#"+class3.replaceAll("\\s+","€")))).getBoundsInLocal().getWidth();
                    double x12 = (x1-15);
                    Line line3 = new Line(x3+w3/2, y3+h3/2, x12, y3+h3/2);
                    Line line4 = new Line(x12, y3+h3/2, x12, y1+h1/2);
                    line3.getStrokeDashArray().addAll(10d, 4d);
                    line4.getStrokeDashArray().addAll(10d, 4d);
                    line3.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
                    line4.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
                    main.getChildren().add(0, line3);
                    main.getChildren().add(0, line4);
                }
            }
        }
        if (type.compareTo("generalization") == 0){
            y3 = y1+h1+50;
            Line line1 = new Line(x1+w1/2, y1+h1/2, x1+w1/2, y3);
            Line line2 = new Line(x2+w2/2, y3, x1+w1/2, y3);
            Line line3 = new Line(x2+w2/2, y2+h2/2, x2+w2/2, y3);
            Polygon poly = new Polygon(x1+w1/2, y1+h1, x1+w1/2-10, y1+h1+20,x1+w1/2+10, y1+h1+20);
            poly.setFill(Color.WHITE);
            poly.setStroke(Color.BLACK);
            line1.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
            line2.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
            line3.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
            poly.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
            main.getChildren().add(0, poly);
            main.getChildren().add(0, line1);
            main.getChildren().add(0, line2);
            main.getChildren().add(0, line3);
        }
        if (type.compareTo("aggregation") == 0){
            y3 = y1+h1+60;
            Line line1 = new Line(x1+w1/4*3, y1+h1/2, x1+w1/4*3, y3);
            Line line2 = new Line(x2+w2/2, y3, x1+w1/4*3, y3);
            Line line3 = new Line(x2+w2/2, y2+h2/2, x2+w2/2, y3);
            Polygon poly = new Polygon(x1+w1/4*3, y1+h1, x1+w1/4*3-6, y1+h1+11, x1+w1/4*3, y1+h1+22, x1+w1/4*3+6, y1+h1+11);
            poly.setFill(Color.WHITE);
            poly.setStroke(Color.BLACK);
            line1.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
            line2.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
            line3.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
            poly.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
            main.getChildren().add(0, poly);
            main.getChildren().add(0, line1);
            main.getChildren().add(0, line2);
            main.getChildren().add(0, line3);
        }
        if (type.compareTo("composition") == 0){
            y3 = y1+h1+40;
            Line line1 = new Line(x1+w1/4, y1+h1/2, x1+w1/4, y3);
            Line line2 = new Line(x2+w2/2, y3, x1+w1/4, y3);
            Line line3 = new Line(x2+w2/2, y2+h2/2, x2+w2/2, y3);
            Polygon poly = new Polygon(x1+w1/4, y1+h1, x1+w1/4-6, y1+h1+11, x1+w1/4, y1+h1+22, x1+w1/4+6, y1+h1+11);
            poly.setStroke(Color.BLACK);
            line1.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
            line2.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
            line3.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
            poly.setId((class1+"ß"+class2+"Line").replaceAll("\\s+","€"));
            main.getChildren().add(0, poly);
            main.getChildren().add(0, line1);
            main.getChildren().add(0, line2);
            main.getChildren().add(0, line3);
        }
    }

    /**
     * Otevře nápovědu
     */
    @FXML public void openHelp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Help.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Help");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
           System.out.println("Failed to create new Window." + e);
        }
    }
}
