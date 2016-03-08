package com.teamc2.travellingsalesbee.gui.view;

import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.*;

public class LayoutSettings extends GroupLayout {

	/**
	 * Creates a {@code GroupLayout} for the specified {@code Container}.
	 *
	 * @param host              the {@code Container} the {@code GroupLayout} is
	 *                          the {@code LayoutManager} for
	 * @param lblAnimationSpeed
	 * @param lblSpeed
	 * @param speedSlider       @throws IllegalArgumentException if host is {@code null}
	 */
	public LayoutSettings(Container host, JLabel infoLabel, JLabel lblExperimentRuns, JLabel lblNoOfRuns,
						  JSlider experimentsSlider, JLabel lblAnimationSpeed, JLabel lblSpeed, JSlider speedSlider,
						  JButton btnRun, JButton btnPrev, JButton btnNext, JFXPanel txtPaneTextWillAppear) {
		super(host);

		setHorizontalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addContainerGap()
								.addGroup(createParallelGroup(Alignment.LEADING)
										.addComponent(infoLabel)
										.addGroup(createSequentialGroup()
												.addComponent(lblExperimentRuns)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(lblNoOfRuns))
										.addComponent(experimentsSlider)
										.addGap(10)
										.addGroup(createSequentialGroup()
												.addComponent(lblAnimationSpeed)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(lblSpeed))
										.addComponent(speedSlider))
								.addGap(10)
								.addGroup(createParallelGroup(Alignment.TRAILING)
										.addGroup(createSequentialGroup()
												.addComponent(btnRun)
												.addGap(18)
												.addComponent(btnPrev)
												.addGap(18)
												.addComponent(btnNext)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE))
										.addComponent(txtPaneTextWillAppear))
								.addContainerGap())
		);
		setVerticalGroup(
				createParallelGroup(Alignment.TRAILING)
						.addGroup(createSequentialGroup()
								.addContainerGap()
								.addComponent(infoLabel)
								.addGap(18)
								.addGroup(createParallelGroup(Alignment.BASELINE)
										.addComponent(lblExperimentRuns)
										.addComponent(lblNoOfRuns))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(experimentsSlider)
								.addContainerGap(211, Short.MAX_VALUE)
								.addGap(18)
								.addGroup(createParallelGroup(Alignment.BASELINE)
										.addComponent(lblAnimationSpeed)
										.addComponent(lblSpeed))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(speedSlider)
								.addContainerGap(211, Short.MAX_VALUE))
						.addGroup(createSequentialGroup()
								.addContainerGap(DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(createParallelGroup(Alignment.BASELINE)
										.addComponent(btnNext)
										.addComponent(btnPrev)
										.addComponent(btnRun))
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(txtPaneTextWillAppear)
								.addGap(69))
		);
	}
}
