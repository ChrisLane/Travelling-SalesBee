package com.teamc2.travellingsalesbee.gui.elements;

import java.util.ArrayList;

public class ElementBeeTable implements Element {
	private ElementMap map;

	public ElementBeeTable(ElementMap map) {
		this.map = map;
	}

	public void fillTable() {
		ArrayList<ElementCell> cells = map.getCells();
		// TODO: implement 'fillTable' method
	}
}
