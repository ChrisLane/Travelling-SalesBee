package com.teamc2.travellingsalesbee.gui.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.io.IOException;

public class LayoutGui extends GroupLayout {

	JTabbedPane tabbedPane;
	;
	private PanelMap panelMap;
	private PanelToolbox panelToolbox;
	private Color backgroundColor = new Color(71, 35, 35);

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
		initialiseTabs();

		/****************************************************************************/
		/******************** Initialising of the group layout **********************/
		/****************************************************************************/

		setHorizontalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addComponent(panelToolbox, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(createParallelGroup(Alignment.LEADING)
										.addComponent(panelSettings, GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
										.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)))
		);
		setVerticalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 446, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(panelSettings, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(105, Short.MAX_VALUE))
						.addComponent(panelToolbox, GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
		);
	}

	private void initialiseTabs() {
		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
		UIManager.getDefaults().put("TabbedPane.tabAreaInsets", new Insets(0, 0, 0, 0));
		UIManager.getDefaults().put("TabbedPane.contentOpaque", false);
		UIManager.getDefaults().put("TabbedPane.selected", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.tabsOpaque", false);
		UIManager.getDefaults().put("TabbedPane.shadow", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.light", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.selectHighlight", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.darkShadow", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.focus", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.lightHighlight", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", false);
		UIManager.getDefaults().put("TabbedPane.borderColor", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.highlight", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.focus", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.selectHighlight", this.backgroundColor);
		UIManager.getDefaults().put("TabbedPane.borderHightlightColor", this.backgroundColor);

		// TO DO CSS FOR TABS
		String tabCSS = "";

		tabbedPane = new JTabbedPane();
		tabbedPane.setTabPlacement(JTabbedPane.LEFT);

		panelMap.setAlgorithmType(AlgorithmType.BEE);
		panelToolbox.setAlgorithmType(AlgorithmType.BEE);

		try {
			@SuppressWarnings("unused")
			Image image;
			image = ImageIO.read(this.getClass().getResource("/assets/icons/SalesBee.png"));
			tabbedPane.setUI(new BasicTabbedPaneUI());
			JLabel lbl = new JLabel("Bee");
			lbl.setVerticalTextPosition(SwingConstants.BOTTOM);
			lbl.setHorizontalTextPosition(SwingConstants.CENTER);

			tabbedPane.add(getHtmlForTitle("BEE", "SalesBee.png"),panelMap);
			tabbedPane.add(getHtmlForTitle("ANT", "SalesBee.png"),tabbedPane.getTabComponentAt(0));
			tabbedPane.add(getHtmlForTitle("NN", "SalesBee.png"),tabbedPane.getTabComponentAt(0));
			tabbedPane.add(getHtmlForTitle("2-OPT", "SalesBee.png"), tabbedPane.getTabComponentAt(0));

			panelMap.setAlgorithmType(AlgorithmType.BEE);

			//Change listener to change algorithms when switching tabs
			tabbedPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					//TO DO - REPAINT THE PANEL WITH GIVEN ART
					int selected = tabbedPane.getSelectedIndex();
					AlgorithmType type = null;
					switch (selected) {
						case 0:
							type = AlgorithmType.BEE;
							break;
						case 1:
							type = AlgorithmType.ANT;
							break;
						case 2:
							type = AlgorithmType.NEARESTNEIGHBOUR;
							break;
						case 3:
							type = AlgorithmType.TWOOPT;
							break;
					}
					panelMap.setAlgorithmType(type);
					panelToolbox.setAlgorithmType(type);
					panelMap.repaint();
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	/**
	 * @param text    The text to present on the tab
	 * @param imgName The image to make as a tab icon
	 * @return htmlString The html string to produce the tab
	 */
	private String getHtmlForTitle(String text, String imgName) {
		return "<html><head><style></style></head><body><div class='circleBase'><center><img src='" + this.getClass().getResource("/assets/icons/" + imgName) + "' height='50' width='50'/><br/><h2 color='#ffffff'>" + text + "</h2></div></body></html>";
	}
}
