package filePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class FilePicker {
	//private static String fileLocation; 
	
	/*---------------------------------------------------------------------
	 * 
	 * randomlyChooseFile() method:
	 * Loops through the files, in the given folder, 
	 * grabbing the names of each file. This returns a File object.
	 * 
	 ----------------------------------------------------------------------*/

	public static String randomlyChooseFile(String fileLocation) {
		ArrayList<String> fileNameArray = new ArrayList<String>();
		File fileToSearch = new File(fileLocation);
		for(File name:fileToSearch.listFiles())
		{
			fileNameArray.add(name.getName());
		}
		Random myRand = new Random();
		int randomInteger = myRand.nextInt(fileNameArray.size());
		return fileLocation + fileNameArray.get(randomInteger);
	}

}
