package com.teamc2.travellingsalesbee.gui.elements;

import javafx.geometry.Point2D;

public class ElementCell extends Point2D implements Element{
	
	private static int CELL_TYPE = 0; //0 for empty cell, 1 for hive, 2 for flower/node
	private int x; //x position of Cell
	private int y; //y position of Cell
	
    /**
     * Creates a new instance of {@code Point2D}.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public ElementCell(double x, double y, int type) {
        super(x, y);
        this.x = x; //x position of object
        this.y = y; //y position of object
        
        CELL_TYPE = type; //type of object (see above for types)
    }
    
    //GET METHODS
    public int getLoc() {
    	return (x, y);
    }
    
    public int getType() {
    	return CELL_TYPE; //We could change this to return a string later on
    }
    
    public String getType() {
    	if(CELL_TYPE == 0) {
    		return "Empty Cell";
    	} else if(CELL_TYPE == 1) {
    		return "Hive";
    	} else if(CELL_TYPE == 2) {
    		return "Flower";
    	} else {
    		return "Error, please enter 0, 1 or 2";
    	}
    }
    
    public getX() {
    	return this.x;
    }
    
    public getImage() {
    	return this.y;
    }
    
    //SET METHODS
    public void setLoc(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    public void setX(int x) {
    	this.x = x;
    }
    
    public void setY(int y) {
    	this.y = y;
    }
}
