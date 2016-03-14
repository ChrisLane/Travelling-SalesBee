package com.teamc2.travellingsalesbee.gui.view.layouts;

import com.teamc2.travellingsalesbee.gui.view.ComponentTabs;
import com.teamc2.travellingsalesbee.gui.view.PanelMap;
import com.teamc2.travellingsalesbee.gui.view.PanelSettings;
import com.teamc2.travellingsalesbee.gui.view.PanelToolbox;

import javax.swing.*;
import java.awt.*;

public class LayoutGui extends GroupLayout {

	/**
	 * Create a layout for the GUI JPanel
	 *
	 * @param host          The container
	 * @param panelMap      The map panel
	 * @param panelSettings The settings panel
	 * @param panelToolbox  The toolbox panel
	 * @param componentTabs The tab pane
	 */
	public LayoutGui(Container host, PanelMap panelMap, PanelSettings panelSettings, PanelToolbox panelToolbox, ComponentTabs componentTabs) {
		super(host);

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
