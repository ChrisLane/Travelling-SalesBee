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
	private PanelMap panelMap;
	private PanelToolbox panelToolbox;
	private Color backgroundColor = new Color(71, 35, 35);
	private Color tabColor = new Color(68, 35, 35);

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
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(createParallelGroup(Alignment.LEADING)
										.addComponent(panelSettings, DEFAULT_SIZE, 789, Short.MAX_VALUE)
										.addComponent(tabbedPane, DEFAULT_SIZE, 789, Short.MAX_VALUE))
								.addContainerGap(5, 5)
								.addComponent(panelToolbox, PREFERRED_SIZE, 110, PREFERRED_SIZE))
						

		);
		setVerticalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addComponent(tabbedPane, PREFERRED_SIZE, 500, PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addContainerGap(25, 25)
								.addComponent(panelSettings, PREFERRED_SIZE, 188, PREFERRED_SIZE)
								)
								.addComponent(panelToolbox, PREFERRED_SIZE, 1000, PREFERRED_SIZE)
						
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

		try {
			@SuppressWarnings("unused")
			Image image;
			image = ImageIO.read(this.getClass().getResource("/assets/icons/SalesBee.png"));
			tabbedPane.setUI(new BasicTabbedPaneUI());
			JLabel lbl = new JLabel("Bee");
			lbl.setVerticalTextPosition(SwingConstants.BOTTOM);
			lbl.setHorizontalTextPosition(SwingConstants.CENTER);

			tabbedPane.add(getHtmlForSelectedTitle("BEE", "SalesBeeSelected.png"),panelMap);
			tabbedPane.add(getHtmlForTitle("ANT", "Ant.png"),tabbedPane.getTabComponentAt(0));
			tabbedPane.add(getHtmlForTitle("NN", "Mailvan.png"),tabbedPane.getTabComponentAt(0));
			tabbedPane.add(getHtmlForTitle("2-OPT", "Mailvan.png"), tabbedPane.getTabComponentAt(0));

			
			tabbedPane.setBackgroundAt(0, this.tabColor);
			
			panelMap.setAlgorithmType(AlgorithmType.BEE);
			

			//Change listener to change algorithms when switching tabs
			tabbedPane.addChangeListener(evt -> {
				//TO DO - IVEN ART
				int selected = tabbedPane.getSelectedIndex();
				String text = "BEE";
				String imgName = "SalesBee.png";
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
				}
				tabbedPane.setTitleAt(selected, getHtmlForSelectedTitle(text ,imgName));
				panelMap.setAlgorithmType(type);
				panelToolbox.setAlgorithmType(type);
				panelMap.repaint();
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
	
	private String getHtmlForSelectedTitle(String text, String imgName) {
		return "<html><head><style></style></head><body><div class='circleBase'><center><img src='" + this.getClass().getResource("/assets/icons/" + imgName) + "' height='50' width='50'/><br/><h2 color='#FF0000'>" + text + "</h2></div></body></html>";
	}
	
	private void updateTabColours(){
		for (int i=0; i<tabbedPane.getTabCount();i++){
			String newTitle = tabbedPane.getTitleAt(i).replace("#FF0000", "#FFFFFF");
			newTitle = newTitle.replaceAll("([A-Z])*Selected", "");
			tabbedPane.setTitleAt(i, newTitle);
		}
	}
}
