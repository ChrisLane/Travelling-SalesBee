package com.teamc2.travellingsalesbee;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.teamc2.travellingsalesbee.gui.elements.PanelMap;
import com.teamc2.travellingsalesbee.gui.elements.PanelSettings;
import com.teamc2.travellingsalesbee.gui.elements.PanelToolbox;

public class TSB extends JFrame implements Observer {

	private final JPanel contentPane;
	private final int width = 50;
	private final int height = 50;
	private final static int roundingFactor = 50;
	private boolean hiveSet = false;
	private static PanelMap panelMap;

	/**
	 * Create the frame.
	 */
	public TSB() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(300, 300, 956, 689);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(71, 35, 35));
		setContentPane(contentPane);

		PanelMap panelMap = new PanelMap(width, height);
		this.panelMap = panelMap;
		JPanel panelToolbox = new PanelToolbox(panelMap);
		JPanel panelSettings = new PanelSettings(panelMap, panelMap);

		//ADD GRID TO THE GRIDMAP
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(panelToolbox, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(panelSettings, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
										.addComponent(panelMap, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)))
		);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(panelMap, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelSettings, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
						.addComponent(panelToolbox, GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//Temporary variables, should be removed and replaced for width and height when not in static main.
		int roundHeight = 50;
		int roundWidth = 50;
		EventQueue.invokeLater(() -> {
			try {
				TSB frame = new TSB();
				frame.setVisible(true);
				
				//Listener to implement correct resizing so the gridmap keeps it's grid proportion
				frame.addComponentListener(new ComponentListener() {
			        public void componentResized(ComponentEvent e) {
			            System.out.println("Resized");
			            //frameWidth = snap to the nearest cell width
			            int frameWidth = (frame.getWidth()/roundWidth)*roundWidth;
			            
			            //frameHeight = snap to the nearest cell Height
			            int frameHeight = (frame.getHeight()/(2*roundHeight))*(2*roundHeight);
			            
			            //Resize frame according to the nearest possible snap
			            frame.setSize(frameWidth,frameHeight);
			        }
			        public void componentHidden(ComponentEvent arg0) {}
			        public void componentMoved(ComponentEvent arg0) {}
			        public void componentShown(ComponentEvent arg0) {}
			    });
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		// There is a new path! Visualise it
	}
}
