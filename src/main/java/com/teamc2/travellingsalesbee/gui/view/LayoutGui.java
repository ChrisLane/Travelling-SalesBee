package com.teamc2.travellingsalesbee.gui.view;

import javax.swing.*;
import java.awt.*;

public class LayoutGui extends GroupLayout {

	Container host;
	PanelMap panelMap;
	PanelSettings panelSettings;
	PanelToolbox panelToolbox;
	//PanelAnimalAnimation panelAnimation;

	/**
	 * Create a layout for the GUI JPanel
	 *
	 * @param host The container
	 * @param panelMap The map panel
	 * @param panelSettings The settings panel
	 * @param panelToolbox The toolbox panel
	 */
	public LayoutGui(Container host, PanelMap panelMap, PanelSettings panelSettings, PanelToolbox panelToolbox/*, PanelAnimalAnimation panelAnimation*/) {
		super(host);

		this.host = host;
		this.panelMap = panelMap;
		this.panelSettings = panelSettings;
		this.panelToolbox = panelToolbox;

		setHorizontalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addComponent(this.panelToolbox, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(createParallelGroup(Alignment.LEADING)
										.addComponent(this.panelSettings, GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
										.addComponent(this.panelMap, GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)))
		);
		setVerticalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addComponent(this.panelMap, GroupLayout.PREFERRED_SIZE, 446, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(this.panelSettings, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(105, Short.MAX_VALUE))
						.addComponent(panelToolbox, GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
		);

	}

	/**
	 *
	 * @return Host dimensions
	 */
	public Dimension getHostDimensions () {
		return new Dimension(host.getWidth(), host.getHeight());
	}

	/**
	 *
	 * @return Panel map dimensions
	 */
	public Dimension getPanelMapDimensions() {
		return new Dimension(panelMap.getWidth(), panelMap.getHeight());
	}

	/**
	 *
	 * @return Panel settings dimensions
	 */
	public Dimension getPanelSettingsDimensions() {
		return new Dimension(panelSettings.getWidth(), panelSettings.getHeight());
	}

	/**
	 *
	 * @return Panel toolbox dimensions
	 */
	public Dimension getPanelToolboxDimensions() {
		return new Dimension(panelSettings.getWidth(), panelSettings.getHeight());
	}
}
