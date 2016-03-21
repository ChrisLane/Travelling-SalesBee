package com.teamc2.travellingsalesbee.gui.view.tabs;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;
import com.teamc2.travellingsalesbee.gui.view.settings.PanelSettings;
import com.teamc2.travellingsalesbee.gui.view.toolbox.PanelToolbox;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class ComponentTabs extends JTabbedPane {
	public final static String name = "ComponentTabs";
	private String bee = "BEE INSPIRED";
	private String ant = "ANT INSPIRED";
	private String nn = "NEAREST NEIGHBOUR";
	private String tos = "TWO-OPT SWAP";

	public ComponentTabs(PanelMap panelMap, PanelSettings panelSettings, PanelToolbox panelToolbox) {
		setName(name);
		Color backgroundColor = new Color(71, 35, 35);
		Color tabColor = new Color(68, 35, 35);

		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
		UIManager.getDefaults().put("TabbedPane.tabAreaInsets", new Insets(5, 0, 0, 0));
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
		UIManager.getDefaults().put("TabbedPane.font", new Font("Amatic Bold", Font.PLAIN, 25));

		addTab(getHtmlForSelectedTitle(bee, "SalesBeeSelected.png"), null);
		addTab(getHtmlForTitle(ant, "Ant.png"), null);
		addTab(getHtmlForTitle(nn, "MailVan.png"), null);
		addTab(getHtmlForTitle(tos, "Boat.png"), null);

		setTabPlacement(JTabbedPane.LEFT);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
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
					text = bee;
					imgName = "SalesBeeSelected.png";
					panelMap.getPanelOverlyingText().setText("BEE NATURE-INSPIRED ALGORITHM\n"
							+ "The bee first conducts nearest neighbour on flowers and then looks to improve this route by switching two nodes in it's best route");
					break;
				case 1:
					type = AlgorithmType.ANT;
					text = ant;
					imgName = "AntSelected.png";
					panelMap.getPanelOverlyingText().setText("ANT NATURE-INSPIRED ALGORITHM\n"
							+ "The ants perform random tours around the map, planting pheromone on short paths for the next ants to follow"
							+ "\nAs each generation of ants tour the map, bad paths evaporate as the best path emerges");
					break;
				case 2:
					type = AlgorithmType.NEARESTNEIGHBOUR;
					text = nn;
					imgName = "MailVanSelected.png";
					panelMap.getPanelOverlyingText().setText("NEAREST NEIGHBOUR ALGORITHM\n"
							+ "At each house the mail van chooses shortest edge from current position to an unvisited house\n"
							+ "until there are no unvisited houses so the edge to starting mail office is chosen");
					break;
				case 3:
					type = AlgorithmType.TWOOPT;
					text = tos;
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
		return "<html><div align='center'><img src='" + getClass().getResource("/assets/icons/" + imgName)
				+ "' height='50' width='50'/><br/><h2 color='#ffffff'>" + text + "</h2></div></html>";
	}

	private String getHtmlForSelectedTitle(String text, String imgName) {
		return "<html><div align='center'><img src='" + getClass().getResource("/assets/icons/" + imgName)
				+ "' height='50' width='50'/><br/><h2 color='lime'>" + text + "</h2></div></html>";
	}

	private void updateTabColours() {
		for (int i = 0; i < getTabCount(); i++) {
			String newTitle = getTitleAt(i).replace("lime", "white");
			newTitle = newTitle.replaceAll("([A-Z])*Selected", "");
			setTitleAt(i, newTitle);
		}
	}
}
