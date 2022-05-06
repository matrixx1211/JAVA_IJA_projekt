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
	 * seznam sekvenčních diagramů
	 */
	public List<SequenceDiagram> sequenceDiagrams;
	/**
	 * seznam komunikačních diagramů
	 */
	public List<CommunicationDiagram> communicationDiagrams;
	/**
	 * data pro undo
	 */
	public String undoData;

	/**
	 * Konstruktor pro ClassDiagram
	 * @param name název Diagramu
	 */
	public ClassDiagram(String name) {
		super(name);
		this.classes = new ArrayList<UMLClass>();
		this.classifiers = new ArrayList<UMLClassifier>();
		this.relations = new ArrayList<UMLRelation>();
		this.sequenceDiagrams = new ArrayList<SequenceDiagram>();
		this.communicationDiagrams = new ArrayList<CommunicationDiagram>();
		this.undoData = null;
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
			if (obj.getName().compareTo(name) == 0) {
				return obj;
			}
		}
		return null;
	}

	/**
	 * Zjistí zda class daného jména již existuje
	 * @param name jméno classy
	 * @return nalezená classa / null
	 */
	public UMLClass findClass(String name) {
		UMLClass obj;
		for (int i = 0; i < this.classes.size(); i++) {
			obj = this.classes.get(i);
			if (obj.getName().compareTo(name) == 0) {
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
		UMLRelation obj;
		for (int i = 0; i < this.relations.size(); i++) {
			obj = this.relations.get(i);
			if ((obj.getClass1().compareTo(class1) == 0) && (obj.getClass2().compareTo(class2) == 0)) {
				return obj;
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

	/**
	 * Metoda pro vytvoření Sekvenčního diagramu
	 * @param name název diagramu
	 * @return vytvořený digram
	 */
	public SequenceDiagram createSeqDiagram(String name) {
		SequenceDiagram obj;
		for (int i = 0; i < this.sequenceDiagrams.size(); i++) {
			obj = this.sequenceDiagrams.get(i);
			if (obj.name.compareTo(name) == 0) {
				return null;
			}
		}
		SequenceDiagram newObj = new SequenceDiagram(name);
		this.sequenceDiagrams.add(newObj);
		return newObj;
	}

	/**
	 * Metoda pro smazání Sekvenčního diagramu
	 * @param name název diagramu
	 * @return true pokud se povedlo, jinak false
	 */
	public Boolean deleteSeqDiagram(String name) {
		SequenceDiagram obj;
		for (int i = 0; i < this.sequenceDiagrams.size(); i++) {
			obj = this.sequenceDiagrams.get(i);
			if (obj.name.compareTo(name) == 0) {
				this.sequenceDiagrams.remove(obj);
				return true;
			}
		}
		return false;
	}

	/**
	 * Metoda pro nalezeni Sekvenčního diagramu
	 * @param name název diagramu
	 * @return SequenceDiagram, nebo NULL
	 */
	public SequenceDiagram findSeqDiagram(String name) {
		SequenceDiagram obj;
		for (int i = 0; i < this.sequenceDiagrams.size(); i++) {
			obj = this.sequenceDiagrams.get(i);
			if (obj.name.compareTo(name) == 0) {
				return obj;
			}
		}
		return null;
	}

	/**
	 * Metoda pro vytvoření Sekvenčního diagramu
	 * @param name název diagramu
	 * @return vytvořený digram
	 */
	public CommunicationDiagram createCommDiagram(String name) {
		CommunicationDiagram obj;
		for (int i = 0; i < this.communicationDiagrams.size(); i++) {
			obj = this.communicationDiagrams.get(i);
			if (obj.name.compareTo(name) == 0) {
				return null;
			}
		}
		CommunicationDiagram newObj = new CommunicationDiagram(name);
		this.communicationDiagrams.add(newObj);
		return newObj;
	}

	/**
	 * Metoda pro smazání Sekvenčního diagramu
	 * @param name název diagramu
	 * @return true pokud se povedlo, jinak false
	 */
	public Boolean deleteCommDiagram(String name) {
		CommunicationDiagram obj;
		for (int i = 0; i < this.communicationDiagrams.size(); i++) {
			obj = this.communicationDiagrams.get(i);
			if (obj.name.compareTo(name) == 0) {
				this.communicationDiagrams.remove(obj);
				return true;
			}
		}
		return false;
	}

	/**
	 * Metoda pro nalezeni Sekvenčního diagramu
	 * @param name název diagramu
	 * @return SequenceDiagram, nebo NULL
	 */
	public CommunicationDiagram findCommDiagram(String name) {
		CommunicationDiagram obj;
		for (int i = 0; i < this.communicationDiagrams.size(); i++) {
			obj = this.communicationDiagrams.get(i);
			if (obj.name.compareTo(name) == 0) {
				return obj;
			}
		}
		return null;
	}

}
