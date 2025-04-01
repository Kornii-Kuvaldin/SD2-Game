package sprites;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	
	private String fileName;
	private int x;
	private int y;
	private int width;
	private int height;
	private BufferedImage image;
	
	public Sprite(String fileName , int x, int y , int width, int height) {
		this.x = x;
		this.y = y - height;
		this.width = width;
		this.height = height;
		File pic = new File("images/"+fileName);
		try {
			image = ImageIO.read(pic);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("file not found");
		}
	}

	
	public BufferedImage getImage() {
		return image;
	}

	
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
