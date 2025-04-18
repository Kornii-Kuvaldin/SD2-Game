package sprites;

public class Block extends Sprite {

	private double hardness;
	private double value;
	
	//Constructor
	public Block(String fileName, int x, int y, int width, int height) {
		super(fileName, x, y, width, height);
	}


	//Getters and Setters
	public double getHardness() 
	{
		return hardness;
	}


	public void setHardness(double hardness) 
	{
		this.hardness = hardness;
	}


	public double getValue() 
	{
		return value;
	}


	public void setValue(double value) 
	{
		this.value = value;
	}
	

}
