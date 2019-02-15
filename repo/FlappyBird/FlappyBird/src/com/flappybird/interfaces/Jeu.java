package com.flappybird.interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.flappybird.animation.Interval;
import com.flappybird.objets.Oiseau;
import com.flappybird.objets.Tuyau;

public class Jeu extends JPanel implements ActionListener, KeyListener {

	// arriere plan du jeu
	private Image bg;
	private Image bg2;
	private int bgX;
	private int bgY;
	private int bg2X;
	// l'oiseau 
	private Oiseau oiseau;
	private Interval delaiAnim;
	private int pos;
	private ArrayList<Tuyau> tuyaux;
	private int intervalTuyau;
	private Random random;
	private long tempsActuel;
	private long dernierTuyau;
	private float tempsTuyau;
	private Color colorRect;
	private Image scoreImg;
	public int score;
	private Image[] scoreT;
	private Image start;
	private Image gameOver;
	private int posGOY;
	private int posGOX;
	private boolean demarrer;
	private boolean stop;
	private Quitter quitter;
	private SauverMS sauvMS;
	private boolean sauvMSVis;
	private boolean etatSauv;

	
	/********************** Constructeur **************************************/

	
	public Jeu(char couleur, int background)
	{
		super();
		pos=0;
		scoreT=new Image[10];
		
		// Activer les evenements du clavier sur ce JPanel
        this.setFocusable(true);
        addKeyListener(this);
		this.setSize(new Dimension(700,800));
        
		oiseau= new Oiseau(couleur, this);
	    Thread t = new Thread(oiseau);
	    t.start();
	    
	    try {
			bg= ImageIO.read(new File("images/maps/" + background + ".png"));
			bg2=ImageIO.read(new File("images/maps/" + background + "-" + background + ".png"));
		} catch (IOException e) {
			System.out.println("Erreur lors de l'initialisation de l'arriere plan du jeu !");
		}
	    

	    try {
			scoreImg= ImageIO.read(new File("images/maps/score.png"));
		} catch (IOException e) {
			System.out.println("Erreur lors de l'initialisation de score!");
		}	    
	    
	    tuyaux=new ArrayList<Tuyau>();
		random=new Random();		
		intervalTuyau=random.nextInt(2000) + 1000;
		bgX=0;
		bg2X=699;
		bgY=0;
		
		colorRect= new Color(255, 0, 0, 0);
		
		
		for(int i=0;i<10;i++)
		    try {
				scoreT[i]= ImageIO.read(new File("images/score/" + i + ".png"));
			} catch (IOException e) {
				System.out.println("Erreur lors de l'initialisation des image de score !");
				break;
			}	
		
		
	    try {
			start= ImageIO.read(new File("images/jeu/start.png"));
		} catch (IOException e) {
			System.out.println("Erreur lors de l'initialisation de l'image Start !");
		}	   
	    
	    
	    try {
			gameOver= ImageIO.read(new File("images/jeu/GAMEOVER.png"));
		} catch (IOException e) {
			System.out.println("Erreur lors de l'initialisation de l'image Gameover !");
		}	   
	    
	    posGOX=(700-start.getWidth(this))/2;
	    posGOY=800;    
	    demarrer=false;
	    stop=false;
	    quitter=new Quitter();
	    sauvMS=new SauverMS();
	    sauvMSVis=false;
	    etatSauv=false;
	}
	
	
	/********************** Animation **************************************/
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);			
		g2d.setColor(colorRect);
		
		
		/********************* Afficher l'arriere plan ********************************/
		if(!oiseau.isMort() && demarrer) 
		{
				if(bgX<-699)
					bgX=699;
		
				if(bg2X<-699)
					bg2X=699;
				
			
				 g.drawImage(bg,bgX,bgY,this);
				 g.drawImage(bg2,bg2X,bgY,this);
				 bgX--;
				 bg2X--;
		}
		else
		{
			 g.drawImage(bg,bgX,bgY,this);
			 g.drawImage(bg2,bg2X,bgY,this);
		}
		
		// animer les tuyaux
		animTuyaux(g2d);
		// animer l'oiseau
		animOiseau(g2d);

		afficherScore(g2d);
	
		if(!demarrer && !oiseau.isMort())
		{
		 	g2d.drawImage(start,(700-start.getWidth(this))/2,(800-start.getHeight(this))/2,this);
		}
		
		g2d.drawImage(scoreImg,10,10,this); 
		
		if(oiseau.isMort())
			g2d.drawImage(gameOver,posGOX,posGOY,this); 			
	}
	
	// animer l'oiseau
	public void animOiseau(Graphics2D g)
	{
		if(delaiAnim == null)
			delaiAnim= new Interval((int) oiseau.getVitesseDep(), this);
		else
			delaiAnim.demarrerAnimation();
		
		if(!oiseau.isMort())
		{
			g.drawImage(oiseau.getImages()[pos],oiseau.getPosX(), oiseau.getPosY(), this);	
			g.draw(oiseau.getRect());
			
			pos++;
			if(pos == 19)
				pos=0;
		}
		else
		{
			g.drawImage(oiseau.getImages()[pos],oiseau.getPosX(), oiseau.getPosY(), this);
		}
	}


	// animer les tuyaux
	public void animTuyaux(Graphics2D g)
	{
		if(!oiseau.isMort() && this.demarrer)
		{
			if(dernierTuyau == 0)
				dernierTuyau=System.currentTimeMillis();
			
			
			// ajout d'un tuyau
			tempsActuel=System.currentTimeMillis();
			tempsTuyau = tempsActuel-dernierTuyau;
			
			if(tempsTuyau > intervalTuyau)
			{
				Tuyau tuyau=new Tuyau(oiseau, this);
				tuyaux.add(tuyau);
				tuyau.start();
				dernierTuyau=tempsActuel;
				intervalTuyau=random.nextInt(1500) + 700;
			}
	
			
			for (Iterator<Tuyau> it = tuyaux.iterator(); it.hasNext(); )
			{   
				Tuyau t=it.next();
				g.drawImage(t.getTuyaux()[0],t.getPosX2(), 700-t.getPosY2() , this);
				g.draw(t.getRect2());
				g.drawImage(t.getTuyaux()[1],t.getPosX1(), -t.getPosY1(), this);	
				g.draw(t.getRect1());
	
				
				if (t.getPosX1() < -t.getTuyaux()[0].getWidth(this))
				{
					int p=tuyaux.indexOf(t);
					try {
						t.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					t.interrupt();
					it.remove();
				}
					
			}		
		}
		else
		{
			for(Tuyau t: tuyaux)
			{   
				g.drawImage(t.getTuyaux()[0],t.getPosX2(), 700-t.getPosY2() , this);
				g.drawImage(t.getTuyaux()[1],t.getPosX1(), -t.getPosY1(), this);	
			}	
		}
	}
	
	
	// afficher le score
	public void afficherScore(Graphics2D g)
	{
		String src= Integer.toString(this.score);
		
		for(int i=0;i<src.length();i++)
		{
			int p=Character.getNumericValue(src.charAt(i));
			if(i>1)
				g.drawImage(scoreT[p], scoreImg.getWidth(this) +(i*33) + 15, 10, this);
			else
				g.drawImage(scoreT[p], scoreImg.getWidth(this) +(i*33) + 15, 10, this);

		}
		
	}

	// redessiner la fenetre
	public void actionPerformed(ActionEvent e) {
		
		if(!oiseau.isMort() && demarrer)
		{
			if(!oiseau.espaceAppuiye())
			{
				oiseau.setPosY(oiseau.getPosY()+5);
			}
		}
		else if(oiseau.isMort())
		{
			if(oiseau.getPosY()<=706)
				oiseau.setPosY(oiseau.getPosY()+10);
			
			if(posGOY > (800-gameOver.getHeight(this))/2)
				posGOY-=15;		
			
			
			if((posGOY <= (800-gameOver.getHeight(this))/2 && !sauvMSVis) && !etatSauv)
			{
				sauvgarde();
				this.sauvMSVis=true;
			}
		}		
		
		if(!sauvMSVis)
			repaint();
	}
	
	
	
	/********************** Evenement du jeu **************************************/

	
	// redemarrer le jeu si l'oiseau est mort
	public void redemarrer()
	{		
		for (Iterator<Tuyau> it = tuyaux.iterator(); it.hasNext(); )
		{
			Tuyau t=it.next();
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t.interrupt();
			it.remove();
		}
		
		this.demarrer=true;
		this.oiseau.setPosY(310);
		oiseau.setMort(false);
		this.score=0;		
		this.etatSauv=false;
		this.sauvMSVis=false;
	}

	public void stop()
	{
		this.demarrer=false;
		this.stop=true;
		if(quitter!=null)
			this.remove(quitter);
		quitter.setLocation((this.getWidth()-quitter.getWidth())/2, (this.getHeight()-quitter.getHeight())/2);
		this.add(quitter);
	}
	
	public void resume()
	{
		this.demarrer=true;
		this.stop=false;
		if(quitter!=null)
			this.remove(quitter);
	}
	
	public void sauvgarde()
	{
		this.demarrer=false;
		this.stop=true;
		sauvMS.setLocation((this.getWidth()-sauvMS.getWidth())/2, (this.getHeight()-sauvMS.getHeight())/2);
		this.add(sauvMS);
		repaint();
		this.sauvMSVis=true;
		
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void annulerSauv()
	{
		if(sauvMSVis)
		{
			this.remove(sauvMS);
			this.sauvMSVis=false;
			this.etatSauv=true;
		}
	}

	
	
	
	/********************** Evenement clavier **************************************/

	
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() ==  KeyEvent.VK_SPACE)
		{
			oiseau.setToucheAppuiye(true);
			
			if(!demarrer)
			{
				this.demarrer=true;
				if(quitter != null)
					this.remove(quitter);
			}
			
			if(oiseau.isMort())
			{		
				redemarrer();
				if(quitter != null)
					this.remove(quitter);
			}

		}
		else if(e.getKeyCode() ==  KeyEvent.VK_ESCAPE)
		{
			if(!stop)
				stop();
			else
				resume();
		}
	}
	
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() ==  KeyEvent.VK_SPACE)
			oiseau.setToucheRelache(true);
	}


	public void keyTyped(KeyEvent e) {
		
	}

	
	
	
	/********************** Getters et Setters **************************************/
	
	public boolean isDemarrer() {
		return demarrer;
	}

	public void setDemarrer(boolean demarrer) {
		this.demarrer = demarrer;
	}

	public boolean getStop()
	{
		return this.stop;
	}

	public Quitter getQuitter() {
		return quitter;
	}
	
	public SauverMS getSauvMS()
	{
		return sauvMS;
	}
	

}
