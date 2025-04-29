package sprites;

public class Block extends Sprite {

	private double hardness;
	private double value;
	private boolean isBroken=false;
	private double progress=0;

	//Constructor
	public Block(String fileName, int x, int y, int width, int height) {
		super(fileName, x, y, width, height);
	}

	public void blockMine() {
		progress += 10;
		if (progress>=hardness)
			isBroken=true;
	}

	//Getters and Setters
	public double getHardness() 
	{
		return hardness;
	}

	public boolean getBroken() {
		return isBroken;
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