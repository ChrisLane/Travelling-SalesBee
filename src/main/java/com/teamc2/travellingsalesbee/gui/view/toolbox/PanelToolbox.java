package com.teamc2.travellingsalesbee.gui.view.toolbox;

import com.teamc2.travellingsalesbee.TravellingSalesBee;
import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.gui.Visualiser;
import com.teamc2.travellingsalesbee.gui.data.cells.CellType;
import com.teamc2.travellingsalesbee.gui.view.layouts.LayoutToolbox;
import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;
import javafx.application.Platform;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * A container for elements to be dragged on to the map
 *
 * @author Christopher Lane (cml476)
 * @author Melvyn Mathews (mxm499)
 */
public class PanelToolbox extends JPanel {
	public final static String name = "PanelToolbox";
	private final PanelMap panelMap;
	private final int cellWidth;
	private final int cellHeight;
	private AlgorithmType type;

	/**
	 * Construct a toolbox
	 *
	 * @param panelMap The map panel
	 * @param type     The algorithm type
	 */
	public PanelToolbox(PanelMap panelMap, AlgorithmType type) {
		this.panelMap = panelMap;
		this.type = type;
		cellWidth = panelMap.getCellWidth();
		cellHeight = panelMap.getCellHeight();

		setName(name);
		setBackground(Color.WHITE);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(event -> Platform.runLater(() -> {
			Visualiser.mainVisualiser.setVisible(false);
			TravellingSalesBee.mainMenu.show();
		}));

		JLabel imgDrag = null;
		JLabel imgKey = null;
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResource("/assets/icons/dragMe.png"));
			if (img != null) imgDrag = new JLabel(new ImageIcon(img));
			img = ImageIO.read(getClass().getResource("/assets/icons/Key.png"));
			if (img != null) imgKey = new JLabel(new ImageIcon(img));
		} catch (IOException e) {
			e.printStackTrace();
		}

		LayoutToolbox layoutToolbox = new LayoutToolbox(this, backButton, imgDrag, imgKey);
		setLayout(layoutToolbox);

		addTools();
	}

	/**
	 * Paint the toolbox and its elements
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(new Color(71, 35, 30));
		g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));
	}

	/**
	 * Add the toolbox tools to the toolbox
	 */
	public void addTools() {
		CellDraggable nodeToolCell = new CellDraggable(cellWidth, cellHeight, CellType.NODE, panelMap, type, false);
		CellDraggable originToolCell = new CellDraggable(cellWidth, cellHeight, CellType.ORIGIN, panelMap, type, false);

		nodeToolCell.setIcon(new ImageIcon(nodeToolCell.getImage(CellType.NODE)));
		originToolCell.setIcon(new ImageIcon(originToolCell.getImage(CellType.ORIGIN)));

		//Add buttons to the toolbox
		originToolCell.setBounds(-20, 160, 100, 100);
		add(originToolCell);

		JLabel lblOrigin = new JLabel("Origin");
		lblOrigin.setFont(new Font("Amatic Bold", Font.PLAIN, 25));
		lblOrigin.setForeground(Color.WHITE);
		lblOrigin.setBounds(65, 180, 100, 50);
		add(lblOrigin);

		nodeToolCell.setBounds(-20, 215, 100, 100);
		add(nodeToolCell);

		JLabel lblNode = new JLabel("Node");
		lblNode.setFont(new Font("Amatic Bold", Font.PLAIN, 25));
		lblNode.setForeground(Color.WHITE);
		lblNode.setBounds(65, 240, 100, 50);
		add(lblNode);
	}

	/**
	 * Set the algorithm type
	 *
	 * @param type The type of algorithm being viewed to adjust the icons of the toolbox draggable cells
	 */
	public void setAlgorithmType(AlgorithmType type) {
		this.type = type;

		for (Component c : getComponents()) {
			if (c instanceof CellDraggable) {
				((CellDraggable) c).setAlgorithmType(type);
				((CellDraggable) c).setImage(((CellDraggable) c).getType());
			}
		}
	}
}
