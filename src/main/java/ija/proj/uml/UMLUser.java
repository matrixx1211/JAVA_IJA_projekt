package ija.proj.uml;

public class UMLUser extends Element{
    double posX;
    double posY;

    public UMLUser(String name, double posX, double posY){
        super(name);
        this.posX = posX;
        this.posY = posY;
    }

    public double getPosX(){
        return posX;
    }

    public double getPosY(){
        return posY;
    }

    public void setPosX(double X){
        posX = X;
    }

    public void setPosY(double Y){
        posY = Y;
    }
}
