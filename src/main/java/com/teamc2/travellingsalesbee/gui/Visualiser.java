package com.teamc2.travellingsalesbee.gui;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.gui.view.*;
import com.teamc2.travellingsalesbee.gui.view.layouts.LayoutGui;
import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;
import com.teamc2.travellingsalesbee.gui.view.settings.PanelSettings;
import com.teamc2.travellingsalesbee.gui.view.tabs.ComponentTabs;
import com.teamc2.travellingsalesbee.gui.view.toolbox.PanelToolbox;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

		//Loads in the image which says: "Algorithms"
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResource("/assets/icons/Algorithms.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JLabel algorithmLabel = new JLabel(new ImageIcon(img));
		algorithmLabel.setName("algorithmLabel"); //Set a name for the label so you can add it to the panel later on

		//Add elements to the container
		container.add(algorithmLabel);
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
		
		setFont();
	}

	/**
	 * Loads in the font: Amatic (Bold/Regular)
	 */
	private void setFont() {
		try {
			InputStream amaticStreamBold = getClass().getResourceAsStream("/assets/fonts/Amatic-Bold.ttf");
			InputStream amaticStreamRegular = getClass().getResourceAsStream("/assets/fonts/AmaticSC-Regular.ttf");

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, amaticStreamBold));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, amaticStreamRegular));

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(FontFormatException e) {
            e.printStackTrace();
        }
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
