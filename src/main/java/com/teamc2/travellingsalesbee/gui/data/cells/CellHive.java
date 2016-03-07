package com.teamc2.travellingsalesbee.gui.data.cells;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.teamc2.travellingsalesbee.gui.view.AlgorithmType;

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
			switch(type){
			case BEE:
				image = ImageIO.read(this.getClass().getResource("/assets/icons/Hive.png"));
				break;
			case ANT:
				image = ImageIO.read(this.getClass().getResource("/assets/icons/AntHill.jpg"));
				break;
			case NEARESTNEIGHBOUR:
				image = ImageIO.read(this.getClass().getResource("/assets/icons/SortingOffice.jpg"));
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