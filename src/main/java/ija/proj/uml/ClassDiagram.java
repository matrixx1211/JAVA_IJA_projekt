/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci Třídy ClassDiagram
 */

package ija.proj.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída pro ClassDiagram
 */
public class ClassDiagram extends Element {
	/**
	 * seznam tříd a rozhraní
	 */
	public List<UMLClass> classes;
	/**
	 * seznam classifierů
	 */
	public List<UMLClassifier> classifiers;
	/**
	 * seznam relací
	 */
	public List<UMLRelation> relations;

	/**
	 * Konstruktor pro ClassDiagram
	 * @param name název Diagramu
	 */
	public ClassDiagram(String name) {
		super(name);
		this.classes = new ArrayList<UMLClass>();
		this.classifiers = new ArrayList<UMLClassifier>();
		this.relations = new ArrayList<UMLRelation>();
	}

	/**
	 * Metoda pro vytvoření Třídy/Rozhraní v ClassDiagramu
	 * @param name název třídy
	 * @param id počítadlo tříd
	 * @param isInterface Rozhraní (true) / Třída (false)
	 * @return vytvořenou třídu / rozhraní
	 */
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

	/**
	 * mazání třídy / rozhraní
	 * @param obj mazaná třída / rozhraní
	 */
	public void deleteClass(UMLClass obj) {
		this.classes.remove(obj);
		this.classifiers.remove(obj);
	}

	/**
	 * Provede změnu názvu třídy / rozhraní
	 * @param oldName staré jméno
	 * @param newName nové jméno
	 * @return true pokud se povedlo, jinak false
	 */
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

	/**
	 * Najde třídu / rozhraní podle názvu
	 * @param name název hledaného objektu
	 * @return hledaný objekt / null
	 */
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

	/**
	 * Přidá nový classifier daného jména, pokud již neexistuje
	 * @param name jméno vytvářeného classifieru
	 * @return vytvořený classifier / null
	 */
	public UMLClassifier classifierForName(String name) {
		UMLClassifier obj = findClassifier(name);
		if (obj == null)
		{
			obj = UMLClassifier.forName(name);
			classifiers.add(obj);
		}
		return obj;
	}

	/**
	 * Zjistí zda classifier daného jména již existuje
	 * @param name jméno classifieru
	 * @return nalezený classifier / null
	 */
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

	/**
	 * Vytvoření relace
	 * @param name jméno relace
	 * @param type typ relace
	 * @param class1 první prvek
	 * @param class2 druhý prvek
	 * @param class3 třetí prvek (pouze pro associaci - mezitabulka)
	 * @return vytvořená relace
	 */
	public UMLRelation createRelation(String name, String type, String class1, String class2, String class3) {
		UMLRelation obj;
		for (int i = 0; i < this.relations.size(); i++) {
			obj = this.relations.get(i);
			if (obj.class1.compareTo(class1) == 0 && obj.class2.compareTo(class2) == 0) {
				//TODO add into bottom bar "relation already exists"
				return null;
			}
		}
		obj = new UMLRelation(name, type, class1, class2, class3);
		this.relations.add(obj);
		return obj;
		//TODO draw relation
	}

	/**
	 * Nalezení relace (unikátní kombinace prvních dvou prvků)
	 * @param class1 název prvního prvku relace
	 * @param class2 název druhého prvku relace
	 * @return nalezená relace / null
	 */
	public UMLRelation findRelation(String class1, String class2){
		for (int i = 0; i < this.relations.size(); i++) {
			if ((this.relations.get(i).getClass1().compareTo(class1) == 0) && (this.relations.get(i).getClass2().compareTo(class2) == 0)) {
				return this.relations.get(i);
			}
		}
		return null;
	}

	/**
	 * Nalezení všech relací obsahující danou třídu
	 * @param class1 název třídy
	 * @return seznam relací
	 */
	public List<UMLRelation> findAllRelationsOfClass(String class1){
		List<UMLRelation> relations = new ArrayList<>();
		for (int i = 0; i < this.relations.size(); i++) {
			if ((this.relations.get(i).getClass1().compareTo(class1) == 0) || (this.relations.get(i).getClass2().compareTo(class1) == 0) || (this.relations.get(i).getClass3().compareTo(class1) == 0)) {
				relations.add(this.relations.get(i));
			}
		}
		return relations;
	}
}
