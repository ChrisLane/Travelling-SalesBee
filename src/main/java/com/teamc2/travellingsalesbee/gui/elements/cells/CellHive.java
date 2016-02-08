package com.teamc2.travellingsalesbee.gui.elements.cells;

import com.sun.javafx.geom.Point2D;

import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class CellHive extends Point2D implements Cell {

	public CellHive(int x, int y) {
		super(x, y);
	}

	public String getType() {
		return Cell.HIVE;
	}

	public BufferedImage getImage() {
		//return hive image location here
		return null;
	}

}