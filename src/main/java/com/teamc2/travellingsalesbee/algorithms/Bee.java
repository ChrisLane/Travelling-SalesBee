package com.teamc2.travellingsalesbee.algorithms;

import com.teamc2.travellingsalesbee.gui.elements.Map;
import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellFlower;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellHive;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Bee {

	private Cell hive;
	private ArrayList<Cell> path = new ArrayList<>();
	private double cost = Double.MAX_VALUE;
	private Map map;

	public Bee(Map map, CellHive hive, int experiments) {
		this.map = map;
		this.hive = hive;

		naiveRun();
		experimentalRuns(experiments);
	}

	public void naiveRun() {
		ArrayList<Cell> newPath = new ArrayList<>();
		ArrayList<CellFlower> flowers = map.getFlowers();
		int cost = 0;

		newPath.add(hive);

		// Loop over flowers missing from path
		while (!flowers.isEmpty()) {
			double bestDistance = Double.MAX_VALUE;
			CellFlower closest = null;

			// Find the closest flower to the previous
			for (CellFlower flower : flowers) {
				double distance = flower.distance(newPath.get(newPath.size() - 1));
				if (distance < bestDistance) {
					closest = flower;
					bestDistance = distance;
				}
			}

			cost += bestDistance;
			newPath.add(closest);
			flowers.remove(closest);
		}
		double distance = hive.distance(newPath.get(newPath.size() - 1));
		cost += distance;
		newPath.add(hive);
		setPath(newPath, cost);
	}

	private void experimentalRun() {
		ArrayList<Cell> testPath = path;

		int flowerPos1 = 0;
		int flowerPos2 = 0;

		while (flowerPos1 == flowerPos2) {
			flowerPos1 = ThreadLocalRandom.current().nextInt(1, testPath.size() - 1);
			flowerPos2 = ThreadLocalRandom.current().nextInt(1, testPath.size() - 1);
		}
		
			Cell flower1 = testPath.get(flowerPos1);
			Cell flower2 = testPath.get(flowerPos2);

			testPath.set(flowerPos1, flower2);
			testPath.set(flowerPos2, flower1);

		double testCost = calculatePathCost(testPath);
		if (testCost < cost) {
			setPath(testPath, testCost);
		}
	}

	public double getPathCost() {
		return cost;
	}

	public double calculatePathCost(ArrayList<Cell> path) {
		double cost = 0;
		for (int i = 0; i < path.size(); i++) {
			if (i + 1 < path.size()) {
				Cell pos1 = path.get(i);
				Cell pos2 = path.get(i + 1);

				cost += pos1.distance(pos2);
			}
		}
		return cost;
	}

	public void experimentalRuns(int experiments) {
		while (experiments > 0) {
			experimentalRun();
			experiments--;
		}
	}

	public void setPath(ArrayList<Cell> path, double cost) {
		this.path = path;
		this.cost = cost;
	}

	public ArrayList<Cell> getPath() {
		return path;
	}
}
