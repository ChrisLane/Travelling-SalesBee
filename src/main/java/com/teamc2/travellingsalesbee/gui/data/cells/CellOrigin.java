package com.teamc2.travellingsalesbee.gui.data.cells;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class CellOrigin extends Cell {

	/**
	 * Create a new origin cell
	 */
	public CellOrigin() {
		//
	}

	/**
	 * Create a new origin cell
	 *
	 * @param x X position of cell
	 * @param y Y position of cell
	 */
	public CellOrigin(double x, double y) {
		super(x, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CellType getType() {
		return CellType.ORIGIN;
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
					image = ImageIO.read(getClass().getResource("/assets/icons/Hive.png"));
					break;
				case ANT:
					image = ImageIO.read(getClass().getResource("/assets/icons/AntHill.png"));
					break;
				case NEARESTNEIGHBOUR:
					image = ImageIO.read(getClass().getResource("/assets/icons/MailOffice.png"));
					break;
				default:
					image = ImageIO.read(getClass().getResource("/assets/icons/Anchor.png"));
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}