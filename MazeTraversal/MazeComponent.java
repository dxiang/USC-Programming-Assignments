// Name: Dennis Xiang
// USC loginid: dxiang
// CS 455 PA3
// Spring 2015

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.Color;
import javax.swing.JComponent;
import java.util.ListIterator;


/**
   MazeComponent class
   
   A component that displays the maze and path through it if one has been found.
*/
public class MazeComponent extends JComponent
{
   
   private static final int START_X = 10;  // where to start drawing maze in frame
   private static final int START_Y = 10;  // (i.e., upper-left corner)
   
   private static final int BOX_WIDTH = 20;  // width and height of one maze unit
   private static final int BOX_HEIGHT = 20;

   private Maze maze;
   
   /**
      Constructs the component.
      @param maze   the maze to display
   */
   public MazeComponent(Maze maze) 
   {      
	   this.maze = maze;
   }

   
   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
   */
   public void paintComponent(Graphics g)
   {
	   
	   //Draws the maze board
	   Graphics2D g2 = (Graphics2D) g;
	   
	   Rectangle box = new Rectangle(BOX_WIDTH, BOX_HEIGHT);
	   
	   for(int i = 1; i <= maze.numRows(); i++) {
		   
		   for(int j = 1; j <= maze.numCols(); j++) {
			   
			   MazeCoord coord = new MazeCoord(i, j);
			   if(i == maze.numRows() && j == maze.numCols() && maze.getValAt(coord) == 0) {
				   g2.setColor(Color.green); //sets color to green if the MazeCoord is the bottom-right coordinate and is not a wall
			   }
			   else if(maze.getValAt(coord) == 1) {
				   g2.setColor(Color.black); //sets color to black if the MazeCoord is a wall
			   }
			   else {
				   g2.setColor(Color.white); //sets color to white if MazeCoord is not a wall
			   }
			   box.setLocation((j-1) * BOX_WIDTH + START_X, (i-1) * BOX_WIDTH + START_Y); //locates square based on MazeCoord
			   g2.draw(box);
			   g2.fill(box);
			   
		   }
	
	   }
	   
	   box.setBounds(START_X, START_Y, BOX_WIDTH * maze.numCols(), BOX_HEIGHT * maze.numRows()); //creates a border around the maze
	   g2.setColor(Color.black);
	   g2.draw(box);
	   
	   
	   //if a path is found in the maze, draws maze path on the board
	   if(maze.getPath() != null) { 
		   ListIterator<MazeCoord> iter = maze.getPath().listIterator();
		   Line2D.Double lineSeg;
		   MazeCoord lastCoord;
		   MazeCoord currCoord;

		   if(iter.hasNext()) { 
			   lastCoord = iter.next();	   

			   while(iter.hasNext()) {
				   currCoord = iter.next();
				   lineSeg = new Line2D.Double(20+(lastCoord.getCol()*20), 20+(lastCoord.getRow()*20), 20+(currCoord.getCol()*20), 20+(currCoord.getRow()*20));
				   g2.draw(lineSeg);
				   lastCoord = currCoord;
			   }
		   }
	   }
	   
   }
   
}



