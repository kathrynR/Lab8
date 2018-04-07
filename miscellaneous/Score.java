/* Author: Kathryn Reese
 * Partners: Hannah Hollenback, Kate Hohenstein
 * Date Created: 03/21/18
 * Sets and gets scores, keeping track of the highest score
 * in an array.
 */
package miscellaneous;

import java.util.ArrayList;
import java.util.Collections;

public class Score {
	private int playerScore;
	private static ArrayList<Integer> topThreeScores = new ArrayList<Integer>();

	/*_______________________________________________
	 * 
	 * Getters and Setters for playerScore variable
	 * 
	 ________________________________________________*/
	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}
	/*________________________________________________________________________________
	 * calculateTopThree()
	 * This method will add 0's to an ArrayList if it is empty. Then it will add the
	 * player's score to the list, sort it, and then reverse the order.
	 * If the ArrayList contains more than 3 integers it will remove the lowest integer.
	 _________________________________________________________________________________*/
		private void calculateTopThree()
		{	if(topThreeScores.isEmpty()) {
			for(int i = 0; i < 3; i++) {
			topThreeScores.add(0);
			}
		}
		
		topThreeScores.add(getPlayerScore());
		Collections.sort(topThreeScores);
		Collections.reverse(topThreeScores);
		
		if (topThreeScores.size() > 3) {		
			topThreeScores.remove(3);
		}
			
		}

		// toString
		public String toString()
		{
			calculateTopThree();
			String scoresString = "<html><body>Top Score: " + topThreeScores.get(0) + "<br>" +
								  "Second Top Score: " + topThreeScores.get(1) + "<br>" +
								  "Third Top Score: " + topThreeScores.get(2) + "<br></body></html>";
			return scoresString;
		}

}

