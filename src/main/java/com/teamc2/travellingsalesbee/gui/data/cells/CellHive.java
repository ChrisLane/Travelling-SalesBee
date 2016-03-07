package com.teamc2.travellingsalesbee.gui.data.cells;

import com.teamc2.travellingsalesbee.gui.view.AlgorithmType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class CellHive extends Cell {

	/**
	 * Create a new hive cell
	 */
	public CellHive() {
	}

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
	public Image getImage(AlgorithmType type) {
		Image image = null;
		try {
			switch (type) {
				case BEE:
					image = ImageIO.read(this.getClass().getResource("/assets/icons/Hive.png"));
					break;
				case ANT:
					image = ImageIO.read(this.getClass().getResource("/assets/icons/AntHill.png"));
					break;
				case NEARESTNEIGHBOUR:
					image = ImageIO.read(this.getClass().getResource("/assets/icons/MailOffice.png"));
					break;
				default:
					image = ImageIO.read(this.getClass().getResource("/assets/icons/Hive.png"));
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}