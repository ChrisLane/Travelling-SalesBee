package com.teamc2.travellingsalesbee.gui.view;

import javax.swing.*;
import java.awt.*;

public class LayoutToolbox extends GroupLayout {
	/**
	 * Creates a layout for the toolbox container
	 *
	 * @param host       Container for the layout to be applied to
	 * @param backButton Back button for the toolbox
	 */
	public LayoutToolbox(Container host, JButton backButton) {
		super(host);

		setHorizontalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addContainerGap()
								.addComponent(backButton)
								.addContainerGap(327, Short.MAX_VALUE))
		);
		setVerticalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addComponent(backButton)
								.addContainerGap(214, Short.MAX_VALUE))
		);
	}
}
