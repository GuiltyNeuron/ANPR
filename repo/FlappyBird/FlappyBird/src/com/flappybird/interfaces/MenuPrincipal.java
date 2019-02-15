package com.flappybird.interfaces;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.flappybird.animation.Interval;

/******************************************************
     Ce classe représente le menu principal du jeu 
******************************************************/

public class MenuPrincipal extends JPanel implements ActionListener {
	
	// un tableau qui contient les images d'oiseau
	private Image oiseau[];
	// l'arriere plan
	private Image bg;
	private Image nuage;
	private int posNuage;
	// un tableau qui contient les images de menu principal
	private JLabel menu[];
	// le chemin des images de menu principal
	private String chemin;
	// le chemin des image d'oiseau
	private String cheminOiseau;
	private Interval delaiAnim;
	private int pos;
	
	// Constructeur
	public MenuPrincipal()
	{
		super(null);
		
		// Initialisation
	    oiseau = new Image[19];
	    menu= new JLabel[4];
	    chemin="images/menup/";
	    cheminOiseau="images/oiseau/jaune/";
	    pos=0;
	    
	    // initialisation des images d'oiseau
	    for (int i = 0; i < oiseau.length; ++i)
			try {
				oiseau[i] = ImageIO.read(new File(cheminOiseau + (i+1) + ".png"));
			} catch (IOException e) {
				System.out.println("Erreur lors de l'initialisation des images d'oiseau !");
				break;
			}
	    
	    try {
			nuage= ImageIO.read(new File(chemin + "nuage.png"));
			posNuage= -nuage.getWidth(this);
		} catch (IOException e1) {
			System.out.println("Erreur lors de l'initialisation de nuages !");
		}
	    
	    // initialisation de l'arrire plan
	    try {
			bg= ImageIO.read(new File(chemin + "main.png"));
		} catch (IOException e) {
			System.out.println("Erreur lors de l'initialisation de l'arriere plan !");
		}
	    
	    // Initialisation de menu principal
	    menu[0]= new JLabel(new ImageIcon(chemin + "nouvelle partie.png"));
		menu[1]= new JLabel(new ImageIcon(chemin + "options.png"));
		menu[2]= new JLabel(new ImageIcon(chemin + "meilleur score.png"));
		menu[3]= new JLabel(new ImageIcon(chemin + "quitter.png"));
	    
	    
		// Ajout des boutons de menu principal
		menu[0].setSize(new Dimension(menu[0].getIcon().getIconWidth(), menu[0].getIcon().getIconHeight()));
		this.add(menu[0]);
		menu[0].setLocation(new Point(40,500));
		menu[1].setSize(new Dimension(menu[1].getIcon().getIconWidth(), menu[1].getIcon().getIconHeight()));
		this.add(menu[1]);
		menu[1].setLocation(new Point(660-menu[1].getWidth(),500));
		menu[2].setSize(new Dimension(menu[2].getIcon().getIconWidth(), menu[2].getIcon().getIconHeight()));
		this.add(menu[2]);
		menu[2].setLocation(new Point(40,600));
		menu[3].setSize(new Dimension(menu[3].getIcon().getIconWidth(), menu[3].getIcon().getIconHeight()));
		this.add(menu[3]);
		menu[3].setLocation(660-menu[3].getWidth(),600);
	}

	
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 // afficher l'arriere plan
		 g.drawImage(bg,0,0,this);
		 g.drawImage(nuage, posNuage,0, this);
		 // afficher l'oiseau
		 animOiseau(g);
	}
	

	private void animOiseau(Graphics g)
	{		
		if(delaiAnim == null)
			delaiAnim= new Interval(20, this);
		else
			delaiAnim.demarrerAnimation();
		
		ImageIcon  o=new ImageIcon(oiseau[pos]);
		
		if (o.getImageLoadStatus() == MediaTracker.COMPLETE) {
			 o.paintIcon(this, g, (this.getWidth() - oiseau[pos].getWidth(this))/2, 250); 
			 pos++;
			 if(pos == 19)
				 pos=0;
		}
	}


	public void actionPerformed(ActionEvent e) {
		if(posNuage <= this.getWidth())
		{
			posNuage+=5;
		}
		else
		{
			posNuage=-this.nuage.getWidth(this);
		}
		repaint();
	}


	public JLabel[] getMenu() {
		return menu;
	}	
	

	
}
