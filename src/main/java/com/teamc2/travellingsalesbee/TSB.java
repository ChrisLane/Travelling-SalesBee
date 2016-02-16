package com.teamc2.travellingsalesbee;

import com.teamc2.travellingsalesbee.gui.elements.PanelMap;
import com.teamc2.travellingsalesbee.gui.elements.PanelSettings;
import com.teamc2.travellingsalesbee.gui.elements.PanelToolbox;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class TSB extends JFrame implements Observer {

	private JPanel contentPane;
	private int width = 50;
	private int height = 50;
	private boolean hiveSet = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				TSB frame = new TSB();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TSB() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(71, 35, 35));
		setContentPane(contentPane);

		JPanel panelMap = new PanelMap(width, height);
		JPanel panelToolbox = new PanelToolbox(panelMap);
		JPanel panelSettings = new PanelSettings(panelMap);

		//ADD GRID TO THE GRIDMAP
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(panelToolbox, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(panelSettings, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
										.addComponent(panelMap, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)))
		);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(panelMap, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelSettings, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
						.addComponent(panelToolbox, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
	}

	@Override
	public void update(Observable o, Object arg) {
		// There is a new path! Visualise it
	}
}
