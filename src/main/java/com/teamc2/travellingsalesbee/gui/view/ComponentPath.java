package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.algorithms.TwoOptSwap;
import com.teamc2.travellingsalesbee.algorithms.cost.Comparison;
import com.teamc2.travellingsalesbee.algorithms.cost.CostMatrix;
import com.teamc2.travellingsalesbee.gui.AntStep;
import com.teamc2.travellingsalesbee.gui.ExperimentalStep;
import com.teamc2.travellingsalesbee.gui.NaiveStep;
import com.teamc2.travellingsalesbee.gui.SwapType;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;
import com.teamc2.travellingsalesbee.visualisation.StepController;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class ComponentPath extends JComponent {

	private ArrayList<Cell> beePath = new ArrayList<>();
	private ArrayList<NaiveStep> naiveSteps = new ArrayList<>();
	private int stepNum = 0;
	private ArrayList<ExperimentalStep> experimentalSteps = new ArrayList<>();
	private AlgorithmType type;
	private ArrayList<AntStep> antSteps = new ArrayList<>();
	private Map map;
	private TwoOptSwap tos;
	private ArrayList<JLabel> distanceBoxes = new ArrayList<>();

	public ComponentPath(AlgorithmType type) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		setBounds(0, 0, screenWidth, screenHeight);
		this.type = type;
	}

	/**
	 * Set the path in algorithms
	 *
	 * @param path Path to set in algorithms
	 */
	public void setPath(ArrayList<Cell> path) {
		ArrayList<Cell> steppedPath = new ArrayList<>();
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setAntSteps(ArrayList<AntStep> antSteps) {
		this.antSteps = antSteps;
		repaint();
	}

	//Naive visualisation
	public void setNaiveSteps(ArrayList<NaiveStep> steps) {
		naiveSteps = steps;
		repaint();
	}

	public ArrayList<NaiveStep> getNaiveSteps() {
		return this.naiveSteps;
	}

	public void setExperimentalSteps(ArrayList<ExperimentalStep> experimentalSteps) {
		this.experimentalSteps = experimentalSteps;
		repaint();
	}

	public ArrayList<ExperimentalStep> getExperimentalSteps() {
		return this.experimentalSteps;
	}

	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
		repaint();
	}

	public void setAlgorithmType(AlgorithmType type) {
		this.type = type;
		this.repaint();
	}

	public String getNaiveStepText(int stepNum) {
		return this.naiveSteps.get(stepNum).getText();
	}

	public ArrayList<Cell> getBeePath() {
		return beePath;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		clearLabels();

		switch (this.type) {
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

		if (beePath.size() > 0 && stepNum >= (naiveSteps.size() + experimentalSteps.size()) - 2) {

			int x1, x2 = 0, y1, y2 = 0;
			int transparencyIncrement = Math.round(170 / beePath.size());
			int transparency = 0;
			int lineThicknessIncrement = Math.round(4 / beePath.size());
			int lineThickness = 5;
			for (int i = 0; i < beePath.size() - 1; i++) {

				x1 = (int) beePath.get(i).x;
				y1 = (int) beePath.get(i).y;
				x2 = (int) beePath.get(i + 1).x;
				y2 = (int) beePath.get(i + 1).y;
				Color lineColor = new Color(255, 255, 0, 75 + transparency);

				g2.setPaint(lineColor);
				g2.setStroke(new BasicStroke(lineThickness));
				g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
				String position = Integer.toString(i);
				g2.setPaint(Color.white);
				g2.setFont(new Font(null, Font.PLAIN, 15));
				g2.drawString(position, x1 + (50 / 12), y1 + (50 / 4));

				lineThickness = lineThickness - lineThicknessIncrement;
				transparency += transparencyIncrement;
			}
			g2.setPaint(new Color(255, 255, 0, 75 + transparency + 10));
			g2.setStroke(new BasicStroke(5));
			g2.drawLine(x2 + (50 / 2), y2 + (50 / 2), (int) beePath.get(0).x + (50 / 2), (int) beePath.get(0).y + (50 / 2));

		}
	}

	private void clearLabels() {
		distanceBoxes.forEach(this::remove);

	}

	private void paintTwoOptSwapPath(Graphics2D g2) {

		if (tos == null) {
			return;
		}

		if (naiveSteps.size() > 0 && stepNum < naiveSteps.size()) {
			int x1, x2, y1, y2;
			tos.setStepNum(stepNum);
		} else if (stepNum >= naiveSteps.size()) {
			tos.swap(stepNum);
			naiveSteps = (new StepController()).getNaiveSteps(tos.getPath());
			setPath(tos.getPath());
		}

		int x1, x2, y1, y2;
		for (int i = 0; i < stepNum + 1; i++) {
			if (i < naiveSteps.size()) {
				NaiveStep step = naiveSteps.get(i);
				x1 = (int) step.getStart().x;
				y1 = (int) step.getStart().y;

				ArrayList<Cell> available = step.getAvailable();

				if (i == stepNum) {
					for (Cell anAvailable : available) {
						g2.setStroke(new BasicStroke(5));
						g2.setPaint(Color.red);
						x2 = (int) anAvailable.x;
						y2 = (int) anAvailable.y;
						g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
					}
				}

				g2.setStroke(new BasicStroke(6));
				g2.setPaint(Color.green);
				x2 = (int) step.getEnd().x;
				y2 = (int) step.getEnd().y;
				g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
			}
		}
	}

	private void paintNearestNeighbourPath(Graphics2D g2) {
		if (naiveSteps.size() > 0 && stepNum < naiveSteps.size()) {
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
									if (step.getEnd() == anAvailable) {
										printDistance(Distance, anAvailable, g2, true);
									} else {
										printDistance(Distance, anAvailable, g2, false);
									}
								} else {
									printDistance(Distance, anAvailable, g2, false);

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

	private void paintBeePath(Graphics2D g2) {
		if (naiveSteps.size() > 0 && stepNum < naiveSteps.size()) {
			paintNearestNeighbourPath(g2);
		} else if (experimentalSteps.size() > 0 && stepNum >= naiveSteps.size()) {
			try{
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
					} else if (step.getType() == SwapType.BEST){
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
			}catch (IndexOutOfBoundsException e){
				stepNum = ((naiveSteps.size()-1) + (experimentalSteps.size()-1))-1;
				int x1, x2, y1, y2;
				ExperimentalStep step = experimentalSteps.get((stepNum-1) - naiveSteps.size());
				ArrayList<Cell> stepPath = step.getPath();
				for (int i=0;i<stepPath.size();i++){
					x1 = (int) stepPath.get(i).x;
					y1 = (int) stepPath.get(i).y;
					x2 = (int) stepPath.get(i + 1).x;
					y2 = (int) stepPath.get(i + 1).y;
					g2.setPaint(Color.white);
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
				}
				
			}
			
		}
			
	}
	

	private void paintAntPath(Graphics2D g2) {
		if (this.antSteps.size() > 0) {
			g2.setPaint(Color.black);
			g2.setStroke(new BasicStroke(5));
			g2.drawLine(0, 0, 50, 50);

			int x1, x2, y1, y2;
			//Fix this stepNum + 1 it thinks it's -1 so had to add 1
			AntStep step = antSteps.get(stepNum + 1);
			ArrayList<Cell> path = step.getPath();

			CostMatrix matrix = step.getCostMatrix();

			for (int i = 0; i < path.size(); i++) {
				x1 = (int) path.get(i).getX();
				y1 = (int) path.get(i).getY();
				for (int j = (i + 1); j < path.size(); j++) {
					x2 = (int) path.get(j).getX();
					y2 = (int) path.get(j).getY();
					System.out.println(i + " -> " + j + " " + matrix.getPheromone(path.get(i), path.get(j)));
					Color lineColor = new Color(255, 255, 0, (int) (((255 / matrix.getMaxPheromone())) * matrix.getPheromone(path.get(i), path.get(j))));
					g2.setPaint(lineColor);
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(x1 + 25, y1 + 25, x2 + 25, y2 + 25);
				}
			}
		}

	}

	//Code to print the distance boxes on top of flowers
	private void printDistance(String text, Cell end, Graphics2D g2, Boolean printOnTopOfLine) {
		JLabel distance = new JLabel(text);
		distance.setBackground(Color.white);
		distance.setOpaque(true);
		if (printOnTopOfLine) {
			distance.setForeground(Color.green);
		} else {
			distance.setForeground(Color.red);
		}
		distance.setBounds((int) (end.x)+15, (int) (end.y) + 15, 30, 20);
		this.add(distance);
		this.setComponentZOrder(distance, 1);
		this.distanceBoxes.add(distance);
	}

	public void setTosObject(TwoOptSwap tos) {
		this.tos = tos;
	}

}
