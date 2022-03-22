package uml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UMLOperation extends UMLAttribute {
    List<UMLAttribute> args;

    public UMLOperation (String name, UMLClassifier type) {
        super(name, type);
        args = new ArrayList<UMLAttribute>();
    }

    public static UMLOperation create(String name, UMLClassifier type, UMLAttribute... args) {
        UMLOperation obj = new UMLOperation(name, type);
        
        for (UMLAttribute arg : args) {
            obj.addArgument(arg);
        };
        return obj;
    }

    public boolean addArgument(UMLAttribute arg) {
        if (!this.args.contains(arg))
            if (this.args.add(arg))
                return true;
        return false;
    }

    public List<UMLAttribute> getArguments() {
        return Collections.unmodifiableList(this.args);
    }
}
