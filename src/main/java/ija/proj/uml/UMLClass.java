/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci Třídy UMLClass
 */

package ija.proj.uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Třída pro Třídy a Rozhraní v ClassDiagramu
 */
public class UMLClass extends UMLClassifier {
    public Boolean isAbstract;
    /**
     * List atributů třídy
     */
    public List<UMLAttribute> attributes;
    /**
     * List operací (metod) třídy / rozhraní
     */
    public List<UMLOperation> operations;
    /**
     * Číslo udávající pořadí přidávaných prvků
     */
    Integer id;
    /**
     * Boolean rozhodující zda se jedná o třídu / rozhraní
     */
    public Boolean isInterface;
    /**
     * X souřadnice elementu na plátně
     */
    double x;
    /**
     * Y souřadnice elementu na plátně
     */
    double y;

    /**
     * Konstruktor pro třídu / rozhraní
     * @param name jméno elementu
     * @param id pořadové číslo
     * @param isInterface zda je rozhraní (true) / třída (false)
     */
    public UMLClass(String name, Integer id, Boolean isInterface) {
        super(name);
        this.isUserDefined = true;
        this.isAbstract = false;
        this.attributes = new ArrayList<UMLAttribute>();
        this.operations = new ArrayList<UMLOperation>();
        this.id = id;
        this.isInterface = isInterface;
        this.x = 0.0;
        this.y = 0.0;
    }

    /**
     * Zjistí zda je třída / rozhraní abstraktní
     * @return true / false
     */
    public Boolean isAbstract() {
        return this.isAbstract;
    }

    /**
     * Zjistí zda se jedná o třídu / rozhraní
     * @return true / false
     */
    public Boolean isInterface() {
        return this.isInterface;
    }

    /**
     * Nastaví abstrakci na:
     * @param isAbstract true / false
     */
    public void setAbstract(Boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    /**
     * Přidá attribut do seznamu attributů
     * @param attr attribut
     * @return true (pokud se povedlo), false (již existuje)
     */
    public Boolean addAttribute(UMLAttribute attr) {
        for (int i = 0; i < this.attributes.size(); i++) {
            if (this.attributes.get(i).getName().compareTo(attr.getName()) == 0) {
                return false;
            }
        }
        this.attributes.add(attr);
        return true;
    }

    /**
     * Zjistí index attributu v listu
     * @param attr hledaný attribut
     * @return index
     */
    public int getAttrPosition(UMLAttribute attr) {
        for (int i = 0; i < this.attributes.size(); i++) {
            if (this.attributes.get(i).equals(attr))
                return i;
        }
        return -1;
    }

    /**
     * Přesune attribut na jinou pozici v seznamu
     * @param attr posunovaný attribut
     * @param pos cílová pozice
     * @return pos pokud se povedlo, -1 pokud ne
     */
    public int moveAttrAtPosition(UMLAttribute attr, int pos) {
        if (this.attributes.contains(attr)) {
            this.attributes.remove(attr);
            this.attributes.add(pos, attr);
            return pos;
        }
        return -1;
    }

    /**
     * Získá id
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Provede smazání attributu ze seznamu
     * @param attr mazaný attribut
     * @return true (pokud se povedlo), false (pokud nebyl attribut nalezen)
     */
    public Boolean removeAttr(UMLAttribute attr) {
        for (int i = 0; i < this.attributes.size(); i++) {
            if (this.attributes.get(i) == attr) {
                this.attributes.remove(attr);
                return true;
            }
        }
        return false;
    }

    /**
     * Nastaví X a Y souřadnice třídy / rozhraní
     * @param x X souřadnice
     * @param y Y souřadnice
     */
    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Získá nemodifikovatelný seznam attributů
     * @return seznam attributů
     */
    public List<UMLAttribute> getAttributes() {
        return Collections.unmodifiableList(this.attributes);
    }
}
