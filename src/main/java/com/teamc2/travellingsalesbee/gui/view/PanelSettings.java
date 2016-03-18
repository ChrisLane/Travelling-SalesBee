package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.algorithms.*;
import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.AntStep;
import com.teamc2.travellingsalesbee.gui.ExperimentalStep;
import com.teamc2.travellingsalesbee.gui.NaiveStep;
import com.teamc2.travellingsalesbee.gui.SwapType;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.data.cells.CellDraggable;
import com.teamc2.travellingsalesbee.gui.data.cells.CellType;
import com.teamc2.travellingsalesbee.gui.view.layouts.LayoutSettings;
import com.teamc2.travellingsalesbee.visualisation.StepController;
import javafx.application.Platform;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PanelSettings extends JPanel {

	public final static String name = "PanelSettings";
	private final PanelMap panelMap;
	private Map map;

	private JLabel infoLabel = new JLabel();
	private JButton btnPrev;
	private JButton btnPlay;
	private JButton btnNext;
	private JButton btnRun;
	private JButton btnRandomise;
	private JButton btnClear;
	
	private final int cellWidth;
	private final int cellHeight;

	private Timer timer;
	private boolean playing = false;

	// Number of runs for a given algorithm
	//For BEE this is the number of experimental runs
	//For ANT this is the number of pheromone runs
	//For TWOOPT this is the number of swap runs
	private int noOfRunsValue = 26;

	private double animationSpeed = 10;
	private int stepNum;
	private AlgorithmType type;
	private double distance;
	private double oldDistance;
	private JSlider noOfRunsSlider;
	private LayoutSettings layoutSettings;
	private JLabel lblRunsOfType;

	/**
	 * Create a settings panel
	 *
	 * @param panelMap Map JPanel
	 */
	public PanelSettings(PanelMap panelMap) {
		cellWidth = panelMap.getCellWidth();
		cellHeight = panelMap.getCellHeight();
		
		this.panelMap = panelMap;
		map = panelMap.getMap();

		setName(name);
		setBackground(Color.LIGHT_GRAY);
		addButtons();
		setStepNum(-1);
		setOpaque(false);
	}

	/**
	 * Add the background to the settings panel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {
			BufferedImage img = ImageIO.read(getClass().getResource("/assets/backgrounds/BrownBack150.png"));
			TexturePaint paint = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
			g2.setPaint(paint);
			Dimension round = new Dimension(30, 30);
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), round.width, round.height);
			g2.setStroke(new BasicStroke(2));
		    g2.drawRoundRect(0, 0, getWidth(),getHeight(), round.width, round.height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add the settings buttons to the panel
	 */
	public void addButtons() {
		createRandomiseButton();
		createClearButton();
		createRunButton();
		createPlayButton();
		createPreviousButton();
		createNextButton();

		noOfRunsSlider = new JSlider();
		noOfRunsSlider.setValue(noOfRunsValue);
		noOfRunsSlider.setOpaque(false);
		lblRunsOfType = new JLabel("Experiment Runs: ");
		JLabel lblNoOfRuns = new JLabel("" + noOfRunsValue);

		noOfRunsSlider.addChangeListener(arg0 -> {
			noOfRunsValue = noOfRunsSlider.getValue();
			lblNoOfRuns.setText(("" + noOfRunsValue));
		});

		JSlider speedSlider = new JSlider();
		speedSlider.setMaximum(20);
		speedSlider.setValue((int) animationSpeed);
		speedSlider.setOpaque(false);
		JLabel lblAnimationSpeed = new JLabel("Animation Speed: ");
		JLabel lblSpeed = new JLabel("" + animationSpeed / 10);

		speedSlider.addChangeListener(arg0 -> {
			animationSpeed = speedSlider.getValue() / 10;
			panelMap.getAnimation().setSpeed(animationSpeed);
			lblSpeed.setText(("" + animationSpeed));
		});

		JEditorPane editorPane = new JEditorPane();
		layoutSettings = new LayoutSettings(this, infoLabel, lblRunsOfType, lblNoOfRuns, noOfRunsSlider,
				lblAnimationSpeed, lblSpeed, speedSlider, btnRandomise, btnClear, btnRun, btnPrev, btnPlay, btnNext);
		setLayout(layoutSettings);
	}

	private void createRunButton() {
		btnRun = new JButton();
		setBtnIcon(btnRun, "/assets/icons/startBtn.png");
		btnRun.addActionListener(new runActionListener());
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 17));
	}

	private void createPlayButton() {
		btnPlay = new JButton();
		setBtnIcon(btnPlay, "/assets/icons/playBtn.png");

		timer = new Timer(1500, arg0 -> {
			setStepNum(stepNum + 1);
			setDistance();
			panelMap.getPathComponent().repaint();
			// panelMap.getPanelAnimalAnimation().incrStepNum();
		});

		btnPlay.addActionListener(arg0 -> {
			if (!playing) {
				timer.start();
				setBtnIcon(btnPlay, "/assets/icons/pauseBtn.png");
				playing = true;
			} else {
				timer.stop();
				setBtnIcon(btnPlay, "/assets/icons/playBtn.png");
				playing = false;
			}
		});
	}

	/**
	 * @param btn     Button to add image icon to
	 * @param iconURL Image URL
	 */
	public void setBtnIcon(JButton btn, String iconURL) {

		Image img = null;
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		btn.setOpaque(false);

		try {
			img = ImageIO.read(getClass().getResource(iconURL));
		} catch (IOException e) {
			e.printStackTrace();
		}

		btn.setIcon(new ImageIcon(img));

	}

	private void createPreviousButton() {
		btnPrev = new JButton();
		setBtnIcon(btnPrev, "/assets/icons/leftArrow.png");
		btnPrev.addActionListener(arg0 -> {
			setStepNum(stepNum - 1);
			Platform.runLater(() -> {
				setDistance();
			});

			try {
				panelMap.getPanelAnimalAnimation().incrStepNum();
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Exception in setting animation");
				e.printStackTrace();
			}
		});
	}
	
	private void createClearButton() {
		btnClear = new JButton();
		setBtnIcon(btnClear, "/assets/icons/clearBtn.png");
		btnClear.addActionListener(arg0 -> {
			panelMap.clear();
			panelMap.repaint();
			//((GuiContainer)getRootPane()).getComponentTextArea().addText("<p>Map Cleared!");
		});
	}
	
	private void createRandomiseButton() {
		btnRandomise = new JButton();
		setBtnIcon(btnRandomise, "/assets/icons/refresh.png");
		btnRandomise.addActionListener(arg0 -> {
			randomise();
			//((GuiContainer)getRootPane()).getComponentTextArea().addText("<p>Map Randomised!");
		});
	}
	
	public void randomise() {
		
		panelMap.clear();

		int maxX = (panelMap.getWidth()) / cellWidth;
		int maxY = ((panelMap.getHeight()) / cellHeight) - 1;

		int x = 0;
		int y = 0;
		CellDraggable newCell = null;

		int nodesPlaced = 0;
		while(nodesPlaced < 12) {

			x = ThreadLocalRandom.current().nextInt(0, maxX) * cellWidth;
			y = (ThreadLocalRandom.current().nextInt(0, maxY) * cellHeight) + 50;

			panelMap.cellFull(x,y);

			if(nodesPlaced < 11) {
				newCell = new CellDraggable(cellWidth, cellHeight, CellType.NODE, panelMap, type);
				newCell.setIcon(new ImageIcon(newCell.getImage(CellType.NODE)));
				panelMap.getMap().setCell(x,y,CellType.NODE);
			} else {
				newCell = new CellDraggable(cellWidth, cellHeight, CellType.ORIGIN, panelMap, type);
				newCell.setIcon(new ImageIcon(newCell.getImage(CellType.ORIGIN)));
				panelMap.getMap().setCell(x,y,CellType.ORIGIN);
			}

			newCell.setBounds(x, y, cellWidth, cellHeight);
			newCell.onMap();
			newCell.setPrevs(x,y);
			newCell.setEnabled(true);
			panelMap.add(newCell);
			panelMap.setComponentZOrder(newCell, 1);

			nodesPlaced++;
		}

		panelMap.repaint();
	}

	private void createNextButton() {
		btnNext = new JButton();
		setBtnIcon(btnNext, "/assets/icons/rightArrow.png");
		btnNext.addActionListener(arg0 -> {
			setStepNum(stepNum + 1);
			Platform.runLater(() -> {
				setDistance();
			});

			try {
				/*if (panelMap.getPanelAnimalAnimation().getPoPathsBool()) {
					if(panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPanelAnimalAnimation().getPathOfPaths().get(0).size())).getType() == SwapType.INSPECTED) {

					}
				}*/
				// if (stepNum < experimentalRuns) {
				panelMap.getPanelAnimalAnimation().incrStepNum();
				oldDistance = distance;
				// }
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Exception in setting animation");
				e.printStackTrace();
			}
		});
	}

	/**
	 * Sets the text for the textbox related to the Bee
	 *
	 * @param nn
	 */
	public void setBeeText(NearestNeighbour nn) {
		setNNText();

		if (stepNum >= panelMap.getPathComponent().getNaiveSteps().size()) {
			if (stepNum - panelMap.getPathComponent().getNaiveSteps().size() == panelMap.getPathComponent().getExperimentalSteps().size()) {
				panelMap.getPanelOverlyingText().setText("Final path produced by bee in " + noOfRunsValue + " experimental runs");
			} else if (panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size())).getType() == SwapType.INSPECTED) {
				panelMap.getPanelOverlyingText().setText("The Bee travels a new path by switching the order it visits two flowers in the path");
			} else if (panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size())).getType() == SwapType.ACCEPTED) {
				int bestDistance = (int) nn.calculatePathCost(panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size()) - 2).getPath());
				int newDistance = (int) nn.calculatePathCost(panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size())).getPath());
				panelMap.getPanelOverlyingText().setText("Previous Best Distance: " + bestDistance + "\nNew Best Distance: " + newDistance + "\nThe new path travelled by the Bee has a lower cost than the bee's previous best, it now remembers this as it's current best path");
			} else if (panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size())).getType() == SwapType.BEST) {
				panelMap.getPanelOverlyingText().setText("\nThe best path the bee has found up to now");
			} else if (panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size())).getType() == SwapType.REJECTED) {
				ArrayList<ExperimentalStep> expSteps = panelMap.getPathComponent().getExperimentalSteps();
				int bestDistance = (int) nn.calculatePathCost(panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size())).getPath());
				int newDistance = (int) nn.calculatePathCost(panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size()) - 1).getPath());
				panelMap.getPanelOverlyingText().setText("Best Distance: " + bestDistance + "\nNew Distance: " + newDistance + "\nThe new path travelled by the Bee has a higher cost than the bee's previous best, it ignores this path");
			}
		}
	}

	/**
	 * Sets the text for the textbox related to the Ant
	 * //Text is set at each step
	 */
	public void setAntText() {
		ArrayList<AntStep> antSteps = panelMap.getPathComponent().getAntSteps();
		if (stepNum < antSteps.size()) {
			panelMap.getPanelOverlyingText().setText("Pheromone run " + stepNum + " complete\n"
					+ "Opacity of edges have been adjusted to the new pheromone level of each edge\n"
					+ "The more opaque and edge is, the more efficient the edge is");
			JButton test = new JButton("test");
			test.setVisible(true);
		}
	}

	/**
	 * Sets the text for the textbox related to the NN or for the naive Section of BEE algorithm
	 */
	public void setNNText() {
		ArrayList<NaiveStep> nearestNeighbourSteps = panelMap.getPathComponent().getNaiveSteps();
		if (stepNum == nearestNeighbourSteps.size() - 1) {
			panelMap.getPanelOverlyingText().setText("All nodes visited, now returning to start");
		}
		if (stepNum == nearestNeighbourSteps.size() - 2) {
			panelMap.getPanelOverlyingText().setText("All nodes visited, finding start point");
		} else if (stepNum < nearestNeighbourSteps.size() && stepNum % 2 == 0) {
			panelMap.getPanelOverlyingText().setText("LOOKING FOR NEAREST NODE");
		} else if (stepNum < nearestNeighbourSteps.size() && stepNum % 2 != 0) {
			panelMap.getPanelOverlyingText().setText("FOUND CLOSEST FLOWER");
		}
	}

	/**
	 * Sets the text for the textbox related to the Two-Opt
	 */
	public void setTOText() {

	}

	private class runActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch (type) {
				case BEE:
					runBeeAlgorithm();
					setDistance();
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
				setStepNum(0);
				map.setCostMatrix();
				TwoOptSwap tos = new TwoOptSwap(map, noOfRunsValue);
				StepController visualise = new StepController();
				tos.naiveRun();
				ArrayList<NaiveStep> steps = visualise.getNaiveSteps(tos.getPath());
				panelMap.getPathComponent().setTosObject(tos);
				panelMap.getPathComponent().setNaiveSteps(steps);

				ArrayList<ArrayList<Cell>> pathOfPaths = new ArrayList<>();
				ArrayList<Cell> hive = new ArrayList<>();
				hive.add(steps.get(0).getStart());
				pathOfPaths.add(hive);

				for (NaiveStep naiveStep : steps) {
					ArrayList<Cell> singlePoint = new ArrayList<>();
					singlePoint.add(naiveStep.getEnd());
					pathOfPaths.add(singlePoint);
				}

				panelMap.getPanelAnimalAnimation().setPathofPaths(pathOfPaths);
			} catch (NullPointerException e) {
				btnPrev.setEnabled(false);
				btnPlay.setEnabled(false);
				btnNext.setEnabled(false);
			}
		}

		private void runNearestNeighbourAlgorithm() {
			try {
				setStepNum(0);
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
				panelMap.getPanelAnimalAnimation().setUrl("/assets/icons/Mailvan.png");
				panelMap.getPanelAnimalAnimation().setPathofPaths(pathOfPaths);
			} catch (NullPointerException e) {
				/*----------------------------------------------*/
				// When no nodes are on the screen: disable the buttons.
				btnPrev.setEnabled(false);
				btnPlay.setEnabled(false);
				btnNext.setEnabled(false);

			}
		}

		private void runAntAlgorithm() {
			setStepNum(0);
			ArrayList<Ant> ants = new ArrayList<>();
			Ant ant = new Ant(map);
			ants.add(ant);
			ArrayList<ArrayList<Cell>> setOfRuns = new ArrayList<>();
			ArrayList<CostMatrix> setOfMatrices = new ArrayList<>();
			CostMatrix initialMatrix;
			map.setCostMatrix();
			initialMatrix = map.getCostMatrix().copy();
			
			int numberOfAnts = 4;
			for(int i=1; i<numberOfAnts; i++) {
				Map cloneMap = new Map();
				cloneMap.setCostMatrix(initialMatrix.copy());
				ant = new Ant(cloneMap);
				ants.add(ant);
			}

			for (int j = 0; j < 50; j++) {
				ants.get(0).pheromoneRun();
				CostMatrix updatedMatrix = map.getCostMatrix().copy();
				for (int i=1; i<numberOfAnts; i++) {
					ant = ants.get(i);
					ant.pheromoneRun();
					CostMatrix nextUpdatedMatrix = ant.getMap().getCostMatrix().copy();
					updatedMatrix.combine(nextUpdatedMatrix);
				}
				setOfMatrices.add(updatedMatrix);
				setOfRuns.add(ants.get(0).getPath());
				for (int i=1; i<numberOfAnts; i++) {
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
				setStepNum(0);

				map.setCostMatrix();
				Bee bee = new Bee(map, noOfRunsValue);
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
				btnPrev.setEnabled(false);
				btnPlay.setEnabled(false);
				btnNext.setEnabled(false);
			}
		}
	}

	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
		panelMap.getPathComponent().setStepNum(stepNum);
		btnPrev.setEnabled((stepNum > 0));
		btnPlay.setEnabled((stepNum != -1));
		btnNext.setEnabled((stepNum != -1));
		if (stepNum == -1) {
			timer.stop();
			btnPlay.setText("");
			setBtnIcon(btnPlay, "/assets/icons/playBtn.png");
			playing = false;
		}
	}

	public void setAlgorithmType(AlgorithmType type) {
		this.type = type;
		updateSliderDetails();
	}

	private void updateSliderDetails() {
		switch (type) {
			case BEE:
				lblRunsOfType.setText("Experimental Runs:");
				break;
			case ANT:
				lblRunsOfType.setText("Pheromone Runs:");
				break;
			case NEARESTNEIGHBOUR:
				break;
			case TWOOPT:
				lblRunsOfType.setText("Swap Runs:");
				break;
		}
		setLayout(layoutSettings);
	}

	private void setDistance() {
		double distance = 0;
		NearestNeighbour nn = new NearestNeighbour(map);
		switch (type) {
			case BEE:
				if (stepNum < panelMap.getPathComponent().getNaiveSteps().size()) {
					NaiveStep step = panelMap.getPathComponent().getNaiveSteps().get(stepNum);
					distance = step.getStart().distance(step.getEnd());
				}
				setBeeText(nn);
				break;
			case ANT:
				setAntText();
				break;
			case NEARESTNEIGHBOUR:
				setNNText();
				break;
			case TWOOPT:
				setTOText();
				break;
		}
		this.distance = distance;
	}

}