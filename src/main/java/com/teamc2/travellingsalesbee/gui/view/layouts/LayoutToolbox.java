package com.teamc2.travellingsalesbee.gui.view.layouts;

import javax.swing.*;
import java.awt.*;

public class LayoutToolbox extends GroupLayout {
	/**
	 * Creates a layout for the toolbox container
	 *
	 * @param host       Container for the layout to be applied to
	 * @param backButton Back button for the toolbox
	 * @param randomiseButton 
	 * @param randomiseSpinner 
	 */
	public LayoutToolbox(Container host, JButton backButton, JSpinner randomiseSpinner, JButton randomiseButton) {
		super(host);

		setHorizontalGroup(
				createParallelGroup(Alignment.LEADING)
					.addComponent(backButton)
					.addComponent(randomiseButton)
		);
		setVerticalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addComponent(backButton)
								.addGap(18)
								.addComponent(randomiseButton)
								.addContainerGap(500, Short.MAX_VALUE))
		);
	}
}
