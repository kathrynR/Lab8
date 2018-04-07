package game;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import enemy.Enemy;
import items.Items;
import maze.Block;
import maze.Maze;
import miscellaneous.Score;
import movement.Movement;
import panels.GamePanel;
import player.Player;

/*
 * Author: Kathryn Reese
 * Partners: Hannah Hollenback
 * 3/10/18
 * edited: 04/05/18
 * Game: set up methods allowing for game execution
 * 
 * Ideas for the future: Could have level one random mazes, level two, and three
 * When the next level approaches change the folder the maze will take from.
 * 
 */
public class Game {
	/*-----------------------------------------------
	 * Attributes
	 ------------------------------------------------*/
	///////////////////////////////////////////////////////////////////////////////////
	// Variables for Player related methods                                          //
	///////////////////////////////////////////////////////////////////////////////////
	private ImageIcon playerIcon;
	// Gather from file to pass into player parameters
	private String name;
	private int x;
	private int y;
	private String imagePath;
	private Player player;
	// file containing player attributes and array that will store each file
	private String filePath = "./src/playerTextDocs/";
	
	///////////////////////////////////////////////////////////////////////////////////
	// Variables for Enemy related methods                                           //
	///////////////////////////////////////////////////////////////////////////////////
	private Enemy enemy;
	private int enemySpeed = 750;

	///////////////////////////////////////////////////////////////////////////////////
	// Variables for Item related methods                                            //
	///////////////////////////////////////////////////////////////////////////////////
	// Instance of random to set the location of items
	private Random randInt = new Random();
	// Item ArrayList, will store instances of Items
	private ArrayList<Items> myItemsList = new ArrayList<Items>();
	private ArrayList<ImageIcon> itemsIconArray = new ArrayList<ImageIcon>();
	private Items items;
	private int numberOfItems = randInt.nextInt(16) + 5;
	
	///////////////////////////////////////////////////////////////////////////////////
	// Variables for Block related methods                                           //
	///////////////////////////////////////////////////////////////////////////////////
	private Block myBlock;
	private ArrayList<Block> myBlocksArray = new ArrayList<Block>();
	private ImageIcon blockIcon;
	private ArrayList<ImageIcon> blockIconArray = new ArrayList<ImageIcon>();
		
	///////////////////////////////////////////////////////////////////////////////////
	// Variables for Maze related methods                                            //
	///////////////////////////////////////////////////////////////////////////////////
	private Maze myMaze;
	private Integer[][] myMazeArray;
	private int row;
	private int column;
	private ImageIcon wallIcon;
	private int numberOfEmptySpaces;

	private GamePanel gamePanel;
	private Movement myMovement;
	
	///////////////////////////////////////////////////////////////////////////////////
	// Variables for Winning the Game	                                             //
	///////////////////////////////////////////////////////////////////////////////////
	private Score myScore = new Score();
	// Score label
	
	private JLabel topThreeScores = new JLabel();
	private JLabel myWinningMessage = new JLabel("You Won!");
	private Font myWinFont = new Font("Britannic Bold", Font.BOLD, 100);
	
	///////////////////////////////////////////////////////////////////////////////////
	// Variables for Game Over			                                             //
	///////////////////////////////////////////////////////////////////////////////////
	private JLabel myLosingMessage = new JLabel("GAME OVER!");
	private Font myLossFont = new Font("Chiller", Font.BOLD, 100);
	
	///////////////////////////////////////////////////////////////////////////////////
	// Variables for Resetting the Game	                                             //
	///////////////////////////////////////////////////////////////////////////////////
	private JFrame myFrame;
	private JButton replayButton = new JButton("Replay");
	
	/*------------------------------------------------
	 * Constructor
	 -------------------------------------------------*/
	public Game(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	/*-----------------------------------------------
	 * Methods
	 * 
	 ------------------------------------------------*/
	
	/* ______________________________________________________________________________________
	 * Method Author: Kathryn Reese
	 * Date: 03/22/18
	 * This method randomly determines which txt file a player will gain its attributes from.
	 ________________________________________________________________________________________*/
	private String randomlySelectPlayer() {
		String fileName = "";
		Random myRand = new Random();
		int randomFileNumber = myRand.nextInt(3);
		if(randomFileNumber == 0) {
			fileName = "CatAttributes.txt";
		}
		else if(randomFileNumber == 1) {
			fileName = "Dog1Attributes.txt";
		}
		else if(randomFileNumber == 2) {
			fileName = "Sprite1Attributes.txt";
		}
		
		return filePath + fileName;
	}
	
	/* _______________________________________________________________________________________
	 * Method Author: Kathryn Reese
	 * Date: 03/22/18
	 * goes through the file, assigning each line to a variable
	 * This creates a new instance of Player with a random character.
	 _________________________________________________________________________________________*/ 
	private void setUpPlayer() throws FileNotFoundException {
		String fileLocation = randomlySelectPlayer();
		File myFile = new File(fileLocation);
		Scanner myScanner = new Scanner(myFile);
		name = myScanner.nextLine();
		imagePath = myScanner.nextLine();
		x = myScanner.nextInt();
		y = myScanner.nextInt();
		player = new Player(x, y, imagePath, name);
		myScanner.close();
	}
	
	/* ____________________________________________________________________________________
	 * Method Author: Kathryn Reese
	 * Original Creation Date: 03/22/18
	 * Lab 8 Edit: 04/06/18
	 * This method sets up the enemy at a random position at the lower right
	 * corner of the screen on the game panel.
	 ______________________________________________________________________________________*/
	private void setUpEnemy() {
		int enemyX = gamePanel.getyDimension() - 50; 
		int enemyY = gamePanel.getxDimension() - 100;
		
		enemy = new Enemy(enemyX, enemyY, "./src/Images/DogCatcher2.png", this, player, gamePanel, myMovement);
		enemy.getMyTimer().start();
	}
	
	/* _______________________________________________________________________________________
	 * Method Author: Kathryn Reese
	 * Date: 04/04/18
	 * setUpMaze() method:
	 * calls an instance of Maze
	 * creates instances of blocks and stores them in a block array.
	 * 
	 _________________________________________________________________________________________*/ 
	private void setUpMaze() throws FileNotFoundException {
		myMaze = new Maze("./src/Images/Wall.png");
		myMaze.mazeReader();
		myMazeArray = myMaze.getMyMazeLayout();
		row = myMaze.getRow();
		column = myMaze.getColumn();
		wallIcon = new ImageIcon(myMaze.getWallImageLocation());
		int x = 0;
		int y = 0;
		for(int r = 0; r < getRow(); r++) {
			for(int c = 0; c < getColumn(); c++) {
				if(getMyMazeArray()[r][c] == 0) {
					
				}
				else if(getMyMazeArray()[r][c] == 1) {
					myBlock = new Block(x, y, myMaze.getWallImageLocation());
					blockIcon = new ImageIcon(myBlock.getImagePath());
					myBlocksArray.add(myBlock);
					blockIconArray.add(blockIcon);
				}

				else if(getMyMazeArray()[r][c] == 2){
					for(int i = 0; i < getMyItemsList().size(); i++) {
					
					}}
				
				x += 50;
			}
			x = 0;
			y += 50;
		}}

	/* _______________________________________________________________________________________
	 * Method Author: Kathryn Reese
	 * Date: 03/22/18
	 * Randomly grabs an item image from the Image file;
	 _________________________________________________________________________________________*/
	private String randomItemImage() {
		String imagePath = "./src/Images/";
		String imageName = "Ball2.png";
		int randomImage = randInt.nextInt(3);
		if(randomImage == 0) {
			imageName = "Ball.png";
		}
		else if(randomImage == 1) {
			imageName = "Ball2.png";
		}
		else if(randomImage == 2) {
			imageName = "Bone.png";
		}
		return imagePath + imageName;
	}
	
	/* _______________________________________________________________________________________
	 * Method Author: Kathryn Reese
	 * Date: 04/05/18
	 * Sets up the items on the screen. Creates a certain number of instances of items.
	 * The first two for loops run through the 2D Maze array.
	 * The third for loop, loops through the Items list and gets the images, puts the image path
	 * in an ImageIcon and stores these icons in another loop.
	 * 
	 _________________________________________________________________________________________*/ 
	private void setUpItems() {
		// This first section determines where the items can be placed in the array.
		int x = 0;
		int y = 0;
		int numberIterater = 0;
		int itemsOnScreen = 0;
		int placeItemTF;
		
		Integer[][] mazeArray = getMyMazeArray();
		while(itemsOnScreen != numberOfItems) { // Ensures that all the items will show up on screen
			for(int r = 0; r < row; r++) {
				for(int c = 0; c < column; c++) {
					placeItemTF = randInt.nextInt(2);
					if(r == 0 && c == 0) { // prevents the item from being painted on top of the character
						
					}
					else {
						
					if(mazeArray[r][c] == 0) {
						if((placeItemTF == 0) && (numberIterater != numberOfItems)) {  // numberIterator != 4 prevents the number of 2 locations from exceeding the number of items
							mazeArray[r][c] = 2;
							numberIterater ++;
							items = new Items(x, y, randomItemImage());
							myItemsList.add(items);		
							itemsOnScreen ++;
						}}}
					x += 50;
				}
				x = 0;
				y += 50;
			}
			for(int i = 0; i < myItemsList.size(); i++) {
				String imagePathLocal = myItemsList.get(i).getImagePath();
				ImageIcon itemsIcon = new ImageIcon(imagePathLocal);
				itemsIconArray.add(itemsIcon);	
			}}}
	

	/*_________________________________________________________
	 * 
	 * setUp() Method:
	 * sets up the player, items, and the maze
	 * 
	 __________________________________________________________*/
	public void setUp() throws FileNotFoundException
	{
		setUpPlayer();
		setUpMaze();
		
		// Adjusts the panel if the maze is bigger than 5 by 10
		gamePanel.setxDimension(myMaze.getRow() * 50);
		gamePanel.setyDimension(myMaze.getColumn() * 50);
		gamePanel.getMyDimensions().setSize(gamePanel.getyDimension(), gamePanel.getxDimension());
		
		setUpItems();
		
		playerIcon = new ImageIcon(player.getImagePath());
		myMovement = new Movement(this, player, gamePanel, myItemsList, itemsIconArray, myBlocksArray, blockIconArray, playerIcon, myScore);
		setUpEnemy();
	}
	
	/*_________________________________________________________
	 * 
	 * endGame() Method:
	 * determines what happens when all the items have been retrieved.
	 * 
	 __________________________________________________________*/
	public void endGame() {
		gamePanel.removeKeyListener(myMovement);
		enemy.getMyTimer().stop();
		for(int i = 0; i < blockIconArray.size(); i ++) {
			myBlocksArray.remove(i);
			blockIconArray.remove(i);
			gamePanel.add(myWinningMessage);
			myWinningMessage.setFont(myWinFont);
		}
		
	}

	/*_________________________________________________________
	 * 
	 * gameOver() Method:
	 * Determines what happens when the player collides with the
	 * enemy.
	 * 
	 __________________________________________________________*/
	public void gameOver() {
		gamePanel.removeKeyListener(myMovement);
		enemy.getMyTimer().stop();
		for(int i = 0; i < blockIconArray.size(); i ++) {
			myBlocksArray.remove(i);
			blockIconArray.remove(i);
			gamePanel.add(myLosingMessage);
			myLosingMessage.setFont(myLossFont);
		}
	}
	
	/*_________________________________________________________
	 * 
	 * resetGame() Method:
	 * resets the game after game over or after all items have been retrieved.
	 * 
	 __________________________________________________________*/
	public void resetGame() throws FileNotFoundException {
		myFrame = gamePanel.getMyFrame();
		gamePanel.getMyFrame().remove(gamePanel);
		GamePanel newGamePanel = new GamePanel(myFrame);
		myFrame.add(newGamePanel);

	}

	/*----------------------------------------------------
	 * Getters and Setters
	 -----------------------------------------------------*/

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}
	
	public ImageIcon getPlayerIcon() {
		return playerIcon;
	}
	public ImageIcon getWallIcon() {
		return wallIcon;
	}

	public void setPlayerIcon(ImageIcon playerIcon) {
		this.playerIcon = playerIcon;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public ArrayList<Items> getMyItemsList() {
		return myItemsList;
	}

	public void setMyItemsList(ArrayList<Items> myItemsList) {
		this.myItemsList = myItemsList;
	}

	public ArrayList<ImageIcon> getItemsIconArray() {
		return itemsIconArray;
	}

	public void setItemsIconArray(ArrayList<ImageIcon> itemsIconArray) {
		this.itemsIconArray = itemsIconArray;
	}
	
	public Integer[][] getMyMazeArray() {
		return myMazeArray;
	}

	public void setMyMazeArray(Integer[][] myMazeArray) {
		this.myMazeArray = myMazeArray;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	public ArrayList<Block> getMyBlocksArray() {
		return myBlocksArray;
	}

	public void setMyBlocksArray(ArrayList<Block> myBlocksArray) {
		this.myBlocksArray = myBlocksArray;
	}

	public ArrayList<ImageIcon> getBlockIconArray() {
		return blockIconArray;
	}

	public void setBlockIconArray(ArrayList<ImageIcon> blockIconArray) {
		this.blockIconArray = blockIconArray;
	}
	
	public int getEnemySpeed() {
		return enemySpeed;
	}

	public void setEnemySpeed(int enemySpeed) {
		this.enemySpeed = enemySpeed;
	}
}
