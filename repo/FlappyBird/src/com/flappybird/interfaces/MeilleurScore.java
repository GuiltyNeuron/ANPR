package com.flappybird.interfaces;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.flappybird.animation.Interval;

public class MeilleurScore extends JPanel implements ActionListener {

	// l'arriere plan
	private HashMap<String, Integer> lstMS;
	private Image bg;
	private Image couronne;
	private JLabel menu[];
	private Interval delaiAnim;	
	private JList<String> meilleurScore;
	private JScrollPane conteneur;
	private int angle;
	private int offset=5;
	
	// Constructeur
	public MeilleurScore(HashMap<String,Integer> lstMS)
	{
		super(null);
		
		this.lstMS=lstMS;
		menu= new JLabel[2];
		
	    // initialisation de l'arrire plan
	    try {
			bg= ImageIO.read(new File("images/meilleurscore/bg.png"));
			couronne= ImageIO.read(new File("images/meilleurscore/couronne.png"));
		} catch (IOException e) {
			System.out.println("Erreur lors de l'initialisation de l'arriere plan de meilleur score !");
		}
	    
	    menu[0]= new JLabel(new ImageIcon("images/options/flesh not pressed.png"));
		menu[1]= new JLabel(new ImageIcon("images/options/flesh pressed.png"));
		
		
		menu[0].setSize(new Dimension(menu[0].getIcon().getIconWidth(), menu[0].getIcon().getIconHeight()));
		this.add(menu[0]);
		menu[0].setLocation(new Point(10,10));
		menu[1].setSize(new Dimension(menu[1].getIcon().getIconWidth(), menu[1].getIcon().getIconHeight()));
		
		meilleurScore= new JList<String>();
		DefaultListModel<String> lm= new DefaultListModel<String>();


		/******************* afficher la liste triée par score dans l'ordre décroissant *******************/
        ArrayList<String> keys = new ArrayList<String>(lstMS.keySet());
		int c=1;
        for(int i=keys.size()-1; i>=0;i--){
			String score= " " + c + "-  " + keys.get(i) + " : " + lstMS.get(keys.get(i)); 
		    lm.addElement(score);
			c++;
        }
		meilleurScore.setModel(lm);
		meilleurScore.setFixedCellHeight(50);
		meilleurScore.setFont(new Font("Arial", Font.BOLD, 16));
		meilleurScore.setOpaque(true);
		conteneur= new JScrollPane(meilleurScore);
		conteneur.setSize(691, 450);
		conteneur.setLocation(new Point(2,350));
		conteneur.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(conteneur);

	}

	
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 // afficher l'arriere plan
		 g.drawImage(bg,0,0,this);	
		 // afficher la couronne
		 animCouronne(g);
	}
	
	// animer la couronne (rotation)
	private void animCouronne(Graphics g)
	{
		if(delaiAnim == null)
			delaiAnim= new Interval(20, this);
		else
			delaiAnim.demarrerAnimation();

		Rectangle rect = this.getBounds();
		float w = couronne.getWidth(this);
		float h = couronne.getHeight(this);
			
		AffineTransform transform = new AffineTransform();
		Graphics2D g2d = (Graphics2D) g;
		transform.setToTranslation((rect.width - w) / 2, 150);
			
		transform.rotate(Math.toRadians(angle), w/2, h/2);
		g2d.drawImage(couronne, transform, this);
	}

	 
	public void actionPerformed(ActionEvent e) {
		angle -= offset;
		if (angle <= 0) {
			angle = 360;
		}
		repaint();
	}
	
	
	public JLabel[] getMenu()
	{
		return this.menu;
	}
	
}
