package ija.proj.uml;

import ija.proj.App;

public class Element{
	String name;

	public Element(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void rename(String newName) {
		this.name = newName;
	}
}
