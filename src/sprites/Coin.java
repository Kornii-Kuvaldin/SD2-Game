package sprites;

public class Coin extends Sprite{
	
	private boolean collected = false;
	
	public Coin(String fileName, int x, int y, int width, int height) {
		super(fileName, x, y, width, height);
	}
	public boolean isCollected() {
		return collected;
	}
	public void setCollected(boolean collected) {
		this.collected = collected;
	}

}
