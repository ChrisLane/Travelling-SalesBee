package com.teamc2.travellingsalesbee.gui.elements;

import com.teamc2.travellingsalesbee.CellDrag;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PanelToolbox extends JPanel {
	private JPanel parent;
	private int width;
	private int height;

	public PanelToolbox(JPanel parent, int width, int height) {
		this.parent = parent;
		this.width = width;
		this.height = height;
		setBackground(Color.WHITE);

		JTextArea txtrDragElementsOnto = new JTextArea();
		txtrDragElementsOnto.setEditable(false);
		txtrDragElementsOnto.setWrapStyleWord(true);
		txtrDragElementsOnto.setLineWrap(true);
		txtrDragElementsOnto.setText("Drag elements onto the gridmap!");

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

	public void addTools() {
		CellDrag flowerToolCell = new CellDrag("", width, height, "FLOWER");
		CellDrag hiveToolCell = new CellDrag("", width, height, "HIVE");
		try {
			Image flowerImg = ImageIO.read(new File("target/classes/icons/Flower.png"));
			Image scaledFlowerImg = flowerImg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);

			Image hiveImg = ImageIO.read(new File("target/classes/icons/Hive.png"));
			Image scaledHiveImg = hiveImg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);


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
