package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import filePicker.FilePicker;

public class Maze {
	private Integer[][] myMazeLayout = new Integer[10][5];
	
	public void mazeReader() throws FileNotFoundException {
		File myFile = new File(FilePicker.randomlyChooseFile("./src/Images/"));
		Scanner myScanner = new Scanner(myFile);
		while(myScanner.hasNextLine()) {
			
		}
		myScanner.close();
		
	}
	

}
