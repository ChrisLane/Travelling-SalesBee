package com.teamc2.travellingsalesbee.gui.view.settings;

import com.teamc2.travellingsalesbee.algorithms.Ant;
import com.teamc2.travellingsalesbee.algorithms.Bee;
import com.teamc2.travellingsalesbee.algorithms.NearestNeighbour;
import com.teamc2.travellingsalesbee.algorithms.TwoOptSwap;
import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.AntStep;
import com.teamc2.travellingsalesbee.gui.ExperimentalStep;
import com.teamc2.travellingsalesbee.gui.NaiveStep;
import com.teamc2.travellingsalesbee.gui.SwapType;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;
import com.teamc2.travellingsalesbee.visualisation.StepController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RunActionListener implements ActionListener {
	private final PanelSettings panelSettings;
	private final PanelMap panelMap;
	private final SettingsButtons settingsButtons;
	private Map map;

	public RunActionListener(PanelSettings panelSettings, PanelMap panelMap, SettingsButtons settingsButtons) {
		this.panelSettings = panelSettings;
		this.panelMap = panelMap;
		this.settingsButtons = settingsButtons;
		map = panelMap.getMap();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (panelSettings.getType()) {
			case BEE:
				runBeeAlgorithm();
				settingsButtons.setDistance();
				break;
			case ANT:
				runAntAlgorithm();
				break;
			case NEARESTNEIGHBOUR:
				runNearestNeighbourAlgorithm();
				break;
			case TWOOPT:
				runTwoOptAlgorithm();
				break;
		}
		panelMap.getPathComponent().setStepNum(0);
	}

	private void runTwoOptAlgorithm() {
		try {
			settingsButtons.setStepNum(0);
			map.setCostMatrix();
			TwoOptSwap tos = new TwoOptSwap(map, panelSettings.getNoOfRunsValue());
			StepController visualise = new StepController();
			tos.naiveRun();
			ArrayList<NaiveStep> steps = visualise.getNaiveSteps(tos.getPath());
			panelMap.getPathComponent().setTosObject(tos);
			panelMap.getPathComponent().setNaiveSteps(steps);

			ArrayList<ArrayList<Cell>> pathOfPaths = new ArrayList<>();
			ArrayList<Cell> hive = new ArrayList<>();
			hive.add(steps.get(0).getStart());
			pathOfPaths.add(hive);

			//Do Two-Opt swap here as well as naive

			for (NaiveStep naiveStep : steps) {
				ArrayList<Cell> singlePoint = new ArrayList<Cell>();
					/* if the path is the 'inspected' path (YELLOW) then add that path to the Path of paths
						if((stepNum - panelMap.getPathComponent().getNaiveSteps().size()) % 3 == 1) {
							//singlePoint = inspected swapped path
						} else {

					*/

				singlePoint.add(naiveStep.getEnd());

					/* } */

				pathOfPaths.add(singlePoint);
			}

			panelMap.getPanelAnimalAnimation().setUrl("/assets/icons/Boat.png");
			panelMap.getPanelAnimalAnimation().setPathofPaths(pathOfPaths);
		} catch (NullPointerException e) {
			settingsButtons.getBtnPrev().setEnabled(false);
			settingsButtons.getBtnPlay().setEnabled(false);
			settingsButtons.getBtnNext().setEnabled(false);
		}
	}

	private void runNearestNeighbourAlgorithm() {
		try {
			settingsButtons.setStepNum(0);
			map = panelMap.getMap();
			map.setCostMatrix();
			NearestNeighbour nearestNeighbour = new NearestNeighbour(map);
			StepController visualise = new StepController();
			nearestNeighbour.naiveRun();
			ArrayList<NaiveStep> naiveSteps = visualise.getNaiveSteps(nearestNeighbour.getPath());
			panelMap.getPathComponent().setNaiveSteps(naiveSteps);

			ArrayList<ArrayList<Cell>> pathOfPaths = new ArrayList<>();
			ArrayList<Cell> hive = new ArrayList<>();
			hive.add(naiveSteps.get(0).getStart());

			hive.addAll(naiveSteps.stream().map(NaiveStep::getEnd).collect(Collectors.toList()));
			pathOfPaths.add(hive);

				/*----------------------------------------------*/
			// panelMap.getPanelAnimalAnimation().setPath(nearestNeighbour.getPath());
			panelMap.getPanelAnimalAnimation().setUrl("/assets/icons/MailVan.png");
			panelMap.getPanelAnimalAnimation().setPathofPaths(pathOfPaths);
		} catch (NullPointerException e) {
				/*----------------------------------------------*/
			// When no nodes are on the screen: disable the buttons.
			settingsButtons.getBtnPrev().setEnabled(false);
			settingsButtons.getBtnPlay().setEnabled(false);
			settingsButtons.getBtnNext().setEnabled(false);

		}
	}

	private void runAntAlgorithm() {
		settingsButtons.setStepNum(0);
		ArrayList<Ant> ants = new ArrayList<>();
		Ant ant = new Ant(map);
		ants.add(ant);
		ArrayList<ArrayList<Cell>> setOfRuns = new ArrayList<>();
		ArrayList<CostMatrix> setOfMatrices = new ArrayList<>();
		CostMatrix initialMatrix;
		map.setCostMatrix();
		initialMatrix = map.getCostMatrix().copy();

		int numberOfAnts = 4;
		for (int i = 1; i < numberOfAnts; i++) {
			Map cloneMap = new Map();
			cloneMap.setCostMatrix(initialMatrix.copy());
			ant = new Ant(cloneMap);
			ants.add(ant);
		}

		for (int j = 0; j < 50; j++) {
			ants.get(0).pheromoneRun();
			CostMatrix updatedMatrix = map.getCostMatrix().copy();
			for (int i = 1; i < numberOfAnts; i++) {
				ant = ants.get(i);
				ant.pheromoneRun();
				CostMatrix nextUpdatedMatrix = ant.getMap().getCostMatrix().copy();
				updatedMatrix.combine(nextUpdatedMatrix);
			}
			setOfMatrices.add(updatedMatrix);
			setOfRuns.add(ants.get(0).getPath());
			for (int i = 1; i < numberOfAnts; i++) {
				ants.get(i).getMap().setCostMatrix(updatedMatrix.copy());
			}
		}




			/*Below is an example of how to pass through the url name and set the pathOfPaths*/

		panelMap.getPanelAnimalAnimation().setUrl("/assets/icons/Ant.png");
		panelMap.getPanelAnimalAnimation().setPathofPaths(setOfRuns);

		StepController stepController = new StepController();
		ArrayList<AntStep> antSteps = stepController.getAntSteps(setOfRuns, setOfMatrices, initialMatrix);
		panelMap.getPathComponent().setAntSteps(antSteps);
	}

	private void runBeeAlgorithm() {
		try {
			settingsButtons.setStepNum(0);

			map.setCostMatrix();
			Bee bee = new Bee(map, panelSettings.getNoOfRunsValue());
			StepController visualise = new StepController();
			bee.naiveRun();
			ArrayList<NaiveStep> naiveSteps = visualise.getNaiveSteps(bee.getPath());
			panelMap.getPathComponent().setNaiveSteps(naiveSteps);

			bee.experimentalRun();
			ArrayList<ExperimentalStep> experimentalSteps = visualise.getExperimentalSteps(bee.getCellComparisons(),
					bee.getIntermediaryPaths(), bee.getIntermediaryPathCosts());
			panelMap.getPathComponent().setExperimentalSteps(experimentalSteps);

			ArrayList<ArrayList<Cell>> pathOfPaths = new ArrayList<>();
			ArrayList<Cell> hive = new ArrayList<>();
			hive.add(naiveSteps.get(0).getStart());

			hive.addAll(naiveSteps.stream().map(NaiveStep::getEnd).collect(Collectors.toList()));
			pathOfPaths.add(hive);

			for (ExperimentalStep experimentalStep : experimentalSteps) {
				ArrayList<Cell> setOfPoints = new ArrayList<Cell>();

				if (experimentalStep.getType() == SwapType.INSPECTED) {
					setOfPoints = experimentalStep.getPath();
				}

				pathOfPaths.add(setOfPoints);
			}

			panelMap.getPathComponent().setBeePath(bee.getPath());

				/*----------------------------------------------*/
			// panelMap.getPanelAnimalAnimation().setPath(bee.getPath());
			panelMap.getPanelAnimalAnimation().setUrl("/assets/icons/SalesBee.png");
			panelMap.getPanelAnimalAnimation().setPathofPaths(pathOfPaths);
		} catch (NullPointerException e) {
				/*----------------------------------------------*/
			// When no nodes are on the screen: disable the buttons.
			settingsButtons.getBtnPrev().setEnabled(false);
			settingsButtons.getBtnPlay().setEnabled(false);
			settingsButtons.getBtnNext().setEnabled(false);
		}
	}
}
