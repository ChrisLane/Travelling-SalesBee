package com.teamc2.travellingsalesbee.gui.elements.cells;

import java.awt.image.BufferedImage;

public interface Cell {

	enum CellType {EMPTY, FLOWER, HIVE};

	CellType getType();

	BufferedImage getImage();
}
