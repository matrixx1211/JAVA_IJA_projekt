/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci atributů.
 */

package ija.proj.uml;

/**
 * Třída, která slouží pro vytváření atributů.
 */
public class UMLAttribute extends Element {
    /**
     * modifikátor přistupu
     */
    String accessibility;
    /**
     * typ attributu
     */
    UMLClassifier type;

    /**
     * Konstruktor pro attribut
     * @param name název attributu
     * @param type typ attributu
     * @param accessibility přístupová metoda attributu
     */
    public UMLAttribute(String name, UMLClassifier type, String accessibility) {
        super(name);
        this.type = type;
        this.accessibility = accessibility;
    }

    /**
     * Zjistí přístupovou metodu
     * @return přístupová metoda
     */
    public String getAccessibility() {
        return this.accessibility;
    }

    /**
     * Zjistí typ attributu
     * @return typ
     */
    public UMLClassifier getType() {
        return this.type;
    }

    /**
     * Vypíše attribut jako formátovaný řetězec
     * @return řetězec název:typ
     */
    public String toString() {
        return String.format("%s:%s", this.name, this.type.getName());
    }
}
