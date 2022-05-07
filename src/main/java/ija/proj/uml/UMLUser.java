package ija.proj.uml;

/**
 * Třída, která slouží pro vytvoření uživatele v komunikačním diagramu.
 */
public class UMLUser extends Element{
    double posX;
    double posY;

    /**
     * Konstruktor pro uživatele
     * @param name jméno
     * @param posX souřadnice na ose X
     * @param posY souřadnice na ose Y
     */
    public UMLUser(String name, double posX, double posY){
        super(name);
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Získá souřadnice na ose X
     * @return souřadnice na ose X
     */
    public double getPosX(){
        return posX;
    }

    /**
     * Získá souřadnice na ose Y
     * @return souřadnice na ose Y
     */
    public double getPosY(){
        return posY;
    }

    /**
     * Nastaví souřadnice na ose X
     * @param X souřadnice na ose X
     */
    public void setPosX(double X){
        posX = X;
    }

    /**
     * Nastaví souřadnice na ose Y
     * @param Y souřadnice na ose Y
     */
    public void setPosY(double Y){
        posY = Y;
    }
}
