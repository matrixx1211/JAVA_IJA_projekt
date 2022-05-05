package ija.proj.uml;

import java.util.ArrayList;

public class UMLConnection extends Element {
    String class1;
    String class2;
    ArrayList<UMLMessage> messageList;
    int messageCounter;

    public UMLConnection(String name, String class1, String class2){
        super(name);
        this.class1 = class1;
        this.class2 = class2;
        this.messageList = new ArrayList<>();
        this.messageCounter = 0;
    }

    public String getClass1(){
        return class1;
    }

    public String getClass2(){
        return class2;
    }

    public ArrayList<UMLMessage> getMsgList() {
        return messageList;
    }

    public UMLMessage createMessage(String class1, String class2, String type, String operation) {
        String name = "msgß"+messageCounter+"ß"+class1+"ß"+class2;
        messageCounter++;
        UMLMessage message = new UMLMessage(name, class1, class2, type, operation);
        messageList.add(message);
        return message;
    }
}
