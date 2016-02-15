package com.teamc2.travellingsalesbee;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class TSB extends JFrame implements Observer {

	/**
	 *
	 */
	private JPanel contentPane;
	private int width = 50;
	private int height = 50;
	private boolean hiveSet = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				TSB frame = new TSB();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TSB() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panel_toolbox = new JPanel();
		panel_toolbox.setBackground(Color.WHITE);

		JPanel panel_settings = new JPanel();
		panel_settings.setBackground(Color.LIGHT_GRAY);


		//ADD GRID TO THE GRIDMAP
		JPanel panel_gridmap = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(panel_toolbox, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(panel_settings, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
										.addComponent(panel_gridmap, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)))
		);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(panel_gridmap, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panel_settings, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
						.addComponent(panel_toolbox, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
		);

		panel_gridmap = genGrid(panel_gridmap);

		panel_settings.setLayout(null);

		JLabel lblSettingsBoxWe = new JLabel("Settings box, we also need to choose a background image for this");
		lblSettingsBoxWe.setBounds(10, 11, 350, 14);
		panel_settings.add(lblSettingsBoxWe);
		panel_gridmap.setLayout(null);


		JTextArea txtrDragElementsOnto = new JTextArea();
		txtrDragElementsOnto.setEditable(false);
		txtrDragElementsOnto.setWrapStyleWord(true);
		txtrDragElementsOnto.setLineWrap(true);
		txtrDragElementsOnto.setText("Drag elements onto the gridmap!");

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
		}
		flowerToolCell.setPanel(panel_gridmap);
		flowerToolCell.setBounds(0, 150, width, height);
		hiveToolCell.setPanel(panel_gridmap);
		hiveToolCell.setBounds(0, 155 + height, width, height);
		panel_toolbox.add(flowerToolCell);
		panel_toolbox.add(hiveToolCell);
		System.out.println(panel_gridmap.getX());

		/*****BACKGROUNDS*****/
		//Adding background to panel_gridmap and tiling it using a nested for loop
		Image background;
		try {
			background = ImageIO.read(new File("target/classes/backgrounds/grass.jpg"));
			addTiledBgImg(panel_gridmap, background, 256, 172);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Background Images for Toolbox
		Image toolbox;
		try {
			toolbox = ImageIO.read(new File("target/classes/backgrounds/brownBack150.png"));
			addTiledBgImg(panel_toolbox, toolbox, 150, 150);

		} catch (IOException e) {
			e.printStackTrace();
		}

		//Background image for Settings
		Image settings;
		try {
			settings = ImageIO.read(new File("target/classes/backgrounds/greyBack150.png"));
			addTiledBgImg(panel_settings, settings, 150, 150);
			
			JButton btnNewButton = new JButton("RUN");
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
			btnNewButton.setBounds(481, 7, 93, 29);
			panel_settings.add(btnNewButton);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Background image for mother panel
		contentPane.setBackground(new Color(71, 35, 35));

		GroupLayout gl_panel_toolbox = new GroupLayout(panel_toolbox);
		gl_panel_toolbox.setHorizontalGroup(
				gl_panel_toolbox.createParallelGroup(Alignment.LEADING)
						.addComponent(txtrDragElementsOnto, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
		);
		gl_panel_toolbox.setVerticalGroup(
				gl_panel_toolbox.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_toolbox.createSequentialGroup()
								.addComponent(txtrDragElementsOnto, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addContainerGap(330, Short.MAX_VALUE))
		);
		panel_toolbox.setLayout(gl_panel_toolbox);
		contentPane.setLayout(gl_contentPane);

		
		/*frame.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
		        // do stuff           
		    	System.out.println("Window is re-sized");
		    }

			@Override
			public void componentHidden(ComponentEvent e) {
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				
			}
		});*/
	}

	public JPanel genGrid(JPanel panel_gridmap) {
		int widthCount = 0; //Keeps track of current horizontal line we're drawing
		int heightCount = 0;//Keeps track of current vertical we're drawing

		//Gets the width and height of the users screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		//Custom translucent colour
		Color lineColor = new Color(255, 255, 255, 65);

		//While the widthCount is less than the width of the users screen, draw horizontal lines
		while (widthCount < screenWidth) {

			@SuppressWarnings("serial")
			GridLine gridLine = new GridLine(widthCount, 0, widthCount, Integer.MAX_VALUE);
			gridLine.setBackground(lineColor);
			gridLine.setBounds(widthCount, 0, 3, Integer.MAX_VALUE);
			panel_gridmap.add(gridLine);

			widthCount += width;
		}

		//While the heightCount is less than the height of the users screen, draw vertical lines
		while (heightCount < screenHeight) {


			@SuppressWarnings("serial")
			GridLine gridLine = new GridLine(0, heightCount, Integer.MAX_VALUE, heightCount);
			gridLine.setBackground(lineColor);
			gridLine.setBounds(0, heightCount, Integer.MAX_VALUE, 3);
			panel_gridmap.add(gridLine);

			heightCount += height;
		}
		return panel_gridmap;
	}

	public static void addTiledBgImg(JPanel panel, Image img, int width, int height) { //Adds tiles image to panel and returns it
		for (int x = 0; x < 3840; x += width) {
			for (int y = 0; y < 2160; y += height) {
				JLabel bg = new JLabel(new ImageIcon(img));

				bg.setBounds(x, y, width, height); //grass.jpg has dimensions of: 257 x 172
				panel.add(bg);
			}
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		// Run method to display new best path
	}
}
