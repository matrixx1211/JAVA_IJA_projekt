/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci Třídy UMLOperation
 */

package ija.proj.uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Třída pro operace (metody) tříd a rozhraní
 */
public class UMLOperation extends UMLAttribute {
    /**
     *List pro vsupní argumenty operace
     */
    List<UMLAttribute> args;

    /**
     * Konstruktor pro operace
     * @param name název operace
     * @param type návratový typ
     * @param accessibility modifikátor přístupu
     */
    public UMLOperation (String name, UMLClassifier type, String accessibility) {
        super(name, type, accessibility);
        args = new ArrayList<UMLAttribute>();
    }

    /**
     * Vytvoření operace
     * @param name jméno operace
     * @param type návratový typ operace
     * @param accessibility modifikátor přístupu
     * @param args seznam vstupních argumentů
     * @return vytvořená operace
     */
    public static UMLOperation create(String name, UMLClassifier type, String accessibility, UMLAttribute... args) {
        UMLOperation obj = new UMLOperation(name, type, accessibility);
        
        for (UMLAttribute arg : args) {
            obj.addArgument(arg);
        };
        return obj;
    }

    /**
     * Přidání vstupního argumentu do operace
     * @param arg přidávaný argument
     * @return true pokud byl přidán, false pokud již existuje
     */
    public boolean addArgument(UMLAttribute arg) {
        for (int i = 0; i < this.args.size(); i++) {
            if (this.args.get(i).getName().compareTo(arg.getName()) == 0) {
                return false;
            }
        }
        this.args.add(arg);
        return true;
    }

    /**
     * Získání listu vstupních argumentů operace
     * @return List vstupních argumentů
     */
    public List<UMLAttribute> getArguments() {
        return Collections.unmodifiableList(this.args);
    }
}
