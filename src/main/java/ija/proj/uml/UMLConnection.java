package ija.proj.uml;

public class UMLConnection extends Element {
    String class1;
    String class2;

    public UMLConnection(String name, String class1, String class2){
        super(name);
        this.class1 = class1;
        this.class2 = class2;
    }

    public String getClass1(){
        return class1;
    }

    public String getClass2(){
        return class2;
    }
}
