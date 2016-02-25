package com.teamc2.travellingsalesbee.gui.elements;

import com.teamc2.travellingsalesbee.algorithms.Bee;
import com.teamc2.travellingsalesbee.gui.CellDrag;
import com.teamc2.travellingsalesbee.gui.elements.cells.Cell;
import com.teamc2.travellingsalesbee.gui.elements.cells.CellType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class PanelSettings extends JPanel {
	private final JPanel parent;
	private final PanelMap gridmap;
	private ArrayList<Cell> path;

	private ArrayList<Cell> beePath;
	private JLabel infoLabel;

	private int experimentalRuns = 26; //Set to 26 by default
	
	// Remove later
	protected boolean debug = true;

	/**
	 * Create a settings panel
	 *
	 * @param parent  Parent panel
	 * @param gridmap Gridmap panel
	 */
	public PanelSettings(JPanel parent, PanelMap gridmap) {
		this.parent = parent;
		this.gridmap = gridmap;

		setBackground(Color.LIGHT_GRAY);
		addSettingsInfo();
		addButtons();
	}

	/**
	 * Add the background to the settings panel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {
			BufferedImage img = ImageIO.read(this.getClass().getResource("/assets/backgrounds/GreyBack150.png"));
			TexturePaint paint = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
			g2.setPaint(paint);
			g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add information on how to use the settings panel
	 */
	public void addSettingsInfo() {
		infoLabel = new JLabel("Settings box, we also need to choose a background image for this");
	}

	public ArrayList<Cell> getPath() {
		return path;
	}

	/**
	 * Add the settings buttons to the panel
	 */
	public void addButtons() {
		JButton btnRun = new JButton("RUN");
		btnRun.addActionListener(arg0 -> {

			//Get parent width and height
			int panelWidth = parent.getWidth();
			int panelHeight = parent.getHeight();

			if (debug) System.out.print(panelWidth);
			if (debug) System.out.print(panelHeight);
			if (debug) System.out.println("Button pressed!");

			//Gets the amount of cells respective to the width and height of the map
			Map map = new Map(panelWidth, panelHeight); //Initialising Map with cellsX and cellsY as width and height of map

			//Add all cells to the map
			for (Component c : parent.getComponents()) {
				if (c instanceof CellDrag) {
					if (c.isEnabled() && ((CellDrag) c).getType().equals("FLOWER")) {
						map.setCell(c.getX(), c.getY(), CellType.FLOWER); //Add flower positions to map
					} else if (c.isEnabled() && ((CellDrag) c).getType().equals("HIVE")) {
						map.setCell(c.getX(), c.getY(), CellType.HIVE); //Add hive position to map
					}
				}
			}

			if (debug) System.out.println("Pre-Bee"); //USE ATLEAST 3 FLOWERS
			Bee bee = new Bee(map, experimentalRuns);
			if (debug) System.out.println("pre path = bee");
			path = bee.getPath();
			this.gridmap.setPath(path);
			if (debug) System.out.println("Path Cost: " + bee.getPathCost());

		});

		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JSlider slider = new JSlider();
		slider.setValue(experimentalRuns);
		JLabel lblExperimentRuns = new JLabel("Experiment Runs:");
		JLabel lblNoOfRuns = new JLabel("" + experimentalRuns);

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (debug ) System.out.println("Slider Changed");
				slider.setValue(slider.getValue());
				lblNoOfRuns.setText("" + slider.getValue());
				experimentalRuns = slider.getValue();
			}

		});

		JTextPane txtpnTextWillAppear = new JTextPane();
		txtpnTextWillAppear.setText("Text will appear here as you step through the algorithm, explaining how it works at each step");

		JButton btnPrev = new JButton("<-");
		JButton btnNext = new JButton("->");

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(infoLabel, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(lblExperimentRuns)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lblNoOfRuns))
										.addComponent(slider, GroupLayout.PREFERRED_SIZE, 457, GroupLayout.PREFERRED_SIZE))
								.addGap(10)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
												.addComponent(btnPrev)
												.addGap(18)
												.addComponent(btnNext))
										.addComponent(txtpnTextWillAppear, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
								.addContainerGap())
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(infoLabel)
								.addGap(18)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblExperimentRuns)
										.addComponent(lblNoOfRuns))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(211, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnPrev, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnRun))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtpnTextWillAppear, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
								.addGap(69))
		);
		setLayout(groupLayout);
	}
}
