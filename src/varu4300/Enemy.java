package varu4300;
/*
 * Title: Space Invaders
 *  Name: Minesh Varu
 *  Date: November 27,2014
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
@SuppressWarnings("serial")
public class Enemy extends JComponent implements Runnable{
	//creating enemy ship class variables
	private Image eship;
	private int x = 0 , y = 0, max = 430, min = 0, healthwidth=70;
	private Thread t;
	boolean alive = false;
	private Random r = new Random();
	public Enemy(){
		//always start the enemy ship off at random x
		x = r.nextInt((max - min)+ 1) +min;
		//start enemy ship off at fixed y
		y = 35;
		try {
			//setting the image up
			eship = ImageIO.read(new File("enemy.png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void draw(Graphics g){
		//draw enemy ship as well as health bar
		g.drawImage(eship,x,y,70,70,this);
		g.setColor(Color.green);
		g.fillRect(x, y, healthwidth, 5);
		
	}
	/**
	 * 
	 * @return the health of the enemy
	 */
	public int getHealth()
	{
		return healthwidth;
	}
	/**
	 * enemy will loose health on invocation
	 */
	public void looseHealth(){
		healthwidth = healthwidth - 10;
		
	}
	/**
	 * 
	 * @return if the thread is running or not
	 */
	public boolean isAlive(){
		return alive;
	}
	/**
	 * Giving the enemy ship a thread and running it until it reaches
	 * the end of its path
	 */
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while(this.getY() < 680){
			y += 5;
			try {
				t.sleep(200);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}
	}
	/**
	 * starting the thread
	 */
	public void start(){
		t = new Thread(this);
		alive = true;
		t.start();

	}


	

}
