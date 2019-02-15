package com.flappybird.interfaces;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.flappybird.animation.Interval;

public class Options extends JPanel implements ActionListener {
	
	// un tableau qui contient les images d'oiseau jaune
	private Image oiseauJaune[];
	// un tableau qui contient les images d'oiseau bleu
	private Image oiseauBleu[];
	// un tableau qui contient les images d'oiseau rouge
	private Image oiseauRouge[];
	// un tableau qui contient les images d'oiseau vert
	private Image oiseauVert[];
	// l'arriere plan
	private Image bg;
	// les maps
	private Image maps[];
	// position de map
	private int posMap;
	// l'indice de map actuel
	private int iMap;
	// un tableau qui contient les boutons de menu options
	private JLabel menu[];
	// le chemin des images de menu options
	private String chemin;
	// le chemin des image d'oiseau
	private String cheminOiseau;
	// l'interval entre 2 images successives
	private Interval delaiAnim;
	// la position de l'image d'oiseau affichée
	private int pos;
	// la couleur d'oiseau affichée
	private char couleur;
	// Vitesse de translation de Map
	private Interval vitesseTrans;
	
	
	// Constructeur
	public Options()
	{
		super(null);
		
		// Initialisation
	    oiseauJaune = new Image[19];
	    oiseauRouge = new Image[19];
	    oiseauBleu = new Image[19];
	    oiseauVert = new Image[19];
	    maps= new Image[3];

	    
	    menu= new JLabel[6];
	    chemin="images/options/";
	    cheminOiseau="images/oiseau/";
	    pos=0;
	    couleur='j';
	    
	    // initialisation des images d'oiseau
	    for (int i = 0; i < oiseauJaune.length && i < oiseauRouge.length && i < oiseauBleu.length && i < oiseauVert.length; ++i)
			try {
				oiseauJaune[i] = ImageIO.read(new File(cheminOiseau + "jaune/" + (i+1) + ".png"));
				oiseauRouge[i] = ImageIO.read(new File(cheminOiseau + "rouge/" + (i+1) + ".png"));
				oiseauBleu[i] = ImageIO.read(new File(cheminOiseau + "bleu/" + (i+1) + ".png"));
				oiseauVert[i] = ImageIO.read(new File(cheminOiseau + "vert/" + (i+1) + ".png"));
			} catch (IOException e) {
				System.out.println("Erreur lors de l'initialisation des images d'oiseau !");
				break;
			}
	    
	    // initialisation de l'arrire plan
	    try {
			bg= ImageIO.read(new File(chemin + "options page.png"));
		} catch (IOException e) {
			System.out.println("Erreur lors de l'initialisation de l'arriere plan !");
		}
	    
	    // initialisation des maps
	    for (int i = 0; i < maps.length; ++i)
			try {
				maps[i] = ImageIO.read(new File("images/options/maps/" + (i+1) + ".png"));
			} catch (IOException e) {
				System.out.println("Erreur lors de l'initialisation des maps !");
				break;
			}
	    
	    posMap=-maps[0].getWidth(this);
	    iMap=0;
	    
	    // Initialisation de menu principal
	    menu[0]= new JLabel(new ImageIcon(chemin + "flesh not pressed.png"));
		menu[1]= new JLabel(new ImageIcon(chemin + "flesh pressed.png"));
		menu[2]= new JLabel(new ImageIcon(chemin + "not pressed.png"));
		menu[3]= new JLabel(new ImageIcon(chemin + "pressed.png"));
		menu[4]= new JLabel(new ImageIcon(chemin + "pressed.png"));
		menu[5]= new JLabel(new ImageIcon(chemin + "not pressed.png"));

	    
		// Ajout des boutons de menu options
		menu[0].setSize(new Dimension(menu[0].getIcon().getIconWidth(), menu[0].getIcon().getIconHeight()));
		this.add(menu[0]);
		menu[0].setLocation(new Point(10,10));
		menu[1].setSize(new Dimension(menu[1].getIcon().getIconWidth(), menu[1].getIcon().getIconHeight()));
		menu[2].setSize(new Dimension(menu[2].getIcon().getIconWidth(), menu[2].getIcon().getIconHeight()));
		this.add(menu[2]);
		menu[2].setLocation(new Point(660-menu[2].getWidth(),200));
		menu[3].setSize(new Dimension(menu[3].getIcon().getIconWidth(), menu[3].getIcon().getIconHeight()));
		menu[3].setLocation(659-menu[3].getWidth(),199);
		menu[3].setVisible(false);
		this.add(menu[3]);
		menu[4].setSize(new Dimension(menu[4].getIcon().getIconWidth(), menu[4].getIcon().getIconHeight()));
		this.add(menu[4]);
		menu[4].setLocation(new Point(660-menu[4].getWidth(),500));
		menu[5].setSize(new Dimension(menu[5].getIcon().getIconWidth(), menu[5].getIcon().getIconHeight()));
		menu[5].setLocation(659-menu[5].getWidth(),499);
		menu[5].setVisible(false);
		this.add(menu[5]);
	}
	
	
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 // afficher l'arriere plan
		 g.drawImage(bg,0,0,this);
		 
		 // afficher l'oiseau
		 animOiseau(g, couleur);
		// animMap(g);
		 animMap(g);
	}
	
	public void animOiseau(Graphics g, char c)
	{			
		this.couleur=c;
		
		if(delaiAnim == null)
			delaiAnim= new Interval(20, this);
		else
			delaiAnim.demarrerAnimation();
		
		ImageIcon  o;

		switch(couleur)
		{
			case 'j':{
				o=new ImageIcon(oiseauJaune[pos]);
				
				if (o.getImageLoadStatus() == MediaTracker.COMPLETE) {
					 o.paintIcon(this, g, (this.getWidth() - oiseauJaune[pos].getWidth(this))/2, 195); 
					 pos++;
					 if(pos == 19)
						 pos=0;
				}
			};break;
			case 'r':{
				o=new ImageIcon(oiseauRouge[pos]);
				
				if (o.getImageLoadStatus() == MediaTracker.COMPLETE) {
					 o.paintIcon(this, g, (this.getWidth() - oiseauRouge[pos].getWidth(this))/2, 195); 
					 pos++;
					 if(pos == 19)
						 pos=0;
				}
			};break;	
			case 'b':{
				o=new ImageIcon(oiseauBleu[pos]);
				
				if (o.getImageLoadStatus() == MediaTracker.COMPLETE) {
					 o.paintIcon(this, g, (this.getWidth() - oiseauBleu[pos].getWidth(this))/2, 195); 
					 pos++;
					 if(pos == 19)
						 pos=0;
				}
			};break;	
			case 'v':{
				o=new ImageIcon(oiseauVert[pos]);
				
				if (o.getImageLoadStatus() == MediaTracker.COMPLETE) {
					 o.paintIcon(this, g, (this.getWidth() - oiseauVert[pos].getWidth(this))/2, 195); 
					 pos++;
					 if(pos == 19)
						 pos=0;
				}
			};break;	
		}
	}

	public void animMap(Graphics g)
	{	
 		g.drawImage(maps[iMap], posMap, 400, this); 
 		
 		if(posMap <= 200)
 			posMap+=20;
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}
		
	public JLabel[] getMenu() {
		return menu;
	}	
	
	public void setPosMap()
	{
		this.posMap=-maps[0].getWidth(this);
	}
	
	
	
	public int getiMap() {
		return iMap;
	}

	
	public void setIMap()
	{
		this.iMap++;
		if(iMap==3)
			iMap=0;
	}
	
	public void setIMap(int iMap)
	{
		this.iMap=iMap;
	}


	public char getCouleur() {
		return couleur;
	}
	
	public void setCouleur(char couleur) {
		this.couleur=couleur;
	}
	

}
