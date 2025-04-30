package tests;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import game.GameManager;
import gameConstants.Constants;
import sprites.Coin;
import sprites.Player;
import sprites.Store;

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
	void testCountdownUpdate() throws InterruptedException {
		GameManager game = new GameManager();
	    int initTime = game.getCountDownTimer();

	    // Wait for 1 second (simulate time passing)
	    Thread.sleep(1000);

	    // Update the game state (which should trigger the countdown update)
	    game.update();  // Call the update method to decrement the countdown timer

	    // Get the updated countdown time
	    int currentTime = game.getCountDownTimer();

	    // Check if the countdown has decreased
	    assertEquals(initTime - 1, currentTime, "Countdown should decrease by 1 second");
	}
	
	@Test
	void testStore() {
		GameManager game = new GameManager();
		Store store = game.getStore();
		
		//Testing if the stores parameters are consistent 
		assertEquals("bank.png", (String) store.getFileName());
		assertEquals(1000, store.getX());
		assertEquals(Constants.GROUND_HEIGHT, store.getY());
		assertEquals(Constants.STORE_WIDTH, store.getWidth());
		assertEquals(Constants.STORE_HEIGHT, store.getHeight());
	}
}
