package ija.proj.uml;

public class UMLAttribute extends Element {
    UMLClassifier type;

    public UMLAttribute(String name, UMLClassifier type) {
        super(name);
        this.type = type;
    }

    public UMLClassifier getType() {
        return this.type;
    }

    public String toString() {
        return String.format("%s:%s", this.name, this.type);
    }
}
