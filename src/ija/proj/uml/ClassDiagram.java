package ija.proj.uml;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram extends Element {
	List<UMLClass> classes;
	List<UMLClassifier> classifiers;

	public ClassDiagram(String name) {
		super(name);
		this.classes = new ArrayList<UMLClass>();
		this.classifiers = new ArrayList<UMLClassifier>();
	}

	public UMLClass createClass(String name) {
		UMLClass obj = new UMLClass(name);
		if (this.classes.contains(obj)) {
			return null;
		} else {
			this.classes.add(obj);
			this.classifiers.add(obj);
			return obj;
		}
	}

	public UMLClassifier classifierForName(String name) {
		UMLClassifier obj = findClassifier(name);
		if (obj == null)
		{
			obj = UMLClassifier.forName(name);
			classifiers.add(obj);
		}
		return obj;
	}

	public UMLClassifier findClassifier(String name) {
		UMLClassifier obj;
		for (int i = 0; i < this.classifiers.size(); i++) {
			obj = this.classifiers.get(i);
			if (obj.name == name) {
				return obj;
			}
		}
		return null;
	}
}
