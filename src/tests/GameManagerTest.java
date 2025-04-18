package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import game.GameManager;
import gameConstants.Constants;
import sprites.Coin;
import sprites.Player;

class GameManagerTest {
	
	//Example tests
	
	@Test
	void testPlayerMovement() {
		GameManager game = new GameManager();
		int startPosition = Constants.PLAYER_START_X;
		game.keyPressed(Constants.RIGHTP1);
		Player player = game.getPlayer();
		assertEquals(player.getX(), startPosition + Constants.PLAYER_SPEED);
	}
	
	
	@Test
	void testPlayerCollision() {
		GameManager game = new GameManager();
		Coin coin = game.getCoins().get(0);
		Player player = game.getPlayer();
		
		game.checkCollision(player, coin);
		assertFalse(coin.isCollected());
		

		coin.setX(player.getX());
		coin.setY(player.getY());
		game.checkCollision(player, coin);
		
		assertTrue(coin.isCollected());
	}
	
	//tests for the countdown timer 
	//Initial countdown timer
	@Test
	void testInitCountdownTimer() {
		GameManager game = new GameManager();
		
		assertEquals(120, game.getCountDownTimer(), "The initila countdwon time should be 120 secs");
	}
	
	//tests if the countdown timer updates every second
	@Test
	void testCountdownUpdate() {
		GameManager game = new GameManager();
		
		///get he initail time 
		int initTime = game.getCountDownTimer();
		
		//Wait for a second 
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		//the countdown should hae decreased by 1
		assertEquals(initTime - 1, game.getCountDownTimer(), "The countdwon time should be decresed by 1 sec");
	}
}
