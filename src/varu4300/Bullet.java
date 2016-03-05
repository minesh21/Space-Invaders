package varu4300;
/*
 * Title: Space Invaders
 *  Name: Minesh Varu
 *  Date: November 27,2014
 */
import java.awt.Color;
import java.awt.Graphics;
public class Bullet implements Runnable{
	private int curX1 = 0,  curY1 = 0,  width = 0,  height = 0;
	private Thread t;
	private boolean alive = false;
	/**
	 * Empty Bullet constructor
	 */
	public Bullet() {
		
	}
	/**
	 * Constructor initialization
	 * @param x1 - the player's x coordinate
	 * @param y1 - the player's y coordinate 
	 * @param w  - the width of the bullet
	 * @param h  - the height of the bullet
	 */
	public Bullet(int x1, int y1, int w, int h) {
		

		this.curX1 = x1;
		this.curY1 = y1;
		this.width = w;
		this.height = h;
	}
	/**
	 * Create the Bullet
	 * @param g - the graphics to draw the bullet
	 */
	public void create(Graphics g){
		
		g.setColor(Color.white);
		g.fillOval(curX1, curY1, width, height);
		
	}
	/**
	 * Checks if Bullet is running or not
	 * @return if bullet is running 
	 */
	public boolean isAlive(){
		return alive;
	}
	/**
	 * Get the X coordinate of bullet
	 * @return integer x coordinate
	 */
	public int getX(){
		return curX1;
	}
	/**
	 * Get the y coordinate of bullet
	 * @return integer y coordinate
	 */
	public int getY(){
		return curY1;
	}
	/**
	 * Run the bullet thread until it reaches the end of its path
	 */
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while (getY() > 0){
			
			try {
				curY1 -= 5;
				t.sleep(5);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * Start the Thread
	 */
	public void start(){
		t = new Thread(this);
		alive = true;
		t.start();
		
	}


}
