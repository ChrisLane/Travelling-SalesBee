package com.teamc2.travellingsalesbee;

import com.teamc2.travellingsalesbee.gui.elements.PanelMap;
import com.teamc2.travellingsalesbee.gui.elements.PanelSettings;
import com.teamc2.travellingsalesbee.gui.elements.PanelToolbox;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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

		JPanel panel_gridmap = new PanelMap(width, height);
		JPanel panel_toolbox = new PanelToolbox(panel_gridmap, width, height);
		JPanel panel_settings = new PanelSettings(panel_gridmap);

		//ADD GRID TO THE GRIDMAP
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
		contentPane.setBackground(new Color(71, 35, 35));

		contentPane.setLayout(gl_contentPane);
	}

	public static void addTiledBgImg(JPanel panel, Image img, int width, int height) { //Adds tiles image to panel and returns it
		for (int x = 0; x < 3840; x += width) {
			for (int y = 0; y < 2160; y += height) {
				JLabel bg = new JLabel(new ImageIcon(img));

				bg.setBounds(x, y, width, height); //Grass.jpg has dimensions of: 257 x 172
				panel.add(bg);
			}
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		// There is a new path! Visualise it
	}
}
