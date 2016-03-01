/* 
Name: Dennis Xiang
USC loginid: dxiang
CS 455 PA1
Spring 2015
*/

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;


/**
 * 
 * This class defines the JComponent depicting a "random walk" of a Drunkard. It is used for displaying the random walk on
 * a JFrame, given a Drunkard object and the number of steps the Drunkard needs to take. This class extends the JComponent class
 * and overrides the paintComponent method.
 * @author Dennis
 * 
 */
public class RandomWalkComponent extends JComponent {

	private Line2D.Double segment;
	private Drunkard drunk;
	private int numSteps;
	
	/**
	 * Creates the random walk component and stores the given Drunkard object and number of steps to take as instance variables.
	 * @param drunk The given Drunkard class
	 * @param numSteps The number of steps for the Drunkard to take
	 */
	public RandomWalkComponent(Drunkard drunk, int numSteps) {
		
		this.drunk = drunk;
		this.numSteps = numSteps;

	}//end constructor method
	
	
	/**
	 * Overrides the paintComponent method in JComponent. Calls the takeStep() method of the Drunkard numSteps times and creates
	 * and draws a line segment for each of those steps.
	 * @param g The graphics object
	 */
	public void paintComponent(Graphics g) {
		
		int lastX;
		int lastY;
		Graphics2D g2 = (Graphics2D) g; //type cast to graphics 2D object
		
		int countStep = 1;
		
		while(countStep <= numSteps) {
			
			lastX = drunk.getCurrentLoc().getX(); //stores the current location coordinates as the "last" location coordinate
			lastY = drunk.getCurrentLoc().getY(); //to remember last location after drunkard takes a step
			drunk.takeStep();
			segment = new Line2D.Double(lastX, lastY, drunk.getCurrentLoc().getX(), drunk.getCurrentLoc().getY()); //creates line segment from previous and current drunkard coordinates
			g2.draw(segment); 
			countStep++;
			
		}//end while
		
	}//end paintComponent method

}//end class
