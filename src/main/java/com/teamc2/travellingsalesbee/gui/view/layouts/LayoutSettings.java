package com.teamc2.travellingsalesbee.gui.view.layouts;

import com.teamc2.travellingsalesbee.gui.view.settings.SettingsButtons;
import com.teamc2.travellingsalesbee.gui.view.settings.SettingsSliders;

import javax.swing.*;
import java.awt.*;

/**
 * A GroupLayout for the settings panel
 *
 * @author Christopher Lane (cml476)
 * @author Melvyn Mathews (mxm499)
 */
public class LayoutSettings extends GroupLayout {

	/**
	 * Construct a layout for the settings panel
	 *
	 * @param host            Container of child elements
	 * @param settingsSliders Sliders for settings panel
	 * @param settingsButtons Buttons for settings panel
	 */
	public LayoutSettings(Container host, SettingsSliders settingsSliders, SettingsButtons settingsButtons) {
		super(host);

		JLabel runsOfType = settingsSliders.getLblRunsOfType();
		JLabel runAmount = settingsSliders.getLblNoOfRuns();

		JSlider runAmountSlider = settingsSliders.getNoOfRunsSlider();

		JButton randomise = settingsButtons.getBtnRandomise();
		JButton clear = settingsButtons.getBtnClear();
		JButton run = settingsButtons.getBtnRun();
		JButton back = settingsButtons.getBtnPrev();
		JButton play = settingsButtons.getBtnPlay();
		JButton forward = settingsButtons.getBtnNext();

		setHorizontalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addContainerGap()
								.addGap(60)
								.addGroup(createParallelGroup(Alignment.LEADING)
										.addGroup(createSequentialGroup()
												.addComponent(runsOfType)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(runAmount)
										)
										.addComponent(runAmountSlider)
								)
								.addGap(10)
								.addGroup(createParallelGroup(Alignment.TRAILING)
										.addGroup(createSequentialGroup()
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
												.addComponent(randomise)
												.addGap(18)
												.addComponent(clear)
												.addGap(18)
												.addComponent(run)
												.addGap(18)
												.addComponent(back)
												.addGap(18)
												.addComponent(play)
												.addGap(18)
												.addComponent(forward)
										)
								)
								.addContainerGap()
						)
		);
		setVerticalGroup(
				createParallelGroup(Alignment.LEADING)
						.addGroup(createSequentialGroup()
								.addContainerGap()
								.addGap(15)
								.addGroup(createParallelGroup(Alignment.BASELINE)
										.addComponent(runsOfType)
										.addComponent(runAmount)
								)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(runAmountSlider)
								.addContainerGap()
						)
						.addGroup(createSequentialGroup()
								.addContainerGap(DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(createParallelGroup(Alignment.BASELINE)
										.addComponent(forward)
										.addComponent(play)
										.addComponent(back)
										.addComponent(run)
										.addComponent(clear)
										.addComponent(randomise)
								)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addGap(30)
						)
		);
	}
}
