package miscellaneous;

public class SolidObject {
	protected int x;
	protected int y;
	protected String imagePath;
	protected int score;
	
	public SolidObject(int x, int y, String imagePath)
	{
		this.x = x;
		this.y  = y;
		this.imagePath = imagePath;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
