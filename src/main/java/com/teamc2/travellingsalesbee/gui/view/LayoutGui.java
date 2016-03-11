package com.teamc2.travellingsalesbee.gui.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class LayoutGui extends GroupLayout {

	private JTabbedPane tabbedPane;
	private PanelMap panelMap;
	private PanelToolbox panelToolbox;
	private Color backgroundColor = new Color(71, 35, 35);
	private Color tabColor = new Color(68, 35, 35);
	private PanelSettings panelSettings;

	/**
	 * Create a layout for the GUI JPanel
	 *
	 * @param host          The container
	 * @param panelMap      The map panel
	 * @param panelSettings The settings panel
	 * @param panelToolbox  The toolbox panel
	 */
	public LayoutGui(Container host, PanelMap panelMap, PanelSettings panelSettings, PanelToolbox panelToolbox) {
		super(host);

		this.panelMap = panelMap;
		this.panelToolbox = panelToolbox;
		this.panelSettings = panelSettings;
		initialiseTabs();


		/****************************************************************************/
		/******************** Initialising of the group layout **********************/
		/****************************************************************************/

		setHorizontalGroup(createSequentialGroup()
				.addComponent(tabbedPane, 0, 100, PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(createParallelGroup()
						.addComponent(panelMap, DEFAULT_SIZE, PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(panelSettings, DEFAULT_SIZE, PREFERRED_SIZE, Short.MAX_VALUE)
				)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(panelToolbox, 0, 100, PREFERRED_SIZE)
		);
		setVerticalGroup(createParallelGroup()
				.addComponent(tabbedPane, DEFAULT_SIZE, 1000, Short.MAX_VALUE)
				.addGroup(createSequentialGroup()
						.addComponent(panelMap, DEFAULT_SIZE, PREFERRED_SIZE, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panelSettings, DEFAULT_SIZE, 200, PREFERRED_SIZE)
				)
				.addComponent(panelToolbox, DEFAULT_SIZE, 1000, Short.MAX_VALUE)
		);
	}

	private void initialiseTabs() {

		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
		UIManager.getDefaults().put("TabbedPane.tabAreaInsets", new Insets(0, 0, 0, 0));
		UIManager.getDefaults().put("TabbedPane.contentOpaque", false);
		UIManager.getDefaults().put("TabbedPane.tabsOpaque", false);
		UIManager.getDefaults().put("TabbedPane.shadow", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.light", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.darkShadow", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.focus", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.lightHighlight", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", false);
		UIManager.getDefaults().put("TabbedPane.borderColor", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.highlight", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.selectHighlight", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.borderHightlightColor", this.backgroundColor);

		// TO DO CSS FOR TABS
		String tabCSS = "";


		tabbedPane = new JTabbedPane();
		tabbedPane.setTabPlacement(JTabbedPane.LEFT);

		panelMap.setAlgorithmType(AlgorithmType.BEE);
		panelToolbox.setAlgorithmType(AlgorithmType.BEE);

		tabbedPane.setUI(new BasicTabbedPaneUI());

		JLabel panel1 = new JLabel("Test");
		tabbedPane.addTab(getHtmlForSelectedTitle("BEE", "SalesBeeSelected.png"), null);
		tabbedPane.addTab(getHtmlForTitle("ANT", "Ant.png"), null);
		tabbedPane.addTab(getHtmlForTitle("NN", "Mailvan.png"), null);
		tabbedPane.addTab(getHtmlForTitle("2-OPT", "Mailvan.png"), null);


		tabbedPane.setBackgroundAt(0, this.tabColor);

		panelMap.setAlgorithmType(AlgorithmType.BEE);
		panelSettings.setAlgorithmType(AlgorithmType.BEE);

		//Change listener to change algorithms when switching tabs
		tabbedPane.addChangeListener(evt -> {

			int selected = tabbedPane.getSelectedIndex();
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
			tabbedPane.setTitleAt(selected, getHtmlForSelectedTitle(text, imgName));
			panelMap.setAlgorithmType(type);
			panelToolbox.setAlgorithmType(type);
			panelMap.getPathComponent().setAlgorithmType(type);
			panelSettings.setStepNum(-1);
			panelSettings.setAlgorithmType(type);
			panelMap.repaint();
		});
	}

	/**
	 * @param text    The text to present on the tab
	 * @param imgName The image to make as a tab icon
	 * @return htmlString The html string to produce the tab
	 */
	private String getHtmlForTitle(String text, String imgName) {
		return "<html><head><style></style></head><body><img src='" + this.getClass().getResource("/assets/icons/" + imgName) + "' height='50' width='50'/><br/><h2 color='#ffffff'>" + text + "</h2></body></html>";
	}

	private String getHtmlForSelectedTitle(String text, String imgName) {
		return "<html><body><img src='" + this.getClass().getResource("/assets/icons/" + imgName)
				+ "' height='50' width='50'/><br/><h2 color='lime'>" + text + "</h2></body></html>";
	}

	private void updateTabColours() {
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			String newTitle = tabbedPane.getTitleAt(i).replace("lime", "white");
			newTitle = newTitle.replaceAll("([A-Z])*Selected", "");
			tabbedPane.setTitleAt(i, newTitle);
		}
	}
}
