/* 
Name: Dennis Xiang
USC loginid: dxiang
CS 455 PA1
Spring 2015
*/

import javax.swing.JFrame;
import java.util.Scanner;

/**
 * This class is the main program module for this drunkard random walk project. This program will require the user to input a
 * positive integer for the number of "steps" for the drunkard to take, and then display a window depicting the path of the random
 * walk.
 * @author Dennis
 *
 */
public class RandomWalkViewer {

	
	/**
	 * Main method for the project. Creates an ImPoint and a Drunkard object and then prompts the user for the number of steps for
	 * the drunkard to take. Creates the JFrame object and passes the Drunkard object and number of steps to create a new
	 * RandomWalkComponent object. Then adds the RandomWalkComponent to the JFrame.
	 * @param args
	 */
	public static void main(String[] args) {
		
		int numSteps;
		final int STEP_SIZE = 5;
		
		ImPoint startLoc = new ImPoint(200,200); //sets start location to middle of JFrame
		Drunkard drunk = new Drunkard(startLoc, STEP_SIZE);
		RandomWalkComponent walkComponent;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Input number of steps for the drunkard to take: ");
		numSteps = sc.nextInt();
		
		while(numSteps <= 0) {
			System.out.println("Number of steps must be a positive integer, please enter another value: ");
			numSteps = sc.nextInt();
		}//end while
		
		sc.close();
		
		JFrame frame = new JFrame("Drunk Guy Walking");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit application when 'X' button is clicked
		
		walkComponent = new RandomWalkComponent(drunk, numSteps);
			
		frame.add(walkComponent);
		frame.setVisible(true);
	}//end main

}//end RandomWalkViewer class
