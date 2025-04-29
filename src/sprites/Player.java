package sprites;

import gameConstants.Constants;

public class Player extends Sprite {
	
	private int score;
	private boolean jumping = true;
	private boolean digging = false;
	private boolean digRight= false;
	private boolean digLeft= false;
	private boolean isMovingRight;
	private boolean isMovingLeft;
	public Player( String fileName,int x , int y, int width, int height ) {
		super(fileName, x,y,width,height);

	}
	public void update()
	{
		if(jumping==true)
			setY( getY() +Constants.PLAYER_FALL_SPEED);
		
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
	public void setDig(boolean b){
		digging = b;
	}
	public boolean isDigging() {
		return digging;
	}
	public void setRightDig(boolean b){
		digRight = b;
	}
	public boolean isRightDig() {
		return digRight;
	}
	public void setLeftDig(boolean b){
		digLeft = b;
	}
	public boolean isLeftDig() {
		return digLeft;
	}
	
	public void setJumping(boolean b) {
		jumping = b;
	}
	public boolean isJumping() {
		return jumping;
	}
	
	public void increaseScore() {
		score+=Constants.COIN_SCORE;
	}
	public int getScore(){
		return score;
	}
	public boolean isMovingRight() {
		return isMovingRight;
	}
	public void setMovingRight(boolean isMovingRight) {
		this.isMovingRight = isMovingRight;
	}
	public boolean isMovingLeft() {
		return isMovingLeft;
	}
	public void setMovingLeft(boolean isMovingLeft) {
		this.isMovingLeft = isMovingLeft;
	}
	
}
