package ija.proj.uml;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram extends Element {
	public List<UMLClass> classes;
	List<UMLClassifier> classifiers;

	public ClassDiagram(String name) {
		super(name);
		this.classes = new ArrayList<UMLClass>();
		this.classifiers = new ArrayList<UMLClassifier>();
	}

	public UMLClass createClass(String name, Integer id, Boolean isInterface) {

		UMLClass obj;
		for (int i = 0; i < this.classes.size(); i++) {
			obj = this.classes.get(i);
			if (obj.name.compareTo(name) == 0) {
				return null;
			}
		}
		UMLClass newObj = new UMLClass(name, id, isInterface);
		this.classes.add(newObj);
		this.classifiers.add(newObj);
		return newObj;
	}


	public void changeClassName(String oldName, String newName) {
		UMLClass objClass;
		//!TODO dodělat toto check 2x cyklus not gut
		for (int i = 0; i < this.classes.size(); i++) {
			objClass = this.classes.get(i);
			if (objClass.name.compareTo(newName) == 0) {
				return;
			}
		}

		for (int i = 0; i < this.classes.size(); i++) {
			objClass = this.classes.get(i);
			if (objClass.name.compareTo(oldName) == 0) {
				objClass.name = newName;
			}
		}

		UMLClassifier objClassifier;
		for (int i = 0; i < this.classifiers.size(); i++) {
			objClassifier = this.classifiers.get(i);
			if (objClassifier.name.compareTo(newName) == 0) {
				return;
			}
		}

		for (int i = 0; i < this.classifiers.size(); i++) {
			objClassifier = this.classifiers.get(i);
			if (objClassifier.name.compareTo(oldName) == 0) {
				objClassifier.name = newName;
			}
		}
	}

	public UMLClass getObject(String name) {
		UMLClass obj;
		for (int i = 0; i < this.classes.size(); i++) {
			obj = this.classes.get(i);
			if (obj.name.compareTo(name) == 0) {
				return obj;
			}
		}
		return null;
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
