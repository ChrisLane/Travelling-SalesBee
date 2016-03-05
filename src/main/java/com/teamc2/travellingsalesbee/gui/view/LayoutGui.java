package com.teamc2.travellingsalesbee.gui.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class LayoutGui extends GroupLayout {

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
		
		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0,0,0,0));
	    UIManager.getDefaults().put("TabbedPane.tabAreaInsets", new Insets(0,0,0,0));
	    UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);
	    UIManager.put("TabbedPane.contentOpaque", false);
		
	    // TO DO CSS FOR TABS
	    String tabCSS = "";	
	    
		JTabbedPane algorithmTabs = new JTabbedPane();
		algorithmTabs.setTabPlacement(JTabbedPane.LEFT);
		
		try {
			@SuppressWarnings("unused")
			Image image;
			image = ImageIO.read(this.getClass().getResource("/assets/icons/SalesBee.png"));
			algorithmTabs.setUI(new BasicTabbedPaneUI());
			JLabel lbl = new JLabel("Bee");
			lbl.setVerticalTextPosition(SwingConstants.BOTTOM);
			lbl.setHorizontalTextPosition(SwingConstants.CENTER);
			algorithmTabs.add(getHtmlForTitle("Bee", "SalesBee.png"),panelMap);
			algorithmTabs.add(getHtmlForTitle("Ant", "SalesBee.png"),new JPanel());
			algorithmTabs.add(getHtmlForTitle("Nearest Neighbour", "SalesBee.png"),new JPanel());
			algorithmTabs.add(getHtmlForTitle("2-opt swap", "SalesBee.png"), new JPanel());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Set background color of tabs to background color of visualiser
		for (int i=0; i<algorithmTabs.getTabCount();i++){
			algorithmTabs.setBackgroundAt(i, new Color(71, 35, 35));
		}
		
		
	
		
		
		setHorizontalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addComponent(panelToolbox, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(createParallelGroup(Alignment.LEADING)
										.addComponent(panelSettings, GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
										.addComponent(algorithmTabs, GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)))
		);
		setVerticalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addComponent(algorithmTabs, GroupLayout.PREFERRED_SIZE, 446, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(panelSettings, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(105, Short.MAX_VALUE))
						.addComponent(panelToolbox, GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
		);
	}

	/**
	 * 
	 * @param text The text to present on the tab
	 * @param imgName The image to make as a tab icon
	 * @return htmlString The html string to produce the tab
	 */
	private String getHtmlForTitle(String text, String imgName) {
		return "<html><head><style></style></head><body><div class='circleBase'><center><img src='"+this.getClass().getResource("/assets/icons/"+imgName)+"' height='50' width='50'/><br/><h2 color='#ffffff'>"+text+"</h2></div></body></html>";
	}
}
