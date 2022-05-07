package ija.proj.uml;

import ija.proj.ClassDiagramController;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;

public class SequenceDiagram extends Element {
    ArrayList<UMLMessage> messageList;
    ArrayList<UMLActivation> activationList;
    Boolean opened;
    ArrayList<String> seqDiagAllClassList;
    ArrayList<String> seqDiagClassList;
    ArrayList<String> instancesList;
    ArrayList<Double> classPosXList;
    ArrayList<Double> instancePosXList;
    int messageCounter;
    int instanceCounter;
    int activationCounter;


    public SequenceDiagram(String name) {
        super(name);
        this.messageList = new ArrayList<>();
        this.activationList = new ArrayList<>();
        this.opened = false;
        this.seqDiagAllClassList = new ArrayList<>();
        this.seqDiagClassList = new ArrayList<>();
        this.instancesList = new ArrayList<>();
        this.classPosXList = new ArrayList<>();
        this.instancePosXList = new ArrayList<>();
        for (int i = 0; i < ClassDiagramController.classDiagram.classes.size(); i++) {
            this.seqDiagAllClassList.add(ClassDiagramController.classDiagram.classes.get(i).getName());
        }
        this.messageCounter = 0;
        this.instanceCounter = 0;
        this.activationCounter = 0;
    }

    public ArrayList<UMLMessage> getMsgList() {
        return messageList;
    }

    public ArrayList<UMLActivation> getActList() {
        return activationList;
    }

    public int getMsgCounter() {
        return messageCounter;
    }

    public void incMsgCounter() {
        messageCounter++;
    }

    public int getInstaceCounter() {
        return instanceCounter;
    }

    public void incInstanceCounter() {
        instanceCounter++;
    }

    public int getActivationCounter() {
        return activationCounter;
    }

    public UMLMessage createMessage(String name, String class1, String class2, String type, String operation) {
        UMLMessage message = new UMLMessage(name, class1, class2, type, operation);
        messageList.add(message);
        return message;
    }

    public UMLActivation createActivation(String name, String class1, Double posY, Double len) {
        UMLActivation activation = new UMLActivation(name, class1, posY, len);
        activationList.add(activation);
        activationCounter++;
        return activation;
    }

    public ArrayList<String> getSeqDiagAllClassList() {
        return seqDiagAllClassList;
    }

    public ArrayList<String> getSeqDiagClassList() {
        return seqDiagClassList;
    }

    public ArrayList<String> getInstancesList() {
        return instancesList;
    }

    public void addClassToList(String name) {
        seqDiagAllClassList.add(name);
    }
    public void removeClassFromList(String name) {
        seqDiagAllClassList.remove(name);
        seqDiagClassList.remove(name);
        //instance
        String[] split;
        String string = "";
        for (int i = 0; i < instancesList.size(); i++) {
            split = instancesList.get(i).split(" ", 0);
            if (split.length >= 4)
                string = split[3];
            for (int j = 4; j < split.length; j++)
                string = (string + " " + split[j]);
            if (name.compareTo(string) == 0) {
                instancesList.remove(i);
                i--; //posunuti indexu pri mazani
            }
        }
    }
    public void renameClassInList(String oldName, String newName) {
        if (seqDiagAllClassList.contains(oldName)) {
            seqDiagAllClassList.set(seqDiagAllClassList.indexOf(oldName), newName);
        }
        if (seqDiagClassList.contains(oldName)) {
            seqDiagClassList.set(seqDiagClassList.indexOf(oldName), newName);
            //prejmenovani ve zpravach
            for (int i = 0; i < messageList.size(); i++){
                if (messageList.get(i).getClass1().compareTo(oldName) == 0)
                    messageList.get(i).class1 = newName;
                if (messageList.get(i).getClass2().compareTo(oldName) == 0)
                    messageList.get(i).class2 = newName;
            }
            //prejmenovani instanci
            String[] split;
            String string = "";
            for (int i = 0; i < instancesList.size(); i++) {
                split = instancesList.get(i).split(" ", 0);
                if (split.length >= 4)
                    string = split[3];
                for (int j = 4; j < split.length; j++)
                    string = (string + " " + split[j]);
                if (name.compareTo(string) == 0) {
                    instancesList.set(i, split[0] + " " + split[1] + " " + split [2] + " " + newName);
                }
            }
        }
    }

    public void addClassPosX(double x){
        classPosXList.add(x);
    }

    public double getClassPosX(String class1){
        if (seqDiagClassList.contains(class1))
            return classPosXList.get(seqDiagClassList.indexOf(class1));
        else if (instancesList.contains(class1))
            return getInstancePosX(class1);
        return 0;
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
        return instancePosXList.get(instancesList.indexOf(class1));
    }

    public void changeInstancePosX(String class1, double x){
        instancePosXList.set(instancesList.indexOf(class1), x);
    }

    public void removeInstancePosX(String class1){
        instancePosXList.remove(instancesList.indexOf(class1));
    }

    public void setOpened(boolean opened){
        this.opened = opened;
    }

    public boolean getOpened(){
        return opened;
    }
}
