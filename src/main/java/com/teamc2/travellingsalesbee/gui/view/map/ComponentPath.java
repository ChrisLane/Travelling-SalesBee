package com.teamc2.travellingsalesbee.gui.view.map;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.algorithms.TwoOptSwap;
import com.teamc2.travellingsalesbee.algorithms.cost.Comparison;
import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.gui.data.steps.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * A class for the drawing of paths
 *
 * @author Bradley Rowe (bmr455)
 * @author Neil Farrington (npf489)
 * @author Todd Waugh Ambridge (txw467)
 */
public class ComponentPath extends JComponent {

	private ArrayList<Cell> path = new ArrayList<>();
	private ArrayList<NaiveStep> naiveSteps = new ArrayList<>();
	private int stepNum = 0;
	private ArrayList<ExperimentalStep> experimentalSteps = new ArrayList<>();
	private AlgorithmType type;
	private ArrayList<AntStep> antSteps = new ArrayList<>();
	private TwoOptSwap tos;
	private ArrayList<JLabel> distanceBoxes = new ArrayList<>();

	/**
	 * Construct a new path component
	 *
	 * @param type Type of algorithm that we're drawing the path for
	 */
	public ComponentPath(AlgorithmType type) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		setBounds(0, 0, screenWidth, screenHeight);
		this.type = type;
	}

	/**
	 * Get the ant algorithm steps
	 *
	 * @return The ant algorithm steps
	 */
	public ArrayList<AntStep> getAntSteps() {
		return antSteps;
	}

	/**
	 * Set the ant algorithm steps to be drawn
	 *
	 * @param antSteps Ant steps to be drawn
	 */
	public void setAntSteps(ArrayList<AntStep> antSteps) {
		this.antSteps = antSteps;
		repaint();
	}

	/**
	 * Get the naive steps
	 *
	 * @return The naive steps
	 */
	public ArrayList<NaiveStep> getNaiveSteps() {
		return naiveSteps;
	}

	/**
	 * Set the naive steps to be drawn
	 *
	 * @param naiveSteps Naive steps to be drawn
	 */
	public void setNaiveSteps(ArrayList<NaiveStep> naiveSteps) {
		this.naiveSteps = naiveSteps;
		repaint();
	}

	/**
	 * Get the experimental swap steps
	 *
	 * @return Experimental steps to be drawn
	 */
	public ArrayList<ExperimentalStep> getExperimentalSteps() {
		return experimentalSteps;
	}

	/**
	 * Set the experimental steps to be drawn
	 *
	 * @param experimentalSteps Experimental steps to be drawn
	 */
	public void setExperimentalSteps(ArrayList<ExperimentalStep> experimentalSteps) {
		this.experimentalSteps = experimentalSteps;
		repaint();
	}

	/**
	 * Set the step number to draw
	 *
	 * @param stepNum Step number to draw
	 */
	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
		clearLabels();
		repaint();
	}

	/**
	 * Set the type of algorithm to draw
	 *
	 * @param type
	 */
	public void setAlgorithmType(AlgorithmType type) {
		this.type = type;
		repaint();
	}

	/**
	 * Get the explanation text for the given naive step number
	 *
	 * @param stepNum Naive step number
	 * @return Explanation text for the given naive step number
	 */
	public String getNaiveStepText(int stepNum) {
		return naiveSteps.get(stepNum).getText();
	}

	/**
	 * Get the current path
	 *
	 * @return The current path
	 */
	public ArrayList<Cell> getPath() {
		return path;
	}

	/**
	 * Set the current path
	 *
	 * @param path The current path
	 */
	public void setPath(ArrayList<Cell> path) {
		this.path = path;
	}

	/**
	 * Run a paint method appropriate to the current algorithm type
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		if (stepNum != -1) {
			switch (type) {
				case BEE:
					paintBeePath(g2);
					break;
				case ANT:
					paintAntPath(g2);
					break;
				case NEARESTNEIGHBOUR:
					paintNearestNeighbourPath(g2);
					break;
				case TWOOPT:
					paintTwoOptSwapPath(g2);
					break;
			}
		}
	}

	/**
	 * Get total number of steps for an algorithm
	 *
	 * @return The total number of steps for an algorithm
	 */
	public int getMaxStepNum() {
		switch (type) {
			case BEE:
				return ((naiveSteps.size() + experimentalSteps.size()));
			case ANT:
				return (antSteps.size() - 1);
			case NEARESTNEIGHBOUR:
				return naiveSteps.size();
			case TWOOPT:
				int expRuns = 3;
				if (tos != null) {
					expRuns *= tos.getRuns();
				}
				return (naiveSteps.size() + expRuns - 1);
		}
		return 0;
	}

	/**
	 * Remove all distance labels
	 */
	private void clearLabels() {
		for (JLabel label : distanceBoxes) {
			label.setVisible(false);
			remove(label);
		}
		distanceBoxes.clear();
	}

	/**
	 * Paint the two opt swap path
	 */
	private void paintTwoOptSwapPath(Graphics2D g2) {
		if (tos == null) {
			return;
		}

		Color lineColor = Color.blue;
		if (naiveSteps.size() > 0 && stepNum < naiveSteps.size()) {
			tos.setStepNum(stepNum);
			paintNearestNeighbourPath(g2);
		} else if (stepNum >= naiveSteps.size() && tos.getRuns() > (stepNum - naiveSteps.size()) / 3.0) {
			if ((stepNum - naiveSteps.size()) % 3 == 0) {
				lineColor = Color.white;
				tos.swap(stepNum);
				naiveSteps = (new StepController()).getNaiveSteps(tos.getPath());
			} else if ((stepNum - naiveSteps.size()) % 3 == 1) {
				lineColor = Color.yellow;
				naiveSteps = (new StepController()).getNaiveSteps(tos.getNewPath());
			} else if ((stepNum - naiveSteps.size()) % 3 == 2) {
				if (tos.swapSuccessful()) {
					lineColor = Color.green;
				} else {
					lineColor = Color.red;
				}
				naiveSteps = (new StepController()).getNaiveSteps(tos.getNewPath());
			}


			g2.setPaint(lineColor);
			g2.setStroke(new BasicStroke(5));

			Shape cellCircle = new Ellipse2D.Double(tos.getNode1()[0], tos.getNode1()[1], 2.0 * 25, 2.0 * 25);
			g2.draw(cellCircle);
			cellCircle = new Ellipse2D.Double(tos.getNode2()[0], tos.getNode2()[1], 2.0 * 25, 2.0 * 25);
			g2.draw(cellCircle);

			int x1, x2, y1, y2;
			for (NaiveStep step : naiveSteps) {
				x1 = (int) step.getStart().x;
				y1 = (int) step.getStart().y;
				x2 = (int) step.getEnd().x;
				y2 = (int) step.getEnd().y;
				g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
			}
		}
	}

	/**
	 * Paint the nearest neighbor path
	 */
	private void paintNearestNeighbourPath(Graphics2D g2) {
		if (naiveSteps.size() > 0 && stepNum <= naiveSteps.size()) {
			int x1, x2, y1, y2;

			for (int i = 0; i < stepNum + 1; i++) {
				if (i < naiveSteps.size()) {
					NaiveStep step = naiveSteps.get(i);
					x1 = (int) step.getStart().x;
					y1 = (int) step.getStart().y;

					ArrayList<Cell> available = step.getAvailable();

					if (i == stepNum) {
						if (available.size() > 0) {
							for (Cell anAvailable : available) {
								g2.setStroke(new BasicStroke(5));
								g2.setPaint(Color.red);
								x2 = (int) anAvailable.x;
								y2 = (int) anAvailable.y;
								g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
								String Distance = Integer.toString((int) Math.round((step.getStart().distance(anAvailable)) * 100) / 100);
								if (!(available.contains(step.getStart()))) {
									printDistance(Distance, anAvailable, step.getEnd() == anAvailable);
								} else if (anAvailable != step.getStart()) {
									printDistance(Distance, anAvailable, false);

								}
							}
						}
					}

					if (!(available.contains(step.getStart()))) {
						g2.setStroke(new BasicStroke(6));
						g2.setPaint(Color.green);
						x2 = (int) step.getEnd().x;
						y2 = (int) step.getEnd().y;
						g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
					} else {
						g2.setStroke(new BasicStroke(5));
						g2.setPaint(Color.red);
						x2 = (int) step.getEnd().x;
						y2 = (int) step.getEnd().y;
						g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
					}
				}
			}
		}
	}

	/**
	 * Paint the bee path
	 */
	private void paintBeePath(Graphics2D g2) {
		if (naiveSteps.size() > 0 && stepNum < naiveSteps.size()) {
			paintNearestNeighbourPath(g2);
		} else if (experimentalSteps.size() > 0 && stepNum >= naiveSteps.size() && stepNum < (naiveSteps.size() + experimentalSteps.size())) {
			try {
				int x1, x2, y1, y2;
				ExperimentalStep step = experimentalSteps.get(stepNum - naiveSteps.size());
				ArrayList<Cell> stepPath = step.getPath();
				for (int i = 0; i < stepPath.size() - 1; i++) {
					Color lineColor;
					if (step.getType() == SwapType.INSPECTED) {
						lineColor = Color.YELLOW;
						x1 = (int) stepPath.get(i).x;
						y1 = (int) stepPath.get(i).y;
						x2 = (int) stepPath.get(i + 1).x;
						y2 = (int) stepPath.get(i + 1).y;
					} else if (step.getType() == SwapType.BEST) {
						lineColor = Color.WHITE;
						x1 = (int) stepPath.get(i).x;
						y1 = (int) stepPath.get(i).y;
						x2 = (int) stepPath.get(i + 1).x;
						y2 = (int) stepPath.get(i + 1).y;
					} else if (step.getType() == SwapType.ACCEPTED) {
						lineColor = Color.GREEN;
						x1 = (int) stepPath.get(i).x;
						y1 = (int) stepPath.get(i).y;
						x2 = (int) stepPath.get(i + 1).x;
						y2 = (int) stepPath.get(i + 1).y;
					} else {
						lineColor = Color.RED;
						ExperimentalStep step2 = experimentalSteps.get(stepNum - naiveSteps.size() - 1);
						ArrayList<Cell> stepPath2 = step2.getPath();
						x1 = (int) stepPath2.get(i).x;
						y1 = (int) stepPath2.get(i).y;
						x2 = (int) stepPath2.get(i + 1).x;
						y2 = (int) stepPath2.get(i + 1).y;


					}

					g2.setPaint(lineColor);
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);

					Comparison<Cell, Cell> comparison = step.getCellsCompared();
					Cell cell1 = comparison.getCell1();
					Cell cell2 = comparison.getCell2();

					x1 = (int) cell1.getX();
					y1 = (int) cell1.getY();

					x2 = (int) cell2.getX();
					y2 = (int) cell2.getY();

					Shape cellCircle = new Ellipse2D.Double(x1, y1, 2.0 * 25, 2.0 * 25);
					g2.draw(cellCircle);
					cellCircle = new Ellipse2D.Double(x2, y2, 2.0 * 25, 2.0 * 25);
					g2.draw(cellCircle);

					double cost = step.getPathCost();
					String costString = "" + cost;
					g2.drawString(costString, 0, 0);
				}
			} catch (IndexOutOfBoundsException e) {
				stepNum = ((naiveSteps.size() - 1) + (experimentalSteps.size() - 1)) - 1;
				int x1, x2, y1, y2;
				ExperimentalStep step = experimentalSteps.get((stepNum - 1) - naiveSteps.size());
				ArrayList<Cell> stepPath = step.getPath();
				for (int i = 0; i < stepPath.size(); i++) {
					x1 = (int) stepPath.get(i).x;
					y1 = (int) stepPath.get(i).y;
					x2 = (int) stepPath.get(i + 1).x;
					y2 = (int) stepPath.get(i + 1).y;
					g2.setPaint(Color.white);
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
				}

			}

			//print the final path if at the end of the bee path process
		} else if (path.size() > 0 && stepNum >= (naiveSteps.size() + experimentalSteps.size()) - 2) {

			int x1, x2 = 0, y1, y2 = 0;


			for (int i = 0; i < path.size() - 1; i++) {

				x1 = (int) path.get(i).x;
				y1 = (int) path.get(i).y;
				x2 = (int) path.get(i + 1).x;
				y2 = (int) path.get(i + 1).y;

				g2.setPaint(Color.white);
				g2.setStroke(new BasicStroke(5));
				g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
			}
			g2.setPaint(new Color(255, 255, 0, 255));
			g2.setStroke(new BasicStroke(5));
			g2.drawLine(x2 + (50 / 2), y2 + (50 / 2), (int) path.get(0).x + (50 / 2), (int) path.get(0).y + (50 / 2));
		}
	}

	/**
	 * Paint the ant path
	 */
	private void paintAntPath(Graphics2D g2) {
		if (antSteps.size() > 0) {
			int x1, x2, y1, y2;

			AntStep step = antSteps.get(stepNum);
			ArrayList<Cell> path = step.getPath();

			CostMatrix matrix = step.getCostMatrix();
			double threshold = stepNum / 8;

			for (int i = 0; i < path.size(); i++) {
				x1 = (int) path.get(i).getX();
				y1 = (int) path.get(i).getY();
				for (int j = (i + 1); j < path.size(); j++) {
					x2 = (int) path.get(j).getX();
					y2 = (int) path.get(j).getY();
					Cell cell1 = matrix.getCell(path.get(i).getX(), path.get(i).getY());
					Cell cell2 = matrix.getCell(path.get(j).getX(), path.get(j).getY());
					Color lineColor = new Color(255, 255, 0, 0);
					if (!(matrix.getPheromone(cell1, cell2) < threshold)) {
						lineColor = new Color(255, 255, 0, (int) (Math.min(255, 10 +
								((255 / matrix.getMaxPheromone()) * matrix.getPheromone(cell1, cell2)))));
					}
					g2.setPaint(lineColor);
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
				}
			}
		}
	}

	/**
	 * Print distance labels for nodes
	 *
	 * @param distanceText Distance to be printed
	 * @param end          Cell to be printed under
	 * @param isChosenCell Whether the cell is chosen or not
	 */
	private void printDistance(String distanceText, Cell end, Boolean isChosenCell) {
		JLabel distance = new JLabel(distanceText + "cm", SwingConstants.CENTER);
		Color translucentBg = new Color(1, 1, 1, 0.5f);
		distance.setBackground(translucentBg);
		distance.setOpaque(true);
		LineBorder border = new LineBorder(translucentBg, 2, true);
		distance.setBorder(border);
		if (isChosenCell) {
			//If the distance to be printed is the end cell of a step (Cell chosen) then print the text
			//with the colour green
			distance.setForeground(Color.green);
		} else {
			//If the distance to be printed is not the end cell of a step then print the text with the 
			//colour red
			distance.setForeground(Color.red);
		}
		distance.setBounds((int) (end.x) - 5, (int) (end.y) + 50, 60, 20);
		add(distance);
		distanceBoxes.add(distance);
	}

	/**
	 * Set the two opt swap algorithm object
	 *
	 * @param tos The two opt swap algorithm object
	 */
	public void setTosObject(TwoOptSwap tos) {
		this.tos = tos;
	}

	/**
	 * Get the current step number
	 *
	 * @return The current step number
	 */
	public int getStepNumber() {
		return stepNum;
	}

}
