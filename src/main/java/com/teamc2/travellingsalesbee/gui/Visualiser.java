package com.teamc2.travellingsalesbee.gui;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.gui.view.*;
import com.teamc2.travellingsalesbee.gui.view.layouts.LayoutGui;

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
		setBounds(50, 25, 1200, 650);
		setMinimumSize(new Dimension(800, 600));
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

		int width = 50;
		int height = 50;
		GuiContainer container = new GuiContainer();
		PanelMap panelMap = new PanelMap(width, height);
		PanelSettings panelSettings = new PanelSettings(panelMap);
		PanelToolbox panelToolbox = new PanelToolbox(panelMap, AlgorithmType.BEE);
		ComponentTabs componentTabs = new ComponentTabs(panelMap, panelSettings, panelToolbox);
		container.add(panelMap);
		container.add(panelSettings);
		container.add(panelToolbox);
		container.add(componentTabs);

		container.setBorder(new EmptyBorder(5, 5, 5, 5));
		container.setBackground(new Color(71, 35, 35));
		container.setOpaque(true);
		setContentPane(container);

		LayoutGui layoutGui = new LayoutGui(container);
		container.setLayout(layoutGui);
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
