package movement;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import game.Game;
import items.Items;
import maze.Block;
import miscellaneous.Score;
import panels.GamePanel;
import player.Player;



public class Movement implements KeyListener{
	
	private Game myGame;
	private Player myPlayer;
	private Score myScore;
	private GamePanel myPanel;
	private ArrayList<Items> myItemsList;
	private ArrayList<ImageIcon> itemsIconArray;
	private ArrayList<Block> myBlockArray;
	private ArrayList<ImageIcon> blockIconArray;
	private ImageIcon playerIcon;
	
	
	// Size of Item Images
	private int itemLeftX;
	private int itemRightX;
	private int itemTopY;
	private int itemBottomY;
	
	// Size of Block Images
	private int blockLeftX;
	private int blockRightX;
	private int blockTopY;
	private int blockBottomY;
	
	
	private int xBeforeCollision = 1;
	private boolean xPressed = false;
	private int yBeforeCollision = 1;
	private boolean yPressed = false;
	
	private JLabel myScoreLabel = new JLabel();
	
	public Movement(Game myGame, Player myPlayer, GamePanel myPanel, ArrayList<Items> myItemsList, ArrayList<ImageIcon> itemsIconArray, ArrayList<Block> myBlockArray, ArrayList<ImageIcon> blockIconArray, ImageIcon playerIcon, Score myScore) {
		this.myGame = myGame;
		this.myItemsList = myItemsList;
		this.itemsIconArray = itemsIconArray;
		this.blockIconArray = blockIconArray;
		this.myBlockArray = myBlockArray;
		this.myPlayer = myPlayer;
		this.myPanel = myPanel;
		this.playerIcon = playerIcon;
		this.myScore = myScore;
		myPanel.addKeyListener(this);
		myPanel.setFocusable(true);
		myPanel.add(myScoreLabel); 
		myScoreLabel.setForeground(Color.blue);
		myScoreLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		myScoreLabel.setHorizontalAlignment(0);
		
	}
	/*______________________________________________________
	 * 
	 * Below are the getters for player and enemy X's and Y's
	 * 
	 _______________________________________________________*/
	public int getPlayerLeftX() {
		return myPlayer.getX();
	}
	public int getPlayerRightX() {
		return myPlayer.getX() + playerIcon.getIconWidth();
	}
	public int getPlayerTopY() {
		return myPlayer.getY();
	}
	public int getPlayerBottomY() {
		return myPlayer.getY() + playerIcon.getIconHeight();
	}
	
	/*_______________________________________________________
	 * 
	 * Getters and Setters
	 * 
	 ________________________________________________________*/
	public ArrayList<Block> getMyBlockArray() {
		return myBlockArray;
	}
	public ArrayList<ImageIcon> getBlockIconArray() {
		return blockIconArray;
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
			System.out.println("ItemsIconArray Size: " + itemsIconArray.size() + "\n" + "myItemsList: " + myItemsList.size());
			itemLeftX = myItemsList.get(i).getX();
			itemRightX = myItemsList.get(i).getX() + itemsIconArray.get(i).getIconWidth();
			itemTopY = myItemsList.get(i).getY();
			itemBottomY = myItemsList.get(i).getY() + itemsIconArray.get(i).getIconHeight();
			
			if(areRectsColliding(getPlayerLeftX(), getPlayerRightX(), getPlayerTopY(), getPlayerBottomY(), itemLeftX, itemRightX, itemTopY, itemBottomY)) {
				myScore.setPlayerScore(myScore.getPlayerScore() + 1);
				myScoreLabel.setText("Score: " + myScore.getPlayerScore());
				itemsIconArray.remove(i);
				myItemsList.remove(i);
				if(myItemsList.isEmpty()) {
					myGame.endGame();
					myScoreLabel.setVisible(false);
				}
			}
		}
		
	}
	
	/*______________________________________________________
	 * 
	 * blockPlayerCollision:
	 * This method determines what will happen when the player
	 * collides with a wall. Runs through the block array, if
	 * the player tries to go towards the block, they will be
	 * rejected.
	 * 
	 _______________________________________________________*/
	private void blockPlayerCollision() {
		for(int i = 0; i < blockIconArray.size(); i++) {
			blockLeftX = myBlockArray.get(i).getX();
			blockRightX = myBlockArray.get(i).getX() + blockIconArray.get(i).getIconWidth();
			blockTopY = myBlockArray.get(i).getY();
			blockBottomY = myBlockArray.get(i).getY() + blockIconArray.get(i).getIconHeight();
			if(areRectsColliding(getPlayerLeftX(), getPlayerRightX(), getPlayerTopY(), getPlayerBottomY(), blockLeftX, blockRightX, blockTopY, blockBottomY)) {
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
				if(yBeforeCollision == 0 && yPressed == true) { // up
					myPlayer.setY(myPlayer.getY() + 50);
				}
				else if(yBeforeCollision == 1 && yPressed == true) { // down
					myPlayer.setY(myPlayer.getY() - 50);
				}
				else if(xBeforeCollision == 0 && xPressed == true) { // left
					myPlayer.setX(myPlayer.getX() + 50);
				}
				else if(xBeforeCollision == 1 && xPressed == true) { // right
					myPlayer.setX(myPlayer.getX() - 50);
				}
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
		int panelLeftX = 0;
		int panelRightX = 0 + myPanel.getyDimension();
		int panelTopY = 0;
		int panelBottomY = 0 + myPanel.getxDimension();
		
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT)
		{
			myPlayer.setX(myPlayer.getX()-50);
			if(getPlayerLeftX() < panelLeftX) {
				myPlayer.setX(myPlayer.getX() + 50); // Panel collision, if the players x is less than the panels x, prevent them from going further.
			}
			/////////////////////////////////////////////////////////////////////////////////////////////////
			// These are used to determine what will happen when a player collides with a block            //
			// if left or right arrow is pressed xPressed becomes true and yPressed becomes false          //
			// xBefore collision determines if it was the left arrow or the right arrow that was pressed   //
			// these will change depending on which arrow was pressed.                                     //
			/////////////////////////////////////////////////////////////////////////////////////////////////
			xPressed = true;
			yPressed = false;
			xBeforeCollision = 0;	
		}
		
		else if(arg0.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			myPlayer.setX(myPlayer.getX()+50);
			if(getPlayerRightX() > panelRightX) {
				myPlayer.setX(myPlayer.getX() - 50); // panel collision, if the players x is greater than the panels right x, prevent them from going further.
			}
			xBeforeCollision = 1;
			xPressed = true;
			yPressed = false;	
		}
		
		else if(arg0.getKeyCode() == KeyEvent.VK_UP)
		{
			myPlayer.setY(myPlayer.getY()-50);
			if(getPlayerTopY() < panelTopY) {
				myPlayer.setY(myPlayer.getY() + 50); // panel collision, if the players y is less than the panels top y, prevent them from going further.
			}
			yBeforeCollision = 0;
			xPressed = false;
			yPressed = true;
			
		}
		
		else if(arg0.getKeyCode() == KeyEvent.VK_DOWN)
		{
			myPlayer.setY(myPlayer.getY()+50);
			if(getPlayerBottomY() > panelBottomY) {
				myPlayer.setY(myPlayer.getY() - 50); // panel collision, if the players y is greater than the panels bottom y, prevent them from going further.
			}
			yBeforeCollision = 1;
			xPressed = false;
			yPressed = true;
		}
		blockPlayerCollision();
		itemPlayerCollision();
		myPanel.repaint();
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
