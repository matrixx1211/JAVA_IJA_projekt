/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci komunikačního diagramu.
 */

package ija.proj.uml;

import ija.proj.ClassDiagramController;
import javafx.stage.Stage;
import java.util.ArrayList;


/**
 * Třídá, která v sobě uchovává všechny informace ohledně jednoho
 * komunikačního diagramu a dědí od Elementu.
 */
public class CommunicationDiagram extends Element {
    ArrayList<UMLConnection> connectionList;
    ArrayList<UMLUser> userList;
    ArrayList<String> commDiagAllClassList;
    ArrayList<String> commDiagClassList;
    ArrayList<String> instanceList;
    ArrayList<Double> classPosXList;
    ArrayList<Double> classPosYList;
    int connectionCounter;
    transient public Stage stage;

    /**
     * Konstruktor pro diagram komunikace
     * @param name jméno diagramu
     */
    public CommunicationDiagram(String name) {
        super(name);
        this.connectionList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.commDiagAllClassList = new ArrayList<>();
        this.commDiagClassList = new ArrayList<>();
        this.instanceList = new ArrayList<>();
        this.classPosXList = new ArrayList<>();
        this.classPosYList = new ArrayList<>();
        for (int i = 0; i < ClassDiagramController.classDiagram.classes.size(); i++) {
            this.commDiagAllClassList.add(ClassDiagramController.classDiagram.classes.get(i).getName());
        }
        this.connectionCounter = 0;
        this.stage = null;
    }

    /**
     * Získá list propojení
     * @return list propojení
     */
    public ArrayList<UMLConnection> getConnList() {
        return connectionList;
    }

    /**
     * Získá list uživatelů
     * @return list uživatelů
     */
    public ArrayList<UMLUser> getUserList() {
        return userList;
    }

    /**
     * Vytvoří uživatele
     * @param name jméno uživatele
     * @param X souřadnice na X
     * @param Y souřadnice na Y
     * @return vytvořený uživatel
     */
    public UMLUser createUser(String name, double X, double Y){
        UMLUser user = new UMLUser(name, X, Y);
        userList.add(user);
        return user;
    }

    /**
     * Vytvoří spojení
     * @param class1 první třída
     * @param class2 druhá třída
     * @return spojení
     */
    public UMLConnection createConnection(String class1, String class2){
        String name = "connß" + connectionCounter;
        UMLConnection connection = new UMLConnection(name, class1, class2);
        connectionList.add(connection);
        connectionCounter++;
        return connection;
    }

    /**
     * Získá všechny třídy, které ještě nebyli přidány
     * @return třídy, které nejsou v diagramu
     */
    public ArrayList<String> getCommDiagAllClassList() {
        return commDiagAllClassList;
    }

    /**
     * Získá všechny třídy, které jsou přidány
     * @return třídy, které jsou v diagramu
     */
    public ArrayList<String> getCommDiagClassList() {
        return commDiagClassList;
    }

    /**
     * Získá list instancí
     * @return list instancí
     */
    public ArrayList<String> getInstanceList() {
        return instanceList;
    }

    /**
     * Přidá třídu do listu tříd
     * @param name jméno třídy
     */
    public void addClassToList(String name) {
        commDiagAllClassList.add(name);
    }

    /**
     * Odstraní třídu z listu tříd
     * @param name jméno třídy
     */
    public void removeClassFromList(String name) {
        commDiagAllClassList.remove(name);
        commDiagClassList.remove(name);
        //prejmenovani ve spojenich
        for (int i = 0; i < connectionList.size(); i++){
            if (connectionList.get(i).getClass1().compareTo(name) == 0) {
                connectionList.remove(i);
                i--;
            }
            else if (connectionList.get(i).getClass2().compareTo(name) == 0) {
                connectionList.remove(i);
                i--;
            }
            //prejmenovani ve zpravach
            for (int j = 0; i < connectionList.get(i).messageList.size(); j++){
                if (connectionList.get(i).messageList.get(j).getClass1().compareTo(name) == 0) {
                    connectionList.get(i).messageList.remove(j);
                    j--;
                }
                else if (connectionList.get(i).messageList.get(j).getClass2().compareTo(name) == 0) {
                    connectionList.get(i).messageList.remove(j);
                    j--;
                }
            }
        }
    }

    /**
     * Přejmenuje třídu v listu tříd
     * @param oldName staré jméno
     * @param newName nové jméno
     */
    public void renameClassInList(String oldName, String newName) {
        if (commDiagAllClassList.contains(oldName)) {
            commDiagAllClassList.set(commDiagAllClassList.indexOf(oldName), newName);
        }
        if (commDiagClassList.contains(oldName)) {
            commDiagClassList.set(commDiagClassList.indexOf(oldName), newName);
            //prejmenovani ve spojenich
            for (int i = 0; i < connectionList.size(); i++){
                if (connectionList.get(i).getClass1().compareTo(oldName) == 0)
                    connectionList.get(i).class1 = newName;
                if (connectionList.get(i).getClass2().compareTo(oldName) == 0)
                    connectionList.get(i).class2 = newName;
                //prejmenovani ve zpravach
                for (int j = 0; i < connectionList.get(i).messageList.size(); j++){
                    if (connectionList.get(i).messageList.get(j).getClass1().compareTo(oldName) == 0)
                        connectionList.get(i).messageList.get(j).class1 = newName;
                    if (connectionList.get(i).messageList.get(j).getClass2().compareTo(oldName) == 0)
                        connectionList.get(i).messageList.get(j).class2 = newName;
                }
            }
        }
    }

    /**
     * Přídá do listu souřadnic souřadnice třídy na ose X a Y
     * @param X souřadnice na X
     * @param Y souřadnice na Y
     */
    public void addClassPosXY(double X, double Y){
        classPosXList.add(X);
        classPosYList.add(Y);
    }

    /**
     * Nastaví souřadnice třídy na ose X a Y
     * @param class1 třída
     * @param X souřadnice na X
     * @param Y souřadnice na Y
     */
    public void setClassPosXY(String class1, double X, double Y){
        if (commDiagClassList.contains(class1)) {
            classPosXList.set(commDiagClassList.indexOf(class1), X);
            classPosYList.set(commDiagClassList.indexOf(class1), Y);
        }
    }

    /**
     * Získá souřadnice na ose X třídy z plochy
     * @param class1 třída
     * @return souřadnice na ose X
     */
    public double getClassPosX(String class1){
        if (commDiagClassList.contains(class1))
            return classPosXList.get(commDiagClassList.indexOf(class1));
        else
            return 0;
    }

    /**
     * Získá souřadnice na ose Y třídy z plochy
     * @param class1 třída
     * @return souřadnice na ose Y
     */
    public double getClassPosY(String class1){
        if (commDiagClassList.contains(class1))
            return classPosYList.get(commDiagClassList.indexOf(class1));
        else
            return 0;
    }

    /**
     * Odstraní souřadnice třídy z listu souřadnic na ploše
     * @param class1 třída
     */
    public void remClassPos(String class1){
        classPosXList.remove(commDiagClassList.indexOf(class1));
        classPosYList.remove(commDiagClassList.indexOf(class1));
    }

    /**
     * Získá pozici na ose X uživatele
     * @param class1 třída
     * @return pozici na ose X
     */
    public double getUserPosX(String class1){
        for (int i = 0; i < userList.size(); i++){
            if (userList.get(i).getName().compareTo(class1) == 0) {
                return userList.get(i).getPosX();
            }
        }
        return 0;
    }

    /**
     * Získá pozici na ose Y uživatele
     * @param class1 třída
     * @return pozici na ose Y
     */
    public double getUserPosY(String class1){
        for (int i = 0; i < userList.size(); i++){
            if (userList.get(i).getName().compareTo(class1) == 0)
                return userList.get(i).getPosY();
        }
        return 0;
    }
}
