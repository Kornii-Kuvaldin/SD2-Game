package gameConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;


public class Constants {
	
	

	public final static int REFRESH_RATE = 100;
	
	//size
	public final static Dimension SCREEN_SIZE =  Toolkit.getDefaultToolkit().getScreenSize();
	public final static int GROUND_HEIGHT  = SCREEN_SIZE.height/10;
	public final static int SCREEN_WIDTH = SCREEN_SIZE.width;
	public final static int SCREEN_HEIGHT = SCREEN_SIZE.height;

	//Key Codes
	public final static int LEFTP1 = 37;
	public final static int RIGHTP1 = 39;
	public final static int UPP1 = 38;
	
	public final static int LEFTP2 = 65;
	public final static int RIGHTP2 = 68;
	public final static int UPP2 = 87;
	//Player


	public final static int PLAYER_WIDTH = 100;
	public final static int PLAYER_HEIGHT = 100;
	public final static int PLAYER_START_X = 0;
	public final static int PLAYER2_START_X = 200;
	public final static int PLAYER_SPEED = 2;
	public final static int PLAYER_JUMP_HEIGHT = 70;
	public final static int PLAYER_FALL_SPEED = 4;
	
	//Enemy
	public final static int ENEMY_SIZE = 100;
	public final static int ENEMY_START_X = 200;
	public final static int ENEMY_SPEED = 3;
	
	//Coin
	public final static int COIN_SIZE = 50;
	public final static int COIN_SCORE = 1;
	
	//Block
	public final static int BLOCK_WIDTH = 100;
	public final static int BLOCK_HEIGHT = 50;
	
	//speed

	//colors
	public final static Color SKY_BLUE = new Color(174, 227, 245);//rgb
	public final static Color BLACK = new Color(0, 0, 0);//rgb

	//Fonts
	public final static	Font SCORE_FONT = new Font( "SansSerif", Font.BOLD, 18 );
}
