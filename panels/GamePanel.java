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
	private Dimension myDimensions = new Dimension(500, 500);
	public GamePanel() throws FileNotFoundException {
		setPreferredSize(myDimensions);
		myGame = new Game(this);
		myGame.setUp();
	}

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
				
				/*?????????????????????????????????????????????????
				 * TODO: When items are instantiated:
				 * 
				 * for(int i = 0; i < myGame.getMyItemsList().size(); i++) {
				 * page.drawImage(myGame.getItemsIconArray().get(i).getImage(), myGame.getMyItemsList().get(i).getX(), myGame.getMyItemsList().get(i).getY(), null);
				 * }
				 * else if(myGame.getmyMazeArray()[r][c] == 2){
				 * }
				 ??????????????????????????????????????????????????*/
				x += 50;
			}
			x = 0;
			y += 50;
		}
			
		
	}
}
