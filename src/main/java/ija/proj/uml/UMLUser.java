package ija.proj.uml;

public class UMLUser extends Element{
    double posX;
    double posY;

    public UMLUser(String name, double posX, double posY){
        super(name);
        this.posX = posX;
        this.posY = posY;
    }
}
