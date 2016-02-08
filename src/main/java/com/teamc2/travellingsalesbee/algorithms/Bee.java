package com.teamc2.travellingsalesbee.algorithms;

import com.sun.javafx.geom.Point2D;
import com.teamc2.travellingsalesbee.gui.elements.Map;
import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellFlower;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellHive;

import java.util.ArrayList;

public class Bee {

	private Cell hive;
	private ArrayList<Cell> bestPath = new ArrayList<>();
	private int bestCost = Integer.MAX_VALUE;
	private Map map;

	public Bee(int hiveX, int hiveY, int experiments, Map map, CellHive hive) {
		this.map = map;
		this.hive = hive;

		naiveRun();
		experimentalRuns(experiments);
	}

	public void naiveRun() {
		ArrayList<Cell> path = new ArrayList<>();
		ArrayList<CellFlower> flowers = map.getFlowers();
		int cost = 0;

		path.add(hive);

		// Loop over flowers missing from path
		while (!flowers.isEmpty()) {
			float bestDistance = Float.MAX_VALUE;
			Cell closest = null;

			// Find the closest flower to the previous
			for (CellFlower flower : flowers) {
				float distance = flower.distance((Point2D) path.get(path.size() - 1));
				if (distance < bestDistance) {
					closest = flower;
					bestDistance = distance;
				}
			}

			cost += bestDistance;
			path.add(closest);
			flowers.remove(closest);
		}

		setNewBest(path, cost);
	}

	private void experimentalRun() {
		ArrayList<Cell> path = new ArrayList<>();
		ArrayList<Cell> visited = new ArrayList<>();
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

	public void setNewBest(ArrayList<Cell> path, int newCost) {
		if (newCost < bestCost) {
			bestPath = path;
			bestCost = newCost;
		}
	}
}
