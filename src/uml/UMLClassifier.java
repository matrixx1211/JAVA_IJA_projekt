package uml;

public class UMLClassifier extends Element {
	boolean isUserDefined;

	public UMLClassifier(String name) {
		super(name);
		this.isUserDefined = false;
	}

	public UMLClassifier(String name, boolean isUserDefined) {
		super(name);
		this.isUserDefined = isUserDefined;
	}

	public static UMLClassifier forName(String name) {
		UMLClassifier obj = new UMLClassifier(name);
		return obj;
	}

	public boolean isUserDefined() {
		return isUserDefined;
	}

	public String toString() {
		return String.format("%s(%s)", this.name, this.isUserDefined);
	}
}
