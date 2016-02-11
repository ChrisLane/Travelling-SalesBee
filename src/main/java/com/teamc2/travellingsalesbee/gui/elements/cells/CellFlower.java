package com.teamc2.travellingsalesbee.gui.elements.cells;

import com.sun.javafx.geom.Point2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CellFlower extends Cell {

	public CellFlower(double x, double y) {
		super(x, y);
	}

	public CellType getType() {
		return CellType.FLOWER;
	}

	@Override
	public BufferedImage getImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("target/classes/icons/Flower.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}