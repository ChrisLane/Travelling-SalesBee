package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.TravellingSalesBee;
import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.gui.Visualiser;
import com.teamc2.travellingsalesbee.gui.data.cells.CellDraggable;
import com.teamc2.travellingsalesbee.gui.data.cells.CellNode;
import com.teamc2.travellingsalesbee.gui.data.cells.CellOrigin;
import com.teamc2.travellingsalesbee.gui.data.cells.CellType;
import com.teamc2.travellingsalesbee.gui.view.layouts.LayoutToolbox;
import javafx.application.Platform;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.awt.Image.SCALE_SMOOTH;

public class PanelToolbox extends JPanel {
	public final static String name = "PanelToolbox";
	private final PanelMap panelMap;
	private final int cellWidth;
	private final int cellHeight;
	private AlgorithmType type;

	public PanelToolbox(PanelMap panelMap, AlgorithmType type) {
		this.panelMap = panelMap;
		cellWidth = panelMap.getCellWidth();
		cellHeight = panelMap.getCellHeight();
		this.type = type;

		setName(name);
		setBackground(Color.WHITE);
	
		JButton backButton = new JButton("Back");
		backButton.addActionListener(event -> Platform.runLater(() -> {
			Visualiser.mainVisualiser.setVisible(false);
			TravellingSalesBee.mainMenu.show();
		}));
		
		JButton randomiseButton = new JButton("Randomise Map");
		randomiseButton.addActionListener(arg0 -> {
			randomise();
			((GuiContainer) getRootPane()).getComponentTextArea().addText("Map Randomised!");
		});
		
		JButton clearButton = new JButton("Clear Map");
		clearButton.addActionListener(arg0 -> {
			panelMap.clear();
			panelMap.repaint();
			((GuiContainer) getRootPane()).getComponentTextArea().addText("Map Cleared!");
		});

		LayoutToolbox layoutToolbox = new LayoutToolbox(this, backButton, randomiseButton, clearButton);
		setLayout(layoutToolbox);

		addTools();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(new Color(71, 35, 30));
		g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));
	}

	public void addTools() {
		CellDraggable nodeToolCell = new CellDraggable(cellWidth, cellHeight, CellType.NODE, panelMap, type);
		CellDraggable originToolCell = new CellDraggable(cellWidth, cellHeight, CellType.ORIGIN, panelMap, type);

		nodeToolCell.setIcon(new ImageIcon(nodeToolCell.getImage(CellType.NODE)));
		originToolCell.setIcon(new ImageIcon(originToolCell.getImage(CellType.ORIGIN)));


		//Add buttons to the toolbox
		originToolCell.setBounds(0, cellHeight * 3, 100, 100);
		add(originToolCell);

		nodeToolCell.setBounds(0, cellHeight * 4 + 10, 100, 100);
		add(nodeToolCell);
	}

	/**
	 * @param type The type of algorithm being viewed to adjust the icons of the toolbox
	 *             draggable cells
	 */
	public void setAlgorithmType(AlgorithmType type) {
		this.type = type;

		for (Component c : this.getComponents()) {
			if (c instanceof CellDraggable) {
				((CellDraggable) c).setAlgorithmType(type);
				((CellDraggable) c).setImage(((CellDraggable) c).getType());
			}
		}
	}

	public void randomise() {
		panelMap.clear();
		
		int maxX = (panelMap.getWidth()) / cellWidth;
		int maxY = (panelMap.getHeight()) / cellHeight;
		
		int x = 0;
		int y = 0;
		CellDraggable newCell = null;

		int nodesPlaced = 0;
		while(nodesPlaced < 12) {
			
			x = ThreadLocalRandom.current().nextInt(0, maxX) * cellWidth;
			y = ThreadLocalRandom.current().nextInt(0, maxY) * cellHeight;
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
			panelMap.setComponentZOrder(newCell, 0);
			
			nodesPlaced++;
		} 
		
		panelMap.repaint();
	}
	
}
