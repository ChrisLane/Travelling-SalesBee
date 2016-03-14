package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.algorithms.*;
import com.teamc2.travellingsalesbee.gui.AntStep;
import com.teamc2.travellingsalesbee.gui.ExperimentalStep;
import com.teamc2.travellingsalesbee.gui.NaiveStep;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.view.layouts.LayoutSettings;
import com.teamc2.travellingsalesbee.visualisation.BeeVisualiser;
import javafx.application.Platform;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class PanelSettings extends JPanel {

	private final PanelMap panelMap;
	private Map map;

	private JLabel infoLabel;
	private JButton btnPrev;
	private JButton btnPlay;
	private JButton btnNext;

	private boolean playing = false;
	private int experimentalRuns = 26; // Set to 26 by default
	private double animationSpeed = 10;
	private int stepNum;
	private AlgorithmType type;
	private double distance;
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
		JButton btnRun = new JButton("RUN");
		btnRun.addActionListener(new runActionListener());
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 17));

		noOfRunsSlider = new JSlider();
		noOfRunsSlider.setValue(experimentalRuns);
		noOfRunsSlider.setOpaque(false);
		lblRunsOfType = new JLabel("Experiment Runs: ");
		JLabel lblNoOfRuns = new JLabel("" + experimentalRuns);

		noOfRunsSlider.addChangeListener(arg0 -> {
			experimentalRuns = noOfRunsSlider.getValue();
			lblNoOfRuns.setText(("" + experimentalRuns));
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

		btnPrev = new JButton("<-");
		btnPlay = new JButton("►");
		btnNext = new JButton("->");

		btnPrev.addActionListener(arg0 -> {
			setStepNum(stepNum - 1);
			Platform.runLater(() -> {
				textArea.setText("Distance: " + distance);
			});

			/*----------------------------------------------*/
			try {
				panelMap.getPanelAnimalAnimation().incrStepNum();
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Exception in setting animation");
				e.printStackTrace();
			}
			/*----------------------------------------------*/
		});

		btnNext.addActionListener(arg0 -> {
			setStepNum(stepNum + 1);
			Platform.runLater(() -> {
				setDistance();
				textArea.setText("Distance: " + distance);
			});

			/*----------------------------------------------*/
			try {
				// if (stepNum < experimentalRuns) {
				panelMap.getPanelAnimalAnimation().incrStepNum();
				// }
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Exception in setting animation");
				e.printStackTrace();
			}
			/*----------------------------------------------*/
		});

		Timer timer = new Timer(150, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setStepNum(stepNum + 1);
				panelMap.getPathComponent().repaint();
				// panelMap.getPanelAnimalAnimation().incrStepNum();
			}
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

		JEditorPane editorPane = new JEditorPane();
		textArea = new ComponentTextArea(editorPane);
		layoutSettings = new LayoutSettings(this, infoLabel, lblRunsOfType, lblNoOfRuns, noOfRunsSlider,
				lblAnimationSpeed, lblSpeed, speedSlider, btnRun, btnPrev, btnPlay, btnNext, textArea);
		setLayout(layoutSettings);
	}

	private class runActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch (type) {
				case BEE:
					runBeeAlgorithm();
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
		}

		private void runTwoOptAlgorithm() {
			try {
				System.out.println("RUNNING TOS PANEL SETTINGS");
				setStepNum(0);
				map.setCostMatrix();
				TwoOptSwap tos = new TwoOptSwap(map, 2);
				BeeVisualiser visualise = new BeeVisualiser();
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
				BeeVisualiser visualise = new BeeVisualiser();
				nearestNeighbour.naiveRun();
				ArrayList<NaiveStep> naiveSteps = visualise.getNaiveSteps(nearestNeighbour.getPath());
				panelMap.getPathComponent().setNaiveSteps(naiveSteps);

				ArrayList<ArrayList<Cell>> pathOfPaths = new ArrayList<>();
				ArrayList<Cell> hive = new ArrayList<>();
				hive.add(naiveSteps.get(0).getStart());

				for (NaiveStep naiveStep : naiveSteps) {
					hive.add(naiveStep.getEnd());

				}
				pathOfPaths.add(hive);

				/*----------------------------------------------*/
				// panelMap.getPanelAnimalAnimation().setPath(nearestNeighbour.getPath());
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
			for (int i = 0; i < 100; i++) {
				ant.pheromoneRun();
				setOfRuns.add(ant.getPath());
			}
			BeeVisualiser visualise = new BeeVisualiser();
			ArrayList<AntStep> antSteps = visualise.getAntSteps(setOfRuns);
			panelMap.getPathComponent().setMap(map);
			panelMap.getPathComponent().setAntSteps(antSteps);

		}

		private void runBeeAlgorithm() {
			try {
				setStepNum(0);

				map.setCostMatrix();
				Bee bee = new Bee(map, experimentalRuns);
				BeeVisualiser visualise = new BeeVisualiser();
				bee.naiveRun();
				ArrayList<NaiveStep> naiveSteps = visualise.getNaiveSteps(bee.getPath());
				panelMap.getPathComponent().setNaiveSteps(naiveSteps);

				bee.experimentalRun();
				ArrayList<ExperimentalStep> experimentalSteps = visualise.getExperimentalSteps(bee.getCellComparisons(),
						bee.getIntermediaryPaths(), bee.getIntermediaryPathCosts());
				panelMap.getPathComponent().setExperimentalSteps(experimentalSteps);
				panelMap.getPathComponent().setPath(bee.getPath()); // THIS DOES
				// NOTHING

				ArrayList<ArrayList<Cell>> pathOfPaths = new ArrayList<>();
				ArrayList<Cell> hive = new ArrayList<>();
				hive.add(naiveSteps.get(0).getStart());

				for (NaiveStep naiveStep : naiveSteps) {
					hive.add(naiveStep.getEnd());

				}
				pathOfPaths.add(hive);

				for (ExperimentalStep experimentalStep : experimentalSteps) {
					ArrayList<Cell> setOfPoints = experimentalStep.getPath();
					pathOfPaths.add(setOfPoints);
				}

				/*----------------------------------------------*/
				// panelMap.getPanelAnimalAnimation().setPath(bee.getPath());
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
				lblRunsOfType.setText("Pheremone Runs:");
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
				break;
			case ANT:
				break;
			case NEARESTNEIGHBOUR:
				break;
			case TWOOPT:
				break;
		}
		this.distance = distance;
	}

}