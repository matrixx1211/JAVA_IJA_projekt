/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci propojení.
 */

package ija.proj.uml;

import java.util.ArrayList;

/**
 * Třída, která slouží pro vytvoření propojení v komunikačním diagramu.
 */
public class UMLConnection extends Element {
    String class1;
    String class2;
    ArrayList<UMLMessage> messageList;
    int messageCounter;

    /**
     * Konstruktor pro propojení
     * @param name identifikátor propojení
     * @param class1 jméno první třídy
     * @param class2 jméno druhé třídy
     */
    public UMLConnection(String name, String class1, String class2){
        super(name);
        this.class1 = class1;
        this.class2 = class2;
        this.messageList = new ArrayList<>();
        this.messageCounter = 0;
    }

    /**
     * Získá jméno první třídy
     * @return jméno první třídy
     */
    public String getClass1(){
        return class1;
    }

    /**
     * Získá jméno druhé třídy
     * @return jméno druhé třídy
     */
    public String getClass2(){
        return class2;
    }

    /**
     * Získá list zpráv
     * @return list zpráv
     */
    public ArrayList<UMLMessage> getMsgList() {
        return messageList;
    }

    /**
     * Vytvoří zprávu
     * @param class1 jméno první třídy
     * @param class2 jméno druhé třídy
     * @param type typ zprávy
     * @param operation operace
     * @return zprávu
     */
    public UMLMessage createMessage(String class1, String class2, String type, String operation) {
        String name = "msgß"+messageCounter+"ß"+class1+"ß"+class2;
        messageCounter++;
        UMLMessage message = new UMLMessage(name, class1, class2, type, operation);
        messageList.add(message);
        return message;
    }
}
