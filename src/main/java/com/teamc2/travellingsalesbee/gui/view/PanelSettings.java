package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.algorithms.*;
import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.AntStep;
import com.teamc2.travellingsalesbee.gui.ExperimentalStep;
import com.teamc2.travellingsalesbee.gui.NaiveStep;
import com.teamc2.travellingsalesbee.gui.SwapType;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
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
import java.util.stream.Collectors;

public class PanelSettings extends JPanel {

	public final static String name = "PanelSettings";
	private final PanelMap panelMap;
	private Map map;

	private JLabel infoLabel;
	private JButton btnPrev;
	private JButton btnPlay;
	private JButton btnNext;
	private JButton btnRun;

	private Timer timer;
	private boolean playing = false;
	
	// Number of runs for a given algorithm
		//For BEE this is the number of experimental runs
		//For ANT this is the number of pheremone runs
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
	private ComponentTextArea textArea;

	/**
	 * Create a settings panel
	 *
	 * @param panelMap Map JPanel
	 */
	public PanelSettings(PanelMap panelMap) {
		this.panelMap = panelMap;
		map = panelMap.getMap();

		setName(name);
		setBackground(Color.LIGHT_GRAY);
		addSettingsInfo();
		addButtons();
		setStepNum(-1);
	}

	/**
	 * Add the background to the settings panel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {
			BufferedImage img = ImageIO.read(this.getClass().getResource("/assets/backgrounds/BrownBack150.png"));
			TexturePaint paint = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
			g2.setPaint(paint);
			g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add information on how to use the settings panel
	 */
	public void addSettingsInfo() {
		infoLabel = new JLabel("");
	}

	/**
	 * Add the settings buttons to the panel
	 */
	public void addButtons() {
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
		textArea = new ComponentTextArea(editorPane);
		layoutSettings = new LayoutSettings(this, infoLabel, lblRunsOfType, lblNoOfRuns, noOfRunsSlider,
				lblAnimationSpeed, lblSpeed, speedSlider, btnRun, btnPrev, btnPlay, btnNext, textArea);
		setLayout(layoutSettings);
	}

	private void createRunButton() {
		btnRun = new JButton("RUN");
		btnRun.addActionListener(new runActionListener());
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 17));
	}

	private void createPlayButton() {
		btnPlay = new JButton("►");

		timer = new Timer(150, arg0 -> {
			setStepNum(stepNum + 1);
			setDistance();
			panelMap.getPathComponent().repaint();
			// panelMap.getPanelAnimalAnimation().incrStepNum();
		});

		btnPlay.addActionListener(arg0 -> {
			if (!playing) {
				timer.start();
				btnPlay.setText("||");
				playing = true;
			} else {
				timer.stop();
				btnPlay.setText("►");
				playing = false;
			}
		});
	}

	private void createPreviousButton() {
		btnPrev = new JButton("<-");
		btnPrev.addActionListener(arg0 -> {
			setStepNum(stepNum - 1);
			Platform.runLater(() -> {
				if (distance > 0) {
					textArea.addText("Distance: " + distance);
				}
			});

			try {
				panelMap.getPanelAnimalAnimation().incrStepNum();
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Exception in setting animation");
				e.printStackTrace();
			}
		});
	}

	private void createNextButton() {
		btnNext = new JButton("->");
		btnNext.addActionListener(arg0 -> {
			setStepNum(stepNum + 1);
			Platform.runLater(() -> {
				setDistance();
			});

			try {
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
	 */
	public void setBeeText() {
		setNNText();
		
		if(stepNum >= this.panelMap.getPathComponent().getNaiveSteps().size()) {
			if(this.panelMap.getPathComponent().getExperimentalSteps().get(stepNum-(this.panelMap.getPathComponent().getNaiveSteps().size())).getType() == SwapType.INSPECTED) {
				textArea.addText("INSPECTED");
				this.panelMap.getPanelOverlyingText().setText("INSPECTED");
			}
			else if(this.panelMap.getPathComponent().getExperimentalSteps().get(stepNum-(this.panelMap.getPathComponent().getNaiveSteps().size())).getType() == SwapType.ACCEPTED) {
				textArea.addText("ACCEPTED");
				this.panelMap.getPanelOverlyingText().setText("ACCEPTED");
			}
			else if(this.panelMap.getPathComponent().getExperimentalSteps().get(stepNum-(this.panelMap.getPathComponent().getNaiveSteps().size())).getType() == SwapType.BEST) {
				textArea.addText("BEST");
				this.panelMap.getPanelOverlyingText().setText("BEST");
			}
			else if(this.panelMap.getPathComponent().getExperimentalSteps().get(stepNum-(this.panelMap.getPathComponent().getNaiveSteps().size())).getType() == SwapType.REJECTED) {
				textArea.addText("REJECTED");
				this.panelMap.getPanelOverlyingText().setText("REJECTED");
			}
		}
	}

	/**
	 * Sets the text for the textbox related to the Ant
	 * 	//Text is set at each step
	 */
	public void setAntText() {
		ArrayList<AntStep> antSteps = this.panelMap.getPathComponent().getAntSteps();
		if (stepNum < antSteps.size()){
			textArea.addText("Pheremone run " + stepNum + " complete");
			JButton test = new JButton("test");
			test.setVisible(true);
			textArea.add(test);
		}
	}

	/**
	 * Sets the text for the textbox related to the NN or for the naive Section of BEE algorithm
	 */
	public void setNNText() {
		ArrayList<NaiveStep> nearestNeighbourSteps = this.panelMap.getPathComponent().getNaiveSteps();
		if (stepNum < nearestNeighbourSteps.size() && stepNum % 2 == 0){
			textArea.addText("LOOKING FOR NEAREST NODE");
		}else if (stepNum < nearestNeighbourSteps.size() && stepNum % 2 != 0) {
			textArea.addText("FOUND CLOSEST FLOWER");
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
					textArea.addText("Naive Path - Travel through the algorithm step-by-step");
					panelMap.getPanelOverlyingText().setText("INSPECTED");
					break;
				case ANT:
					runAntAlgorithm();
					textArea.addText("Ant Algorithm");
					panelMap.getPanelOverlyingText().setText("INSPECTED");
					break;
				case NEARESTNEIGHBOUR:
					runNearestNeighbourAlgorithm();
					textArea.addText("Nearest Neighbour");
					panelMap.getPanelOverlyingText().setText("INSPECTED");
					break;
				case TWOOPT:
					runTwoOptAlgorithm();
					textArea.addText("Two Opt Swap");
					panelMap.getPanelOverlyingText().setText("INSPECTED");
					break;
			}
		}

		private void runTwoOptAlgorithm() {
			try {
				setStepNum(0);
				map.setCostMatrix();
				TwoOptSwap tos = new TwoOptSwap(map);
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
			Ant ant = new Ant(map);
			ArrayList<ArrayList<Cell>> setOfRuns = new ArrayList<>();
			ArrayList<CostMatrix> setOfMatrices = new ArrayList<>();
			CostMatrix initialMatrix;
			initialMatrix = map.getCostMatrix().copy();

			for (int i = 0; i < noOfRunsValue; i++) {
				ant.pheromoneRun();
				CostMatrix updatedMatrix = map.getCostMatrix().copy();
				setOfMatrices.add(updatedMatrix);
				setOfRuns.add(ant.getPath());
			}


			/*

			Below is an example of how to pass through the url name and set the pathOfPaths

			panelMap.getPanelAnimalAnimation().setUrl("/assets/icons/Ant.png");
			panelMap.getPanelAnimalAnimation().setPathofPaths(antSteps);*/

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
					ArrayList<Cell> setOfPoints = experimentalStep.getPath();
					pathOfPaths.add(setOfPoints);
				}

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
			btnPlay.setText("►");
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
		switch (type) {
			case BEE:
				if (stepNum < panelMap.getPathComponent().getNaiveSteps().size()) {
					NaiveStep step = panelMap.getPathComponent().getNaiveSteps().get(stepNum);
					distance = step.getStart().distance(step.getEnd());
				}
				setBeeText();
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

	public ComponentTextArea getTextArea() {
		return textArea;
	}
}