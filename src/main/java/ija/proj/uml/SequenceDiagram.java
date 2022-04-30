package ija.proj.uml;

import ija.proj.ClassDiagramController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class SequenceDiagram extends Element {
    ArrayList<UMLClass> classList;
    ArrayList<UMLMessage> messageList;
    Boolean opened;
    public static ObservableList<String> seqDiagAllClassList;
    public static ObservableList<String> seqDiagClassList;

    public SequenceDiagram(String name) {
        super(name);
        this.classList = new ArrayList<>();
        this.messageList = new ArrayList<>();
        this.opened = false;
        seqDiagAllClassList = FXCollections.observableArrayList();
        seqDiagClassList = FXCollections.observableArrayList();
        for (int i = 0; i < ClassDiagramController.classDiagram.classes.size(); i++) {
            seqDiagAllClassList.add(ClassDiagramController.classDiagram.classes.get(i).getName());
        }
    }

    public void addClass(UMLClass existingClass) {
        if (this.classList.add(existingClass)){

        }
    }

    public void createMessage(String name, UMLClass class1, UMLClass class2, Character type, UMLOperation operation) {
        UMLMessage message = new UMLMessage(name, class1, class2, type, operation);
        messageList.add(message);
    }

    public ObservableList<String> getSeqDiagAllClassList() {
        return seqDiagAllClassList;
    }

    public ObservableList<String> getSeqDiagClassList() {
        return seqDiagClassList;
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
        }
    }
}
