package enemy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import game.Game;
import miscellaneous.SolidObject;
import movement.Movement;
import panels.GamePanel;
import player.Player;

/*
 * Author: Kathryn Reese
 * Partners: Hannah Hollenback
 * Date: 04/04/2018
 * The enemy class extends SolidObjects class and has Game, MainPanel, and Player added onto it.
 */
public class Enemy extends SolidObject {
	

	private GamePanel myPanel;
	private Player myPlayer;
	private Game myGame;
	private Movement myMovement;
	private Timer myTimer;
	private ImageIcon enemyIcon;
	
	// Size of Block Images
	private int blockLeftX;
	private int blockRightX;
	private int blockTopY;
	private int blockBottomY;
	private int xBeforeCollision = 1;
	private boolean xPressed = false;
	private int yBeforeCollision = 1;
	private boolean yPressed = false;
		

	public Enemy(int x, int y, String imagePath, Game myGame, Player myPlayer, GamePanel myPanel, Movement myMovement) {
		super(x, y, imagePath);
		this.myGame = myGame;
		this.myPlayer = myPlayer;
		this.myPanel = myPanel;
		this.myMovement = myMovement;
		enemyIcon = new ImageIcon(imagePath);
		myTimer = new Timer(myGame.getEnemySpeed(), new timerListener());
		
	}

	public ImageIcon getEnemyIcon() {
		return enemyIcon;
	}

	public void setEnemyIcon(ImageIcon enemyIcon) {
		this.enemyIcon = enemyIcon;
	}
	
	public Timer getMyTimer() {
		return myTimer;
	}
	
	private int getEnemyLeftX() {
		return getX();
	}
	private int getEnemyRightX() {
		return getX() + enemyIcon.getIconWidth();
	}
	private int getEnemyTopY() {
		return getY();
	}
	private int getEnemyBottomY() {
		return getY() + enemyIcon.getIconHeight();	
	}
	
	// Cassen's collision detector 
		private boolean areRectsColliding(int r1TopLeftX, int r1BottomRightX,int r1TopLeftY, int r1BottomRightY, int r2TopLeftX,int r2BottomRightX, int r2TopLeftY, int r2BottomRightY)
		{
			if (r1TopLeftX < r2BottomRightX && r1BottomRightX > r2TopLeftX&& r1TopLeftY < r2BottomRightY && r1BottomRightY > r2TopLeftY){
				return true;
			}
			else{
			return false;
			}
		}
	
	/*______________________________________________________
	 * 
	 * blockEnemyCollision:
	 * This method determines what will happen when the enemy
	 * collides with a wall. Runs through the block array, if
	 * the enemy tries to go towards the block, they will be
	 * rejected.
	 * 
	 _______________________________________________________*/
	private void blockEnemyCollision() {
		for(int i = 0; i < myMovement.getBlockIconArray().size(); i++) {
			blockLeftX = myMovement.getMyBlockArray().get(i).getX();
			blockRightX = myMovement.getMyBlockArray().get(i).getX() + myMovement.getBlockIconArray().get(i).getIconWidth();
			blockTopY = myMovement.getMyBlockArray().get(i).getY();
			blockBottomY = myMovement.getMyBlockArray().get(i).getY() + myMovement.getBlockIconArray().get(i).getIconHeight();
			if(areRectsColliding(getEnemyLeftX(), getEnemyRightX(), getEnemyTopY(), getEnemyBottomY(), blockLeftX, blockRightX, blockTopY, blockBottomY)) {
				//////////////////////////////////////////////////////////////////////////////////////////////
				// Explanation:																				//
				// 		This determines what will happen if the player collides with a block.				//
				//		- If yBeforeCollision == 0 (corresponds to the Up key pressed before collision) 	//
				//			and yPressed (up or down keys are pressed before collision) is true,			//
				//			then the player will move down 50 pixels.										//
				//		- If yBeforeCollision == 1 (corresponds to the Down key pressed before collision)	//
				//			and yPressed (up or down keys are pressed before collision) is true,		 	//
				//			then the player will move up 50 pixels.									 	 	//
				//		- If xBeforeCollision == 0 (corresponds to the left key pressed before collision)  	//
				//			and xPressed (left or right keys are pressed before collision) is true,		 	//
				//			then the player will move right 50 pixels.									 	//
				//		- If xBeforeCollision == 1 (corresponds to the right key pressed before collision)  //
				//			and xPressed (left or right keys are pressed before collision) is true,		 	//
				//			then the player will move left 50 pixels.									 	//
				//																						 	//
				//////////////////////////////////////////////////////////////////////////////////////////////
				if(yBeforeCollision == 0 && yPressed == true) { // down
					setY(getY() - 50);
				}
				else if(yBeforeCollision == 1 && yPressed == true) { // up
					setY(getY() + 50);
				}
				else if(xBeforeCollision == 0 && xPressed == true) { // right
					setX(getX() - 50);
				}
				else if(xBeforeCollision == 1 && xPressed == true) { // left
					setX(getX() + 50);
				}
			}
		}
		
	}
	
	/*______________________________________________________
	 * 
	 * enemyPlayerCollision() method:
	 * This method determines what will happen when the enemy
	 * collides with the player.
	 * First the enemy timer will stop and the players ability
	 * to move will stop. 
	 * Then the GamePanel displays the top scores in the 
	 * topThreeScores label. The replayButton appears on the
	 * gamePanel. Sets the score label to invisible.
	 * 
	 * 
	 _______________________________________________________*/
	private void enemyPlayerCollision() throws FileNotFoundException {
		if(areRectsColliding(myMovement.getPlayerLeftX(), myMovement.getPlayerRightX(), myMovement.getPlayerTopY(), myMovement.getPlayerBottomY(), getEnemyLeftX(),getEnemyRightX(), getEnemyTopY(), getEnemyBottomY())) {
			myGame.gameOver();
		}
	}
	

	/*______________________________________________________
	 * 
	 * Enemies Timer Listener:
	 * Follows the player. When the Enemies x position or y
	 * position is less than or greater than the players,
	 * the enemy will move 5 units in the players direction.
	 * 
	 _______________________________________________________*/
	private class timerListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int panelLeftX = 0;
			int panelRightX = 0 + myPanel.getyDimension();
			int panelTopY = 0;
			int panelBottomY = 0 + myPanel.getxDimension();
			if (getX() < myPlayer.getX()) {
				setX(getX() + 50);
				if(getEnemyLeftX() < panelLeftX) {
					setX(getX() + 50); // Panel collision, if the enemies x is less than the panels x, prevent them from going further.
				}
				xPressed = true;
				yPressed = false;
				xBeforeCollision = 0;	// right
			}
			if (getX() > myPlayer.getX()) {
				setX(getX() - 50);
				if(getEnemyRightX() > panelRightX) {
					setX(getX() - 50); // panel collision, if the enemies x is greater than the panels right x, prevent them from going further.
				}
				xBeforeCollision = 1; // left
				xPressed = true;
				yPressed = false;	
			}
			if (getY() < myPlayer.getY()) {
				setY(getY() + 50);
				if(getEnemyTopY() < panelTopY) {
					setY(getY() + 50); // panel collision, if the enemies y is less than the panels top y, prevent them from going further.
				}
				yBeforeCollision = 0;
				xPressed = false;
				yPressed = true;
			}
			if (getY() > myPlayer.getY()) {
				setY(getY() - 50);
				if(getEnemyBottomY() > panelBottomY) {
					setY(getY() - 50); // panel collision, if the enemies y is greater than the panels bottom y, prevent them from going further.
				}
				yBeforeCollision = 1;
				xPressed = false;
				yPressed = true;
			}
			blockEnemyCollision();
			try {
				enemyPlayerCollision();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myPanel.repaint();
		}
		
	}
	
}
