package varu4300;
/*
 * Title: Space Invaders
 *  Name: Minesh Varu
 *  Date: November 27,2014
 */
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel{

	public static void main(String[] args)  {
		
		//Creating and drawing the entire game
		JFrame panel = new JFrame("Asteroids");
		Board b = new Board();
		b.start();
		panel.addKeyListener(b);
		panel.setContentPane(b);
		panel.setResizable(false);
		panel.setBackground(Color.black);
		panel.setVisible(true);
		panel.setSize(500,760);
		panel.setLocation(0,0);
		panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
