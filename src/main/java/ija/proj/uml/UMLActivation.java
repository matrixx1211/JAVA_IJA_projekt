/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci aktivací.
 */

package ija.proj.uml;

/**
 * Třída, která slouží pro vytvoření aktivace v sekvenčním diagramu.
 */
public class UMLActivation extends Element{
    String class1;
    double posY;
    double len;

    /**
     * Konstruktor pro aktivaci
     * @param name identifikátor aktivace
     * @param class1 jméno třídy
     * @param posY souřadnice na Y
     * @param len výška aktivace
     */
    public UMLActivation(String name, String class1, double posY, double len){
        super(name);
        this.class1 = class1;
        this.posY = posY;
        this.len = len;
    }

    /**
     * Získá jméno třídy
     * @return jméno třídy
     */
    public String getClass1(){
        return class1;
    }

    /**
     * Nastaví souřadnice na ose Y
     * @param Y souřadnice na ose Y
     */
    public void setPosY(double Y){
        posY = Y;
    }

    /**
     * Získá souřadnice na ose Y
     * @return souřadnice na ose Y
     */
    public double getPosY(){
        return posY;
    }

    /**
     * Získá výšku aktivace
     * @return výška aktivace
     */
    public double getLen(){
        return len;
    }
}

