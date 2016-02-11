package com.teamc2.travellingsalesbee.algorithms;

import com.sun.javafx.geom.Point2D;
import com.teamc2.travellingsalesbee.gui.elements.Map;
import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellFlower;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellHive;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Bee {

	private Cell hive;
	private ArrayList<Cell> path = new ArrayList<>();
	private int cost = Integer.MAX_VALUE;
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
			float bestDistance = Float.MAX_VALUE;
			CellFlower closest = null;

			// Find the closest flower to the previous
			for (CellFlower flower : flowers) {
				float distance = flower.distance((Point2D) newPath.get(newPath.size() - 1));
				if (distance < bestDistance) {
					closest = flower;
					bestDistance = distance;
				}
			}

			cost += bestDistance;
			newPath.add(closest);
			flowers.remove(closest);
		}
		float distance = ((Point2D) hive).distance((Point2D) newPath.get(newPath.size() - 1));
		cost += distance;
		newPath.add(hive);
		setPath(newPath, cost);
	}

	private void experimentalRunChris() {
		ArrayList<Cell> testPath = path;

		int flowerPos1 = ThreadLocalRandom.current().nextInt(0, testPath.size()-1);
		int flowerPos2 = ThreadLocalRandom.current().nextInt(0, testPath.size()-1);

		if (flowerPos1 != flowerPos2) {
			Cell flower1 = testPath.get(flowerPos1);
			Cell flower2 = testPath.get(flowerPos2);

			testPath.set(flowerPos1, flower2);
			testPath.set(flowerPos2, flower1);
		} else {
			experimentalRunChris();
		}

		int testCost = calculatePathCost(testPath);
		if (testCost < cost) {
			setPath(testPath, testCost);
		}
	}
	
	private void experimentalRunTodd() {
		ArrayList<Cell> newPath = new ArrayList<>();
		ArrayList<CellFlower> flowers = map.getFlowers();
		int cost = 0;

		newPath.add(hive);

		// Loop over flowers missing from path
		while (!flowers.isEmpty()) {
			float distance = Float.MAX_VALUE;
			int flowerPos;
			CellFlower flower = null;
			
			while(distance == Float.MAX_VALUE || distance == 0) {
				flowerPos = ThreadLocalRandom.current().nextInt(1, flowers.size());
				flower = flowers.get(flowerPos);
				
				distance = flower.distance((Point2D) newPath.get(newPath.size() - 1));
			}

			cost += distance;
			newPath.add(flower);
			flowers.remove(flower);
		}
		float distance = ((Point2D) hive).distance((Point2D) newPath.get(newPath.size() - 1));
		cost += distance;
		newPath.add(hive);
		setPath(newPath, cost);
	}

	public int getPathCost() {
		return cost;
	}

	public int calculatePathCost(ArrayList<Cell> path) {
		int cost = 0;
		for (int i = 0; i < path.size(); i++) {
			if (i + 1 < path.size()) {
				Point2D pos1 = (Point2D) path.get(i);
				Point2D pos2 = (Point2D) path.get(i + 1);

				cost += pos1.distance(pos2);
			}
		}

		return cost;
	}

	public void experimentalRuns(int experiments) {
		while (experiments > 0) {
			//experimentalRun();
			experiments--;
		}
	}

	public void setPath(ArrayList<Cell> path, int cost) {
		this.path = path;
		this.cost = cost;
	}

	public ArrayList<Cell> getPath() {
		return path;
	}
}
