package ija.proj;

import ija.proj.uml.CommunicationDiagram;
import ija.proj.uml.SequenceDiagram;
import ija.proj.uml.UMLClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CommunicationDiagramController {
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

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

    }

    @FXML
    public void classAddBtnClicked() {
        if (classNewChoice.getValue() != null){
            String name = classNewChoice.getValue();
            commDiag.getCommDiagAllClassList().remove(name);
            commDiag.getCommDiagClassList().add(name);
            classUsedList.add(name);
            classNotUsedList.remove(name);
            //update vyvolanim eventu pri zmene typu
            String type = msgTypeChoice.getValue();
            msgTypeChoice.setValue("Asynch");
            msgTypeChoice.setValue("null");
            msgTypeChoice.setValue(type);
            drawClass(name);
        }
    }

    @FXML
    public void classDelBtnClicked() {
        if (classChoice.getValue() != null){
            String name = classChoice.getValue();
/*
            //mazani zprav dane class
            for (int i = 0; i < seqDiag.getMsgList().size(); i++) {
                if (seqDiag.getMsgList().get(i).getClass1().compareTo(name) == 0 || seqDiag.getMsgList().get(i).getClass2().compareTo(name) == 0) {
                    main.getChildren().removeAll(main.lookupAll("#" + seqDiag.getMsgList().get(i).getName().replaceAll("\\s+", "€") + "line"));
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

            //mazani aktivaci na dane class
            for (int i = 0; i < seqDiag.getActList().size(); i++) {
                if (seqDiag.getActList().get(i).getClass1().compareTo(name) == 0) {
                    deleteActivation(seqDiag.getActList().get(i));
                    i--; //index se posunul smazanim prvku
                }
            }

            seqDiag.getSeqDiagClassList().remove(name);
            seqDiag.getSeqDiagAllClassList().add(name);
            classUsedList.remove(name);
            classNotUsedList.add(name);
            class1List.remove(name);
            actClassList.remove(name);
            seqDiag.removeClassPosX(name);
            //update vyvolanim eventu pri zmene typu
            String type = msgTypeChoice.getValue();
            msgTypeChoice.setValue("Asynch");
            msgTypeChoice.setValue("null");
            msgTypeChoice.setValue(type);

            //mazani graficke reprezentace
            eraseClass(name);*/
        }
    }

    public void drawClass(String name){

    }

    @FXML
    public void userAddBtnClicked(){

    }

    @FXML
    public void userDelBtnClicked(){

    }

    @FXML
    public void connAddBtnClicked(){

    }

    @FXML
    public void msgAddBtnClicked(){

    }


}
