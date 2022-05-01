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

    ObservableList<String> combinedList;

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


    SequenceDiagram seqDiag;

    public void initialize(){
        seqDiag = ClassDiagramController.classDiagram.findSeqDiagram(ClassDiagramController.title);
        System.out.println(seqDiag.getName());
        System.out.println(seqDiag.getSeqDiagClassList());
        System.out.println(seqDiag.getSeqDiagAllClassList());
        System.out.println(seqDiag.getMsgCounter());
        classNewChoice.setItems(seqDiag.getSeqDiagAllClassList());
        classChoice.setItems(seqDiag.getSeqDiagClassList());
        ObservableList<String> msgTypeList = FXCollections.observableArrayList();
        msgTypeList.add("Message");
        msgTypeList.add("Return");
        msgTypeList.add("New Instance");
        msgTypeList.add("Rem Instance");
        msgTypeChoice.setItems(msgTypeList);
        msgTypeChoice.setValue("Message");
        msgClass1Choice.setItems(seqDiag.getSeqDiagClassList());
        msgClass2Choice.setItems(seqDiag.getSeqDiagClassList());
        msgClass2Choice.setOnAction(event -> {
            if (msgClass2Choice.getValue() != null && msgTypeChoice.getValue() != "New Instance" && msgTypeChoice.getValue() != "Return"){
                msgOperationChoice.getItems().removeAll(msgOperationChoice.getItems());
                UMLClass class2 = ClassDiagramController.classDiagram.getObject(msgClass2Choice.getValue());
                if (seqDiag.getSeqDiagClassList().contains(class2.getName())) {
                    for (int i = 0; i < class2.operations.size(); i++) {
                        msgOperationChoice.getItems().add(class2.operations.get(i).getName());
                    }
                }
            }
        });
        msgTypeChoice.setOnAction(event -> {
            if (msgTypeChoice.getValue() != null) {
                String class2 = msgClass2Choice.getValue();
                if (msgTypeChoice.getValue() == "New Instance") {
                    combinedList = FXCollections.observableArrayList();
                    combinedList.addAll(seqDiag.getSeqDiagClassList());
                    combinedList.addAll(seqDiag.getSeqDiagAllClassList());
                    msgClass2Choice.setItems(combinedList);
                    msgClass2Choice.setValue(class2);
                    msgOperationChoice.getItems().removeAll(msgOperationChoice.getItems());
                    msgOperationChoice.getItems().add("Create Instance");
                    msgOperationChoice.setValue("Create Instance");
                }
                else if (msgTypeChoice.getValue() == "Rem Instance") {
                    msgClass2Choice.setItems(seqDiag.getInstancesList());
                    msgOperationChoice.getItems().removeAll(msgOperationChoice.getItems());
                    msgOperationChoice.getItems().add("Remove Instance");
                    msgOperationChoice.setValue("Remove Instance");
                }
                else {
                    msgClass2Choice.setItems(seqDiag.getSeqDiagClassList());
                    msgClass2Choice.setValue(null);
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
            seqDiag.addClassPosX(0);
            classNewChoice.setItems(seqDiag.getSeqDiagAllClassList());
            classChoice.setItems(seqDiag.getSeqDiagClassList());
            msgClass1Choice.setItems(seqDiag.getSeqDiagClassList());
            msgClass2Choice.setItems(seqDiag.getSeqDiagClassList());

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
        //vytvoreni grafickeho elementu
        TextField textField = new TextField(name);
        textField.setAlignment(Pos.CENTER);
        textField.setTranslateY(Y);
        textField.setTranslateX(seqDiag.getInstancePosX(name));
        textField.setPrefWidth(100);
        textField.setMaxWidth(100);
        textField.setEditable(false);
        textField.setId(name.replaceAll("\\s+", "€"));

        Line line = new Line(50, Y, 50,1500);
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
            seqDiag.getSeqDiagClassList().remove(name);
            seqDiag.getSeqDiagAllClassList().add(name);
            classNewChoice.setItems(seqDiag.getSeqDiagAllClassList());
            classChoice.setItems(seqDiag.getSeqDiagClassList());

            //mazani graficke reprezentace
            main.getChildren().remove(main.lookup("#" + name.replaceAll("\\s+", "€")));
        }
    }

    @FXML
    public void msgAddBtnClicked() {
        if (msgClass2Choice.getValue() != null && msgClass1Choice.getValue() != null && msgTypeChoice.getValue() != null && msgOperationChoice.getValue() != null){
            String name = ("msg" + seqDiag.getMsgCounter());
            UMLMessage message = seqDiag.createMessage(name, msgClass1Choice.getValue(), msgClass2Choice.getValue(), msgTypeChoice.getValue(), msgOperationChoice.getValue());
            seqDiag.incMsgCounter();
            drawMessage(message);
        }
    }

    public void drawMessage(UMLMessage message){
        //pro pripad prekreslovani
        main.getChildren().removeAll(main.lookupAll("#" + message.getName() + "line"));
        //vykresleni message, return, Rem Instance
        Line line;
        if (message.getType().compareTo("New Instance") != 0){
            line = new Line(main.lookup("#" + message.getClass1() + "Line").getTranslateX()+50, message.getHeight(), main.lookup("#" + message.getClass2() + "Line").getTranslateX()+50, message.getHeight());
            Text text = new Text(message.getOperation());
            text.setLayoutY(message.getHeight() - 2);
            text.setLayoutX((main.lookup("#" + message.getClass1() + "Line").getTranslateX() + main.lookup("#" + message.getClass2() + "Line").getTranslateX() + 100 - text.getBoundsInLocal().getWidth())/2);
            line.setId(message.getName()+"line");
            text.setId(message.getName()+"line");

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
                    message.setHeight(newTranslateY + orgHeight);
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
                    message.setHeight(newTranslateY + orgHeight);
                }
            });
            //mazani
            text.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY){
                    seqDiag.getMsgList().remove(message);
                    main.getChildren().removeAll(main.lookupAll("#" + message.getName() + "line"));
                }
            });

            main.getChildren().add(line);
            main.getChildren().add(text);
        }
        else {
            seqDiag.getInstancesList().add(message.getClass2());
            seqDiag.addInstancePosX(seqDiag.getClassPosX(message.getClass1()));
            drawClass("Instance of " + message.getClass2());
            if (seqDiag.getInstancePosX(message.getClass1()) < seqDiag.getClassPosX(message.getClass2()))
                line = new Line(main.lookup("#" + message.getClass1() + "Line").getTranslateX()+50, message.getHeight(), main.lookup("#" + message.getClass2() + "Line").getTranslateX(), message.getHeight());
            else
                line = new Line(main.lookup("#" + message.getClass1() + "Line").getTranslateX()+50, message.getHeight(), main.lookup("#" + message.getClass2() + "Line").getTranslateX()+100, message.getHeight());
        }
        //sipka
        Polygon poly;
        if (seqDiag.getClassPosX(message.getClass1()) < seqDiag.getClassPosX(message.getClass2()))
            poly = new Polygon(line.getStartX(), line.getStartY(), line.getStartX()+10, line.getStartY()+5, line.getStartX()+10, line.getStartY()-5);
        else
            poly = new Polygon(line.getStartX(), line.getStartY(), line.getStartX()-10, line.getStartY()+5, line.getStartX()-10, line.getStartY()-5);
        poly.setId(message.getName()+"line");
        main.getChildren().add(poly);
    }


    public void synch(){

    }

}
