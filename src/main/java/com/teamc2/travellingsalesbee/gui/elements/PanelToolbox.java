package com.teamc2.travellingsalesbee.gui.elements;

import com.teamc2.travellingsalesbee.gui.CellDrag;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.Image.SCALE_SMOOTH;

public class PanelToolbox extends JPanel {
	private final JPanel parent;
	private final int width;
	private final int height;

	public PanelToolbox(PanelMap parent) {
		this.parent = parent;
		this.width = parent.getGridWidth();
		this.height = parent.getGridHeight();
		setBackground(Color.WHITE);

		JTextArea txtrDragElementsOnto = new JTextArea();
		txtrDragElementsOnto.setEditable(false);
		txtrDragElementsOnto.setWrapStyleWord(true);
		txtrDragElementsOnto.setLineWrap(true);
		txtrDragElementsOnto.setText("Toolbox");

		GroupLayout gl_panel_toolbox = new GroupLayout(this);
		gl_panel_toolbox.setHorizontalGroup(
				gl_panel_toolbox.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(txtrDragElementsOnto, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
		);
		gl_panel_toolbox.setVerticalGroup(
				gl_panel_toolbox.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(gl_panel_toolbox.createSequentialGroup()
								.addComponent(txtrDragElementsOnto, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addContainerGap(330, Short.MAX_VALUE))
		);

		setLayout(gl_panel_toolbox);

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
		CellDrag flowerToolCell = new CellDrag("", width, height, "FLOWER");
		CellDrag hiveToolCell = new CellDrag("", width, height, "HIVE");

		try {
			Image flowerImg = ImageIO.read(this.getClass().getResource("/assets/icons/Flower.png"));
			Image scaledFlowerImg = flowerImg.getScaledInstance(width, height, SCALE_SMOOTH);

			Image hiveImg = ImageIO.read(this.getClass().getResource("/assets/icons/Hive.png"));
			Image scaledHiveImg = hiveImg.getScaledInstance(width, height, SCALE_SMOOTH);


			flowerToolCell.setIcon(new ImageIcon(scaledFlowerImg));
			hiveToolCell.setIcon(new ImageIcon(scaledHiveImg));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		flowerToolCell.setPanel(parent);
		flowerToolCell.setBounds(0, 150, width, height);
		hiveToolCell.setPanel(parent);
		hiveToolCell.setBounds(0, 155 + width, width, height);

		add(flowerToolCell);
		add(hiveToolCell);
	}
}
