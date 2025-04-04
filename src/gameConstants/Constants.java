package gameConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;


public class Constants {
	
	

	public final static int REFRESH_RATE = 100;
	
	//size
	public final static Dimension SCREEN_SIZE =  Toolkit.getDefaultToolkit().getScreenSize();
	public final static int GROUND_HEIGHT  = SCREEN_SIZE.height/3;

	//Key Codes
	public final static int LEFTKEY = 37;
	public final static int RIGHTKEY = 39;
	public final static int SPACEKEY = 32;
	//Player


	public final static int PLAYER_WIDTH = 50;
	public final static int PLAYER_HEIGHT = 50;
	public final static int PLAYER_START_X = 0;
	public final static int PLAYER_SPEED = 2;
	public final static int PLAYER_JUMP_HEIGHT = 70;
	public final static int PLAYER_FALL_SPEED = 4;
	
	//Enemy
	public final static int ENEMY_SIZE = 50;
	public final static int ENEMY_START_X = 200;
	public final static int ENEMY_SPEED = 3;
	
	//Coin
	public final static int COIN_SIZE = 50;
	public final static int COIN_SCORE = 1;

	
	//speed

	//colors
	public final static Color SKY_BLUE = new Color(174, 227, 245);//rgb
	public final static Color GRASS_GREEN = new Color(79, 179, 85);//rgb

	//Fonts
	public final static	Font SCORE_FONT = new Font( "SansSerif", Font.BOLD, 18 );
}
