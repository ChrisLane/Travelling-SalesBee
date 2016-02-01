package com.teamc2.travellingsalesbee.gui.elements;

public class ElementSettings implements Element {
    private ElementMap gridMap;

    public ElementSettings(ElementMap gridMap) {
        this.gridMap = gridMap;
    }

    public void setSpeed(int speed) {
        gridMap.setSpeed(speed);
    }

    public void resetMap() {
        gridMap.resetMap();
    }
}
