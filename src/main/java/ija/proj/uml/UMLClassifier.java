/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci Třídy UMLClassifier
 */

package ija.proj.uml;

/**
 * Třída pro classifier nebo-li typ.
 */
public class UMLClassifier extends Element {
	/**
	 * Proměnná udávající, jestli byl typ nadefinován uživatelem.
	 */
	Boolean isUserDefined;

	/**
	 * Konstruktor pro classifier/typ
	 *
	 * @param name název typu
	 */
	public UMLClassifier(String name) {
		super(name);
		this.isUserDefined = false;
	}

	/**
	 * Konstruktor pro classifier/typ
	 *
	 * @param name název typu
	 * @param isUserDefined zadal uživatel?
	 */
	public UMLClassifier(String name, Boolean isUserDefined) {
		super(name);
		this.isUserDefined = isUserDefined;
	}

	/**
	 * Vytvoří nový classifier/typ jako uživatelsky nedefinovaný.
	 *
	 * @param name název typu
	 * @return objekt classifieru/typu se zadaným jménem
	 */
	public static UMLClassifier forName(String name) {
		UMLClassifier obj = new UMLClassifier(name);
		return obj;
	}

	/**
	 * Vrací isUserDefined hodnotu.
	 * @return hodnotu, jestli je uživatelsky definovaný
	 */
	public boolean isUserDefined() {
		return this.isUserDefined;
	}

	/**
	 * Vypisuje informace o classifieru/typu ve formátu "%s(%s)".
	 *
	 * @return řetezec s názvem typu a prav. hod. uživatelsky definované
	 */
	public String toString() {
		return String.format("%s(%s)", this.name, this.isUserDefined);
	}
}
