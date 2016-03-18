package com.teamc2.travellingsalesbee.gui.data.cells;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class CellNode extends Cell {

	/**
	 * Create a new flower cell
	 */
	public CellNode() {
		//
	}

	/**
	 * Create a new flower cell
	 *
	 * @param x X position of cell
	 * @param y Y position of cell
	 */
	public CellNode(double x, double y) {
		super(x, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CellType getType() {
		return CellType.NODE;
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
					image = ImageIO.read(getClass().getResource("/assets/icons/Flower.png"));
					break;
				case ANT:
					image = ImageIO.read(getClass().getResource("/assets/icons/Fruit.png"));
					break;
				case NEARESTNEIGHBOUR:
					image = ImageIO.read(getClass().getResource("/assets/icons/House.png"));
					break;
				default:
					image = ImageIO.read(getClass().getResource("/assets/icons/X.png"));
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}