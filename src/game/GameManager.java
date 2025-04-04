package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import gameConstants.Constants;
import sprites.Block;
import sprites.Coin;
import sprites.Enemy;
import sprites.Player;
import sprites.Sprite;


//Controls game logic
public class GameManager {


	private Player player;
	private Enemy enemy;
	private ArrayList<Coin> coins;
	private ArrayList<Block> blocks;

	public GameManager() {
		restart();
	}

	public void restart() {

		player = new Player("mario.png", 0, Constants.GROUND_HEIGHT  ,Constants.PLAYER_WIDTH , Constants.PLAYER_HEIGHT);


		enemy = new Enemy("goomba.png", Constants.ENEMY_START_X , Constants.GROUND_HEIGHT  , Constants.ENEMY_SIZE, Constants.ENEMY_SIZE);
		enemy.setPatrol(Constants.ENEMY_START_X ,Constants.SCREEN_SIZE.width/2, 3);

		coins = new ArrayList<Coin>();
		coins.add(new Coin("coin.png", 100, Constants.GROUND_HEIGHT , Constants.COIN_SIZE,Constants.COIN_SIZE));
		coins.add(new Coin("coin.png", 250, Constants.GROUND_HEIGHT  - 60, Constants.COIN_SIZE,Constants.COIN_SIZE));

		blocks = new ArrayList<Block>();
		blocks.add(new Block("rock_amethyst.png", Constants.GROUND_HEIGHT-100, Constants.GROUND_HEIGHT, Constants.COIN_SIZE, Constants.COIN_SIZE));
		blocks.get(0).setHardness(50);

	}


	public void drawSprites(Graphics2D graphics, JPanel panel) {

		//Draw player
		graphics.drawImage(player.getImage(), player.getX(), player.getY(),player.getWidth(),player.getHeight(),panel);
		//Draw enemy
		graphics.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(),enemy.getWidth(),enemy.getHeight(),panel);


		//Draw coins
		for(Coin coin : coins)
		{
			if(coin.isCollected() == false)
				graphics.drawImage(coin.getImage(), coin.getX(), coin.getY(),coin.getWidth(),coin.getHeight(),panel);
		}

		for(Block block : blocks)
		{
			graphics.drawImage(block.getImage(), block.getX(), block.getY(),block.getWidth(),block.getHeight(),panel);
		}

		//Draw GUI - score

		graphics.setColor(Color.white);
		graphics.setFont(Constants.SCORE_FONT);
		graphics.drawString(Integer.toString(player.getScore()), 20, 20);
	}

	public void update()
	{
		player.update();
		enemy.update();

		//collision checking
		checkCollision(player,enemy);
		for(Coin coin: coins) {
			if(coin.isCollected() == false) //only check for coins that haven't been picked up yet
				checkCollision(player,coin);
			for(Block block: blocks){
				checkCollision(player,block);
			}
		}
	}

	public void keyPressed(int code) {
		//switch keyCode
		// 39 - right key
		// 37 - left key
		// 32 - space key

		switch(code)
		{
		case  Constants.RIGHTKEY: //right
			player.moveRight();
			break;
		case Constants.LEFTKEY: //left
			player.moveLeft();
			break;
		case Constants.SPACEKEY: //space
			player.jump();
			break;
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
				if(other instanceof Enemy) {
					restart();
				}
				if(other instanceof Coin ) {
					player.increaseScore();
					((Coin)other).setCollected(true);
				}
				if(other instanceof Block) {
					player.moveLeft();
					System.out.println(((Block) other).getBroken());
					if(((Block) other).getBroken()) {
						blocks.remove(other);
					}
				}
			}
		}
	}

	//getters
	public Player getPlayer() {
		return player;
	}

	public Enemy getEnemy() {
		return enemy;
	}


	public ArrayList<Coin> getCoins() {
		return coins;
	}

}
