package sprites;

import java.util.ArrayList;
import java.util.List;

import gameConstants.Constants;

public class Player extends Sprite {
	
	private int score;
	private boolean jumping = false;
	private List<Block> inventory = new ArrayList<>();
	
	public Player( String fileName,int x , int y, int width, int height ) {
		super(fileName, x,y,width,height);

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
		if(getX() + getWidth() + Constants.PLAYER_SPEED < Constants.SCREEN_SIZE.width) 
			this.setX(getX() +Constants.PLAYER_SPEED);
	}
	
	public void moveLeft()
	{
		if(getX() -Constants.PLAYER_SPEED > 0) // don't go off screen
			this.setX(getX() -Constants.PLAYER_SPEED);
	}
	
	public void jump()
	{
		if(jumping == false)
		{
			setY(getY() -Constants.PLAYER_JUMP_HEIGHT);
			jumping = true;
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
}
