package com.teamc2.travellingsalesbee.gui.elements.cells;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CellHive extends Cell {

	public CellHive(double x, double y) {
		super(x, y);
	}

	@Override
	public CellType getType() {
		return CellType.HIVE;
	}

	@Override
	public BufferedImage getImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("target/classes/icons/Hive.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}