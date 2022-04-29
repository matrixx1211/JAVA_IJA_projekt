package ija.proj.uml;

public class UMLMessage extends Element {
    Character type;
    UMLClass class1;
    UMLClass class2;
    UMLOperation operation;

    public UMLMessage(String name, UMLClass class1, UMLClass class2, Character type, UMLOperation operation) {
        super(name);
        this.type = type;
        this.class1 = class1;
        this.class2 = class2;
        this.operation = operation;
    }

}
