/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci sekvenčního diagramu.
 */

package ija.proj.uml;

import ija.proj.ClassDiagramController;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * Třída sekvenčního diagramu, která v sobě uchovává informace o jednom diagramu.
 */
public class SequenceDiagram extends Element {
    ArrayList<UMLMessage> messageList;
    ArrayList<UMLActivation> activationList;
    ArrayList<String> seqDiagAllClassList;
    ArrayList<String> seqDiagClassList;
    ArrayList<String> instancesList;
    ArrayList<Double> classPosXList;
    ArrayList<Double> instancePosXList;
    int messageCounter;
    int instanceCounter;
    int activationCounter;
    transient public Stage stage;

    /**
     * Konstruktor pro sekvenční diagram
     * @param name jméno diagramu
     */
    public SequenceDiagram(String name) {
        super(name);
        this.messageList = new ArrayList<>();
        this.activationList = new ArrayList<>();
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
        this.stage = null;
    }

    /**
     * Získá list zpráv
     * @return list zpráv
     */
    public ArrayList<UMLMessage> getMsgList() {
        return messageList;
    }

    /**
     * Získá list aktivaci
     * @return list aktivaci
     */
    public ArrayList<UMLActivation> getActList() {
        return activationList;
    }

    /**
     * Získá počítadlo zpráv
     * @return počítadlo zpráv
     */
    public int getMsgCounter() {
        return messageCounter;
    }

    /**
     * Zvýší počítadlo zpráv
     */
    public void incMsgCounter() {
        messageCounter++;
    }

    /**
     * Získá počítadlo instancí
     * @return počítadlo instancí
     */
    public int getInstaceCounter() {
        return instanceCounter;
    }

    /**
     * Zvýší počítadlo instancí
     */
    public void incInstanceCounter() {
        instanceCounter++;
    }

    /**
     * Získá počítadlo aktivací
     * @return počítadlo aktivací
     */
    public int getActivationCounter() {
        return activationCounter;
    }

    /**
     * Vytvoří zprávu
     * @param name identifikátor zprávy
     * @param class1 jméno první třídy
     * @param class2 jméno druhé třídy
     * @param type typ zprávy
     * @param operation operace
     * @return
     */
    public UMLMessage createMessage(String name, String class1, String class2, String type, String operation) {
        UMLMessage message = new UMLMessage(name, class1, class2, type, operation);
        messageList.add(message);
        return message;
    }

    /**
     * Vytvoří aktivaci na třídě
     * @param name jméno aktivace
     * @param class1 jméno třídy
     * @param posY pozice aktivace na ose Y
     * @param len výška aktivace
     * @return
     */
    public UMLActivation createActivation(String name, String class1, Double posY, Double len) {
        UMLActivation activation = new UMLActivation(name, class1, posY, len);
        activationList.add(activation);
        activationCounter++;
        return activation;
    }

    /**
     * Získá list tříd, které nejsou přídány do diagramu
     * @return list tříd, které nejsou v diagramu
     */
    public ArrayList<String> getSeqDiagAllClassList() {
        return seqDiagAllClassList;
    }

    /**
     * Získá list tříd, které jsou přidány do diagramu
     * @return list tříd, které jsou v diagramu
     */
    public ArrayList<String> getSeqDiagClassList() {
        return seqDiagClassList;
    }

    /**
     * Získá list instancí
     * @return list instancí
     */
    public ArrayList<String> getInstancesList() {
        return instancesList;
    }

    /**
     * Přídá třídu do listu tříd
     * @param name jméno třídy
     */
    public void addClassToList(String name) {
        seqDiagAllClassList.add(name);
    }

    /**
     * Odstraní třídu z listu tříd
     * @param name jméno třídy
     */
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

    /**
     * Přejmenuje jméno třídy v listu tříd
     * @param oldName staré jméno
     * @param newName nové jméno
     */
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

    /**
     * Přídá souřadníce na ose X třídy do listu souřadnic
     * @param x souřadnice na ose X
     */
    public void addClassPosX(double x){
        classPosXList.add(x);
    }

    /**
     * Získá souřadníce na ose X třídy z listu souřadnic
     * @param class1 jméno třídy
     * @return souřadnice na ose X
     */
    public double getClassPosX(String class1){
        if (seqDiagClassList.contains(class1))
            return classPosXList.get(seqDiagClassList.indexOf(class1));
        else if (instancesList.contains(class1))
            return getInstancePosX(class1);
        return 0;
    }

    /**
     * Změní souřadníce na ose X třídě v listu souřadnic
     * @param class1 jméno třídy
     * @param x souřadnice na ose X
     */
    public void changeClassPosX(String class1, double x){
        classPosXList.set(seqDiagClassList.indexOf(class1), x);
    }

    /**
     * Odstraní souřadníce na ose X třídě z listu souřadnic
     * @param class1 jméno třídy
     */
    public void removeClassPosX(String class1){
        classPosXList.remove(seqDiagClassList.indexOf(class1));
    }

    /**
     * Přidá souřadníce na ose X instance do listu souřadnic
     * @param x souřadnice na ose X
     */
    public void addInstancePosX(double x){
        instancePosXList.add(x);
    }

    /**
     * Získá souřadníce na ose X instance v listu souřadnic
     * @param class1 jméno instance
     * @return souřadnice na ose X
     */
    public double getInstancePosX(String class1){
        return instancePosXList.get(instancesList.indexOf(class1));
    }

    /**
     * Změní souřadníce na ose X instance v listu souřadnic
     * @param class1 jméno instance
     * @param x souřadnice na ose X
     */
    public void changeInstancePosX(String class1, double x){
        instancePosXList.set(instancesList.indexOf(class1), x);
    }

    /**
     * Odstraní souřadníce na ose X instance z listu souřadnic
     * @param class1 jméno instance
     */
    public void removeInstancePosX(String class1){
        instancePosXList.remove(instancesList.indexOf(class1));
    }
}
