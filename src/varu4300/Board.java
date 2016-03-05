package varu4300;
/*
 * Title: Space Invaders
 *  Name: Minesh Varu
 *  Date: November 27,2014
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;
@SuppressWarnings("serial")
public class Board extends JComponent implements Runnable, KeyListener, ActionListener{
	//Creating variables
	private Thread t;

	private int x = 0, y =0, tempy=0, counter = 0, index=0, score = 0, playerhealth = 120, round = 0;
	private boolean left = false, right = false,running =true, hit = false,  s= false;
	private ArrayList<Bullet> store = new ArrayList<Bullet>();
	private ArrayList<Enemy> enemyship = new ArrayList<Enemy>();
	private BufferedImage ship = null;
	private Timer timer;
	public Board() {
		//Initialization constructor 
		x = 200;
		y = 685;
		enemyship.add(new Enemy());
		try {
			ship = ImageIO.read(new File("alienspaceship.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(true);
		this.addKeyListener(this);
	}
	public void onStart(){
		running = false;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Drawing score to screen
		
		if(s==true && playerhealth ==0){
			if(s==true){
				restart(g);
			}
		}
		g.setColor(Color.blue);
		g.setFont(new Font("Courier New",Font.PLAIN, 18));
		g.drawString("Score: " + score, 0, 20);
		//draw health to screen
		g.drawString("Health:", 270, 20);
		g.drawLine(0, 30, 620, 30);
		//draw round number to screen
		g.drawString("Round: " + round, 120, 20);
		g.setColor(Color.red);
		g.fillRect(360,13,playerhealth,5);
		
		//Drawing player ship to screen
		g.drawImage(ship,x,y,50,50,this);
		if(!enemyship.isEmpty()){
			//drawing enemy ship to screen
			for(int i = 0; i < enemyship.size(); i++){		
				enemyship.get(i).draw(g);		
				try{
					//if enemy goes past your ship you loose health
					if (enemyship.get(i).getY() >= 680){
						playerhealth -= 120;
						enemyship.remove(i);
					}
					//if enemy ship hits your ship you loose health
					if(enemyship.get(i).getY() > y && enemyship.get(i).getY() < y-70){
						if(enemyship.get(i).getX() > x && enemyship.get(i).getX() < x-50){
							playerhealth -= 1;
						}
					}
				
				}
				catch(Exception e){
					
				}
			}
		}
		if(!store.isEmpty()){		
			for(int i =0; i < store.size(); i++){
				//creating bullets on space hit
				store.get(i).create(g);
				for(int j = 0; j < enemyship.size(); j++){
					//if bullet hits enemy ship enemy ship looses health
					if (store.get(i).getY() > enemyship.get(j).getY() && store.get(i).getY() < enemyship.get(j).getY()+50){
						if(store.get(i).getX() > enemyship.get(j).getX() && store.get(i).getX() < enemyship.get(j).getX() + 70 ){
							enemyship.get(j).looseHealth();
							//determine if enemy ship hit
							hit = true;
							//get the index of the bullet that hit ship
							index = i;
						}
						//if health of enemy ship is hit it is removed from the screen
						if(enemyship.get(j).getHealth() == 0){
							score = score + 10;
							enemyship.remove(j);
						}
					}
				}
				//if bullet reaches the end of the screen remove from the array list 
				if(store.get(i).getY() <= 0){
					store.remove(i);
				}
				//remove the bullet from array list if it collided with the enemy ship
				if(hit){
					store.remove(index);
					hit = false;
				}
			}
		}
		if(playerhealth == 0){
			
			running = false;
			g.setColor(Color.blue);
			g.setFont(new Font("Courier New",Font.PLAIN, 34));
			g.drawString("Game Over" ,getWidth()/2-100, getHeight()/2);
			g.setFont(new Font("Courier New",Font.PLAIN, 18));
			g.drawString("Press q to quit" ,getWidth()/2-100, getHeight()/2+50);
			for(int j = 0; j < enemyship.size();j++){
				enemyship.remove(j);
			}
			System.out.println(running);
			System.out.println(t.isInterrupted());
			
		}
		//repaint canvas constantly
		
	}
	public void restart(Graphics g){
		g.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//determine the key that's pressed
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT){
			left = true;
		}
		else if(key == KeyEvent.VK_RIGHT){
			right = true;
		}
		else if (key == KeyEvent.VK_Q){
			s = true;
		}
		
		if(left == true && x != 0){
			x -=5;
		}
		else if(right == true && x != 450){
			x +=5;	
		}
		else if(s == true && running == false){
			System.exit(0);
			
			
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		//determine if the key is released
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT){
			left = false;
		}
		else if(key == KeyEvent.VK_RIGHT){
			right = false;
		}
		else if(key == KeyEvent.VK_Q){
			s = false;
		}
		if(key == KeyEvent.VK_SPACE){
			tempy = y;
			
			store.add(new Bullet(x+22,tempy,5,5));
			for(int i =0; i < store.size(); i++){
				if(!store.get(i).isAlive()){
					store.get(i).start();
				}
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void run() {
		//Giving the canvas a thread in order to perform multiple tasks efficiently 
		while(running)
		{	
			
			//timer keeps track of when the ship should be added to the list and created
			if(counter == 15){
				enemyship.add(new Enemy());
				//updating the current round player is in
				round += 1;
				//after counter reaches goal then restart counter
				counter = 0;
				//increase level of difficulty as game progresses
				if (round == 5){
					timer.setDelay(850);
				}
				else if (round == 10){
					timer.setDelay(650);
				}
				else if(round == 20){
					timer.setDelay(500);
				}
			}
			for(int i = 0; i < enemyship.size(); i++){
				try{
					//check if the ship is already drawn on screen if not then start the enemy ship thread
					if(!enemyship.get(i).isAlive()){
						
							enemyship.get(i).start();
						}
				}
				catch(Exception e){
					
				}
			}
			this.repaint();
		}
	}
	public void start(){
		//creating and starting thread
		t = new Thread(this);
		t.start();
		//creating and starting thread
		timer = new Timer(1000,this);
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//update counter in order to keep track of ship
		if(timer.isRunning()){
			counter++;
		}
	}
}