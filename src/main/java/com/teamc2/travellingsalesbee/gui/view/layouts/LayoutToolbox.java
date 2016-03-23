package com.teamc2.travellingsalesbee.gui.view.layouts;

import javax.swing.*;
import java.awt.*;

/**
 * A GroupLayout for the toolbox
 */
public class LayoutToolbox extends GroupLayout {

	/**
	 * Construct a layout for the toolbox
	 *
	 * @param host       Container for the child elements
	 * @param backButton The back button
	 * @param imgDrag    Drag tool explanation image
	 * @param imgKey     Line colour explanation image
	 */
	public LayoutToolbox(Container host, JButton backButton, JLabel imgDrag, JLabel imgKey) {
		super(host);

		setHorizontalGroup(
				createParallelGroup(Alignment.CENTER)
						.addComponent(backButton)
						.addComponent(imgDrag)
						.addComponent(imgKey)
		);
		setVerticalGroup(
				createParallelGroup()
						.addGroup(createSequentialGroup()
								.addComponent(backButton)
								.addGap(30)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(imgDrag)
								.addGap(250)
								.addComponent(imgKey)
								.addContainerGap(500, Short.MAX_VALUE))
		);
	}
}
