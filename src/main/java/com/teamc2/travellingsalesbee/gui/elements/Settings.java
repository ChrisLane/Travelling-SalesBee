package com.teamc2.travellingsalesbee.gui.elements;

import javax.swing.*;

public class Settings extends JPanel {
	private Map gridMap;

	public Settings(Map gridMap) {
		this.gridMap = gridMap;
	}

	public void setSpeed(int speed) {
		gridMap.setSpeed(speed);
	}

	public void resetMap() {
		gridMap.setMap();
	}
}
