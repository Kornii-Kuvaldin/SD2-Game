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
	int initY = Constants.GROUND_HEIGHT + 85;
	int height = Constants.SCREEN_HEIGHT - initY;
	int width = Constants.SCREEN_WIDTH;
	int columns = height/Constants.BLOCK_HEIGHT;
	int rows = width/Constants.BLOCK_WIDTH;
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

		store = new Store("bank.png", 1000, Constants.GROUND_HEIGHT, Constants.STORE_WIDTH, Constants.STORE_HEIGHT);

		player = new Player("player1_idle.png", 0, Constants.GROUND_HEIGHT-50  ,Constants.PLAYER_WIDTH , Constants.PLAYER_HEIGHT);
		player2 = new Player("player2_idle.png", 200, Constants.GROUND_HEIGHT-50  ,Constants.PLAYER_WIDTH , Constants.PLAYER_HEIGHT);

		int x = 0; //setting x to 0 to make sure 
		int y = Constants.GROUND_HEIGHT + 85; //setting y to a bit bellow Ground height
		blocks = new ArrayList<>(); //initialize ArrayList


		//saves the position of the blocks in a grid 
		for(int row = 0; row < rows + 17; row++) {
			for (int column = 0; column < columns + 19; column++) {
				String fileName = "block1.png"; //name of the file 
				x = column * Constants.BLOCK_WIDTH; //increases the z factor 
				y = (Constants.GROUND_HEIGHT + 100) + (row * Constants.BLOCK_HEIGHT); //increases the y factor 
				blocks.add(new Block(fileName, x, y, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT)); //adds the position to the ArrayList
			}
		}

		//rest timer 
		timeLeft = 120; //120 secs = 2 min
		lastTimeUpdate = System.currentTimeMillis();

		isGameResetting = false;
	}


	public void drawSprites1(Graphics2D graphics, JPanel panel) {

		//Draw player
		graphics.drawImage(player.getImage(), player.getX(), player.getY(),player.getWidth(),player.getHeight(),panel);

		int cameraX1 = player.getX() - Constants.SCREEN_WIDTH / 4;
		int storeScreenX = store.getX() - cameraX1;
		graphics.drawImage(store.getImage(), storeScreenX, store.getY(), store.getWidth(), store.getHeight(), panel);

		//Draw blocks
		ArrayList<Block> tempBlocks = new ArrayList<>(blocks); // Make a copy of the blocks list
		synchronized(blocks) {
			for (Block block : tempBlocks) {
				graphics.drawImage(block.getImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight(), panel);
			}
		}

		//Draw GUI - score
		graphics.setColor(Color.white);
		graphics.setFont(Constants.SCORE_FONT);
		graphics.drawString(Integer.toString(player.getScore()), 20, 20);

		//Draw countdown timer 
		int minutes = timeLeft/60;
		int seconds = timeLeft%60;
		graphics.setColor(Color.RED);
		graphics.setFont(Constants.SCORE_FONT);
		graphics.drawString(String.format("Time Left: %02d:%02d", minutes, seconds), 20, 50);
	}

	public void drawSprites2(Graphics2D graphics, JPanel panel) {

		//Draw player
		graphics.drawImage(player2.getImage(), player2.getX() - (Constants.SCREEN_WIDTH/2), player2.getY(),player2.getWidth(),player2.getHeight(),panel);

		int cameraX2 = player2.getX() - Constants.SCREEN_WIDTH / 4;
		int storeScreenX = store.getX() - cameraX2;
		graphics.drawImage(store.getImage(), storeScreenX, store.getY(), store.getWidth(), store.getHeight(), panel);

		//Draw blocks
		ArrayList<Block> tempBlocks = new ArrayList<>(blocks); // Make a copy of the blocks list
		synchronized(blocks) {
			for (Block block : tempBlocks) {
				graphics.drawImage(block.getImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight(), panel);
			}
		}

		//Draw GUI - score
		graphics.setColor(Color.white);
		graphics.setFont(Constants.SCORE_FONT);
		graphics.drawString(Integer.toString(player.getScore()), 20, 20);

		//Draw countdown timer 
		int minutes = timeLeft/60;
		int seconds = timeLeft%60;
		graphics.setColor(Color.RED);
		graphics.setFont(Constants.SCORE_FONT);
		graphics.drawString(String.format("Time Left: %02d:%02d", minutes, seconds), 20, 50);
	}

	public void update()
	{
		player.setBounds(0, Constants.SCREEN_SIZE.width/2);
		player.update();

		player2.setBounds(Constants.SCREEN_SIZE.width/2, Constants.SCREEN_SIZE.width);
		player2.update();
		//Set jumping as true to check for any changes under the players, otherwise players fly when leaving a block
		player.setJumping(true);
		player2.setJumping(true);

		//Use an Iterator to allow removing blocks while iterating
		synchronized(blocks) {
			Iterator<Block> iterator = blocks.iterator();
			while (iterator.hasNext()) {
				Block block = iterator.next();
				checkCollision(player, block);
				checkCollision(player2, block);

				// Safely remove broken blocks
				if (block.getBroken()) {
					iterator.remove();  // Safe removal using iterator
					activeKeys.clear();
				}
			}
		}
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

		if (timeLeft <= 0) {
			restart(); //restarts the game onece teh countdown reaches 0
			return; //stops theh updating
		}

		//the logic for the countdown 
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastTimeUpdate >= 1000) {
			timeLeft--;
			lastTimeUpdate = currentTime;
		}
	}

	public void keyPressed(int code) {
		activeKeys.add(code); //Adding key pressed to HashSet once pressed
		updatePlayerMovement();

	}


	public void keyReleased(int code) {
		activeKeys.remove(code); //Removing key pressed from HashSet once released 
		updatePlayerMovement();

		//If no keys are being pressed, set player's image to idle 
		if (!activeKeys.contains(Constants.LEFTP1) && !activeKeys.contains(Constants.RIGHTP1) && !activeKeys.contains(Constants.UPP1)) {
			player.setImage(player.getImageIdle());
		}
		if (!activeKeys.contains(Constants.LEFTP2) && !activeKeys.contains(Constants.RIGHTP2) && !activeKeys.contains(Constants.UPP2)) {
			player2.setImage(player2.getImageIdle());
		}
	}

	//Method takes care of players movement
	public void updatePlayerMovement () {

		//Movement for player 1
		if (activeKeys.contains(Constants.LEFTP1))
		{
			player.setMovingLeft(true);
			player.moveLeft();

		}
		else
			player.setMovingLeft(false);
		if (activeKeys.contains(Constants.RIGHTP1))
		{
			player.setMovingRight(true);
			player.moveRight();

		}
		else
			player.setMovingRight(false);
		if (activeKeys.contains(Constants.UPP1))
		{
			player.jump();
		}
		player.setDig(activeKeys.contains(Constants.DOWNP1));

		//Movement for player 2
		if (activeKeys.contains(Constants.LEFTP2))
		{
			player2.setMovingLeft(true);
			player2.moveLeft();

		}
		else
			player2.setMovingLeft(false);
		if (activeKeys.contains(Constants.RIGHTP2))
		{
			player2.setMovingRight(true);
			player2.moveRight();

		}
		else
			player2.setMovingRight(false);
		if (activeKeys.contains(Constants.UPP2))
		{
			player2.jump();
		}
		player2.setDig(activeKeys.contains(Constants.DOWNP2));
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
		int verticalLeniency = 10;
		int leftOffset=40;
		int rightOffset=64;
		if(player.getX() + player.getWidth() >= other.getX()+leftOffset && player.getX() + player.getWidth()  <= other.getX()+rightOffset + other.getWidth())
		{ //check intersection on y axis
			if(	player.getY()+ player.getHeight()  >= other.getY()-verticalLeniency && player.getY() + player.getHeight()  <= other.getY()+verticalLeniency + other.getHeight())
			{	//check what we collided with
				//if(other instanceof Coin ) {
				//player.increaseScore();
				//((Coin)other).setCollected(true);
				//}
//				System.out.println("Player x:" +player.getX());
//				System.out.println("Other x: " + other.getX());
//				System.out.println("Player y:" +player.getY());
//				System.out.println("Other y: " + other.getY());
				if(other instanceof Block) {
					//Check if player is above the block we're colliding

					int playerBottom = player.getY() + player.getHeight();
					int blockTop = other.getY();

					if (Math.abs(playerBottom - blockTop) <= verticalLeniency && player.getX() + player.getWidth() >= other.getX() && player.getX() <= other.getX() + other.getWidth()) {
						player.setJumping(false);

						if (player.isDigging()) {
							((Block) other).blockMine();
						}
					}
					//					Check if player is to the left of block
					if (player.getY() + player.getHeight() > other.getY() && player.getY() < other.getY() + other.getHeight()) {
						//Vertical Overlap
						if (player.isMovingRight() && player.getX() + player.getWidth() > other.getX() && player.getX() < other.getX()) {
							//Player moves from left
							player.setX(other.getX()-other.getWidth()+ Constants.PLAYER_KNOCKBACK );
							((Block) other).blockMine();
						} else if (player.isMovingLeft() && player.getX() < other.getX() + other.getWidth() && player.getX() + player.getWidth() > other.getX()) {
							//Player moves from right
							player.setX(other.getX() + other.getWidth()-Constants.PLAYER_KNOCKBACK);
							((Block) other).blockMine();

						}
					}
				}
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