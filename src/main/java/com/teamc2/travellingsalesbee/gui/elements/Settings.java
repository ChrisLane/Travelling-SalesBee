package com.teamc2.travellingsalesbee.gui.elements;

public class Settings {
	private Map gridMap;

	public Settings(Map gridMap) {
		this.gridMap = gridMap;
	}

	public void setSpeed(int speed) {
		gridMap.setSpeed(speed);
	}

	public void resetMap() {
		gridMap.resetMap();
	}
}
