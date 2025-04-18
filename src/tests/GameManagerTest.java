package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import game.GameManager;
import gameConstants.Constants;
import sprites.Coin;
import sprites.Enemy;
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

}
