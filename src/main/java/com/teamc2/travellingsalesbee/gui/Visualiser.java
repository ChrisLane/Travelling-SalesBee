package com.teamc2.travellingsalesbee.gui;

import com.teamc2.travellingsalesbee.gui.view.LayoutGui;
import com.teamc2.travellingsalesbee.gui.view.PanelMap;
import com.teamc2.travellingsalesbee.gui.view.PanelSettings;
import com.teamc2.travellingsalesbee.gui.view.PanelToolbox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Visualiser extends JFrame {
	/**
	 * Create the frame.
	 */
	private Visualiser() {

		// Set the UIManager look and feel to the system default
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(300, 300, 956, 689);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(71, 35, 35));
		setContentPane(contentPane);

		int width = 50;
		int height = 50;
		PanelMap panelMap = new PanelMap(width, height);
		PanelToolbox panelToolbox = new PanelToolbox(panelMap);
		PanelSettings panelSettings = new PanelSettings(panelMap);


		// Add grid to the map
		LayoutGui layoutGui = new LayoutGui(contentPane, panelMap, panelSettings, panelToolbox);

		contentPane.setLayout(layoutGui);
	}

	/**
	 * Launch the application.
	 *
	 * @param args The runtime arguments for the application.
	 */
	public static void main(String[] args) {
		// Temporary variables, should be removed and replaced for width and height when not in static main.
		int roundHeight = 50;
		int roundWidth = 50;
		EventQueue.invokeLater(() -> {
			try {
				Visualiser frame = new Visualiser();
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				frame.setVisible(true);

				// Listener to implement correct resizing so that the map keeps it's grid proportion
				frame.addComponentListener(new ComponentListener() {
					@Override
					public void componentResized(ComponentEvent e) {
						System.out.println("Resized");
						// frameWidth = snap to the nearest cell width
						int frameWidth = (frame.getWidth() / roundWidth) * roundWidth;

						// frameHeight = snap to the nearest cell Height
						int frameHeight = (frame.getHeight() / (2 * roundHeight)) * (2 * roundHeight);

						// Resize frame according to the nearest possible snap
						frame.setSize(frameWidth, frameHeight);
					}

					@Override
					public void componentMoved(ComponentEvent e) {

					}

					@Override
					public void componentShown(ComponentEvent e) {

					}

					@Override
					public void componentHidden(ComponentEvent e) {

					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
