package com.teamc2.travellingsalesbee.gui.view.layouts;

import com.teamc2.travellingsalesbee.gui.view.GuiContainer;
import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;
import com.teamc2.travellingsalesbee.gui.view.settings.PanelSettings;
import com.teamc2.travellingsalesbee.gui.view.tabs.ComponentTabs;
import com.teamc2.travellingsalesbee.gui.view.toolbox.PanelToolbox;

import javax.swing.*;

/**
 * Group layout for the GUI
 *
 * @author Bradley Rowe (bmr455)
 * @author Christopher Lane (cml476)
 * @author Melvyn Mathews (mxm499)
 */
public class LayoutGui extends GroupLayout {

	/**
	 * Construct a layout for the GUI
	 *
	 * @param host The container for child elements
	 */
	public LayoutGui(GuiContainer host) {
		super(host);

		PanelMap panelMap = (PanelMap) host.getComponentByName("PanelMap");
		PanelSettings panelSettings = (PanelSettings) host.getComponentByName("PanelSettings");
		PanelToolbox panelToolbox = (PanelToolbox) host.getComponentByName("PanelToolbox");
		ComponentTabs componentTabs = (ComponentTabs) host.getComponentByName("ComponentTabs");
		JLabel algorithmLabel = (JLabel) host.getComponentByName("algorithmLabel");

		setHorizontalGroup(createSequentialGroup()
				.addGroup(createParallelGroup()
						.addComponent(algorithmLabel, DEFAULT_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
						.addComponent(componentTabs, DEFAULT_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
				)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(createParallelGroup()
						.addComponent(panelMap, 0, PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(panelSettings, DEFAULT_SIZE, PREFERRED_SIZE, Short.MAX_VALUE)
				)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(panelToolbox, DEFAULT_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
		);
		setVerticalGroup(createParallelGroup()
				.addGroup(createSequentialGroup()
						.addGap(50)
						.addComponent(algorithmLabel, DEFAULT_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(componentTabs, DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE)
				)
				.addGroup(createSequentialGroup()
						.addComponent(panelMap, DEFAULT_SIZE, PREFERRED_SIZE, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panelSettings, DEFAULT_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
				)
				.addComponent(panelToolbox, DEFAULT_SIZE, PREFERRED_SIZE, DEFAULT_SIZE)
		);
	}
}
