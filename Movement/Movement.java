/* Author: Kathryn Reese
 * Partners: Hannah Hollenback, Kate Hohenstein
 * Date Created: 03/22/18
 * Controls the player and enemy movement
 * Determines what happens when player, enemy, or item
 * collides.
 * Give kudos to Ian and Jesse! They helped me considerably with
 * my code for this class. 
 */
package Movement;

import player.Player;

/*
 * Author: Kate Hohenstein
 * Partners: Hannah Hollenback, Kathryn Reese
 * Lab 6
 * 3/10/18
 * Movement: set up methods allowing for movement of and collisions of characters
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

import Miscellaneous.LevelUp;
import Miscellaneous.Score;
import enemy.Enemy;
import items.Items;
import main.Frame;
import panels.GamePanel;

public class Movement implements KeyListener{
	private Score myScore;
	private Player myPlayer;
	private Enemy myEnemy;
	private GamePanel myPanel;
	private ArrayList<Items> myItemsList;
	private ArrayList<ImageIcon> itemsIconArray;
	private ImageIcon playerIcon;
	private ImageIcon enemyIcon;
	private LevelUp levelUp = new LevelUp(500); // will later be used to set item number and enemy speed
	
	private Timer myTimer = new Timer(levelUp.getEnemySpeed(), new timerListener());
	
	// Score label
	private JLabel myScoreLabel = new JLabel();
	private JLabel topThreeScores = new JLabel();
	
	private JButton replayButton = new JButton("Replay");
	
	// Size of Item Images
	private int itemLeftX;
	private int itemRightX;
	private int itemTopY;
	private int itemBottomY;
	
	/*______________________________________________________
	 * 
	 * Movement() Constructor
	 * 
	 _______________________________________________________*/
	
	public Movement(Player myPlayer, Enemy myEnemy, GamePanel myPanel, ArrayList<Items> myItemsList, ArrayList<ImageIcon> itemsIconArray, ImageIcon playerIcon, ImageIcon enemyIcon, Score myScore) {
		this.myItemsList = myItemsList;
		this.itemsIconArray = itemsIconArray;
		this.myPlayer = myPlayer;
		this.myEnemy = myEnemy;
		this.myPanel = myPanel;
		this.playerIcon = playerIcon;
		this.enemyIcon = enemyIcon;
		this.myScore = myScore;
		myPanel.addKeyListener(this);
		myPanel.requestFocus();
		myPanel.add(myScoreLabel);
		
	}
	
	/*______________________________________________________
	 * 
	 * Below are the getters for player and enemy X's and Y's
	 * 
	 _______________________________________________________*/
	private int getPlayerLeftX() {
		return myPlayer.getX();
	}
	private int getPlayerRightX() {
		return myPlayer.getX() + playerIcon.getIconWidth();
	}
	private int getPlayerTopY() {
		return myPlayer.getY();
	}
	private int getPlayerBottomY() {
		return myPlayer.getY() + playerIcon.getIconHeight();
	}
	private int getEnemyLeftX() {
		return myEnemy.getX();
	}
	private int getEnemyRightX() {
		return myEnemy.getX() + enemyIcon.getIconWidth();
	}
	private int getEnemyTopY() {
		return myEnemy.getY();
	}
	private int getEnemyBottomY() {
		return myEnemy.getY() + enemyIcon.getIconHeight();	
	}
	
	
	/*______________________________________________________
	 * 
	 * startTheTimer() & stopTheTimer() Methods:
	 * Start and stop the enemy timer respectively.
	 * 
	 * Start timer is used in the StartGameButton when the
	 * startbutton is pressed. This starts the enemy's movement
	 * to start the game.
	 * 
	 * The stop timer is used in the enemycollision detector
	 * of this class. It prevents the program from creating an
	 * error.
	 _______________________________________________________*/
	public void startTheTimer() {
		myTimer.start();
	}
	public void stopTheTimer() {
		myTimer.stop();
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
		if(areRectsColliding(getPlayerLeftX(), getPlayerRightX(), getPlayerTopY(), getPlayerBottomY(), getEnemyLeftX(),getEnemyRightX(), getEnemyTopY(), getEnemyBottomY())) {
			myTimer.stop();
			myPanel.removeKeyListener(this);
			myPanel.add(topThreeScores);
			topThreeScores.setText("<html><body><br>" + myScore.toString() + "</body></html>");
			myPanel.add(replayButton);
			replayButton.addActionListener(new buttonListener());
			myScoreLabel.setVisible(false);
			
			
		}
	}

	/*______________________________________________________
	 * 
	 * ItemPlayerCollision:
	 * This method determines what will happen when the player
	 * collides with an item. It gets the sides of the items
	 * from a list. Then it adds these x and y coordinates.
	 * If the player collides with an item the score is
	 * updated, and the item is removed from the items array
	 * and the itemsIconArray.
	 * 
	 _______________________________________________________*/
	private void itemPlayerCollision() {
		for(int i = 0; i < itemsIconArray.size(); i++) {
			itemLeftX = myItemsList.get(i).getX();
			itemRightX = myItemsList.get(i).getX() + itemsIconArray.get(i).getIconWidth();
			itemTopY = myItemsList.get(i).getY();
			itemBottomY = myItemsList.get(i).getY() + itemsIconArray.get(i).getIconHeight();
			
			if(areRectsColliding(getPlayerLeftX(), getPlayerRightX(), getPlayerTopY(), getPlayerBottomY(), itemLeftX, itemRightX, itemTopY, itemBottomY)) {
				myScore.setPlayerScore(myScore.getPlayerScore() + 1);
				myScoreLabel.setText("Score: " + myScore.getPlayerScore());
				itemsIconArray.remove(i);
				myItemsList.remove(i);
			}
		}
		
	}

	/*______________________________________________________
	 * 
	 *	keyPressed method of Movement:
	 * determines what happens when the player moves 5 units
	 * to the right, left, up, and down.
	 * It also determines what will happen when the player
	 * reaches the edge of the screen.
	 * 
	 _______________________________________________________*/
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT)
		{
			myPlayer.setX(myPlayer.getX()-5);
			
			if (myPlayer.getX() <= 0){ // (Kathryn) if the x value of the players position is less than or equal to 0, then the player will reset to the right side of the screen.
				myPlayer.setX(600);
				}
			
		}
		
		else if(arg0.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			myPlayer.setX(myPlayer.getX()+5);
			
			if (myPlayer.getX() >= 600){ // if the X value of the players position is greater than the right side of the screen, then the player will be reset to the left side of the screen
				myPlayer.setX(0);
			}
			
		}
		
		else if(arg0.getKeyCode() == KeyEvent.VK_UP)
		{
			myPlayer.setY(myPlayer.getY()-5);
			if (myPlayer.getY() <= 0){ // if the y value of the players position is less than or equal to 0, then the player will reset to the bottom of the screen.
				 myPlayer.setY(600);
				 }

		}
		
		else if(arg0.getKeyCode() == KeyEvent.VK_DOWN)
		{
			myPlayer.setY(myPlayer.getY()+5);
			if (myPlayer.getY() >= 600){ // if the y value of the players position is greater than or equal to the height of the panel, then the player will reset to the top of the screen.
				 myPlayer.setY(0);
			}

		}
		try {
			enemyPlayerCollision();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		itemPlayerCollision();
	myPanel.repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
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
			if (myEnemy.getX() < myPlayer.getX()) {
				myEnemy.setX(myEnemy.getX() + 5);
			}
			if (myEnemy.getX() > myPlayer.getX()) {
				myEnemy.setX(myEnemy.getX() - 5);
				
			}
			if (myEnemy.getY() < myPlayer.getY()) {
				myEnemy.setY(myEnemy.getY() + 5);
			}
			if (myEnemy.getY() > myPlayer.getY()) {
				myEnemy.setY(myEnemy.getY() - 5);
			}
			try {
				enemyPlayerCollision();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myPanel.repaint();
		}
		
	}
	
	/*______________________________________________________
	 * 
	 * button Listener:
	 * This determines what the replay button will do.
	 * It removes the old frame, and starts a new frame, which
	 * will begin a new game.
	 * 
	 _______________________________________________________*/
	private class buttonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Frame.removeFrame();
			try {
				Frame.newGame();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}


}
