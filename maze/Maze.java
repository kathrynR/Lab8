package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import filePicker.FilePicker;

public class Maze {
	private int row;
	private int column;
	private String wallImageLocation;
	private Integer[][] myMazeLayout;
	
	public Maze(String wallImageLocation) {
		this.wallImageLocation = wallImageLocation;
	}
	

	/*_______________________________________________________________________________
	 * 
	 * Author: Kathryn Reese
	 * mazeReader() Method:
	 * This method takes a random File from the files folder. It then
	 * parses this file into lines. This method creates an Array of integers.
	 * Does not return anything, but it will set up the maze's array.
	 * A map of any size can be created.
	 * 
	 * Note: THIS METHOD SHOULD ONLY BE CALLED ONCE. IF YOU CALL IT MORE THAN ONCE
	 * YOU WILL RANDOMLY PICK ANOTHER MAZE. AFTER THIS IS CALLED, USE THE
	 * GETMYMAZELAYOUT GETTER IF IT IS NEEDED.
	 *
	 ________________________________________________________________________________*/
	public void mazeReader() throws FileNotFoundException {
		File myFile = new File(FilePicker.randomlyChooseFile("./src/files/"));
		Scanner myScanner = new Scanner(myFile);
		
		// determines the number of rows and columns in a file
		while (myScanner.hasNextLine()) {
			String line = myScanner.nextLine();
			row ++;
			column = line.replaceAll(" ", "").length();
		}
		myScanner.close();
		myScanner = new Scanner(myFile);
		myMazeLayout = new Integer[row][column];
			
			
		// Loop to assign integers to a specific row and column
		for(int r = 0; r < row; r++) {
				
			String line = myScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			for(int c = 0; c < column; c++) {
				int myNumber = lineScanner.nextInt();
				myMazeLayout[r][c] = myNumber;

			}
			lineScanner.close();
	
		}
		myScanner.close();
		
	}
	
	/*__________________________________________________
	 * 
	 * Getters and Setters
	 * 
	 ___________________________________________________*/
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Integer[][] getMyMazeLayout() {
		return myMazeLayout;
	}

	public void setMyMazeLayout(Integer[][] myMazeLayout) {
		this.myMazeLayout = myMazeLayout;
	}
	public String getWallImageLocation() {
		return wallImageLocation;
	}

	public void setWallImageLocation(String wallImageLocation) {
		this.wallImageLocation = wallImageLocation;
	}
	

}
