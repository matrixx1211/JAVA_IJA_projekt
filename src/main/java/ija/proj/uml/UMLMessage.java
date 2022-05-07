/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci zpráv.
 */

package ija.proj.uml;

/**
 * Třída, která slouží pro vytváření zpráv v diagramech.
 */
public class UMLMessage extends Element {
    String type;
    String class1;
    String class2;
    String operation;
    double height;
    String order;

    /**
     * Konstruktor pro zprávu
     * @param name identifikátor zprávy
     * @param class1 jméno první třídy
     * @param class2 jméno druhé třídy
     * @param type typ zprávy
     * @param operation operace
     */
    public UMLMessage(String name, String class1, String class2, String type, String operation) {
        super(name);
        this.type = type;
        this.class1 = class1;
        this.class2 = class2;
        this.operation = operation;
        this.height = 100;
        this.order = "1";
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
     * Získá typ zprávy
     * @return typ zprávy
     */
    public String getType(){
        return type;
    }

    /**
     * Získá operaci
     * @return operaci
     */
    public String getOperation(){
        return operation;
    }

    /**
     * Získá výšku
     * @return výška
     */
    public double getHeight(){
        return height;
    }

    /**
     * Nastaví výšku
     * @param height výška
     */
    public void setHeight(double height){
        this.height = height;
    }

    /**
     * Získá pořadí
     * @return pořadí
     */
    public String getOrder(){
        return order;
    }

    /**
     * Nastaví pořadí
     */
    public void setOrder(String order){
        this.order = order;
    }
}
