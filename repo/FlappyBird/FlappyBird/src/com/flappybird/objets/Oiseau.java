package com.flappybird.objets;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.flappybird.animation.Interval;
import com.flappybird.interfaces.Jeu;


/***********************************************************************
	Ce classe représente l'oiseau
 ***********************************************************************/

public class Oiseau implements Runnable {
	
   // contient les images de l'oiseau
   private Image images[];
   // le temps de dernier saut
   private long dernierSaut;
   // le temps actuel
   private long temps;
   // la durée entre le dernier saut et le temps actuel
   private long tempsSaut;
   // l'inverval de temps entre deux sauts successifs
   private int intervalSaut;
   // vrai si l'oiseau peut sauter
   private boolean peutSauter;
   // vrai si l'oiseau est mort
   private boolean mort;
   // position de l'oiseau sur l'axe X
   private int posX;
   // position de l'oiseau sur l'axe Y
   private int posY;
   private int width;
   private int height;
   // le chemin des images de l'oiseau dans le projet
   private String chemin;
   // vitesse de l'animation de déplacement horizontal
   private int vitesseDep;
   // vitesse de déplacement vertical
   private int vitesse;
   private boolean toucheAppuiye;
   private boolean toucheRelache;
   private Rectangle rect;
   private Jeu j;


   
   
   public Oiseau(char couleur, Jeu p)
   {   
	   /************************* Importation des images de l'oiseau **************************************/
	   
	   images= new Image[19];
	   j=p;
	   
	   switch(couleur)
	   {
	   		case 'j':
	   			chemin="images/jeu/jaune/";
	   			break;
	   		case 'r':
	   			chemin="images/jeu/rouge/";
	   			break;
	   		case 'v':
	   			chemin="images/jeu/vert/";
	   			break;
	   		case 'b':
	   			chemin="images/jeu/bleu/";
	   			break;
	   }
	   
	    for (int i = 0; i < images.length; ++i)
			try {
				images[i] = ImageIO.read(new File(chemin + (i+1) + ".png"));
			} catch (IOException e) {
				System.out.println("Erreur lors de l'initialisation des images d'oiseau !");
				break;
			}
	    
	    
	    /************************* Initialisation **************************************/
	    
	    posX=100;
	    posY=310;
	    intervalSaut=600;
	    mort=false;
	    peutSauter=true;
	    vitesseDep=20;
	    vitesse=10;
	    width=images[0].getWidth(p);
	    height=images[0].getHeight(p);
	    rect= new Rectangle();
   }
   
   
   public void run()
   {	
	   dernierSaut=System.nanoTime();
	   
	   while(true)
	   {	
		   
		   try {
			   Thread.sleep(10);
		   } catch (InterruptedException e) {
			   e.printStackTrace();
		   } 
		    
		   if(!mort && j.isDemarrer())
		   {
			   temps=System.nanoTime();
			   tempsSaut = Interval.deltaTime(dernierSaut, temps);

			   
			   if(tempsSaut > intervalSaut)
			   {
				   peutSauter=true;
				   dernierSaut=temps;
			   }	
			   
			   if(espaceAppuiye() && peutSauter)
			   {
				   volHaut();
			   }
			   
			   if (posY >= (762-rect.getHeight()) || posY <= (-height+80))
			   {
				   mort = true;					
			   }

		   }
	   }
   }
   
   
   public void volHaut() {	
	   
	   long dernierDep=0;
	   int posActuel=posY;
	   
	   while(posY>=posActuel-80)
	   {
		   long temps=System.currentTimeMillis() / 10;
		   if(temps != dernierDep)
		   {	
			   posY-=vitesse;
			   dernierDep=temps;
		   }
		   
	   }
	   
	   toucheAppuiye=false;
	   toucheRelache=false;
   }
   
   
   public Image[] getImages() {
	   return images;
   }
	
	
   public int getPosX() {
	   return this.posX;
   }
	   
	   
   public void setPosX(int x) {
	   this.posX=x;
   }
	   
	   
   public int getPosY() {
	      return this.posY;
   }
	   
	   
   public void setPosY(int y) {
	   this.posY=y;
   }


	public boolean isPeutSauter() {
		return peutSauter;
	}
	
	public int getVitesseDep()
	{
		return this.vitesseDep;
	}
	
	public void setVitesse(int vitesse)
	{
		this.vitesseDep=vitesse;
	}


	public boolean isMort() {
		return this.mort;
	}
	
	public void setMort(boolean mort) {
		this.mort=mort;
	}
	
	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public void setToucheAppuiye(boolean toucheAppuiye) {
		this.toucheAppuiye = toucheAppuiye;
	}


	public void setToucheRelache(boolean toucheRelache) {
		this.toucheRelache = toucheRelache;
	}
	
	
	public boolean espaceAppuiye()
	{
		return (toucheAppuiye && toucheRelache);
	}
	
	
	public Rectangle getRect()
	{
		//rect.set
		rect.setSize(this.width, this.height-10);
		rect.setLocation(this.posX, this.posY+10);
		return rect;
	}
	
	
}