package com.teamc2.travellingsalesbee.gui.elements;

import java.util.ArrayList;

public class ElementMap implements Element {

    private int width;
    private int height;
    private int speed;
    private ArrayList<ElementCell> cells;

    /**
     * Create a new map
     *
     * @param width
     * @param height
     */
    public ElementMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addCell(int x, int y) {
        // TODO: Implement 'addCell' method
    }

    public void setSpeed(int speed) {
        // TODO: Implement 'setSpeed' method
    }

    public ArrayList<ElementCell> getCellsOfType() {
        // TODO: Implement 'getCellsOfType' method
        return null;
    }

    public void resetMap() {
        // TODO: Implement 'resetMap' method
    }
}
