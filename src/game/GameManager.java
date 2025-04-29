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
import sprites.Store;


//Controls game logic
public class GameManager {
	private Player player;
	private Player player2;
	private Store store;
	private ArrayList<Coin> coins;
	private Set<Integer> activeKeys = new HashSet<Integer>();
	private ArrayList<Block> blocks; //List that holds all the blocks
	int columns = (int) Math.ceil((double)(Constants.SCREEN_HEIGHT - (Constants.GROUND_HEIGHT + 85))/Constants.BLOCK_HEIGHT);
	int rows = (int) Math.ceil((double)Constants.SCREEN_WIDTH/Constants.BLOCK_WIDTH); 
	private boolean isGameResetting = false;
	
	//variables for countdown timer 
	private int timeLeft; //this is in secs 
	private long lastTimeUpdate; //for tracking 
	
	public GameManager() {
		this.blocks = new ArrayList<>();
		restart();
	}

	public void restart() {
		if(isGameResetting) {
			return;
		}
		
		isGameResetting = true;
		
		store = new Store("bank.png", Constants.SCREEN_WIDTH/2, Constants.GROUND_HEIGHT, Constants.STORE_WIDTH, Constants.STORE_HEIGHT);
		
		player = new Player("player1.png", 0, Constants.GROUND_HEIGHT  ,Constants.PLAYER_WIDTH , Constants.PLAYER_HEIGHT);
		player2 = new Player("player2.png", 200, Constants.GROUND_HEIGHT  ,Constants.PLAYER_WIDTH , Constants.PLAYER_HEIGHT);
		
		int x = 0; //setting x to 0 to make sure 
		int y = Constants.GROUND_HEIGHT + 85; //setting y to a bit bellow Ground height
		blocks = new ArrayList<>(); //initialize ArrayList
		
		
		//saves the position of the blocks in a grid 
		for(int row = 0; row < rows + 20; row++) {
			for (int column = 0; column < columns + 35; column++) {
				String fileName = "block1.png"; //name of the file 
				x = column * 53; //increases the z factor 
				y = (Constants.GROUND_HEIGHT + 85) + (row * 35); //increases the y factor 
				blocks.add(new Block(fileName, x, y, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT)); //adds the position to the ArrayList
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

		//rest timer 
		timeLeft = 120; //120 secs = 2 min
		lastTimeUpdate = System.currentTimeMillis();
	}


	public void drawSprites1(Graphics2D graphics, JPanel panel) {

		//Draw player
		graphics.drawImage(player.getImage(), player.getX(), player.getY(),player.getWidth(),player.getHeight(),panel);
		
		//Draw blocks
		for (Block block : blocks) {
			graphics.drawImage(block.getImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight(), panel);
		}

		//Draw GUI - score
		graphics.setColor(Color.white);
		graphics.setFont(Constants.SCORE_FONT);
		graphics.drawString(Integer.toString(player.getScore()), 20, 20);
		
		//Draw countdown timer 
		graphics.setColor(Color.RED);
		graphics.setFont(Constants.SCORE_FONT);
		graphics.drawString("Time Left: " + timeLeft, 20, 50);
	}
	
	public void drawSprites2(Graphics2D graphics, JPanel panel) {

		//Draw player
		graphics.drawImage(player2.getImage(), player2.getX() - (Constants.SCREEN_WIDTH/2), player2.getY(),player2.getWidth(),player2.getHeight(),panel);

		//Draw blocks
		for (Block block : blocks) {
			graphics.drawImage(block.getImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight(), panel);
		}

		//Draw GUI - score
		graphics.setColor(Color.white);
		graphics.setFont(Constants.SCORE_FONT);
		graphics.drawString(Integer.toString(player.getScore()), 20, 20);
		
		//Draw countdown timer 
		graphics.setColor(Color.RED);
		graphics.setFont(Constants.SCORE_FONT);
		graphics.drawString("Time Left: " + timeLeft, 20, 50);
	}

	public void update()
	{
		player.setBounds(0, Constants.SCREEN_SIZE.width/2);
		player.update();
		
		player2.setBounds(Constants.SCREEN_SIZE.width/2, Constants.SCREEN_SIZE.width);
		player2.update();
		
		updatePlayerMovement();
		
		checkStoreProximity(player);
		checkStoreProximity(player2);
		
		//Player 1 stays on the left of the screen 
		int xMaxP1 = Constants.SCREEN_WIDTH/2 - Constants.PLAYER_WIDTH;
		player.setX(Math.max(0, Math.min(player.getX(), xMaxP1)));
				
		//Player 2 stays on the right of the screen 
		int xMaxP2 = Constants.WORLD_WIDTH - Constants.PLAYER_WIDTH;
		int xMinP2 = Constants.SCREEN_WIDTH/2;
		player2.setX(Math.max(xMinP2, Math.min(player2.getX(), xMaxP2)));
		
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
	
	//Method checks if either player is close to the store and pressing their designated button 
	public void checkStoreProximity(Player player)
	{
		int storeX = store.getX();
		int storeY = store.getY();
		int storeWidth = store.getWidth();
		int storeHeight = store.getHeight();
		
		//If in the same position, check if pressing button
		if (player.getX() >= storeX - storeWidth / 2 && player.getX() <= storeX + storeWidth / 2 && player.getY() >= storeY - storeHeight / 2 && player.getY() <= storeY + storeHeight / 2 )
		{
			//If pressing button, call method to sell inventory
			if (activeKeys.contains(Constants.STORE_SELL_P1) || activeKeys.contains(Constants.STORE_SELL_P2))
			{
				player.sellInventory();
			}
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
	
	public Store getStore() {
		return store;
	}

	public ArrayList<Coin> getCoins() {
		return coins;
	}
	
	public int getCountDownTimer() {
		return timeLeft;
	}

}
