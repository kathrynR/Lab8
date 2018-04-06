package game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import items.Items;
import maze.Maze;
import panels.GamePanel;
import player.Player;

/*
 * Author: Kate Hohenstein
 * Partners: Hannah Hollenback, Kathryn Reese
 * Lab 6
 * 3/10/18
 * Game: set up methods allowing for game execution
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
	// Variables for Item related methods                                            //
	///////////////////////////////////////////////////////////////////////////////////
	// Instance of random to set the location of items
	private Random randInt = new Random();
	// Item ArrayList, will store instances of Items
	private ArrayList<Items> myItemsList = new ArrayList<Items>();
	private ArrayList<ImageIcon> itemsIconArray = new ArrayList<ImageIcon>();
	private Items items;
		
	///////////////////////////////////////////////////////////////////////////////////
	// Variables for Maze related methods                                            //
	///////////////////////////////////////////////////////////////////////////////////
	private Maze myMaze;
	private Integer[][] myMazeArray;
	private int row;
	private int column;
	private ImageIcon wallIcon;

	private GamePanel gamePanel;
	
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
			imageName = "Bone.jpg";
		}
		return imagePath + imageName;
	}
	
	/* _______________________________________________________________________________________
	 * Method Author: Kathryn Reese
	 * Date: 03/22/18
	 * Sets up the items on the screen. Creates a certain number of instances of items.
	 * The first for loop will create instances of items giving them random X's, Y's, and images.
	 * It adds these Item instances to a loop.
	 * The second for loop, loops through the Items list and gets the images, puts the image path
	 * in an ImageIcon and stores these icons in another loop.
	 * 
	 _________________________________________________________________________________________*/ 
	private void setUpItems() {
		for(int i = 0; i < (5) ; i++) {
			
			int randomItemX = randInt.nextInt(550 + 1);
			int randomItemY = randInt.nextInt(550 + 1);
			items = new Items(randomItemX, randomItemY, randomItemImage());
			myItemsList.add(items);
		}
		for(int i = 0; i < myItemsList.size(); i++) {
			String imagePathLocal = myItemsList.get(i).getImagePath();
			ImageIcon itemsIcon = new ImageIcon(imagePathLocal);
			itemsIconArray.add(itemsIcon);
			
		}
		
		
	}
	
	/* _______________________________________________________________________________________
	 * Method Author: Kathryn Reese
	 * Date: 04/04/18
	 * setUpMaze() method:
	 * calls an instance of Maze
	 * 
	 _________________________________________________________________________________________*/ 
	private void setUpMaze() throws FileNotFoundException {
		myMaze = new Maze("./src/Images/Wall.jpg");
		myMaze.mazeReader();
		myMazeArray = myMaze.getMyMazeLayout();
		row = myMaze.getRow();
		column = myMaze.getColumn();
		wallIcon = new ImageIcon(myMaze.getWallImageLocation());
		
	}
	
	

	/*_________________________________________________________
	 * 
	 * setUp() Method:
	 * sets up the player, items, and the maze
	 * 
	 __________________________________________________________*/
	public void setUp() throws FileNotFoundException
	{
		setUpPlayer();
		setUpItems();
		setUpMaze();
		playerIcon = new ImageIcon(player.getImagePath());
	}

	
	/*----------------------------------------------------
	 * Getters and Setters
	 -----------------------------------------------------*/
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
}
