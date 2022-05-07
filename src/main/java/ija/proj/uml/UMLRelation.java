/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci relaci.
 */

package ija.proj.uml;

/**
 * Třída, která slouží k vytvoření relace.
 */
public class UMLRelation extends Element {
	/**
	 * typ relace
	 */
	String type;
	/**
	 * první člen relace
	 */
	String class1;
	/**
	 * druhý člen relace
	 */
	String class2;
	/**
	 * třetí člen relace (pouze u asociace - mezitabulka)
	 */
	String class3;

	/**
	 * Konstruktor pro relace
	 * @param name jméno relace
	 * @param type typ relace
	 * @param class1 první člen relace
	 * @param class2 druhý člen relace
	 * @param class3 třetí člen relace (pouze u asociace - mezitabulka)
	 */
	public UMLRelation(String name,String type,String class1, String class2, String class3) {
		super(name);
		this.type = type;
		this.class1 = class1;
		this.class2 = class2;
		this.class3 = class3;
	}

	/**
	 * metoda getType
	 * @return typ relace
	 */
	public String getType() {
		return type;
	}

	/**
	 * metoda getClass1
	 * @return název prvního člena relace
	 */
	public String getClass1() {
		return class1;
	}

	/**
	 * metoda getClass2
	 * @return název druhého člena relace
	 */
	public String getClass2() {
		return class2;
	}

	/**
	 * metoda getClass3
	 * @return název třetího člena relace
	 */
	public String getClass3() {
		return class3;
	}

	/**
	 * metoda setClass1
	 * @param newName nové jméno
	 */
	public void setClass1(String newName) {
		class1 = newName;
	}

	/**
	 * metoda setClass2
	 * @param newName nové jméno
	 */
	public void setClass2(String newName) {
		class2 = newName;
	}

	/**
	 * metoda setClass3
	 * @param newName nové jméno
	 */
	public void setClass3(String newName) {
		class3 = newName;
	}
}
