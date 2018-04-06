/* Author: Kathryn Reese
 * Date: 04/04/18
 * GamePanel class inherits JPanel and has a paintComponent and calls an instance of Game class (or start game-when the start button is pressed a new game is commenced).
 */
package panels;


import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileNotFoundException;

import javax.swing.JPanel;

import game.Game;

public class GamePanel extends JPanel{
	private Game myGame;
	private int xDimension = 500;
	private int yDimension = 250;
	private Dimension myDimensions = new Dimension(xDimension, yDimension);

	public GamePanel() throws FileNotFoundException {
		setPreferredSize(myDimensions);
		myGame = new Game(this);
		myGame.setUp();
	}
	
	// Getters and setters to adjust the dimensions, depending on how big the maze file is.
	public Dimension getMyDimensions() {
		return myDimensions;
	}
	
	public void setxDimension(int xDimension) {
		this.xDimension = xDimension;
	}

	public void setyDimension(int yDimension) {
		this.yDimension = yDimension;
	}

	public int getxDimension() {
		return xDimension;
	}

	public int getyDimension() {
		return yDimension;
	}

	/*_______________________________________________________________
	 * paintComponent
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 ________________________________________________________________*/
	public void paintComponent(Graphics page)
	{
		int x = 0;
		int y = 0;
		super.paintComponent(page);
		page.drawImage(myGame.getPlayerIcon().getImage(), myGame.getPlayer().getX(), myGame.getPlayer().getY(), null);
		for(int r = 0; r < myGame.getRow(); r++) {
			for(int c = 0; c < myGame.getColumn(); c++) {
				if(myGame.getMyMazeArray()[r][c] == 0) {
					
				}
				else if(myGame.getMyMazeArray()[r][c] == 1) {
					page.drawImage(myGame.getWallIcon().getImage(), x, y, null);
				}

				else if(myGame.getMyMazeArray()[r][c] == 2){
					for(int i = 0; i < myGame.getMyItemsList().size(); i++) {
							page.drawImage(myGame.getItemsIconArray().get(i).getImage(), myGame.getMyItemsList().get(i).getX(), myGame.getMyItemsList().get(i).getY(), null);
					}
				 }
				
				x += 50;
			}
			x = 0;
			y += 50;
		}
			
		
	}
}
