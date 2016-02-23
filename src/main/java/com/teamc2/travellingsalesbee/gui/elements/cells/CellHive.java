package com.teamc2.travellingsalesbee.gui.elements.cells;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CellHive extends Cell {

	/**
	 * Create a new hive cell
	 *
	 * @param x X position of cell
	 * @param y Y position of cell
	 */
	public CellHive(double x, double y) {
		super(x, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CellType getType() {
		return CellType.HIVE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BufferedImage getImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(this.getClass().getResource("/assets/icons/Hive.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}