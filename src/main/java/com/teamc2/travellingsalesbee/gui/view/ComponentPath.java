package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.gui.NaiveStep;
import com.teamc2.travellingsalesbee.gui.data.cells.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ComponentPath extends JComponent {

	private ArrayList<Cell> beePath = new ArrayList<>();
	private ArrayList<NaiveStep> naiveSteps = new ArrayList<>();
	private int stepNum = 0;

	public ComponentPath() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		setBounds(0, 0, screenWidth, screenHeight);
	}

	/**
	 * Set the path in algorithms
	 *
	 * @param path Path to set in algorithms
	 */
	public void setPath(ArrayList<Cell> path) {
		beePath = path;
		repaint();
	}

	//Naive visualisation
	public void setNaiveSteps(ArrayList<NaiveStep> steps) {
		naiveSteps = steps;
		repaint();
	}

	public void setStepNumber(int stepNum){
		this.stepNum = stepNum;
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		if (beePath.size() > 100) {

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

		} else if (naiveSteps.size() > 0) {
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
	}
}
