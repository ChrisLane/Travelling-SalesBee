package com.teamc2.travellingsalesbee.gui.view.layouts;

import com.teamc2.travellingsalesbee.gui.view.*;

import javax.swing.*;

public class LayoutGui extends GroupLayout {

	/**
	 * Create a layout for the GUI JPanel
	 *
	 * @param host The container
	 */
	public LayoutGui(GuiContainer host) {
		super(host);

		PanelMap panelMap = (PanelMap) host.getComponentByName("PanelMap");
		PanelSettings panelSettings = (PanelSettings) host.getComponentByName("PanelSettings");
		PanelToolbox panelToolbox = (PanelToolbox) host.getComponentByName("PanelToolbox");
		ComponentTabs componentTabs = (ComponentTabs) host.getComponentByName("ComponentTabs");

		setHorizontalGroup(createSequentialGroup()
				.addComponent(componentTabs, 125, 125, PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(createParallelGroup()
						.addComponent(panelMap, 0, PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(panelSettings, DEFAULT_SIZE, PREFERRED_SIZE, Short.MAX_VALUE)
				)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(panelToolbox, DEFAULT_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
		);
		setVerticalGroup(createParallelGroup()
				.addComponent(componentTabs, 500, PREFERRED_SIZE, Short.MAX_VALUE)
				.addGroup(createSequentialGroup()
						.addComponent(panelMap, 0, PREFERRED_SIZE, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panelSettings, DEFAULT_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
				)
				.addComponent(panelToolbox, DEFAULT_SIZE, PREFERRED_SIZE, Short.MAX_VALUE)
		);
	}
}
