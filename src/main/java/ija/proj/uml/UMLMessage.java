package ija.proj.uml;

public class UMLMessage extends Element {
    String type;
    String class1;
    String class2;
    String operation;
    double height;
    String order;

    public UMLMessage(String name, String class1, String class2, String type, String operation) {
        super(name);
        this.type = type;
        this.class1 = class1;
        this.class2 = class2;
        this.operation = operation;
        this.height = 100;
        this.order = "1";
    }

    public String getClass1(){
        return class1;
    }

    public String getClass2(){
        return class2;
    }

    public String getType(){
        return type;
    }

    public String getOperation(){
        return operation;
    }

    public double getHeight(){
        return height;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public String getOrder(){
        return order;
    }

    public void setOrder(String order){
        this.order = order;
    }
}
