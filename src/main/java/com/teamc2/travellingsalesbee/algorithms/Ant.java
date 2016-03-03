package com.teamc2.travellingsalesbee.algorithms;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.data.cells.CellFlower;

public class Ant extends NearestNeighbour {

	private double heuristicCost;
	private final double pheromone = 1.1;
	private final double nearProbability = 0.7;
	
	public Ant(Map map) {
		super(map);
		naiveRun();
		setHeuristicCost(getCost());
	}
	
	private void pheremoneRun() {
		if (!(hive == null)) {
			ArrayList<Cell> newPath = new ArrayList<>();
			ArrayList<CellFlower> flowers = map.getFlowers();

			newPath.add(hive);
			
			// Loop over flowers missing from path
			Cell currentCell;
			Cell next = null;
			
			while (!flowers.isEmpty()) {
				currentCell = newPath.get(newPath.size() - 1);
		
				// Find the next flower to go to
				while(next == null) {
					next = findNextFlower(newPath,currentCell);
				}
				
				//Remove the next flower from the set
				//Release some pheremone
				flowers.remove(next);
				newPath.add(next);
				plantPheremone(currentCell,next);
			}
			
			newPath.add(hive);
			plantPheremone(next,hive);

			double cost = calculatePathCost(newPath);
			setPath(newPath, cost);
			if (cost < heuristicCost) {
				plantAllPheremone(path);
			}
		}
	}

	private Cell findNextFlower(ArrayList<Cell> newPath, Cell currentCell) {
		int flowerPos1 = 0;
		int flowerPos2 = 0;

		while (flowerPos1 == flowerPos2) {
			flowerPos1 = ThreadLocalRandom.current().nextInt(1, newPath.size() - 2);
			flowerPos2 = ThreadLocalRandom.current().nextInt(1, newPath.size() - 2);
		}
		
		Cell flower1 = newPath.get(flowerPos1);
		Cell flower2 = newPath.get(flowerPos2);
		
		CostMatrix costMatrix = map.getCostMatrix();
		
		double flower1Cost = costMatrix.getCost(currentCell,flower1);
		double flower2Cost = costMatrix.getCost(currentCell,flower2);
		double flower1Pher = costMatrix.getPheremone(currentCell,flower1);
		double flower2Pher = costMatrix.getPheremone(currentCell,flower2);
		
		double r = ThreadLocalRandom.current().nextDouble(0, 1);
		
		if (flower1Cost<=flower2Cost) {
			if (r <= nearProbability*flower1Pher) {
				return flower1;
			} else {
				return flower2;
			}
		} else {
			if (r <= nearProbability*flower2Pher) {
				return flower2;
			} else {
				return flower1;
			}
		}
		
	}

	private void setHeuristicCost(double cost) {
		heuristicCost = cost;
	}
	
	private void plantPheremone(Cell cell1, Cell cell2) {
		map.getCostMatrix().getEntry(cell1,cell2).plantPheremone(pheromone);
	}
	
	private void plantAllPheremone(ArrayList<Cell> path) {
		for (int i=0; i<path.size()-1; i++) {
			Cell cell1 = path.get(i);
			Cell cell2 = path.get(i+1);
			plantPheremone(cell1,cell2);
		}
	}
}