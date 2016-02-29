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

	public PanelAnimalAnimation() {
		//this.graphics = g;

		System.out.println("aAnimation constructor");
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		System.out.println("Drawing Bees");

		try {
			BufferedImage beeImg = ImageIO.read(this.getClass().getResource(url));
			TexturePaint paint = new TexturePaint(beeImg, new Rectangle((int)(beePosX + incrementX), (int)(beePosY + incrementY), 50, 50));
			g2.setPaint(paint);
			g2.fill(new Rectangle((int)beePosX, (int)beePosY, 50, 50));
		} catch (IOException e) {
			System.out.println("Image UEL was not set/url is invalid");
			e.printStackTrace();
		}

		Font font = new Font("Tahoma", Font.BOLD+Font.PLAIN, 100);
		g2.setFont(font);
		g2.setColor(Color.yellow);
		g2.drawString("HELLO THERE, HOW ARE YOU?", 15, 15);


		(new Thread() {
			public void run() {
				try {
					Thread.sleep(100);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	public void setImageURL(String url) {
		this.url = url;
	}

	/* Bee's X position */
	public double getBeePosX() { return beePosX; }

	public void setBeePosX(double beePosX) {
		this.beePosX = beePosX;
	}

	/* Bee's Y position */
	public double getBeePosY() { return beePosY; }

	public void setBeePosY(double beePosY) {
		this.beePosY = beePosY;
	}

	/* The amount that the bee will move along the paths x and y position per frame of animation */
	public void setIncrementX(double incrementX) {
		this.incrementX = incrementX;
	}

	public void setIncrementY(double incrementY) {
		this.incrementY = incrementY;
	}

}
