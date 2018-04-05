package miscellaneous;

import enemy.Enemy;
import game.Game;

public class NextLevel {
	
	private int itemNumberIncrease;
	private int enemySpeedIncrease;
	private static Game myGame;
	
	public NextLevel(Game myGame, int itemNumberIncrease, int enemySpeedIncrease) {
		this.myGame = myGame;
		this.itemNumberIncrease = itemNumberIncrease;
		this.enemySpeedIncrease = enemySpeedIncrease;
	}
	
	public static void increaseEnemySpeed() {
		myGame.setSpeed(myGame.getSpeed() + 50);
	}
	public static void increaseItems() {
		myGame.setNumberOfItems(myGame.getNumberOfItems() + 1);
	}

}
