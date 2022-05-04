package ija.proj;

import ija.proj.uml.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.List;

public class SequenceDiagramController {
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    double orgHeight;

    @FXML
    AnchorPane main;

    @FXML
    ChoiceBox<String> classNewChoice;
    @FXML
    ChoiceBox<String> classChoice;
    @FXML
    Button classAddBtn;
    @FXML
    Button classDelBtn;

    @FXML
    ChoiceBox<String> msgClass1Choice;
    @FXML
    ChoiceBox<String> msgClass2Choice;
    @FXML
    ChoiceBox<String> msgTypeChoice;
    @FXML
    ChoiceBox<String> msgOperationChoice;
    @FXML
    Button msgAddBtn;

    ObservableList<String> classUsedList = FXCollections.observableArrayList();
    ObservableList<String> classNotUsedList = FXCollections.observableArrayList();
    ObservableList<String> class1List = FXCollections.observableArrayList();
    ObservableList<String> class2List = FXCollections.observableArrayList();
    ObservableList<String> operationList = FXCollections.observableArrayList();
    SequenceDiagram seqDiag;

    public void initialize(){
        seqDiag = ClassDiagramController.classDiagram.findSeqDiagram(ClassDiagramController.title);
        classNotUsedList.addAll(seqDiag.getSeqDiagAllClassList());
        classUsedList.addAll(seqDiag.getSeqDiagClassList());
        class1List.addAll(classUsedList);
        class1List.addAll(seqDiag.getInstancesList());
        class2List.addAll(class1List);

        classNewChoice.setItems(classNotUsedList);
        classChoice.setItems(classUsedList);
        msgClass1Choice.setItems(class1List);
        msgClass2Choice.setItems(class2List);
        ObservableList<String> msgTypeList = FXCollections.observableArrayList();
        msgTypeList.add("Message");
        msgTypeList.add("Return");
        msgTypeList.add("New Instance");
        msgTypeList.add("Rem Instance");
        msgTypeChoice.setItems(msgTypeList);
        msgTypeChoice.setValue("Message");
        msgOperationChoice.setItems(operationList);
        //naplneni operaci podle Class2
        msgClass2Choice.setOnAction(event -> {
            if (msgClass2Choice.getValue() != null && msgTypeChoice.getValue().compareTo("Message") == 0){
                msgOperationChoice.getItems().removeAll(msgOperationChoice.getItems());
                UMLClass class2 = null;
                String[] split;
                String string = "";
                if (seqDiag.getSeqDiagClassList().contains(msgClass2Choice.getValue()))
                    class2 = ClassDiagramController.classDiagram.getObject(msgClass2Choice.getValue());
                else if (seqDiag.getInstancesList().contains(msgClass2Choice.getValue())) {
                    split = msgClass2Choice.getValue().split(" ", 0);
                    if (split.length >= 4)
                    string = split[3];
                    for (int i = 4; i < split.length; i++)
                        string = (string + " " + split[i]);
                    class2 = ClassDiagramController.classDiagram.getObject(string);
                }
                for (int i = 0; i < class2.operations.size(); i++) {
                    msgOperationChoice.getItems().add(class2.operations.get(i).getName());
                }
            }
        });
        msgTypeChoice.setOnAction(event -> {
            if (msgTypeChoice.getValue() != null) {
                String class2 = msgClass2Choice.getValue();
                if (msgTypeChoice.getValue() == "New Instance") {
                    msgClass2Choice.getItems().removeAll(msgClass2Choice.getItems());
                    msgClass2Choice.getItems().addAll(seqDiag.getSeqDiagClassList());
                    msgClass2Choice.getItems().addAll(seqDiag.getSeqDiagAllClassList());
                    if (msgClass2Choice.getItems().contains(class2))
                        msgClass2Choice.setValue(class2);
                    msgOperationChoice.getItems().removeAll(msgOperationChoice.getItems());
                    msgOperationChoice.getItems().add("Create Instance");
                    msgOperationChoice.setValue("Create Instance");
                }
                else if (msgTypeChoice.getValue() == "Rem Instance") {
                    msgClass2Choice.getItems().removeAll(msgClass2Choice.getItems());
                    msgClass2Choice.getItems().addAll(seqDiag.getInstancesList());
                    if (msgClass2Choice.getItems().contains(class2))
                        msgClass2Choice.setValue(class2);
                    msgOperationChoice.getItems().removeAll(msgOperationChoice.getItems());
                    msgOperationChoice.getItems().add("Remove Instance");
                    msgOperationChoice.setValue("Remove Instance");
                }
                else {
                    msgClass2Choice.getItems().removeAll(msgClass2Choice.getItems());
                    msgClass2Choice.getItems().addAll(seqDiag.getSeqDiagClassList());
                    msgClass2Choice.getItems().addAll(seqDiag.getInstancesList());

                    msgClass2Choice.setValue(null);
                    if (msgClass2Choice.getItems().contains(class2))
                        msgClass2Choice.setValue(class2);
                    if (msgTypeChoice.getValue() == "Return") {
                        msgOperationChoice.getItems().removeAll(msgOperationChoice.getItems());
                        msgOperationChoice.getItems().add("Return");
                        msgOperationChoice.setValue("Return");
                    }
                }
            }
        });

        //nacteni
        //objekty
        System.out.println(seqDiag.getSeqDiagClassList());
        for (int i = 0; i < seqDiag.getSeqDiagClassList().size(); i++){
            drawClass(seqDiag.getSeqDiagClassList().get(i));
        }
        //zpravy
        for (int i = 0; i < seqDiag.getMsgList().size(); i++){
            drawMessage(seqDiag.getMsgList().get(i));
        }
    }

    @FXML
    public void classAddBtnClicked() {
        if (classNewChoice.getValue() != null){
            String name = classNewChoice.getValue();
            seqDiag.getSeqDiagAllClassList().remove(name);
            seqDiag.getSeqDiagClassList().add(name);
            classUsedList.add(name);
            classNotUsedList.remove(name);
            seqDiag.addClassPosX(0);
            //update vyvolanim eventu pri zmene typu
            String type = msgTypeChoice.getValue();
            msgTypeChoice.setValue("Message");
            msgTypeChoice.setValue("null");
            msgTypeChoice.setValue(type);
            drawClass(name);
        }
    }

    public void drawClass(String name) {
        //vytvoreni grafickeho elementu
        TextField textField = new TextField(name);
        textField.setAlignment(Pos.CENTER);
        textField.setTranslateY(30);
        textField.setTranslateX(seqDiag.getClassPosX(name));
        textField.setPrefWidth(100);
        textField.setEditable(false);
        textField.setId(name.replaceAll("\\s+", "€"));
        System.out.println(textField.getId());

        Line line = new Line(50, 40, 50,1500);
        line.setTranslateX(seqDiag.getClassPosX(name));
        line.getStrokeDashArray().addAll(10d, 4d);
        line.setId(name.replaceAll("\\s+", "€") + "Line");

        //horizontalni drag
        textField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                orgSceneX = event.getSceneX();
                orgTranslateX = ((TextField) (event.getSource())).getTranslateX();
            }
        });
        textField.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double offsetX = event.getSceneX() - orgSceneX;
                double newTranslateX = orgTranslateX + offsetX;

                ((TextField) (event.getSource())).setTranslateX(newTranslateX);
                line.setTranslateX(newTranslateX);
                seqDiag.changeClassPosX(name, newTranslateX);

                //prekresleni zprav
                for (int i = 0; i < seqDiag.getMsgList().size(); i++) {
                    if (name.compareTo(seqDiag.getMsgList().get(i).getClass1()) == 0 || name.compareTo(seqDiag.getMsgList().get(i).getClass2()) == 0){
                        drawMessage(seqDiag.getMsgList().get(i));
                    }
                }
            }
        });
        main.getChildren().add(line);
        main.getChildren().add(textField);
    }

    public void drawInstance(String name, double Y) {
        //pro prekresleni mazani stare
        main.getChildren().removeAll(main.lookupAll("#" + name.replaceAll("\\s+", "€")));
        main.getChildren().removeAll(main.lookupAll("#" + name.replaceAll("\\s+", "€") + "Line"));
        //vytvoreni grafickeho elementu
        TextField textField = new TextField(name);
        textField.setAlignment(Pos.CENTER);
        textField.setTranslateY(Y);
        textField.setTranslateX(seqDiag.getInstancePosX(name));
        textField.setPrefWidth(100);
        textField.setMaxWidth(100);
        textField.setEditable(false);
        textField.setId(name.replaceAll("\\s+", "€"));

        Line line = new Line(50, Y+5, 50,1500);
        line.setTranslateX(seqDiag.getInstancePosX(name));
        line.getStrokeDashArray().addAll(10d, 4d);
        line.setId(name.replaceAll("\\s+", "€") + "Line");
        //horizontalni drag
        textField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                orgSceneX = event.getSceneX();
                orgTranslateX = ((TextField) (event.getSource())).getTranslateX();
            }
        });
        textField.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double offsetX = event.getSceneX() - orgSceneX;
                double newTranslateX = orgTranslateX + offsetX;

                ((TextField) (event.getSource())).setTranslateX(newTranslateX);
                line.setTranslateX(newTranslateX);
                seqDiag.changeInstancePosX(name, newTranslateX);

                //prekresleni zprav
                for (int i = 0; i < seqDiag.getMsgList().size(); i++) {
                    if (name.compareTo(seqDiag.getMsgList().get(i).getClass1()) == 0 || name.compareTo(seqDiag.getMsgList().get(i).getClass2()) == 0){
                        drawMessage(seqDiag.getMsgList().get(i));
                    }
                }
            }
        });
        main.getChildren().add(line);

        main.getChildren().add(textField);
    }

    @FXML
    public void classDelBtnClicked() {
        if (classChoice.getValue() != null){
            String name = classChoice.getValue();

            //mazani zprav dane class
            for (int i = 0; i < seqDiag.getMsgList().size(); i++) {
                if (seqDiag.getMsgList().get(i).getClass1().compareTo(name) == 0 || seqDiag.getMsgList().get(i).getClass2().compareTo(name) == 0) {
                    main.getChildren().removeAll(main.lookupAll("#" + seqDiag.getMsgList().get(i).getName() + "line"));
                    if (seqDiag.getMsgList().get(i).getType().compareTo("New Instance") == 0){
                        deleteInstance(seqDiag.getMsgList().get(i).getClass2());
                        seqDiag.getMsgList().remove(i);
                        i = 0; //reset counteru, deleteInstance mohl zmenit seqDiag.getMsgList().size()
                    }
                    else {
                        seqDiag.getMsgList().remove(i);
                        i--; //index se posunul smazanim prvku
                    }
                }
            }

            seqDiag.getSeqDiagClassList().remove(name);
            seqDiag.getSeqDiagAllClassList().add(name);
            classUsedList.remove(name);
            classNotUsedList.add(name);
            //update vyvolanim eventu pri zmene typu
            String type = msgTypeChoice.getValue();
            msgTypeChoice.setValue("Message");
            msgTypeChoice.setValue("null");
            msgTypeChoice.setValue(type);

            //mazani graficke reprezentace
            eraseClass(name);
        }
    }

    public void eraseClass(String class1){
        main.getChildren().remove(main.lookup("#" + class1));
        main.getChildren().remove(main.lookup("#" + class1 + "Line"));
    }

    public void deleteInstance(String instance){
        //mazani zprav dane class
        for (int i = 0; i < seqDiag.getMsgList().size(); i++) {
            if (seqDiag.getMsgList().get(i).getClass1().compareTo(instance) == 0 || seqDiag.getMsgList().get(i).getClass2().compareTo(instance) == 0) {
                main.getChildren().removeAll(main.lookupAll("#" + seqDiag.getMsgList().get(i).getName() + "line"));
                if (seqDiag.getMsgList().get(i).getType().compareTo("New Instance") == 0){
                    deleteInstance(seqDiag.getMsgList().get(i).getClass2());
                    seqDiag.getMsgList().remove(i);
                    i = 0; //reset counteru, deleteInstance mohl zmenit seqDiag.getMsgList().size()
                }
                else {
                    seqDiag.getMsgList().remove(i);
                    i--; //index se posunul smazanim prvku
                }
            }
        }

        seqDiag.getSeqDiagClassList().remove(instance);
        classUsedList.remove(instance);
        //update class 2 vyvolanim eventu pri zmene typu
        String type = msgTypeChoice.getValue();
        msgTypeChoice.setValue("Message");
        msgTypeChoice.setValue("null");
        msgTypeChoice.setValue(type);

        class1List.remove(instance);
        //mazani graficke reprezentace
        eraseClass(instance);
    }

    @FXML
    public void msgAddBtnClicked() {
        if (msgClass2Choice.getValue() != null && msgClass1Choice.getValue() != null && msgTypeChoice.getValue() != null && msgOperationChoice.getValue() != null){
            String name = ("msg" + seqDiag.getMsgCounter());
            System.out.println(name);
            UMLMessage message;
            if (msgTypeChoice.getValue().compareTo("New Instance") != 0)
                message = seqDiag.createMessage(name, msgClass1Choice.getValue(), msgClass2Choice.getValue(), msgTypeChoice.getValue(), msgOperationChoice.getValue());
            else {
                message = seqDiag.createMessage(name, msgClass1Choice.getValue(),   "Instance " + seqDiag.getInstaceCounter() + " of " + msgClass2Choice.getValue(), msgTypeChoice.getValue(), msgOperationChoice.getValue());
                seqDiag.incInstanceCounter();
            }
            seqDiag.incMsgCounter();

            drawMessage(message);
        }
    }

    public void drawMessage(UMLMessage message){
        //pro pripad prekreslovani
        main.getChildren().removeAll(main.lookupAll("#" + message.getName() + "line"));
        //vykresleni message, return, Rem Instance
        Line line;
        if (message.getType().compareTo("New Instance") != 0)
            line = new Line(main.lookup("#" + message.getClass1().replaceAll("\\s+", "€") + "Line").getTranslateX()+50, message.getHeight(), main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line").getTranslateX()+50, message.getHeight());
        else{
            //pokud neexistuje - nakresli instanci
            if (main.lookupAll("#" + message.getClass2().replaceAll("\\s+", "€")).isEmpty()){
                seqDiag.getInstancesList().add(message.getClass2());
                class1List.add(message.getClass2());
                seqDiag.addInstancePosX(seqDiag.getClassPosX(message.getClass1()) + 200);
                drawInstance(message.getClass2(), message.getHeight()-5);
            }
            if (seqDiag.getClassPosX(message.getClass1()) < seqDiag.getInstancePosX(message.getClass2()))
                line = new Line(main.lookup("#" + message.getClass1().replaceAll("\\s+", "€") + "Line").getTranslateX() + 50, message.getHeight(), main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line").getTranslateX(), message.getHeight());
            else
                line = new Line(main.lookup("#" + message.getClass1().replaceAll("\\s+", "€") + "Line").getTranslateX() + 50, message.getHeight(), main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line").getTranslateX() + 100, message.getHeight());
        }
        Text text = new Text(message.getOperation());
        text.setLayoutY(message.getHeight() - 2);
        text.setLayoutX((main.lookup("#" + message.getClass1().replaceAll("\\s+", "€") + "Line").getTranslateX() + main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line").getTranslateX() + 100 - text.getBoundsInLocal().getWidth())/2);
        line.setId(message.getName()+"line");
        text.setId(message.getName()+"line");
        //sipka
        Polygon poly;
        if (seqDiag.getClassPosX(message.getClass1()) > seqDiag.getClassPosX(message.getClass2()))
            poly = new Polygon(line.getEndX(), line.getEndY(), line.getEndX()+10, line.getEndY()+5, line.getEndX()+10, line.getEndY()-5);
        else
            poly = new Polygon(line.getEndX(), line.getEndY(), line.getEndX()-10, line.getEndY()+5, line.getEndX()-10, line.getEndY()-5);
        poly.setId(message.getName()+"line");
        main.getChildren().add(poly);

        //pripad mazani instance - zkrat caru z instance
        if (message.getType().compareTo("Rem Instance") == 0) {
            ((Line) main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setEndY(message.getHeight());
            //nastaveni barvy pri spatnem otocenim cary
            if (((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).getStartY() > ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).getEndY())
                ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStroke(Color.RED);
            else
                ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStroke(Color.BLACK);
        }
        //dragovani
        line.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                orgSceneY = event.getSceneY();
                orgTranslateY = ((Line) (event.getSource())).getTranslateY();
                orgHeight = message.getHeight();
            }
        });
        line.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double offsetY = event.getSceneY() - orgSceneY;
                double newTranslateY = orgTranslateY + offsetY;

                ((Line) (event.getSource())).setTranslateY(newTranslateY);
                line.setTranslateY(newTranslateY);
                text.setTranslateY(newTranslateY);
                poly.setTranslateY(newTranslateY);
                message.setHeight(orgHeight + offsetY);

                if (message.getType().compareTo("New Instance") == 0){
                    ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStartY(message.getHeight());
                    main.lookup("#" + message.getClass2().replaceAll("\\s+", "€")).setTranslateY(message.getHeight());
                    //main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line").setTranslateY(message.getHeight());
                    //nastaveni barvy pri spatnem otocenim cary
                    if (((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).getStartY() > ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).getEndY())
                        ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStroke(Color.RED);
                    else
                        ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStroke(Color.BLACK);
                }

                //pripad mazani instance - zkrat caru z instance
                if (message.getType().compareTo("Rem Instance") == 0) {
                    ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setEndY(message.getHeight());
                    //nastaveni barvy pri spatnem otocenim cary
                    if (((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).getStartY() > ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).getEndY())
                        ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStroke(Color.RED);
                    else
                        ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStroke(Color.BLACK);
                }
            }
        });
        text.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                orgSceneY = event.getSceneY();
                orgTranslateY = ((Text) (event.getSource())).getTranslateY();
                orgHeight = message.getHeight();
            }
        });
        text.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double offsetY = event.getSceneY() - orgSceneY;
                double newTranslateY = orgTranslateY + offsetY;

                ((Text) (event.getSource())).setTranslateY(newTranslateY);
                line.setTranslateY(newTranslateY);
                text.setTranslateY(newTranslateY);
                poly.setTranslateY(newTranslateY);
                message.setHeight(orgHeight + offsetY);

                if (message.getType().compareTo("New Instance") == 0){
                    ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStartY(message.getHeight());
                    main.lookup("#" + message.getClass2().replaceAll("\\s+", "€")).setTranslateY(message.getHeight());
                    //nastaveni barvy pri spatnem otocenim cary
                    if (((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).getStartY() > ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).getEndY())
                        ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStroke(Color.RED);
                    else
                        ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStroke(Color.BLACK);
                }

                //pripad mazani instance - zkrat caru z instance
                if (message.getType().compareTo("Rem Instance") == 0) {
                    ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setEndY(message.getHeight());
                    //nastaveni barvy pri spatnem otocenim cary
                    if (((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).getStartY() > ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).getEndY())
                        ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStroke(Color.RED);
                    else
                        ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStroke(Color.BLACK);
                }
            }
        });
        //mazani
        text.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY){
                seqDiag.getMsgList().remove(message);
                main.getChildren().removeAll(main.lookupAll("#" + message.getName() + "line"));
                if (message.getType().compareTo("New Instance") == 0){
                    deleteInstance(message.getClass2());
                }
                if (message.getType().compareTo("Rem Instance") == 0){
                    ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setEndY(1500);
                    ((Line)main.lookup("#" + message.getClass2().replaceAll("\\s+", "€") + "Line")).setStroke(Color.BLACK);
                }
            }
        });

        main.getChildren().add(line);
        main.getChildren().add(text);
    }


    public void synch(){

    }

}
