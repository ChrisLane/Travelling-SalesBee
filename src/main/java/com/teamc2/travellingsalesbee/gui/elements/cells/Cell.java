package com.teamc2.travellingsalesbee.gui.elements.cells;

import java.awt.image.BufferedImage;

public interface Cell {

	CellType getType();

	BufferedImage getImage();

	enum CellType {EMPTY, FLOWER, HIVE}
}
