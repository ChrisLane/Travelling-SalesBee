package com.teamc2.travellingsalesbee.gui.view.tabs;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.gui.view.toolbox.PanelToolbox;
import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;
import com.teamc2.travellingsalesbee.gui.view.settings.PanelSettings;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class ComponentTabs extends JTabbedPane {
	public final static String name = "ComponentTabs";

	public ComponentTabs(PanelMap panelMap, PanelSettings panelSettings, PanelToolbox panelToolbox) {
		setName(name);
		Color backgroundColor = new Color(71, 35, 35);
		Color tabColor = new Color(68, 35, 35);

		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
		UIManager.getDefaults().put("TabbedPane.tabAreaInsets", new Insets(30, 0, 0, 0));
		UIManager.getDefaults().put("TabbedPane.contentOpaque", false);
		UIManager.getDefaults().put("TabbedPane.tabsOpaque", false);
		UIManager.getDefaults().put("TabbedPane.shadow", backgroundColor);
		UIManager.getDefaults().put("TabbedPane.light", backgroundColor);
		UIManager.getDefaults().put("TabbedPane.darkShadow", backgroundColor);
		UIManager.getDefaults().put("TabbedPane.focus", backgroundColor);
		UIManager.getDefaults().put("TabbedPane.lightHighlight", backgroundColor);
		UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", false);
		UIManager.getDefaults().put("TabbedPane.borderColor", backgroundColor);
		UIManager.getDefaults().put("TabbedPane.highlight", backgroundColor);
		UIManager.getDefaults().put("TabbedPane.selectHighlight", backgroundColor);
		UIManager.getDefaults().put("TabbedPane.borderHightlightColor", backgroundColor);

		addTab(getHtmlForSelectedTitle("Bee", "SalesBeeSelected.png"), null);
		addTab(getHtmlForTitle("Ant", "Ant.png"), null);
		addTab(getHtmlForTitle("NN", "Mailvan.png"), null);
		addTab(getHtmlForTitle("2-Opt", "Boat.png"), null);

		setTabPlacement(JTabbedPane.LEFT);
		setUI(new BasicTabbedPaneUI());
		setBackgroundAt(0, tabColor);

		panelMap.setAlgorithmType(AlgorithmType.BEE);
		panelToolbox.setAlgorithmType(AlgorithmType.BEE);
		panelSettings.setAlgorithmType(AlgorithmType.BEE);
		
		panelMap.getPanelOverlyingText().setText("BEE ALGORITHM\n"
				+ "The bee first conducts nearest neighbour on flowers and then looks to improve this route by switching two nodes in it's best route");

		//Change listener to change algorithms when switching tabs
		addChangeListener(evt -> {

			int selected = getSelectedIndex();
			String text;
			String imgName;
			updateTabColours();


			AlgorithmType type = null;
			switch (selected) {
				case 0:
					type = AlgorithmType.BEE;
					text = "Bee";
					imgName = "SalesBeeSelected.png";
					panelMap.getPanelOverlyingText().setText("BEE ALGORITHM\n"
							+ "The bee first conducts nearest neighbour on flowers and then looks to improve this route by switching two nodes in it's best route");
					break;
				case 1:
					type = AlgorithmType.ANT;
					text = "Ant";
					imgName = "AntSelected.png";
					panelMap.getPanelOverlyingText().setText("ANT ALGORITHM\n"
							+ "The ants .................");
					break;
				case 2:
					type = AlgorithmType.NEARESTNEIGHBOUR;
					text = "NN";
					imgName = "MailvanSelected.png";
					panelMap.getPanelOverlyingText().setText("NEAREST NEIGHBOUR ALGORITHM\n"
							+ "At each house the mailvan chooses shortest edge from current position to an unvisited house\n"
							+ "until there are no unvisted houses so the edge to starting mail office is chosen");
					break;
				case 3:
					type = AlgorithmType.TWOOPT;
					text = "2-Opt";
					imgName = "BoatSelected.png";
					panelMap.getPanelOverlyingText().setText("TWO OPT SWAP\n"
							+ "An initial path is created using nearest neighbour\n"
							+ "For each swap run two nodes are swapped so that the order of which they are visited in the path is switched");
					break;
				default:
					text = "Error";
					imgName = "Error";
					break;
			}
			setTitleAt(selected, getHtmlForSelectedTitle(text, imgName));
			panelToolbox.setAlgorithmType(type);
			panelSettings.setAlgorithmType(type);
			panelMap.setAlgorithmType(type);
			panelMap.getPathComponent().setAlgorithmType(type);
			panelMap.repaint();
		});
	}

	/**
	 * @param text    The text to present on the tab
	 * @param imgName The image to make as a tab icon
	 * @return htmlString The html string to produce the tab
	 */
	private String getHtmlForTitle(String text, String imgName) {
		return "<html><img src='" + getClass().getResource("/assets/icons/" + imgName)
				+ "' height='50' width='50'/><br/><h3 color='#ffffff'>" + text + "</h3></html>";
	}

	private String getHtmlForSelectedTitle(String text, String imgName) {
		return "<html><img src='" + getClass().getResource("/assets/icons/" + imgName)
				+ "' height='50' width='50'/><br/><h3 color='lime'>" + text + "</h3></html>";
	}

	private void updateTabColours() {
		for (int i = 0; i < getTabCount(); i++) {
			String newTitle = getTitleAt(i).replace("lime", "white");
			newTitle = newTitle.replaceAll("([A-Z])*Selected", "");
			setTitleAt(i, newTitle);
		}
	}
}
