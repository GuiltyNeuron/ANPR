package com.flappybird.animation;

import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Interval extends Timer {

	private int periode;
	private ActionListener e;
	
	public Interval(int periode, ActionListener e) {
		super(periode, e);
		this.periode=periode;
		this.e=e;;
		this.start();
	}
	
	
	public void demarrerAnimation()
	{
		if(!this.isRunning())
			this.restart();
	}
	
	public void arreterAnimation()
	{
		this.stop();
	}


	public static long deltaTime(long debut, long fin)
	{
		return (fin-debut) / 1000000;
	}
	
	
}
