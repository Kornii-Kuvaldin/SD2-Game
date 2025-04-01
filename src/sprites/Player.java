package sprites;

import gameConstants.Constants;

public class Player extends Sprite {
	
	private int score;
	private boolean jumping = false;
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
		if(getX() + getWidth() + Constants.PLAYER_SPEED < Constants.SCREEN_SIZE.width/2) 
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
	
	public void increaseScore() {
		score+=Constants.COIN_SCORE;
	}
	public int getScore(){
		return score;
	}
}
