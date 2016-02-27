package com.teamc2.travellingsalesbee.gui.view;

import com.teamc2.travellingsalesbee.gui.NaiveStep;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PanelAnimalAnimation extends JPanel {

	private double beePosX = 200, beePosY = 200,incrementX = 0, incrementY = 0;
	private String url;
	private NaiveStep move;
	private JPanel panel;

	private Graphics2D graphics;

	public PanelAnimalAnimation(JPanel panel, String url, NaiveStep move, Graphics2D g) {
		this.panel = panel;
		this.url = url;
		this.move = move;
		this.graphics = g;

		System.out.println("aAnimation constructor");

		this.paint(g);

		panel.add(this);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = this.graphics;

		System.out.println("Drawing Bees");


		try {
			BufferedImage beeImg = ImageIO.read(this.getClass().getResource(url));
			TexturePaint paint = new TexturePaint(beeImg, new Rectangle((int)(beePosX + incrementX), (int)(beePosY + incrementY), 50, 50));
			g2.setPaint(paint);
			g2.fill(new Rectangle((int)beePosX, (int)beePosY, 50, 50));
		} catch (IOException e) {
			e.printStackTrace();
		}

		(new Thread() {
			public void run() {
				try {
					Thread.sleep(100);
				} catch(Exception e) {
					e.printStackTrace();
				}

				repaint();
			}
		}).start();

	}
}
