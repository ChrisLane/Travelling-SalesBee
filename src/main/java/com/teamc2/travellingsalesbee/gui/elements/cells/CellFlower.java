package com.teamc2.travellingsalesbee.gui.elements.cells;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CellFlower extends Cell {

	public CellFlower(double x, double y) {
		super(x, y);
	}

	@Override
	public CellType getType() {
		return CellType.FLOWER;
	}

	@Override
	public BufferedImage getImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(this.getClass().getResource("/assets/icons/Flower.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}