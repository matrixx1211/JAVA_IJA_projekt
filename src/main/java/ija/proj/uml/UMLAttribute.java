package ija.proj.uml;

public class UMLAttribute extends Element {
    String accessibility;
    UMLClassifier type;

    public UMLAttribute(String name, UMLClassifier type, String accessibility) {
        super(name);
        this.type = type;
        this.accessibility = accessibility;
    }

    public String getAccessibility() {
        return this.accessibility;
    }

    public UMLClassifier getType() {
        return this.type;
    }

    public String toString() {
        return String.format("%s:%s", this.name, this.type);
    }
}
