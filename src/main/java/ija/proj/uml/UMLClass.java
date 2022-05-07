/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci tříd a rozhraní.
 */

package ija.proj.uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Třída, která slouží pro vytváření Třídy a Rozhraní.
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
     * Přidá operaci do seznamu operací
     * @param op operace
     * @return true (pokud se povedlo), false (již existuje)
     */
    public Boolean addOperation(UMLOperation op) {
        for (int i = 0; i < this.operations.size(); i++) {
            if (this.operations.get(i).getName().compareTo(op.getName()) == 0) {
                return false;
            }
        }
        this.operations.add(op);
        return true;
    }

    /**
     * Provede smazání operace ze seznamu
     * @param op mazaná operace
     * @return true (pokud se povedlo), false (pokud nebyla operace nalezena)
     */
    public Boolean removeOperation(UMLOperation op) {
        for (int i = 0; i < this.operations.size(); i++) {
            if (this.operations.get(i) == op) {
                this.operations.remove(op);
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

    public double getPositionX(){
        return this.x;
    }

    public double getPositionY(){
        return this.y;
    }

    /**
     * Hledá operaci, jestli existuje
     * @param name jméno operace
     * @return operaci nebo null
     */
    public UMLOperation findOperation(String name) {
        UMLOperation obj;
        for (int i = 0; i < this.operations.size(); i++) {
            obj = this.operations.get(i);
            if (obj.getName().compareTo(name) == 0) {
                return obj;
            }
        }
        return null;
    }
    /**
     * Hledá atribut, jestli existuje
     * @param name jméno atributu
     * @return atribut nebo null
     */
    public UMLAttribute findAttribute(String name) {
        UMLAttribute obj;
        for (int i = 0; i < this.attributes.size(); i++) {
            obj = this.attributes.get(i);
            if (obj.getName().compareTo(name) == 0) {
                return obj;
            }
        }
        return null;
    }

    /**
     * Získá nemodifikovatelný seznam attributů
     * @return seznam attributů
     */
    public List<UMLAttribute> getAttributes() {
        return Collections.unmodifiableList(this.attributes);
    }

    /**
     * Získá seznam attributů z rodiče (dědičnost)
     * @param classDiagram  diagram tříd
     * @return seznam attributů
     */
    public List<List<String>> getParentAttributes(ClassDiagram classDiagram) {
        List<UMLRelation> relationList = new ArrayList<>();
        List<List<String>> parentAttributeList = new ArrayList<>();
        parentAttributeList.add(new ArrayList<>());
        parentAttributeList.add(new ArrayList<>());
        UMLClass parent;
        //ziskani vsech generalizaci
        for (int i = 0; i < classDiagram.relations.size(); i++)
            if (classDiagram.relations.get(i).getType().compareTo("generalization") == 0)
                relationList.add(classDiagram.relations.get(i));
        //ziskani attributu vsech parentu
        for (int i = 0; i < relationList.size(); i++)
            if (relationList.get(i).getClass2().compareTo(this.getName()) == 0) {
                parent = classDiagram.findClass(relationList.get(i).getClass1());
                parentAttributeList = parent.getParentAttributes(classDiagram);
                parentAttributeList.remove(1);
                parentAttributeList.add(0, new ArrayList<>());
                parentAttributeList.get(0).addAll(parentAttributeList.get(1));
                for (int j = 0; j < attributes.size(); j++){
                    Boolean bool = false;
                    for (int k = 0; k < parentAttributeList.get(0).size() && bool == false; k++) {
                        if (parentAttributeList.get(0).get(k).compareTo(attributes.get(j).getName()) == 0) {
                            bool = true;
                        }
                    }
                    if (! bool) {
                        parentAttributeList.get(0).add(attributes.get(j).getName());
                    }
                }
            }
        if (parentAttributeList.get(0).isEmpty()){
            for (int i = 0; i < attributes.size(); i++) {
                parentAttributeList.get(0).add(attributes.get(i).getName());
            }
        }
        return parentAttributeList;
    }

    /**
     * Získá seznam operací z rodiče (dědičnost)
     * @param classDiagram diagram tříd
     * @return seznam attributů z rodiče a finální seznam atributů
     */
    public List<List<String>> getParentOperations(ClassDiagram classDiagram) {
        List<UMLRelation> relationList = new ArrayList<>();
        List<List<String>> parentOperationList = new ArrayList<>();
        parentOperationList.add(new ArrayList<>());
        parentOperationList.add(new ArrayList<>());
        UMLClass parent;
        //ziskani vsech generalizaci
        for (int i = 0; i < classDiagram.relations.size(); i++)
            if (classDiagram.relations.get(i).getType().compareTo("generalization") == 0)
                relationList.add(classDiagram.relations.get(i));
        //ziskani operace vsech parentu
        for (int i = 0; i < relationList.size(); i++)
            if (relationList.get(i).getClass2().compareTo(this.getName()) == 0) {
                parent = classDiagram.findClass(relationList.get(i).getClass1());
                parentOperationList = parent.getParentOperations(classDiagram);
                parentOperationList.remove(1);
                parentOperationList.add(0, new ArrayList<>());
                parentOperationList.get(0).addAll(parentOperationList.get(1));
                for (int j = 0; j < operations.size(); j++){
                    boolean bool = false;
                    for (int k = 0; k < parentOperationList.get(0).size() && bool == false; k++) {
                        if (parentOperationList.get(0).get(k).compareTo(operations.get(j).getName()) == 0) {
                            bool = true;
                        }
                    }
                    if (! bool) {
                        parentOperationList.get(0).add(operations.get(j).getName());
                    }
                }
            }
        if (parentOperationList.get(0).isEmpty()){
            for (int i = 0; i < operations.size(); i++) {
                parentOperationList.get(0).add(operations.get(i).getName());
            }
        }
        return parentOperationList;
    }
}
