package ija.proj.uml;

public class UMLActivation extends Element{
    String class1;
    double posY;
    double len;

    public UMLActivation(String name, String class1, double posY, double len){
        super(name);
        this.class1 = class1;
        this.posY = posY;
        this.len = len;
    }

    public String getClass1(){
        return class1;
    }
    public void setPosY(double Y){
        posY = Y;
    }

    public double getPosY(){
        return posY;
    }

    public double getLen(){
        return len;
    }
}

