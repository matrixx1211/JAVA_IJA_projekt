package ija.proj.uml;

import ija.proj.ClassDiagramController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class SequenceDiagram extends Element {
    ArrayList<UMLClass> classList;
    ArrayList<UMLMessage> messageList;
    Boolean opened;
    ObservableList<String> seqDiagAllClassList;
    ObservableList<String> seqDiagClassList;
    ObservableList<String> instancesList;
    //3 arraylisty pro ulozeni ObservableList do gson
    ArrayList<String> GSONseqDiagAllClassList;
    ArrayList<String> GSONseqDiagClassList;
    ArrayList<String> GSONinstancesList;
    ArrayList<Double> classPosXList;
    ArrayList<Double> instancePosXList;
    int msgcounter;

    public SequenceDiagram(String name) {
        super(name);
        this.classList = new ArrayList<>();
        this.messageList = new ArrayList<>();
        this.opened = false;
        this.seqDiagAllClassList = FXCollections.observableArrayList();
        this.seqDiagClassList = FXCollections.observableArrayList();
        this.instancesList = FXCollections.observableArrayList();
        this.GSONseqDiagAllClassList = new ArrayList<>();
        this.GSONseqDiagClassList = new ArrayList<>();
        this.GSONinstancesList = new ArrayList<>();
        this.classPosXList = new ArrayList<>();
        this.instancePosXList = new ArrayList<>();
        for (int i = 0; i < ClassDiagramController.classDiagram.classes.size(); i++) {
            this.seqDiagAllClassList.add(ClassDiagramController.classDiagram.classes.get(i).getName());
        }
        this.msgcounter = 0;
    }

    public ArrayList<UMLMessage> getMsgList() {
        return messageList;
    }

    public int getMsgCounter() {
        return msgcounter;
    }

    public void incMsgCounter() {
        msgcounter++;
    }

    public UMLMessage createMessage(String name, String class1, String class2, String type, String operation) {
        UMLMessage message = new UMLMessage(name, class1, class2, type, operation);
        messageList.add(message);
        return message;
    }

    public ObservableList<String> getSeqDiagAllClassList() {
        return seqDiagAllClassList;
    }

    public ObservableList<String> getSeqDiagClassList() {
        return seqDiagClassList;
    }

    public ObservableList<String> getInstancesList() {
        return instancesList;
    }

    public void addClassToList(String name) {
        seqDiagAllClassList.add(name);
    }
    public void removeClassFromList(String name) {
        seqDiagAllClassList.remove(name);
    }
    public void renameClassInList(String oldName, String newName) {
        if (seqDiagAllClassList.contains(oldName)) {
            seqDiagAllClassList.remove(oldName);
            seqDiagAllClassList.add(newName);
        }
        if (seqDiagClassList.contains(oldName)) {
            seqDiagClassList.remove(oldName);
            seqDiagClassList.add(newName);
            //prejmenovani ve zpravach
            for (int i = 0; i < messageList.size(); i++){
                if (messageList.get(i).getClass1().compareTo(oldName) == 0)
                    messageList.get(i).class1 = newName;
                if (messageList.get(i).getClass2().compareTo(oldName) == 0)
                    messageList.get(i).class2 = newName;
            }
            //prejmenovani instanci
            //TODO
        }
    }
    public void addClassPosX(double x){
        classPosXList.add(x);
    }

    public double getClassPosX(String class1){
        return classPosXList.get(seqDiagClassList.indexOf(class1));
    }

    public void changeClassPosX(String class1, double x){
        classPosXList.set(seqDiagClassList.indexOf(class1), x);
    }

    public void removeClassPosX(String class1){
        classPosXList.remove(seqDiagClassList.indexOf(class1));
    }

    public void addInstancePosX(double x){
        instancePosXList.add(x);
    }

    public double getInstancePosX(String class1){
        return instancePosXList.get(seqDiagClassList.indexOf(class1));
    }

    public void changeInstancePosX(String class1, double x){
        instancePosXList.set(seqDiagClassList.indexOf(class1), x);
    }

    public void removeInstancePosX(String class1){
        instancePosXList.remove(seqDiagClassList.indexOf(class1));
    }

    public void backupObservable(){
        GSONseqDiagAllClassList.clear();
        GSONseqDiagClassList.clear();
        GSONinstancesList.clear();
        for (int i = 0; i < seqDiagAllClassList.size(); i++)
            GSONseqDiagAllClassList.add(seqDiagAllClassList.get(i));
        for (int i = 0; i < seqDiagClassList.size(); i++)
            GSONseqDiagClassList.add(seqDiagClassList.get(i));
        for (int i = 0; i < instancesList.size(); i++)
            GSONinstancesList.add(instancesList.get(i));
    }

    public void restoreObservable(){
        seqDiagAllClassList = FXCollections.observableArrayList();
        seqDiagClassList = FXCollections.observableArrayList();
        instancesList = FXCollections.observableArrayList();
        for (int i = 0; i < GSONseqDiagAllClassList.size(); i++)
            seqDiagAllClassList.add(GSONseqDiagAllClassList.get(i));
        for (int i = 0; i < GSONseqDiagClassList.size(); i++)
            seqDiagClassList.add(GSONseqDiagClassList.get(i));
        for (int i = 0; i < GSONinstancesList.size(); i++)
            instancesList.add(GSONinstancesList.get(i));
    }

}
