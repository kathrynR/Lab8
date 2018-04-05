package player;

import miscellaneous.SolidObject;

/*
 * Author: Kathryn Reese
 * Partner: Hannah Hollenback
 * Date: 04/04/2018
 * The Player class extends Solid object. Players also have names so that was added.
 */
public class Player extends SolidObject{
	private String name;

	public Player(int x, int y, String imagePath, String name) {
		super(x, y, imagePath);
		this.name = name;
		// TODO Auto-generated constructor stub
	}

	
}

