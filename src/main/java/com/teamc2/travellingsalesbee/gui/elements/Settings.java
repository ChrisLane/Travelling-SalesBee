package com.teamc2.travellingsalesbee.gui.elements;

import javax.swing.*;

public class Settings extends JPanel {
	private final Map map;

	/**
	 * Create a settings control object
	 *
	 * @param map Map to manipulate
	 */
	public Settings(Map map) {
		this.map = map;
	}

	/**
	 * Set the speed of the visualiser
	 *
	 * @param speed Speed to be set for visualiser
	 */
	public void setSpeed(int speed) {
		map.setSpeed(speed);
	}

	/**
	 * Reset the map
	 */
	public void resetMap() {
		map.setMap();
	}
}
