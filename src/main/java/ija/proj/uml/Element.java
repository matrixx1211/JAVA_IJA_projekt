/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci elementu.
 */

package ija.proj.uml;

import ija.proj.App;

/**
 * Třída pro Element (nadtřída ostatních UML prvků)
 */
public class Element{
	/**
	 * název Elementu
	 */
	String name;

	/**
	 * Konstruktor pro Element
	 * @param name název elementu
	 */
	public Element(String name) {
		this.name = name;
	}

	/**
	 * Získá název elementu
	 * @return název
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Změní jméno elementu
	 * @param newName nové jméno elementu
	 */
	public void rename(String newName) {
		this.name = newName;
	}
}
