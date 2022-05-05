package ija.proj.uml;

import ija.proj.ClassDiagramController;
import javafx.collections.FXCollections;

import java.util.ArrayList;

public class CommunicationDiagram extends Element {
    ArrayList<UMLConnection> connectionList;
    ArrayList<UMLUser> userList;
    Boolean opened;
    ArrayList<String> commDiagAllClassList;
    ArrayList<String> commDiagClassList;
    ArrayList<String> instanceList;
    ArrayList<Double> classPosXList;
    ArrayList<Double> classPosYList;
    int connectionCounter;

    public CommunicationDiagram(String name) {
        super(name);
        this.connectionList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.opened = false;
        this.commDiagAllClassList = new ArrayList<>();
        this.commDiagClassList = new ArrayList<>();
        this.instanceList = new ArrayList<>();
        this.classPosXList = new ArrayList<>();
        this.classPosYList = new ArrayList<>();
        for (int i = 0; i < ClassDiagramController.classDiagram.classes.size(); i++) {
            this.commDiagAllClassList.add(ClassDiagramController.classDiagram.classes.get(i).getName());
        }
        this.connectionCounter = 0;
    }

    public ArrayList<UMLConnection> getConnList() {
        return connectionList;
    }

    public ArrayList<UMLUser> getUserList() {
        return userList;
    }

    public  UMLUser createUser(String name, double X, double Y){
        UMLUser user = new UMLUser(name, X, Y);
        userList.add(user);
        return user;
    }

    public  UMLConnection createConnection(String class1, String class2){
        String name = "connß" + connectionCounter;
        UMLConnection connection = new UMLConnection(name, class1, class2);
        connectionList.add(connection);
        connectionCounter++;
        return connection;
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
            /*for (int i = 0; i < messageList.size(); i++){
                if (messageList.get(i).getClass1().compareTo(oldName) == 0)
                    messageList.get(i).class1 = newName;
                if (messageList.get(i).getClass2().compareTo(oldName) == 0)
                    messageList.get(i).class2 = newName;
            }*/
        }
    }

    public void addClassPosXY(double X, double Y){
        classPosXList.add(X);
        classPosYList.add(Y);
    }

    public void setClassPosXY(String class1, double X, double Y){
        if (commDiagClassList.contains(class1)) {
            classPosXList.set(commDiagClassList.indexOf(class1), X);
            classPosYList.set(commDiagClassList.indexOf(class1), Y);
        }
    }

    public double getClassPosX(String class1){
        if (commDiagClassList.contains(class1))
            return classPosXList.get(commDiagClassList.indexOf(class1));
        else
            return 0;
    }

    public double getClassPosY(String class1){
        if (commDiagClassList.contains(class1))
            return classPosYList.get(commDiagClassList.indexOf(class1));
        else
            return 0;
    }

    public void remClassPos(String class1){
        classPosXList.remove(commDiagClassList.indexOf(class1));
        classPosYList.remove(commDiagClassList.indexOf(class1));
    }

    public double getUserPosX(String class1){
        for (int i = 0; i < userList.size(); i++){
            if (userList.get(i).getName() == class1)
                return userList.get(i).getPosX();
        }
        return 0;
    }

    public double getUserPosY(String class1){
        for (int i = 0; i < userList.size(); i++){
            if (userList.get(i).getName() == class1)
                return userList.get(i).getPosY();
        }
        return 0;
    }
}
