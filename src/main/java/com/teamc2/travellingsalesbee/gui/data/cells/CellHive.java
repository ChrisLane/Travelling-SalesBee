package com.teamc2.travellingsalesbee.gui.data.cells;

import javax.imageio.ImageIO;
import java.awt.*;
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
	public Image getImage() {
		Image image = null;
		try {
			image = ImageIO.read(this.getClass().getResource("/assets/icons/Hive.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}