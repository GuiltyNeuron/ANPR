package com.flappybird.objets;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.flappybird.interfaces.Jeu;


/***********************************************************************
Ce classe représente les deux tuyaux (en haut et en bas)
***********************************************************************/

public class Tuyau extends Thread {
	
	private int posX1;
	private int posX2;
	private int posY1;
	private int posY2;
	private boolean pass;
	private Image[] tuyaux;
	// vitesse de translation des tuyaux
	private int vitesse;
	private Random random;
	private Oiseau oiseau;
	private Rectangle rect1;
	private Rectangle rect2;
	private int width;
	private int height;
	private Jeu j;

	
	public Tuyau(Oiseau oiseau, Jeu p) 
	{
		tuyaux= new Image[2];
		this.oiseau=oiseau;
		j=p;
		
		try 
		{
			tuyaux[0] = ImageIO.read(new File("images/maps/tuyau.png"));
			tuyaux[1] = ImageIO.read(new File("images/maps/tuyau2.png"));
		} catch (IOException e) {
			System.out.println("Erreur lors de l'initialisation des images de tuyaux !");
		}
		
		vitesse=2;
		pass=false;
		random=new Random();
		posX1=700;
		posX2=700;
		float diff;
		
		do
		{
			posY1=random.nextInt(150);
			posY2=random.nextInt(250); 
			diff=Math.abs((350-posY2)-(700-posY2));
		}
		while(diff<250 || diff>450);
		
		width=tuyaux[0].getWidth(p);
		height=tuyaux[0].getHeight(p);
		rect1=new Rectangle();
		rect2=new Rectangle();
	}


	
	public void run() {
	
		   while(true)
		   {		   
			   try {
				   Thread.sleep(10);
			   } catch (InterruptedException e) {
				   e.printStackTrace();
			   } 
			 
			   if(!oiseau.isMort() && j.isDemarrer())
			   {
				   
				   if (this.posX1 < -this.tuyaux[0].getWidth(j))
					   break;
				   
				   posX1-=vitesse;
				   posX2-=vitesse;
				   
				   if(oiseau.getRect().intersects(rect1) || oiseau.getRect().intersects(rect2))
				   {
					   oiseau.setMort(true);
				   }
				   
				   if(!pass && !oiseau.isMort())
					   if(oiseau.getPosX() > (this.posX1+this.width))
					   {   
						   pass=true;
				   		   j.score++;
					   }
			   }
			   else
			   {
				   if(!j.getStop() || oiseau.isMort())
					   break;
			   }

		   }
	}


	public int getPosX1() {
		return posX1;
	}



	public void setPosX1(int posX1) {
		this.posX1 = posX1;
	}



	public int getPosX2() {
		return posX2;
	}



	public void setPosX2(int posX2) {
		this.posX2 = posX2;
	}



	public int getPosY1() {
		return posY1;
	}



	public void setPosY1(int posY1) {
		this.posY1 = posY1;
	}



	public int getPosY2() {
		return posY2;
	}



	public void setPosY2(int posY2) {
		this.posY2 = posY2;
	}



	public boolean isPass() {
		return pass;
	}



	public void setPass(boolean pass) {
		this.pass = pass;
	}



	public int getVitesse() {
		return vitesse;
	}



	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}



	public Image[] getTuyaux() {
		return tuyaux;
	}
	

	public Rectangle getRect1()
	{
		rect1.setSize(this.width, this.height);
		rect1.setLocation(this.posX1, -this.posY1);
		return rect1;
	}
	
	public Rectangle getRect2()
	{
		rect2.setSize(this.width, this.height);
		rect2.setLocation(this.posX2, 700-this.posY2);
		return rect2;
	}

	
}
