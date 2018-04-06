
package main;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

import panels.GamePanel;

public class Frame {

	public static void main(String[] args) throws FileNotFoundException {
		JFrame myFrame = new JFrame("MAZE");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel myPanel = new GamePanel();
		myFrame.getContentPane().add(myPanel);
		myFrame.setVisible(true);
		myFrame.pack();
	}

}
