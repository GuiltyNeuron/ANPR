package com.flappybird.fichiers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GestFichier {
	
	private Scanner sc;
	private FileWriter fout;
	private File f;
	
	
	public GestFichier(String fichier)
	{
		f=new File(fichier);
		

	}
	
	 
	public HashMap<String, Integer> listerMS()
	{
		
		// si le fichier n'existe pas on va le créer
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
		// ouvrir le fichier en lecture
		try {
			sc=new Scanner(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/***************** Lire les scores a partir du fichier ********************************/
		String ligne;
		String nickname;
		int score;
		HashMap<String, Integer> ms= new HashMap<String, Integer>();
				
		while (sc.hasNext())
		{
		   ligne=sc.nextLine();
		   nickname=ligne.split("-")[0];
		   score=Integer.valueOf(ligne.split("-")[1]);
		   ms.put(nickname, score);
		}
		sc.close();
		

		HashMap<String, Integer> lstTri=triParScore(ms);

		// Trier les scores
		return 	lstTri;
	}
	
	
	// Trier la liste par score
	private HashMap<String, Integer> triParScore(HashMap<String,Integer> ms)
	{
		 List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(ms.entrySet());

	     Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	    	 public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
	    		 return (o1.getValue()).compareTo(o2.getValue());
	         }
	     });

	     HashMap<String, Integer> lstTri = new LinkedHashMap<String, Integer>();
	     for (Map.Entry<String, Integer> val : list) {
	    	 lstTri.put(val.getKey(), val.getValue());
	     }
	        	        
	     return lstTri;
	}
	
	
	// ajouter un nouveau meilleur score
	public void ajouterScore(String nickname, int score)
	{
		String ms=nickname + "-" + score + "\n";
		
		// ouvrir le fichier en ecriture
		try {
			fout=new FileWriter(f, true);
			fout.write(ms);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public String getOptions()
	{	
		// si le fichier n'existe pas on va le créer et on ajoute les options de jeu par défaut
		if(!f.exists())
			try {
				f.createNewFile();
				this.setOptions('j', 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
		// ouvrir le fichier en lecture
		try {
			sc=new Scanner(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/***************** Recuperer les options à partir du fichier ********************************/
		char oiseau;
		int map;


		String ligne=sc.nextLine();
		oiseau=ligne.split(":")[1].charAt(0);
		ligne=sc.nextLine();
		map= Integer.valueOf(ligne.split(":")[1]);
		
		sc.close();
		
		return 	oiseau + "-" + map;
	}
	
	public void setOptions(char oiseau, int map)
	{	
		String options="oiseau:" + oiseau + "\nmap:" + map;
		
		// ouvrir le fichier en ecriture
		try {
			fout=new FileWriter(f);
			fout.write(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
