package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class ComponentTabs extends JTabbedPane {
	public ComponentTabs(PanelMap panelMap, PanelSettings panelSettings, PanelToolbox panelToolbox) {
		Color backgroundColor = new Color(71, 35, 35);
		Color tabColor = new Color(68, 35, 35);

		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
		UIManager.getDefaults().put("TabbedPane.tabAreaInsets", new Insets(12, 10, 0, 0));
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

		addTab(getHtmlForSelectedTitle("BEE", "SalesBeeSelected.png"), null);
		addTab(getHtmlForTitle("ANT", "Ant.png"), null);
		addTab(getHtmlForTitle("NN", "Mailvan.png"), null);
		addTab(getHtmlForTitle("2-OPT", "Mailvan.png"), null);

		setTabPlacement(JTabbedPane.LEFT);
		setUI(new BasicTabbedPaneUI());
		setBackgroundAt(0, tabColor);

		panelMap.setAlgorithmType(AlgorithmType.BEE);
		panelToolbox.setAlgorithmType(AlgorithmType.BEE);
		panelSettings.setAlgorithmType(AlgorithmType.BEE);

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
					text = "BEE";
					imgName = "SalesBeeSelected.png";
					break;
				case 1:
					type = AlgorithmType.ANT;
					text = "ANT";
					imgName = "AntSelected.png";
					break;
				case 2:
					type = AlgorithmType.NEARESTNEIGHBOUR;
					text = "NN";
					imgName = "MailvanSelected.png";
					break;
				case 3:
					type = AlgorithmType.TWOOPT;
					text = "2-OPT";
					imgName = "MailvanSelected.png";
					break;
				default:
					text = "Error";
					imgName = "Error";
					break;
			}
			setTitleAt(selected, getHtmlForSelectedTitle(text, imgName));
			panelToolbox.setAlgorithmType(type);
			panelSettings.setStepNum(-1);
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
		return "<html><img src='" + this.getClass().getResource("/assets/icons/" + imgName)
				+ "' height='50' width='50'/><br/><h2 color='#ffffff'>" + text + "</h2></html>";
	}

	private String getHtmlForSelectedTitle(String text, String imgName) {
		return "<html><img src='" + this.getClass().getResource("/assets/icons/" + imgName)
				+ "' height='50' width='50'/><br/><h2 color='lime'>" + text + "</h2></html>";
	}

	private void updateTabColours() {
		for (int i = 0; i < getTabCount(); i++) {
			String newTitle = getTitleAt(i).replace("lime", "white");
			newTitle = newTitle.replaceAll("([A-Z])*Selected", "");
			setTitleAt(i, newTitle);
		}
	}
}
