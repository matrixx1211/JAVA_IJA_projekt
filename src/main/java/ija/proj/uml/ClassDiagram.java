package ija.proj.uml;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram extends Element {
	public List<UMLClass> classes;
	public List<UMLClassifier> classifiers;
	public List<UMLRelation> relations;

	public ClassDiagram(String name) {
		super(name);
		this.classes = new ArrayList<UMLClass>();
		this.classifiers = new ArrayList<UMLClassifier>();
		this.relations = new ArrayList<UMLRelation>();
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


	public Boolean changeClassName(String oldName, String newName) {
		UMLClass classObj;
		UMLClassifier classifierObj;

		// kontrola jestli můžu změnit jméno
		for (int i = 0; i < this.classes.size(); i++) {
			classObj = this.classes.get(i);
			if (classObj.name.compareTo(newName) == 0) {
				return false;
			}
		}
		for (int i = 0; i < this.classifiers.size(); i++) {
			classifierObj = this.classifiers.get(i);
			if (classifierObj.name.compareTo(newName) == 0) {
				return false;
			}
		}

		// kontrola proběhla může se stát změna jména
		for (int i = 0; i < this.classes.size(); i++) {
			classObj = this.classes.get(i);
			if (classObj.name.compareTo(oldName) == 0) {
				classObj.name = newName;
			}
		}
		for (int i = 0; i < this.classifiers.size(); i++) {
			classifierObj = this.classifiers.get(i);
			if (classifierObj.name.compareTo(oldName) == 0) {
				classifierObj.name = newName;
			}
		}

		return true;
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
	//relations
	public UMLRelation createRelation(String name, String type, String class1, String class2, String class3) {
		UMLRelation obj;
		for (int i = 0; i < this.relations.size(); i++) {
			obj = this.relations.get(i);
			if ((obj.class1 == class1 && obj.class2 == class2) || (obj.class1 == class2 && obj.class2 == class1)) {
				//TODO add into bottom bar "relation already exists"
				return null;
			}
		}
		obj = new UMLRelation(name, type, class1, class2, class3);
		this.relations.add(obj);
		return obj;
		//TODO draw relation
	}

	public UMLRelation findRelation(String class1, String class2){
		for (int i = 0; i < this.relations.size(); i++) {
			if ((this.relations.get(i).getClass1().compareTo(class1) == 0) && (this.relations.get(i).getClass2().compareTo(class2) == 0)) {
				return this.relations.get(i);
			}
		}
		return null;
	}

	public List<UMLRelation> findAllRelationsOfClass(String class1){
		List<UMLRelation> relations = new ArrayList<>();
		for (int i = 0; i < this.relations.size(); i++) {
			if ((this.relations.get(i).getClass1().compareTo(class1) == 0) || (this.relations.get(i).getClass2().compareTo(class1) == 0)) {
				relations.add(this.relations.get(i));
			}
		}
		return relations;
	}
}
