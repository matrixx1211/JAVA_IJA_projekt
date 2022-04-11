package ija.proj.uml;

public class UMLRelation extends Element {
	String type;
	String class1;
	String class2;
	String class3;

	public UMLRelation(String name,String type,String class1, String class2, String class3) {
		super(name);
		this.type = type;
		this.class1 = class1;
		this.class2 = class2;
		this.class3 = class3;
	}

	public String getType() {
		return type;
	}

	public String getClass1() {
		return class1;
	}

	public String getClass2() {
		return class2;
	}

	public String getClass3() {
		return class3;
	}
}
