package com.teamc2.travellingsalesbee.gui.elements;

import com.teamc2.travellingsalesbee.CellDrag;
import com.teamc2.travellingsalesbee.GridLine;
import com.teamc2.travellingsalesbee.algorithms.Bee;
import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PanelSettings extends JPanel {
	private final JPanel parent;
	private final PanelMap gridmap;
	private ArrayList<Cell> path;

	private ArrayList<Cell> beePath;
	
	/**
	 * @param parent Parent panel
	 * @param gridmap Gridmap panel
	 */
	public PanelSettings(JPanel parent, PanelMap gridmap) {
		this.parent = parent;
		this.gridmap = gridmap;
		
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		addSettingsInfo();
		addButtons();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {
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
	
	public ArrayList<Cell> getPath() {
		return path;
	}

	public void addButtons() {
		JButton btnNewButton = new JButton("RUN");
		btnNewButton.addActionListener(arg0 -> {
			
			//Get parent width and height
			int panelWidth = parent.getWidth();
			int panelHeight = parent.getHeight();

			System.out.print(panelWidth);
			System.out.print(panelHeight);
			System.out.println("Button pressed!");

			//Gets the amount of cells respective to the width and height of the map
			Map map = new Map(panelWidth, panelHeight); //Initialising Map with cellsX and cellsY as width and height of map

			//Add all cells to the map
			for (Component c : parent.getComponents()) {
				if (c instanceof CellDrag) {
					if (c.isEnabled() && ((CellDrag) c).getType().equals("FLOWER")) {
						map.setCell(c.getX(), c.getY(), Cell.CellType.FLOWER); //Add flower positions to map
					} else if (c.isEnabled() && ((CellDrag) c).getType().equals("HIVE")) {
						map.setCell(c.getX(), c.getY(), Cell.CellType.HIVE); //Add hive position to map
					}
				}
			}

			System.out.println("Pre-Bee"); //USE ATLEAST 3 FLOWERS
			Bee bee = new Bee(map, 26);
			System.out.println("pre path = bee");
			path = bee.getPath();
			this.gridmap.setPath(path);
			System.out.println("Path Cost: " + bee.getPathCost());
			
		});

		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnNewButton.setBounds(481, 7, 93, 29);

		add(btnNewButton);
	}

}
