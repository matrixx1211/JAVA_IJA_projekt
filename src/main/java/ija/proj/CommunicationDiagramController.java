package ija.proj;

import ija.proj.uml.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CommunicationDiagramController {
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    @FXML
    AnchorPane main;
    @FXML
    Label leftStatusLabel;

    @FXML
    ChoiceBox<String> classNewChoice;
    @FXML
    ChoiceBox<String> classChoice;
    @FXML
    Button classAddBtn;
    @FXML
    Button classDelBtn;

    @FXML
    TextField newUser;
    @FXML
    ChoiceBox<String> userChoice;
    @FXML
    Button userAddBtn;
    @FXML
    Button userDelBtn;

    @FXML
    ChoiceBox<String> connClass1Choice;
    @FXML
    ChoiceBox<String> connClass2Choice;
    @FXML
    Button connAddBtn;

    @FXML
    ChoiceBox<String> msgClass1Choice;
    @FXML
    ChoiceBox<String> msgClass2Choice;
    @FXML
    ChoiceBox<String> msgTypeChoice;
    @FXML
    ChoiceBox<String> msgOperationChoice;
    @FXML
    TextField msgOrder;
    @FXML
    Button msgAddBtn;

    ObservableList<String> classUsedList = FXCollections.observableArrayList();
    ObservableList<String> classNotUsedList = FXCollections.observableArrayList();
    ObservableList<String> class1List = FXCollections.observableArrayList();
    ObservableList<String> instanceList = FXCollections.observableArrayList();
    ObservableList<String> operationList = FXCollections.observableArrayList();
    ObservableList<String> userList = FXCollections.observableArrayList();
    ObservableList<String> msgTypeList = FXCollections.observableArrayList();

    CommunicationDiagram commDiag;

    public void initialize(){
        commDiag = ClassDiagramController.classDiagram.findCommDiagram(ClassDiagramController.title);
        classNotUsedList.addAll(commDiag.getCommDiagAllClassList());
        classUsedList.addAll(commDiag.getCommDiagClassList());
        for (int i = 0; i < commDiag.getUserList().size(); i++)
            userList.add(commDiag.getUserList().get(i).getName());
        class1List.addAll(classUsedList);
        class1List.addAll(userList);
        instanceList.addAll(commDiag.getInstanceList());
        msgTypeList.add("Asynch");
        msgTypeList.add("Synch");
        msgTypeList.add("Return");
        msgTypeList.add("New Instance");
        msgTypeList.add("Rem Instance");

        classNewChoice.setItems(classNotUsedList);
        classChoice.setItems(classUsedList);
        userChoice.setItems(userList);
        connClass1Choice.setItems(class1List);
        connClass2Choice.setItems(class1List);
        msgClass1Choice.setItems(class1List);
        msgClass2Choice.setItems(class1List);
        msgTypeChoice.setItems(msgTypeList);
        msgTypeChoice.setValue("Asynch");
        msgOperationChoice.setItems(operationList);

        //naplneni operaci podle Class2
        msgClass2Choice.setOnAction(event -> {
            if (msgClass2Choice.getValue() != null && (msgTypeChoice.getValue().compareTo("Asynch") == 0 || msgTypeChoice.getValue().compareTo("Synch") == 0)){
                msgOperationChoice.getItems().removeAll(msgOperationChoice.getItems());
                if (commDiag.getCommDiagClassList().contains(msgClass2Choice.getValue())) {
                    UMLClass class2 = ClassDiagramController.classDiagram.getObject(msgClass2Choice.getValue());
                    for (int i = 0; i < class2.operations.size(); i++) {
                        msgOperationChoice.getItems().add(class2.operations.get(i).getName());
                    }
                }
            }
        });
        //nastaveni class 2 listu a operaci podle vybraneho typu
        msgTypeChoice.setOnAction(event -> {
            if (msgTypeChoice.getValue() != null) {
                String class2 = msgClass2Choice.getValue();
                if (msgTypeChoice.getValue() == "New Instance") {
                    msgClass2Choice.setItems(class1List);
                    msgOperationChoice.getItems().removeAll(msgOperationChoice.getItems());
                    msgOperationChoice.getItems().add("Create Instance");
                    msgOperationChoice.setValue("Create Instance");
                }
                else if (msgTypeChoice.getValue() == "Rem Instance") {
                    msgClass2Choice.setItems(instanceList);
                    if (msgClass2Choice.getItems().contains(class2))
                        msgClass2Choice.setValue(class2);
                    msgOperationChoice.getItems().removeAll(msgOperationChoice.getItems());
                    msgOperationChoice.getItems().add("Remove Instance");
                    msgOperationChoice.setValue("Remove Instance");
                }
                else {
                    msgClass2Choice.setItems(class1List);
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

        //objekty
        for (int i = 0; i < commDiag.getCommDiagClassList().size(); i++){
            drawClass(commDiag.getCommDiagClassList().get(i));
        }
        //uzivatele
        for (int i = 0; i < commDiag.getUserList().size(); i++){
            drawUser(commDiag.getUserList().get(i));
        }
        //konekce a zpravy
        for (int i = 0; i < commDiag.getConnList().size(); i++){
            drawConnection(commDiag.getConnList().get(i));
        }
    }

    @FXML
    public void classAddBtnClicked() {
        if (classNewChoice.getValue() != null){
            String name = classNewChoice.getValue();
            commDiag.getCommDiagAllClassList().remove(name);
            commDiag.getCommDiagClassList().add(name);
            classUsedList.add(name);
            classNotUsedList.remove(name);
            class1List.add(name);
            commDiag.addClassPosXY(40, 40);
            //update vyvolanim eventu pri zmene typu
            String type = msgTypeChoice.getValue();
            msgTypeChoice.setValue("Asynch");
            msgTypeChoice.setValue(null);
            msgTypeChoice.setValue(type);
            drawClass(name);
        }
    }

    public void drawClass(String name){
        TextField textField = new TextField(name);
        textField.setAlignment(Pos.CENTER);
        textField.setTranslateX(commDiag.getClassPosX(name));
        textField.setTranslateY(commDiag.getClassPosY(name));
        textField.setPrefWidth(100);
        textField.setPrefHeight(30);
        textField.setEditable(false);
        textField.setId(name.replaceAll("\\s+", "€"));

        //drag
        textField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                orgSceneX = event.getSceneX();
                orgSceneY = event.getSceneY();
                orgTranslateX = ((TextField) (event.getSource())).getTranslateX();
                orgTranslateY = ((TextField) (event.getSource())).getTranslateY();

            }
        });
        textField.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double offsetX = event.getSceneX() - orgSceneX;
                double offsetY = event.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;

                ((TextField) (event.getSource())).setTranslateX(newTranslateX);
                ((TextField) (event.getSource())).setTranslateY(newTranslateY);

                commDiag.setClassPosXY(name, newTranslateX, newTranslateY);

                //prekresleni spojeni
                for (int i = 0; i < commDiag.getConnList().size(); i++) {
                    if (name.compareTo(commDiag.getConnList().get(i).getClass1()) == 0 || name.compareTo(commDiag.getConnList().get(i).getClass2()) == 0){
                        drawConnection(commDiag.getConnList().get(i));
                    }
                }

                /*
                //prekresleni zprav
                for (int i = 0; i < seqDiag.getMsgList().size(); i++) {
                    if (name.compareTo(seqDiag.getMsgList().get(i).getClass1()) == 0 || name.compareTo(seqDiag.getMsgList().get(i).getClass2()) == 0){
                        drawMessage(seqDiag.getMsgList().get(i));
                    }
                }

                //prekresleni aktivaci
                for (int i = 0; i < seqDiag.getActList().size(); i++) {
                    if (name.compareTo(seqDiag.getActList().get(i).getClass1()) == 0){
                        drawActivation(seqDiag.getActList().get(i));
                    }
                }*/
            }
        });
        main.getChildren().add(textField);

    }

    @FXML
    public void classDelBtnClicked() {
        if (classChoice.getValue() != null){
            String name = classChoice.getValue();

            //mazani spojeni
            for (int i = 0; i < commDiag.getConnList().size(); i++) {
                if (commDiag.getConnList().get(i).getClass1().compareTo(name) == 0 || commDiag.getConnList().get(i).getClass2().compareTo(name) == 0) {
                    main.getChildren().removeAll(main.lookupAll("#" + commDiag.getConnList().get(i).getName()));
                    for (int j = 0; j < commDiag.getConnList().get(i).getMsgList().size(); j++) {
                        removeMsg(commDiag.getConnList().get(i).getMsgList().get(j), commDiag.getConnList().get(i));
                        commDiag.getConnList().get(i).getMsgList().remove(commDiag.getConnList().get(i).getMsgList().get(j));
                        j--; //index se posunul smazanim
                    }
                    commDiag.getConnList().remove(i);
                    i--; //index se posunul smazanim
                }
            }

            commDiag.remClassPos(name);
            commDiag.getCommDiagClassList().remove(name);
            commDiag.getCommDiagAllClassList().add(name);
            classUsedList.remove(name);
            classNotUsedList.add(name);
            class1List.remove(name);

            //update vyvolanim eventu pri zmene typu
            String type = msgTypeChoice.getValue();
            msgTypeChoice.setValue("Asynch");
            msgTypeChoice.setValue("null");
            msgTypeChoice.setValue(type);

            //mazani graficke reprezentace
            main.getChildren().removeAll(main.lookupAll("#" + name.replaceAll("\\s+", "€")));
        }
    }

    @FXML
    public void userAddBtnClicked(){
        if (newUser.getText() != null){
            String name = newUser.getText();
            for (int i = 0; i < commDiag.getUserList().size(); i++){
                if (commDiag.getUserList().get(i).getName().compareTo(name) == 0){
                    leftStatusLabel.setText("user jiz existuje");
                    return;
                }
            }
            if (classUsedList.contains(name) || classNotUsedList.contains(name)){
                leftStatusLabel.setText("jiz existuje class se stejnym jmenem");
                return;
            }
            UMLUser user = commDiag.createUser(name, 40, 40);

            userList.add(name);
            class1List.add(name);
            drawUser(user);
        }
    }

    @FXML
    public void userDelBtnClicked(){

    }

    public void drawUser(UMLUser user){
        Group group = new Group();
        //panacek
        Circle circle = new Circle(0, 0, 20);
        Line line1 = new Line(0,20,0,50);
        Line line2 = new Line(20,25,-20,25);
        Line line3 = new Line(0,50,20,80);
        Line line4 = new Line(0,50,-20,80);
        Text text = new Text(user.getName());
        text.setY(-25);
        text.setX(-20);

        group.getChildren().add(line1);
        group.getChildren().add(line2);
        group.getChildren().add(line3);
        group.getChildren().add(line4);
        group.getChildren().add(circle);
        group.getChildren().add(text);

        group.setTranslateX(user.getPosX());
        group.setTranslateY(user.getPosY());
        group.setId(user.getName().replaceAll("\\s+", "€"));

        group.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                orgSceneX = event.getSceneX();
                orgSceneY = event.getSceneY();
                orgTranslateX = ((Group) (event.getSource())).getTranslateX();
                orgTranslateY = ((Group) (event.getSource())).getTranslateY();

            }
        });
        group.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double offsetX = event.getSceneX() - orgSceneX;
                double offsetY = event.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;

                ((Group) (event.getSource())).setTranslateX(newTranslateX);
                ((Group) (event.getSource())).setTranslateY(newTranslateY);

                user.setPosX(newTranslateX);
                user.setPosY(newTranslateY);

                //prekresleni spojeni
                for (int i = 0; i < commDiag.getConnList().size(); i++) {
                    if (user.getName().compareTo(commDiag.getConnList().get(i).getClass1()) == 0 || user.getName().compareTo(commDiag.getConnList().get(i).getClass2()) == 0){
                        drawConnection(commDiag.getConnList().get(i));
                    }
                }
            }
        });
        main.getChildren().add(group);
    }

    @FXML
    public void connAddBtnClicked(){
        if (connClass1Choice.getValue() != null && connClass1Choice.getValue() != null){
            String class1 = connClass1Choice.getValue();
            String class2 = connClass2Choice.getValue();
            for (int i = 0; i < commDiag.getConnList().size(); i++){
                //pokud jiz existuje
                if (commDiag.getConnList().get(i).getClass1().compareTo(class1) == 0 && commDiag.getConnList().get(i).getClass2().compareTo(class2) == 0
                        || commDiag.getConnList().get(i).getClass1().compareTo(class2) == 0 && commDiag.getConnList().get(i).getClass2().compareTo(class1) == 0){
                    leftStatusLabel.setText("spojeni jiz existuje");
                    return;
                }
            }
            UMLConnection connection = commDiag.createConnection(class1, class2);

            drawConnection(connection);
        }
        else
            leftStatusLabel.setText("nevybran objekt");
    }

    public void drawConnection(UMLConnection connection){
        //pripad prekresleni
        main.getChildren().removeAll(main.lookupAll("#" + connection.getName()));

        //ziskani souradnic
        double class1X;
        double class1Y;
        double class2X;
        double class2Y;
        if (userList.contains(connection.getClass1())){
            class1X = commDiag.getUserPosX(connection.getClass1());
            class1Y = commDiag.getUserPosY(connection.getClass1());
        }
        else if (commDiag.getCommDiagClassList().contains(connection.getClass1())){
            class1X = commDiag.getClassPosX(connection.getClass1()) + 50;
            class1Y = commDiag.getClassPosY(connection.getClass1()) + 15;
        }
        else {
            leftStatusLabel.setText("nastala chyba pri vykresleni - nenalezena class1");
            return;
        }
        if (userList.contains(connection.getClass2())){
            class2X = commDiag.getUserPosX(connection.getClass2());
            class2Y = commDiag.getUserPosY(connection.getClass2());
        }
        else if (commDiag.getCommDiagClassList().contains(connection.getClass2())){
            class2X = commDiag.getClassPosX(connection.getClass2()) + 50;
            class2Y = commDiag.getClassPosY(connection.getClass2()) + 15;
        }
        else {
            leftStatusLabel.setText("nastala chyba pri vykresleni - nenalezena class2");
            return;
        }
        Line line = new Line(class1X, class1Y, class2X, class2Y);
        line.setId(connection.getName());
        line.setStrokeWidth(3);

        line.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY){
                main.getChildren().removeAll(main.lookupAll("#" + connection.getName()));
                for (int i = 0; i < commDiag.getConnList().get(i).getMsgList().size(); i++) {
                    removeMsg(commDiag.getConnList().get(i).getMsgList().get(i), commDiag.getConnList().get(i));
                    commDiag.getConnList().get(i).getMsgList().remove(commDiag.getConnList().get(i).getMsgList().get(i));
                    i--; //index se posunul smazanim
                }
                //mazani
                commDiag.getConnList().remove(connection);
            }
        });

        main.getChildren().add(line);

        //prekresleni zprav
        for (int i = 0; i < connection.getMsgList().size(); i++){
            drawMessage(connection.getMsgList().get(i), line, connection);
        }

        //nastaveni prekryti
        main.lookup("#" + connection.getClass1().replaceAll("\\s+", "€")).toFront();
        main.lookup("#" + connection.getClass2().replaceAll("\\s+", "€")).toFront();
    }

    @FXML
    public void msgAddBtnClicked(){
        if (msgClass1Choice.getValue() != null && msgClass2Choice.getValue() != null && msgTypeChoice.getValue() != null && msgOperationChoice.getValue() != null && msgOrder.getText() != null){
            String class1 = msgClass1Choice.getValue();
            String class2 = msgClass2Choice.getValue();
            //hledani zda existuje spojeni
            for (int i = 0; i < commDiag.getConnList().size(); i++){
                if (commDiag.getConnList().get(i).getClass1().compareTo(class1) == 0 && commDiag.getConnList().get(i).getClass2().compareTo(class2) == 0
                        || commDiag.getConnList().get(i).getClass1().compareTo(class2) == 0 && commDiag.getConnList().get(i).getClass2().compareTo(class1) == 0){
                    //existuje
                    UMLMessage message = commDiag.getConnList().get(i).createMessage(class1, class2, msgTypeChoice.getValue(), msgOperationChoice.getValue());

                    //vytvareni a mazani instanci
                    if (message.getType().compareTo("New Instance") == 0) {
                        instanceList.add(class2);
                        commDiag.getInstanceList().add(class2);
                    }
                    if (message.getType().compareTo("Rem Instance") == 0) {
                        instanceList.remove(class2);
                        commDiag.getInstanceList().remove(class2);
                    }
                    //pocitani instanci - moznost ze byl smazan new instance - chybi Instance
                    int countIns = 0;
                    for (int k = 0; k < commDiag.getConnList().size(); k++)
                        if (commDiag.getConnList().get(k).getClass1().compareTo(class2) == 0 || commDiag.getConnList().get(k).getClass2().compareTo(class2) == 0)
                            for (int j = 0; j < commDiag.getConnList().get(k).getMsgList().size(); j++){
                                if (message.getClass2().compareTo(commDiag.getConnList().get(k).getMsgList().get(j).getClass2()) == 0 && commDiag.getConnList().get(k).getMsgList().get(j).getType().compareTo("New Instance") == 0)
                                    countIns++;
                                else if (message.getClass2().compareTo(commDiag.getConnList().get(k).getMsgList().get(j).getClass2()) == 0 && commDiag.getConnList().get(k).getMsgList().get(j).getType().compareTo("Rem Instance") == 0)
                                    countIns--;
                    }
                    int countIns2 = 0;
                    for (int j = 0; j < instanceList.size(); j++)
                        if (instanceList.get(j).compareTo(message.getClass2()) == 0)
                            countIns2++;
                    //prepocitani
                    while (countIns2 > countIns){ //mazani prebytecnych
                        instanceList.remove(message.getClass2());
                        commDiag.getInstanceList().remove(message.getClass2());
                        countIns++;
                    }
                    while (countIns2 < countIns){ //vkladani zbyvajicich
                        instanceList.add(message.getClass2());
                        commDiag.getInstanceList().add(message.getClass2());
                        countIns--;
                    }

                    message.setOrder(msgOrder.getText());

                    drawMessage(message ,(Line)main.lookup("#"+commDiag.getConnList().get(i).getName()), commDiag.getConnList().get(i));
                    return;
                }
            }
            leftStatusLabel.setText("Mezi třídami neexistuje spojeni");
            return;
        }
        else
            leftStatusLabel.setText("nezadana vsechna data");
    }

    public void drawMessage(UMLMessage message, Line connLine, UMLConnection connection){
        //pripad prekresleni
        main.getChildren().removeAll(main.lookupAll("#" + message.getName()));

        //vypocet rotace sipky
        double rotation = Math.toDegrees(Math.atan2(connLine.getStartX() - connLine.getEndX(), - connLine.getStartY() + connLine.getEndY()));

        //sipka
        Group group = new Group();
        Line line1 = new Line(0,-25,0,25);
        Line line2 = new Line(0,25,-5,20);
        Line line3 = new Line(0,25,5,20);

        group.getChildren().add(line1);
        group.getChildren().add(line2);
        group.getChildren().add(line3);

        System.out.println(rotation);

        //text
        Text text = new Text(message.getOrder() + ":" + message.getOperation());
        text.setX(-text.getBoundsInLocal().getWidth()/2);

        group.getChildren().add(text);

        double sin = Math.sin(Math.toRadians(rotation));
        double cos = Math.cos(Math.toRadians(rotation));

        int count = 1;
        for (int i = 0; i < connection.getMsgList().indexOf(message); i++)
            if (message.getClass1().compareTo(connection.getMsgList().get(i).getClass1()) == 0)
                count++;

        //nastaveni rotace sipky
        if (message.getClass1().compareTo(connection.getClass1()) == 0) {
            group.setTranslateX((connLine.getStartX() + connLine.getEndX())/2 + 30*count*cos);
            group.setTranslateY((connLine.getStartY() + connLine.getEndY())/2 + 30*count*sin);
            group.setRotate(rotation);
            text.setRotate(-rotation);
        }
        else {
            group.setTranslateX((connLine.getStartX() + connLine.getEndX())/2 - 30*count*cos);
            group.setTranslateY((connLine.getStartY() + connLine.getEndY())/2 - 30*count*sin);
            group.setRotate(rotation + 180);
            text.setRotate(-rotation + 180);
        }

        group.setId(message.getName());

        //mazani
        group.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY){
                connection.getMsgList().remove(message);
                //prekresleni
                for (int i = 0; i < connection.getMsgList().size(); i++){
                    drawMessage(connection.getMsgList().get(i), connLine, connection);
                }
                //mazani
                removeMsg(message, connection);
            }
        });

        main.getChildren().add(group);
    }

    public void removeMsg(UMLMessage message, UMLConnection connection) {
        main.getChildren().removeAll(main.lookupAll("#" + message.getName().replaceAll("\\s+", "€")));
        //vyreseni poctu instanci
        if (message.getType().compareTo("New Instance") == 0 || message.getType().compareTo("Rem Instance") == 0) {
            //pocitani instanci class2
            int countIns = 0;
            for (int i = 0; i < connection.getMsgList().size(); i++){
                if (message.getClass2().compareTo(connection.getMsgList().get(i).getClass2()) == 0 && connection.getMsgList().get(i).getType().compareTo("New Instance") == 0)
                    countIns++;
                else if (message.getClass2().compareTo(connection.getMsgList().get(i).getClass2()) == 0 && connection.getMsgList().get(i).getType().compareTo("Rem Instance") == 0)
                    countIns--;
            }
            int countIns2 = 0;
            for (int i = 0; i < instanceList.size(); i++)
                if (instanceList.get(i).compareTo(message.getClass2()) == 0)
                    countIns2++;
            //prepocitani
            while (countIns2 > countIns){ //mazani prebytecnych
                instanceList.remove(message.getClass2());
                commDiag.getInstanceList().remove(message.getClass2());
                countIns++;
            }
            while (countIns2 < countIns){ //vkladani zbyvajicich
                instanceList.add(message.getClass2());
                commDiag.getInstanceList().add(message.getClass2());
                countIns--;
            }
        }
    }


}
