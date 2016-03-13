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
import static java.awt.Image.SCALE_SMOOTH;

public class PanelToolbox extends JPanel {
	private final PanelMap panelMap;
	private final int width;
	private final int height;
	private AlgorithmType type;

	public PanelToolbox(PanelMap panelMap, AlgorithmType type) {
		this.panelMap = panelMap;
		width = panelMap.getCellWidth();
		height = panelMap.getCellHeight();
		this.type = type;
		setBackground(Color.WHITE);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(event -> Platform.runLater(() -> {
			Visualiser.mainVisualiser.setVisible(false);
			TravellingSalesBee.mainMenu.show();
		}));

		LayoutToolbox layoutToolbox = new LayoutToolbox(this, backButton);
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
		CellDraggable nodeToolCell = new CellDraggable(width, height, CellType.NODE, panelMap, type);
		CellDraggable originToolCell = new CellDraggable(width, height, CellType.ORIGIN, panelMap, type);

		Image flowerImg = new CellNode().getImage(type);
		Image scaledFlowerImg = flowerImg.getScaledInstance(width, height, SCALE_SMOOTH);

		Image hiveImg = new CellOrigin().getImage(type);
		Image scaledHiveImg = hiveImg.getScaledInstance(width, height, SCALE_SMOOTH);


		nodeToolCell.setIcon(new ImageIcon(scaledFlowerImg));
		originToolCell.setIcon(new ImageIcon(scaledHiveImg));


		//Add buttons to the toolbox
		originToolCell.setBounds(0, height, 100, 100);
		add(originToolCell);

		nodeToolCell.setBounds(0, height * 2 + 10, 100, 100);
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


}
