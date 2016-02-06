package com.teamc2.travellingsalesbee.gui.elements.cells;

public interface Cell {
	String EMPTY = "Empty";
	String FLOWER = "Flower";
	String Hive = "Hive";

	String getType();

	String getImage();
	
	int getX();
	
	int getY();
}
