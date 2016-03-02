package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.TravellingSalesBee;
import com.teamc2.travellingsalesbee.gui.data.cells.CellDraggable;
import com.teamc2.travellingsalesbee.gui.Visualiser;
import com.teamc2.travellingsalesbee.gui.data.cells.CellFlower;
import com.teamc2.travellingsalesbee.gui.data.cells.CellHive;
import com.teamc2.travellingsalesbee.gui.data.cells.CellType;
import javafx.application.Platform;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.awt.Image.SCALE_SMOOTH;

public class PanelToolbox extends JPanel {
	private final JPanel panelMap;
	private final int width;
	private final int height;

	public PanelToolbox(PanelMap panelMap) {
		this.panelMap = panelMap;
		width = panelMap.getCellWidth();
		height = panelMap.getCellHeight();
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
		try {
			Graphics2D g2 = (Graphics2D) g;
			BufferedImage img = ImageIO.read(this.getClass().getResource("/assets/backgrounds/BrownBack150.png"));
			TexturePaint paint = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
			g2.setPaint(paint);
			g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addTools() {
		CellDraggable flowerToolCell = new CellDraggable(width, height, CellType.FLOWER);
		CellDraggable hiveToolCell = new CellDraggable(width, height, CellType.HIVE);

		Image flowerImg = new CellFlower().getImage();
		Image scaledFlowerImg = flowerImg.getScaledInstance(width, height, SCALE_SMOOTH);

		Image hiveImg = new CellHive().getImage();
		Image scaledHiveImg = hiveImg.getScaledInstance(width, height, SCALE_SMOOTH);


		flowerToolCell.setIcon(new ImageIcon(scaledFlowerImg));
		hiveToolCell.setIcon(new ImageIcon(scaledHiveImg));


		//Add buttons to the toolbox
		hiveToolCell.setPanel(panelMap);
		hiveToolCell.setBounds(0, 150, 100, 100);
		add(hiveToolCell);

		flowerToolCell.setPanel(panelMap);
		flowerToolCell.setBounds(0, 160 + height, 100, 100);
		add(flowerToolCell);
	}
}
