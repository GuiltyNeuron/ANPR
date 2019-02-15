package com.flappybird.interfaces;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Quitter extends JPanel {
	
	Image bg;
	private JLabel[] boutons;

	public Quitter()
	{
		super(null);
		
		try {
			bg= ImageIO.read(new File("images/menup/quit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boutons=new JLabel[2];
		boutons[0]= new JLabel(new ImageIcon("images/menup/yes.png"));
		boutons[0].setLocation(new Point(160,200));
		boutons[0].setSize(108,105);
		
		boutons[1]= new JLabel(new ImageIcon("images/menup/NO.png"));
		boutons[1].setLocation(new Point(300,200));
		boutons[1].setSize(108,105);

		
		this.setSize(bg.getWidth(this), bg.getHeight(this));
		this.setOpaque(false);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg,0,0,this);
		this.add(boutons[0]);
		this.add(boutons[1]);
	}

	public JLabel[] getBoutons() {
		return boutons;
	}

}
