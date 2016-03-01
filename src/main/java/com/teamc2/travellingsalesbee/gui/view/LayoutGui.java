package com.teamc2.travellingsalesbee.gui.view;

import javax.swing.*;
import java.awt.*;

public class LayoutGui extends GroupLayout {
	/**
	 * Creates a {@code GroupLayout} for the specified {@code Container}.
	 *
	 * @param host the {@code Container} the {@code GroupLayout} is
	 *             the {@code LayoutManager} for
	 * @throws IllegalArgumentException if host is {@code null}
	 */
	public LayoutGui(Container host, PanelMap panelMap, PanelSettings panelSettings, PanelToolbox panelToolbox) {
		super(host);

		setHorizontalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addComponent(panelToolbox, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(createParallelGroup(Alignment.LEADING)
										.addComponent(panelSettings, GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
										.addComponent(panelMap, GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)))
		);
		setVerticalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addComponent(panelMap, GroupLayout.PREFERRED_SIZE, 446, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(panelSettings, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(105, Short.MAX_VALUE))
						.addComponent(panelToolbox, GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
		);
	}
}
