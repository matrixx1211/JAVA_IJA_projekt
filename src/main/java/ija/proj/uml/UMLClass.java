package ija.proj.uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UMLClass extends UMLClassifier {
    public Boolean isAbstract;
    public List<UMLAttribute> attributes;
    public List<UMLOperation> operations;
    Integer id;
    public Boolean isInterface;
    double x;
    double y;

    public UMLClass(String name, Integer id, Boolean isInterface) {
        super(name);
        this.isUserDefined = true;
        this.isAbstract = false;
        this.attributes = new ArrayList<UMLAttribute>();
        this.operations = new ArrayList<UMLOperation>();
        this.id = id;
        this.isInterface = isInterface;
        this.x = 0.0;
        this.y = 0.0;
    }

    public Boolean isAbstract() {
        return this.isAbstract;
    }

    public Boolean isInterface() {
        return this.isInterface;
    }

    public void setAbstract(Boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public Boolean addAttribute(UMLAttribute attr) {
        for (int i = 0; i < this.attributes.size(); i++) {
            if (this.attributes.get(i).getName().compareTo(attr.getName()) == 0) {
                return false;
            }
        }
        this.attributes.add(attr);
        return true;
    }

    public int getAttrPosition(UMLAttribute attr) {
        for (int i = 0; i < this.attributes.size(); i++) {
            if (this.attributes.get(i).equals(attr))
                return i;
        }
        return -1;
    }

    public int moveAttrAtPosition(UMLAttribute attr, int pos) {
        if (this.attributes.contains(attr)) {
            this.attributes.remove(attr);
            this.attributes.add(pos, attr);
            return pos;
        }
        return -1;
    }

    public int getId() {
        return this.id;
    }

    public Boolean removeAttr(UMLAttribute attr) {
        for (int i = 0; i < this.attributes.size(); i++) {
            if (this.attributes.get(i) == attr) {
                this.attributes.remove(attr);
                return true;
            }
        }
        return false;
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    public List<UMLAttribute> getAttributes() {
        return Collections.unmodifiableList(this.attributes);
    }
}
