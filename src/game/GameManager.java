package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;

import gameConstants.Constants;
//import sprites.Block;
import sprites.Block;
import sprites.Coin;
import sprites.Player;
import sprites.Sprite;


//Controls game logic
public class GameManager {
	private Player player;
	private Player player2;
	private ArrayList<Coin> coins;
	private Set<Integer> activeKeys = new HashSet<Integer>();
	private ArrayList<Block> blocks; //List that holds all the blocks
	int columns = (int) Math.ceil((double)(Constants.SCREEN_HEIGHT - (Constants.GROUND_HEIGHT + 85))/Constants.BLOCK_HEIGHT);
	int rows = (int) Math.ceil((double)Constants.SCREEN_WIDTH/Constants.BLOCK_WIDTH); 
	private boolean isGameResetting = false;
	
	//variables for countdown timer 
	private int timeLeft; //this is in secs 
	private long lastTimeUpdate; //for tracking 
	//private ArrayList<Block> blocks1;
	
	public GameManager() {
		this.blocks = new ArrayList<>();
		restart();
	}

	public void restart() {
		if(isGameResetting) {
			return;
		}
		
		isGameResetting = true;
		
		player = new Player("player1.png", 0, Constants.GROUND_HEIGHT  ,Constants.PLAYER_WIDTH , Constants.PLAYER_HEIGHT);
		player2 = new Player("player2.png", 200, Constants.GROUND_HEIGHT  ,Constants.PLAYER_WIDTH , Constants.PLAYER_HEIGHT);
		
		//enemy = new Enemy("player2.png", Constants.ENEMY_START_X , Constants.GROUND_HEIGHT  , Constants.ENEMY_SIZE, Constants.ENEMY_SIZE);

		//player = new Player("mario.png", 0, Constants.GROUND_HEIGHT  ,Constants.PLAYER_WIDTH , Constants.PLAYER_HEIGHT);


		//enemy = new Enemy("goomba.png", Constants.ENEMY_START_X , Constants.GROUND_HEIGHT  , Constants.ENEMY_SIZE, Constants.ENEMY_SIZE);
		//trials
		//int screenWidth = frame.getWidth();
		//int screenHeight = frame.getHeight();
		//int groundHeight = screenHeight/10;
		//int columns = (int) Math.ceil((double)(screenHeight - (groundHeight + 43))/blockHeight);
		//int rows = (int) Math.ceil((double)screenWidth/blockWidth);
		
		int x = 0; //setting x to 0 to make sure 
		int y = Constants.GROUND_HEIGHT + 85; //setting y to a bit bellow Ground height
		blocks = new ArrayList<>(); //initialize ArrayList
		
		//debugging for columns and rows 
		//System.out.println("Clolumns: " + columns);
		//System.out.println("Rows: " + rows);
		
		//trial
		//blocks.clear();
		/*
		 * for(int row = 0; row < rows + 10; row++) {
				if (x + blockWidth <= SCREEN_WIDTH && y + blockHeight <= SCREEN_HEIGHT) {
					String fileName = "block1.png"; //name of the file 
					x = column * 53; //increases the z factor 
					y = (Constants.GROUND_HEIGHT + 43) + (row * 35); //increases the y factor 
					if (x >= SCREEN_WIDTH) {
						x = 0;
						y = (GROUND_HEIGHT +43) + (row * blockHeight);
				}
				blocks.add(new Blocks(fileName, x, y, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT)); //adds the position to the ArrayList
			}
		 */
		
		//saves the position of the blocks in a grid 
				for(int row = 0; row < rows + 20; row++) {
					for (int column = 0; column < columns + 35; column++) {
						String fileName = "block1.png"; //name of the file 
						x = column * 53; //increases the z factor 
						y = (Constants.GROUND_HEIGHT + 85) + (row * 35); //increases the y factor 
						blocks.add(new Block(fileName, x, y, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT)); //adds the position to the ArrayList
						
						//Too many blocks were causing a "java.lang.OutOfMemoryError: Java heap space" error, downscalling the image for blocks fixed it
						
						//debuggig for possitioning 
						//System.out.println("Block created at position: (" + x + ", " + y + ")");
					}
				}
		new Thread(()->{
		try {
			Thread.sleep(500);
			restart();
			}
		catch (InterruptedException e){
			e.printStackTrace();
			}
		}).start();

		//blocks1 = new ArrayList<Block>();
		//blocks1.add(new Block("rock_amethyst.png", Constants.GROUND_HEIGHT-100, Constants.GROUND_HEIGHT, Constants.COIN_SIZE, Constants.COIN_SIZE));
		//blocks1.get(0).setHardness(50);
		
		//rest timer 
		timeLeft = 120; //120 secs = 2 min
		lastTimeUpdate = System.currentTimeMillis();
	}


	public void drawSprites(Graphics2D graphics, JPanel panel) {

		//Draw player
		graphics.drawImage(player.getImage(), player.getX(), player.getY(),player.getWidth(),player.getHeight(),panel);
		graphics.drawImage(player2.getImage(), player2.getX(), player2.getY(),player2.getWidth(),player2.getHeight(),panel);
		//Draw enemy
		//graphics.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(),enemy.getWidth(),enemy.getHeight(),panel);
		//Draw blocks
		for (Block block : blocks) {
			graphics.drawImage(block.getImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight(), panel);
		}
		
		//trial
		//Iterator<Blocks> iterator = blocks.iterator();
		//while (iterator.hasNext()) {
			//Blocks block = iterator.next();
			//graphics.drawImage(block.getImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight(), panel);
		//}
		
		//debbuging for height and width of image 
		//System.out.println("Height: " + block.getHeight());
		//System.out.println("Width: " + block.getWidth());
		
		//for(Block block : blocks1)
		//{
			//graphics.drawImage(block.getImage(), block.getX(), block.getY(),block.getWidth(),block.getHeight(),panel);
		//}

		//Draw GUI - score

		graphics.setColor(Color.white);
		graphics.setFont(Constants.SCORE_FONT);
		graphics.drawString(Integer.toString(player.getScore()), 20, 20);
		
		//Draw countdown timer 
		graphics.setColor(Color.RED);
		graphics.setFont(Constants.SCORE_FONT);
		graphics.drawString("Time Left" + timeLeft, 20, 50);
	}

	public void update()
	{
		player.update();
		player2.update();
		//enemy.update();
		//collision checking
		//checkCollision(player,enemy);
		//for(Coin coin: coins) {
			//if(coin.isCollected() == false) //only check for coins that haven't been picked up yet
				//checkCollision(player,coin);
			//for(Block block: blocks1){
				//checkCollision(player,block);
			//}
		//}
		
		//the logic for the countdown 
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastTimeUpdate >= 1000) {
			timeLeft--;
			lastTimeUpdate = currentTime;
			
			if (timeLeft <= 0) {
				restart(); //restarts the game onece teh countdown reaches 0
			}
		}
	}

	public void keyPressed(int code) {
		activeKeys.add(code); //Adding key pressed to HashSet once pressed
		updatePlayerMovement();

	}
	
	
	public void keyReleased(int code) {
		int playerY = Constants.GROUND_HEIGHT;
		activeKeys.remove(code); //Removing key pressed from HashSet once released 
		updatePlayerMovement();
	}
	
	//Method takes care of players movement
	public void updatePlayerMovement () {
		
		//Movement for player 1
		if (activeKeys.contains(Constants.LEFTP1))
		{
			player.moveLeft();
		}
		if (activeKeys.contains(Constants.RIGHTP1))
		{
			player.moveRight();
		}
		if (activeKeys.contains(Constants.UPP1))
		{
			player.jump();
		}
		//Movement for player 2
		if (activeKeys.contains(Constants.LEFTP2))
		{
			player2.moveLeft();;
		}
		if (activeKeys.contains(Constants.RIGHTP2))
		{
			player2.moveRight();
		}
		if (activeKeys.contains(Constants.UPP2))
		{
			player2.jump();
		}
	}
	public void checkCollision(Player player, Sprite other) {

		//basic collision detection 
		//check if one image intersects the other

		//check intersection on x axis
		if(player.getX() + player.getWidth() >= other.getX() && player.getX() + player.getWidth()  <= other.getX() + other.getWidth())
		{ //check intersection on y axis
			if(	player.getY()+ player.getHeight()  >= other.getY() && player.getY() + player.getHeight()  <= other.getY() + other.getHeight())
			{
				//check what we collided with
				//if(other instanceof Coin ) {
					//player.increaseScore();
					//((Coin)other).setCollected(true);
				//}
				//if(other instanceof Block) {
					//player.moveLeft();
					//System.out.println(((Block) other).getBroken());
					//if(((Block) other).getBroken()) {
						//blocks1.remove(other);
					//}
				//}
			}
		}
	}
	private void resetGame() {
		if(isGameResetting){
			return;
		}
	}
	//getters
	public Player getPlayer() {
		return player;
	}



	public ArrayList<Coin> getCoins() {
		return coins;
	}
	
	public int getCountDownTimer() {
		return timeLeft;
	}

}
