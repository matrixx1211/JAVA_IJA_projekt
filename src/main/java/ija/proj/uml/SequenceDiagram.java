package ija.proj.uml;

import java.util.ArrayList;

public class SequenceDiagram extends Element {
    ArrayList<UMLClass> classList;
    ArrayList<UMLMessage> messageList;
    Boolean opened;

    public SequenceDiagram(String name) {
        super(name);
        this.classList = new ArrayList<>();
        this.messageList = new ArrayList<>();
        this.opened = false;
    }

    public void addClass(UMLClass existingClass) {

    }

    public void createMessage(String name, UMLClass class1, UMLClass class2, Character type, UMLOperation operation) {
        UMLMessage message = new UMLMessage(name, class1, class2, type, operation);
        messageList.add(message);
    }
}
