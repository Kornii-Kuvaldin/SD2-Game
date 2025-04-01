package sprites;

import gameConstants.Direction;

public class Enemy extends Sprite{


	private int patrolStart =0;
	private int patrolEnd = 0;
	private int speed = 1;
	
	private Direction currentDirection =Direction.RIGHT;
	
	public Enemy( String fileName,int x , int y, int width, int height) {
		super(fileName,x,y, width,height);
	}
	
	public void setPatrol(int patrolStart, int patrolEnd, int speed ) {
		this.patrolStart = patrolStart;
		this.patrolEnd = patrolEnd;
		this.speed = speed;
	}
	
	public void update()
	{
		patrol() ;
	}
	
	//move back and forwards between start and end point
	public void patrol() {
		if(currentDirection == Direction.RIGHT && getX() <= patrolEnd)
		{
			setX( getX() + speed);
		}
		else if (currentDirection == Direction.RIGHT && getX() >= patrolEnd)
		{
			currentDirection = Direction.LEFT;
		}
		else if(currentDirection == Direction.LEFT  && getX() >= patrolStart )
		{
			setX( getX() - speed);
		}	
		else if(currentDirection == Direction.LEFT  && getX() <= patrolStart )
		{
			currentDirection = Direction.RIGHT;
		}
	}
}
