package com.teamc2.travellingsalesbee.gui;

import com.teamc2.travellingsalesbee.gui.elements.PanelMap;
import com.teamc2.travellingsalesbee.gui.elements.PanelSettings;
import com.teamc2.travellingsalesbee.gui.elements.PanelToolbox;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Observable;
import java.util.Observer;

public class Visualiser extends JFrame implements Observer {

	private final static int roundingFactor = 50;
	private static PanelMap panelMap;
	private final JPanel contentPane;
	private final int width = 50;
	private final int height = 50;
	private boolean hiveSet = false;

	/**
	 * Create the frame.
	 */
	public Visualiser() {

		//Set the UIManager look and feel to the system default
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(300, 300, 956, 689);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(71, 35, 35));
		setContentPane(contentPane);


		PanelMap panelMap = new PanelMap(width, height);
		this.panelMap = panelMap;
		JPanel panelToolbox = new PanelToolbox(panelMap);
		JPanel panelSettings = new PanelSettings(panelMap, panelMap);
		//ADD GRID TO THE GRIDMAP
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(panelToolbox, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(panelSettings, GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
										.addComponent(panelMap, GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)))
		);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(panelMap, GroupLayout.PREFERRED_SIZE, 446, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelSettings, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(105, Short.MAX_VALUE))
						.addComponent(panelToolbox, GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
		);

		contentPane.setLayout(gl_contentPane);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//Temporary variables, should be removed and replaced for width and height when not in static main.
		int roundHeight = 50;
		int roundWidth = 50;
		EventQueue.invokeLater(() -> {
			try {
				Visualiser frame = new Visualiser();
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				frame.setVisible(true);

				//Listener to implement correct resizing so the gridmap keeps it's grid proportion
				frame.addComponentListener(new ComponentListener() {
					public void componentResized(ComponentEvent e) {
						System.out.println("Resized");
						//frameWidth = snap to the nearest cell width
						int frameWidth = (frame.getWidth() / roundWidth) * roundWidth;

						//frameHeight = snap to the nearest cell Height
						int frameHeight = (frame.getHeight() / (2 * roundHeight)) * (2 * roundHeight);

						//Resize frame according to the nearest possible snap
						frame.setSize(frameWidth, frameHeight);
					}

					public void componentHidden(ComponentEvent arg0) {
					}

					public void componentMoved(ComponentEvent arg0) {
					}

					public void componentShown(ComponentEvent arg0) {
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable o, Object arg) {
		// There is a new path! Visualise it
	}
}
