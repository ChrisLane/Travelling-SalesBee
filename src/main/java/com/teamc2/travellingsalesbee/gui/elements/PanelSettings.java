package com.teamc2.travellingsalesbee.gui.elements;

import com.teamc2.travellingsalesbee.CellDrag;
import com.teamc2.travellingsalesbee.algorithms.Bee;
import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PanelSettings extends JPanel {
	private final JPanel parent;

	public PanelSettings(JPanel parent) {
		this.parent = parent;
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		addSettingsInfo();
		addButtons();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			Graphics2D g2 = (Graphics2D) g;
			BufferedImage img = ImageIO.read(new File("target/classes/backgrounds/GreyBack150.png"));
			TexturePaint paint = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
			g2.setPaint(paint);
			g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addSettingsInfo() {
		JLabel infoLabel = new JLabel("Settings box, we also need to choose a background image for this");
		infoLabel.setBounds(10, 11, 350, 14);
		add(infoLabel);
	}

	public void addButtons() {
		JButton btnNewButton = new JButton("RUN");
		btnNewButton.addActionListener(arg0 -> {
			int panelWidth = parent.getWidth();
			int panelHeight = parent.getHeight();

			System.out.print(panelWidth);
			System.out.print(panelHeight);
			System.out.println("Button pressed!");

			//Gets the amount of cells respective to the width and height of the map
			Map map = new Map(panelWidth, panelHeight); //Initialising Map with cellsX and cellsY as width and height of map

			for (Component c : parent.getComponents()) {
				if (c instanceof CellDrag) {
					if (c.isEnabled() && ((CellDrag) c).getType().equals("FLOWER")) {
						System.out.println("c.getX(): " + c.getX() + "c.getY(): " + c.getY());
						System.out.println("Width of panel: " + parent.getWidth() + ", height of panel: " + parent.getHeight());

						map.setCell(c.getX(), c.getY(), Cell.CellType.FLOWER); //Add flower positions to map

					} else if (c.isEnabled() && ((CellDrag) c).getType().equals("HIVE")) {

						map.setCell(c.getX(), c.getY(), Cell.CellType.HIVE); //Add hive position to map

					}
				}
			}

			Bee bee = new Bee(map, map.getHive(), 26);
			ArrayList<Cell> beePath = bee.getPath();
			System.out.println(bee.getPathCost());

			for (Cell cell : beePath) {
				System.out.println(cell.x / 50 + " " + cell.y / 50);
			}
		});

		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnNewButton.setBounds(481, 7, 93, 29);

		add(btnNewButton);
	}
}
