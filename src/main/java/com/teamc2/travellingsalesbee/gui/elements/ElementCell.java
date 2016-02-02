package main.java.com.teamc2.travellingsalesbee.gui.elements;

import java.awt.geom.Point2D;

public class ElementCell extends Point2D implements Element{
	
	private static int CELL_TYPE = 0; //0 for empty cell, 1 for hive, 2 for flower/node
	private double x; //x position of Cell
	private double y; //y position of Cell
	
    /**
     * Creates a new instance of {@code Point2D}.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public ElementCell(double x, double y, int type) {
        super();
        this.x = x; //x position of object
        this.y = y; //y position of object
        
        CELL_TYPE = type; //type of object (see above for types)
    }
    
    //GET METHODS
    public int getLoc() {
    	return 0;
    }
    
    public int getTypeInt() {
    	return CELL_TYPE; //We could change this to return a string later on
    }
    
    public String getTypeStr() {
    	if(CELL_TYPE == 0) {
    		return "Empty Cell";
    	} else if(CELL_TYPE == 1) {
    		return "Hive";
    	} else if(CELL_TYPE == 2) {
    		return "Flower";
    	} else {
    		return "Error";
    	}
    }
    
    //Still need to work out what to do for this
    public int getImage() {
    	return 0;
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

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLocation(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}
}
