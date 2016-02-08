package com.teamc2.travellingsalesbee.algorithms;

import com.sun.javafx.geom.Point2D;
import java.util.ArrayList;

import com.teamc2.travellingsalesbee.gui.elements.Map;
import com.teamc2.travellingsalesbee.gui.elements.cells.*;

public class Bee {

	private Point2D hive;
	private ArrayList<CellFlower> bestPath;
	private int bestCost;
	private Map map; //

	public Bee(int hiveX, int hiveY, int experiments, Map map) {
		this.hive = getHiveCell(hiveX,hiveY);
		bestPath = new ArrayList<>();
		bestCost = Integer.MAX_VALUE;
		this.map = map; //
		naiveRun();
		experimentalRuns(experiments);
	}

	public void naiveRun() {
		ArrayList<Point2D> path = new ArrayList<>();
		ArrayList<Point2D> visited = new ArrayList<>();
		int cost = 0;
		Point2D beeCell = hive;
		visited.add(beeCell);
		// implement cost matrix at later date
		ArrayList<CellFlower> flowers = map.getFlowers();
		while (true) {
			while (true) {
				int bestDistance = Integer.MAX_VALUE;
				Point2D bestCell = beeCell; 
				for(int i=0; i<flowers.size(); i++) {
					Point2D currentCell = flowers.get(i);
					if (!visited.contains(currentCell)) {
						int currentDistance = (int) currentCell.distance(beeCell);
						if (currentDistance < bestDistance) {
							bestCell = currentCell;
							bestDistance = currentDistance;
						}
					}
				}
				beeCell = bestCell;
				cost += bestDistance;
				visited.add(beeCell);
			}
		}
		// TODO
		// setNewBest(path, cost);
	}

	private Point2D getHiveCell(int hiveX, int hiveY) {
		// TODO Auto-generated method stub
		return null;
	}

	private void experimentalRun() {
		ArrayList<CellFlower> path = new ArrayList<>();
		ArrayList<CellFlower> visited = new ArrayList<>();
		int cost = 0;


		// TODO
		setNewBest(path, cost);
	}

	public void experimentalRuns(int experiments) {
		while (experiments > 0) {
			experimentalRun();
			experiments--;
		}
	}

	public void setNewBest(ArrayList<CellFlower> path, int newCost) {
		if (newCost < bestCost) {
			bestPath = path;
			bestCost = newCost;
		}
	}

}
