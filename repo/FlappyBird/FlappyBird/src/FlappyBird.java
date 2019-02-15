import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import com.flappybird.interfaces.Jeu;
import com.flappybird.interfaces.MeilleurScore;
import com.flappybird.interfaces.MenuPrincipal;
import com.flappybird.interfaces.Options;


public class FlappyBird extends JFrame {
	
	private JPanel panel;
	private MenuPrincipal menup;
	private Options options;
	private MeilleurScore meilleurScore;
	private Jeu jeu;
	
	public FlappyBird()
	{
		// Configuration de la fenetre
	   super("Flappy Bird");
	   this.panel= (JPanel) this.getContentPane();
	   this.setSize(new Dimension(700,800));
	   this.setResizable(false);
	   this.setLocationRelativeTo(null);	 
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   menup= new MenuPrincipal();
	   panel.add(menup);
	   
	   // Gestion des evenements
	   eventMenup();	   
	   
	   this.setVisible(true);
	}
	
	
	public void eventMenup()
	{
		menup.getMenu()[0].addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e) {
				panel.removeAll();
				jeu= new Jeu('j', 1);
				panel.add(jeu);
				eventJeu();
				panel.repaint();
				panel.revalidate();
				jeu.requestFocus();
			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {
				
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}			
		});
		menup.getMenu()[1].addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e) {
				panel.removeAll();
				options= new Options();
				eventOptions();
				panel.add(options);
				panel.repaint();
				panel.revalidate();
			}

			public void mouseEntered(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}			
		});
		
		menup.getMenu()[2].addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e) {
				panel.removeAll();
				meilleurScore= new MeilleurScore();
				eventMeilleurScore();
				panel.add(meilleurScore);
				panel.repaint();
				panel.revalidate();
			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {
				
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}			
		});
		
		menup.getMenu()[3].addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {
				
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}			
		});
	}
	
	
	public void eventOptions()
	{
		final char[] couleur={'j','b','r','v'};
		
		options.getMenu()[0].addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e) {
				panel.removeAll();
				panel.add(menup);
				panel.repaint();
				panel.revalidate();
			}

			public void mouseEntered(MouseEvent e) {
				options.getMenu()[1].setLocation(9,9);
				options.add(options.getMenu()[1]);
				options.repaint();
				options.revalidate();
			}

			public void mouseExited(MouseEvent e) {
				options.remove(options.getMenu()[1]);
				options.repaint();
				options.revalidate();
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}			
		});
		
		options.getMenu()[2].addMouseListener(new MouseListener()
		{
			int i=1;
			
			public void mouseClicked(MouseEvent e) {
				options.animOiseau(options.getGraphics(), couleur[i]);
				i++;
				if(i==4)
					i=0;
			}

			public void mouseEntered(MouseEvent e) {
				options.getMenu()[3].setVisible(true);
			}

			public void mouseExited(MouseEvent e) {
				options.getMenu()[3].setVisible(false);
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}			
		});
		
		options.getMenu()[4].addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e) {
				options.setPosMap();
				options.setIMap();
			}

			public void mouseEntered(MouseEvent e) {
				options.getMenu()[5].setVisible(true);
			}

			public void mouseExited(MouseEvent e) {
				options.getMenu()[5].setVisible(false);
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}			
		});

	}

	
	public void eventMeilleurScore()
	{
		meilleurScore.getMenu()[0].addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e) {
				panel.removeAll();
				panel.add(menup);
				panel.repaint();
				panel.revalidate();
			}

			public void mouseEntered(MouseEvent e) {
				meilleurScore.getMenu()[1].setLocation(9,9);
				meilleurScore.add(meilleurScore.getMenu()[1]);
				meilleurScore.repaint();
				meilleurScore.revalidate();
			}

			public void mouseExited(MouseEvent e) {
				meilleurScore.remove(meilleurScore.getMenu()[1]);
				meilleurScore.repaint();
				meilleurScore.revalidate();
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}			
		});
	}
	
	
	public void eventJeu()
	{
		jeu.getQuitter().getBoutons()[0].addMouseListener(new MouseListener()
		{

			public void mouseClicked(MouseEvent e) {
				panel.removeAll();
				panel.add(menup);
				panel.repaint();
				panel.revalidate();
			}

			public void mouseEntered(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}
		});
		
		
		jeu.getQuitter().getBoutons()[1].addMouseListener(new MouseListener()
		{

			public void mouseClicked(MouseEvent e) {
				jeu.resume();
			}

			public void mouseEntered(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}
		});
		
		
		jeu.getSauvMS().getBoutons()[0].addMouseListener(new MouseListener()
		{

			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}
		});
		
		
		jeu.getSauvMS().getBoutons()[1].addMouseListener(new MouseListener()
		{

			public void mouseClicked(MouseEvent e) {
				jeu.annulerSauv();
			}

			public void mouseEntered(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}
		});
			
	}
	
	public static void main(String[] args) {
		
		FlappyBird  fb= new FlappyBird();
		
	}	
	
}
