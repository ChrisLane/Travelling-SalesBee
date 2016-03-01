package com.teamc2.travellingsalesbee.gui;

import com.teamc2.travellingsalesbee.gui.view.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Visualiser extends JFrame {
	public static Visualiser mainVisualiser;

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

		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
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

		System.out.println("this.getWidth: " + panelMap.getWidth() + ", this.getHeight: " + panelMap.getHeight());

		// Add grid to the map
		LayoutGui layoutGui = new LayoutGui(contentPane, panelMap, panelSettings, panelToolbox);

		int panelMapWidth = (int) layoutGui.getPanelMapDimensions().getWidth();
		int panelMapHeight = (int) layoutGui.getPanelMapDimensions().getHeight();


		//DOESN'T WORK
		/*System.out.println("panelMapWidth " + panelMapWidth + " panelMapHeight " + panelMapHeight);

		PanelAnimalAnimation panelAnimation = new PanelAnimalAnimation(0, 0, panelMapWidth, panelMapWidth);
		panelAnimation.setBounds(panelMap.getX(), panelMap.getY(), panelMapWidth, panelMapHeight);
		add(panelAnimation);
		setComponentZOrder(panelAnimation, 0);*/

		contentPane.setLayout(layoutGui);
	}

	/**
	 * Launch the application.
	 *
	 * @param args The runtime arguments for the application.
	 */
	public static void main(String[] args) {
		Visualiser frame = new Visualiser();
		Visualiser.mainVisualiser = frame;
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
