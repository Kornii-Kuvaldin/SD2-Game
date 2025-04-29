package sprites;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import gameConstants.Constants;

public class Player extends Sprite {
	
	private int score;
	private boolean jumping = false;
	private List<Block> inventory = new ArrayList<>();
	private BufferedImage imageIdle;
	private BufferedImage imageLeft;
	private BufferedImage imageRight;
	private BufferedImage imageUp;
	
	public Player( String fileName,int x , int y, int width, int height ) {
		super(fileName, x,y,width,height);
		
		try {
			imageIdle = ImageIO.read(new File ("images/" + (fileName.contains("player1") ? "player1_idle.png" : "player2_idle.png")));
			imageLeft = ImageIO.read(new File ("images/" + (fileName.contains("player1") ? "player1_left.png" : "player2_left.png")));
			imageRight = ImageIO.read(new File ("images/" + (fileName.contains("player1") ? "player1_right.png" : "player2_right.png")));
			imageUp = ImageIO.read(new File ("images/" + (fileName.contains("player1") ? "player1_up.png" : "player2_up.png")));
			
			setImage(imageIdle);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("file not found");
		}
	}
	public void update()
	{
		if(getY() < Constants.GROUND_HEIGHT - getHeight())
			setY( getY() +Constants.PLAYER_FALL_SPEED);
		else
		{
			if(jumping == true)
				setY(Constants.GROUND_HEIGHT  - getHeight());
			jumping = false;
		}
		
	}
	public void moveRight()
	{
		int xMax = Constants.SCREEN_SIZE.width - Constants.PLAYER_WIDTH;
		if(getX() + getWidth() < xMax) {
			this.setX(getX() +Constants.PLAYER_SPEED);
			setImage(imageRight);
		}
	}
	
	public void moveLeft()
	{
		int xMin = 0;
		if(getX() -Constants.PLAYER_SPEED > xMin) { // don't go off screen 
			this.setX(getX() -Constants.PLAYER_SPEED);
			setImage(imageLeft);
		}
	}
	
	public void jump()
	{
		if(jumping == false)
		{
			setY(getY() -Constants.PLAYER_JUMP_HEIGHT);
			jumping = true;
			setImage(imageUp);
		}
	}
	
	//Adds blocks to inventory to be sold
	public void collectBlock(Block block) 
	{
		inventory.add(block);	
	}
	
	//"Selling" inventory by adding values to score and then clearing inventory
	public void sellInventory()
	{
		for (Block block : inventory)
		{
			score += block.getValue();
		}
		
		inventory.clear();
	}
	
	public void increaseScore() 
	{
		score+=Constants.COIN_SCORE;
	}
	
	public int getScore()
	{
		return score;
	}
	
	//sets movement boundaries 
	public void setBounds(int left, int right) {
		if(getX() < left) {
			setX(left);
		}
			
		if (getX() + getWidth() > right) {
			setX(right - getWidth());
		}
	}
}
