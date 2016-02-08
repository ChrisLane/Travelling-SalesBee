package com.teamc2.travellingsalesbee.gui.elements.cells;

import java.awt.image.BufferedImage;

public interface Cell {
	
	String EMPTY = "Empty";
	String FLOWER = "Flower";
	String HIVE = "Hive";

	String getType();

	BufferedImage getImage();
}
