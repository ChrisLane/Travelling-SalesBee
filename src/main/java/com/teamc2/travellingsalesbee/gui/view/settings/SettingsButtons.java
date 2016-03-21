package com.teamc2.travellingsalesbee.gui.view.settings;

import com.teamc2.travellingsalesbee.algorithms.NearestNeighbour;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.CellType;
import com.teamc2.travellingsalesbee.gui.data.steps.AntStep;
import com.teamc2.travellingsalesbee.gui.data.steps.NaiveStep;
import com.teamc2.travellingsalesbee.gui.data.steps.SwapType;
import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;
import com.teamc2.travellingsalesbee.gui.view.toolbox.CellDraggable;
import javafx.application.Platform;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SettingsButtons {
	private final PanelSettings panelSettings;
	private final Map map;
	private JButton btnPrev;
	private JButton btnPlay;
	private JButton btnNext;
	private JButton btnRun;
	private JButton btnRandomise;
	private JButton btnClear;

	private boolean playing = false;

	private int stepNum = -1;
	private double distance;
	private double oldDistance;

	private Timer timer;
	private PanelMap panelMap;

	public SettingsButtons(PanelSettings panelSettings, PanelMap panelMap) {
		this.panelSettings = panelSettings;
		this.panelMap = panelMap;
		map = panelMap.getMap();

		createRandomiseButton();
		createClearButton();
		createRunButton();
		createPlayButton();
		createPreviousButton();
		createNextButton();
	}

	private void createRunButton() {
		btnRun = new JButton();
		setBtnIcon(btnRun, "/assets/icons/startBtn.png");
		btnRun.addActionListener(new RunActionListener(panelSettings, panelMap, this));
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 17));
	}

	private void createPlayButton() {
		btnPlay = new JButton();
		setBtnIcon(btnPlay, "/assets/icons/playBtn.png");

		timer = new Timer(1500, arg0 -> {
			setStepNum(stepNum + 1);
			panelMap.getPanelAnimalAnimation().incrStepNum();
			setDistance();
			panelMap.getPathComponent().repaint();
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

		if (img != null) {
			btn.setIcon(new ImageIcon(img));
		}

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
			setStepNum(-1);
			panelMap.getPanelOverlyingText().setText("\nMap Cleared!\n");

		});
	}

	private void createRandomiseButton() {
		btnRandomise = new JButton();
		setBtnIcon(btnRandomise, "/assets/icons/refresh.png");
		btnRandomise.addActionListener(arg0 -> {
			randomise();
			panelMap.getPanelOverlyingText().setText("\nMap Randomised!\n");
		});
	}

	public void randomise() {
		int cellWidth = panelMap.getCellWidth();
		int cellHeight = panelMap.getCellHeight();

		panelMap.clear();
		int maxX = (panelMap.getWidth()) / cellWidth;
		int maxY = ((panelMap.getHeight()) / cellHeight) - 1;

		int x;
		int y;
		CellDraggable newCell;

		int nodesPlaced = 0;
		while (nodesPlaced < 12) {

			x = ThreadLocalRandom.current().nextInt(0, maxX) * cellWidth;
			y = (ThreadLocalRandom.current().nextInt(0, maxY) * cellHeight) + 50;

			panelMap.cellFull(x, y);

			if (nodesPlaced < 11) {
				newCell = new CellDraggable(cellWidth, cellHeight, CellType.NODE, panelMap, panelSettings.getType(), true);
				newCell.setIcon(new ImageIcon(newCell.getImage(CellType.NODE)));
				panelMap.getMap().setCell(x, y, CellType.NODE);
			} else {
				newCell = new CellDraggable(cellWidth, cellHeight, CellType.ORIGIN, panelMap, panelSettings.getType(), true);
				newCell.setIcon(new ImageIcon(newCell.getImage(CellType.ORIGIN)));
				panelMap.getMap().setCell(x, y, CellType.ORIGIN);
			}

			newCell.setBounds(x, y, cellWidth, cellHeight);
			newCell.onMap();
			newCell.setPrevs(x, y);
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

	public JButton getBtnPrev() {
		return btnPrev;
	}

	public JButton getBtnPlay() {
		return btnPlay;
	}

	public JButton getBtnNext() {
		return btnNext;
	}

	public JButton getBtnRun() {
		return btnRun;
	}

	public JButton getBtnRandomise() {
		return btnRandomise;
	}

	public JButton getBtnClear() {
		return btnClear;
	}

	public void setDistance() {
		double distance = 0;
		NearestNeighbour nn = new NearestNeighbour(map);
		switch (panelSettings.getType()) {
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
	}

	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
		panelMap.getPathComponent().setStepNum(stepNum);
		int maxStepNum = panelMap.getPathComponent().getMaxStepNum();

		setSpeed();

		btnPrev.setEnabled((stepNum > 0));
		btnPlay.setEnabled((stepNum != -1));
		btnNext.setEnabled((stepNum == 0) || ((stepNum != -1) && (stepNum < maxStepNum)));
		if (stepNum == -1) {
			timer.stop();
			btnPlay.setText("");
			setBtnIcon(btnPlay, "/assets/icons/playBtn.png");
			playing = false;
		}
	}

	/**
	 * Sets the text for the text box related to the Bee
	 *
	 * @param nn Nearest neighbor
	 */
	public void setBeeText(NearestNeighbour nn) {
		setNNText();

		if (stepNum >= panelMap.getPathComponent().getNaiveSteps().size()) {
			if (stepNum - panelMap.getPathComponent().getNaiveSteps().size() == panelMap.getPathComponent().getExperimentalSteps().size()) {
				panelMap.getPanelOverlyingText().setText("\nFinal path produced by bee in " + panelSettings.getNoOfRunsValue() + " experimental runs");
			} else if (panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size())).getType() == SwapType.INSPECTED) {
				panelMap.getPanelOverlyingText().setText("Experimental Run " + (1+(stepNum-(panelMap.getPathComponent().getNaiveSteps().size()))/3)
						+ "\nThe Bee travels a new path by switching the order it visits two flowers in the path");
			} else if (panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size())).getType() == SwapType.ACCEPTED) {
				int bestDistance = (int) nn.calculatePathCost(panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size()) - 2).getPath());
				int newDistance = (int) nn.calculatePathCost(panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size())).getPath());
				panelMap.getPanelOverlyingText().setText("Previous Best Distance: " + bestDistance + "\nNew Best Distance: " + newDistance + "\nThe new path travelled by the Bee has a lower cost than the bee's previous best, it now remembers this as it's current best path");
			} else if (panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size())).getType() == SwapType.BEST) {
				panelMap.getPanelOverlyingText().setText("\nThe shortest path the bee has found so far!");
			} else if (panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size())).getType() == SwapType.REJECTED) {
				int bestDistance = (int) nn.calculatePathCost(panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size())).getPath());
				int newDistance = (int) nn.calculatePathCost(panelMap.getPathComponent().getExperimentalSteps().get(stepNum - (panelMap.getPathComponent().getNaiveSteps().size()) - 1).getPath());
				panelMap.getPanelOverlyingText().setText("Best Distance: " + bestDistance + "\nNew Distance: " + newDistance + "\nThe new path travelled by the Bee has a higher cost than the bee's previous best, it ignores this path");
			}
		}
	}

	/**
	 * Sets the text for the text box related to the Ant
	 * //Text is set at each step
	 */
	public void setAntText() {
		ArrayList<AntStep> antSteps = panelMap.getPathComponent().getAntSteps();
		if (stepNum < antSteps.size()) {
			panelMap.getPanelOverlyingText().setText("Pheromone run " + stepNum + " complete\n"
					+ "New pheromones planted, old pheromone levels have evaporated\n"
					+ "The best path slowly emerges...");
		}
	}

	/**
	 * Sets the text for the text box related to the NN or for the naive Section of BEE algorithm
	 */
	public void setNNText() {
		ArrayList<NaiveStep> nearestNeighbourSteps = panelMap.getPathComponent().getNaiveSteps();
		if (stepNum == nearestNeighbourSteps.size()) {
			panelMap.getPanelOverlyingText().setText("\nFinal path produced by Nearest Neighbour");
		} else if (stepNum == nearestNeighbourSteps.size() - 1) {
			panelMap.getPanelOverlyingText().setText("\nAll nodes visited, now returning to start");
		} else if (stepNum == nearestNeighbourSteps.size() - 2) {
			panelMap.getPanelOverlyingText().setText("\nAll nodes visited, finding start point");
		} else if (stepNum < nearestNeighbourSteps.size() && stepNum % 2 == 0) {
			panelMap.getPanelOverlyingText().setText("\nLooking for nearest node...");
		} else if (stepNum < nearestNeighbourSteps.size() && stepNum % 2 != 0) {
			panelMap.getPanelOverlyingText().setText("\nFound nearest node!");
		}
	}

	/**
	 * Sets the text for the text box related to the Two-Opt
	 */
	public void setTOText() {

	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(int delay) {
		timer.setDelay(delay);
	}

	public void setSpeed() {

		if (panelMap.getPathComponent().getNaiveSteps().size() > 0) {

			//Timer is 1500ms by default
			//get size of path
			//bee time is timer.delay / path.size
			//set transition speed

			double beeSpeed = (timer.getDelay() / 1000) / panelMap.getPathComponent().getNaiveSteps().size();
			panelMap.getPanelAnimalAnimation().setDelay(beeSpeed);

			//For naive steps
			if (stepNum % 2 == 0) {
				setTimer(1500);
			} else {
				setTimer(50);
			}

            /*  To do:
			*
            *   Implement timings for Experimental
            *
            */
		}
	}
}
