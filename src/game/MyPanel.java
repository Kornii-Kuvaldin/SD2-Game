package game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import gameConstants.Constants;


//Draws to Panel and listens for key inputs
@SuppressWarnings("serial")
public class MyPanel extends JPanel implements  KeyListener {

	private GameManager game;

	public MyPanel() {
		addKeyListener(this); // set up keyboard input event listener
		 game = new GameManager();
	}

	//Method to draw to panel
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);


		Graphics2D graphics = (Graphics2D)g;
		//improves image rendering
		RenderingHints hints = new RenderingHints( RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHints(hints);
		
		drawBackGround(graphics);
		
		int width = getWidth();
		int height = getHeight();
		
		//Player 1 screen (left screen)
		Graphics2D gLeft = (Graphics2D) graphics.create(0,0, width/2, height);
		drawBackGround(gLeft);
		game.drawSprites1(gLeft, this);
		gLeft.dispose();
		
		//Player 2 screen (right screen)
		Graphics2D gRight = (Graphics2D) graphics.create(width/2, 0, width/2, height);
		drawBackGround(gRight);
		game.drawSprites2(gRight, this);
		gRight.dispose();
		
		//divider (black line)
		graphics.setColor(Color.BLACK);
		graphics.drawLine(width/2, 0, width/2, height);
		
		this.repaint();
	}
	
	public void drawBackGround(	Graphics2D graphics) {
		//blue background, size of screen	
		graphics.setColor(Constants.SKY_BLUE);
		graphics.fillRect(0,0,Constants.SCREEN_SIZE.width,Constants.SCREEN_SIZE.height);
		
		//green rectangle for ground  
		graphics.setColor(Constants.BLACK);
		graphics.fillRect(0,Constants.GROUND_HEIGHT ,Constants.SCREEN_SIZE.width,Constants.SCREEN_SIZE.height);
	}
	
	public void drawCountDownTimer(Graphics2D graphics) {
		int remainingTime = game.getCountDownTimer();
		graphics.setColor(Constants.BLACK);  // Assuming RED is defined in Constants
	    graphics.setFont(Constants.SCORE_FONT);  // Use the defined font for consistency
	    graphics.drawString("Time Left: " + remainingTime + "s", 20, 50);  // Display timer at the top
	}
	
	public void update()
	{
		game.update();
		this.repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e.getKeyCode());
		this.repaint();
	}

	//not implemented
	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e.getKeyCode());
		this.repaint();
	}

}
