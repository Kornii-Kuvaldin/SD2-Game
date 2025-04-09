package sprites;

import sprites.Sprite;

public class Block extends Sprite {

	private double hardness;
	private double value;
	private double progress=0;
	private boolean isBroken=false;

	public Block(String fileName, int x, int y, int width, int height) {
		super(fileName, x, y, width, height);
	}
	public void blockMine() {
		progress += 10;
		if (progress>=hardness)
			isBroken=true;
	}
	public boolean getBroken() {
		return isBroken;
	}
	public void setHardness(double x) {
		hardness=x;
	}


}
