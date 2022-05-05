package ija.proj.uml;

import ija.proj.ClassDiagramController;
import javafx.collections.FXCollections;

import java.util.ArrayList;

public class CommunicationDiagram extends Element {
    ArrayList<UMLMessage> messageList;
    ArrayList<UMLConnection> connectionList;
    ArrayList<UMLUser> userList;
    Boolean opened;
    ArrayList<String> commDiagAllClassList;
    ArrayList<String> commDiagClassList;
    ArrayList<String> instanceList;
    int messageCounter;

    public CommunicationDiagram(String name) {
        super(name);
        this.messageList = new ArrayList<>();
        this.connectionList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.opened = false;
        this.commDiagAllClassList = new ArrayList<>();
        this.commDiagClassList = new ArrayList<>();
        this.instanceList = new ArrayList<>();
        for (int i = 0; i < ClassDiagramController.classDiagram.classes.size(); i++) {
            this.commDiagAllClassList.add(ClassDiagramController.classDiagram.classes.get(i).getName());
        }
        this.messageCounter = 0;
    }

    public ArrayList<UMLMessage> getMsgList() {
        return messageList;
    }

    public ArrayList<UMLConnection> getConnList() {
        return connectionList;
    }

    public ArrayList<UMLUser> getUserList() {
        return userList;
    }

    public int getMsgCounter() {
        return messageCounter;
    }

    public void incMsgCounter() {
        messageCounter++;
    }

    public UMLMessage createMessage(String name, String class1, String class2, String type, String operation) {
        UMLMessage message = new UMLMessage(name, class1, class2, type, operation);
        messageList.add(message);
        return message;
    }

    public ArrayList<String> getCommDiagAllClassList() {
        return commDiagAllClassList;
    }

    public ArrayList<String> getCommDiagClassList() {
        return commDiagClassList;
    }

    public ArrayList<String> getInstanceList() {
        return instanceList;
    }

    public void addClassToList(String name) {
        commDiagAllClassList.add(name);
    }
    public void removeClassFromList(String name) {
        commDiagAllClassList.remove(name);
        commDiagClassList.remove(name);
    }
    public void renameClassInList(String oldName, String newName) {
        if (commDiagAllClassList.contains(oldName)) {
            commDiagAllClassList.set(commDiagAllClassList.indexOf(oldName), newName);
        }
        if (commDiagClassList.contains(oldName)) {
            commDiagClassList.set(commDiagClassList.indexOf(oldName), newName);
            //prejmenovani ve zpravach
            for (int i = 0; i < messageList.size(); i++){
                if (messageList.get(i).getClass1().compareTo(oldName) == 0)
                    messageList.get(i).class1 = newName;
                if (messageList.get(i).getClass2().compareTo(oldName) == 0)
                    messageList.get(i).class2 = newName;
            }
        }
    }
}
