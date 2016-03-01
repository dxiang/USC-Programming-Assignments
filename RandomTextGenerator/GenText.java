/* 
 * Name: Dennis Xiang
 * USC loginid: dxiang
 * CS 455 PA4
 * Spring 2015
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.ListIterator;



/**
 * Program main for generating a random set of words using the Markov property. Program reads in a "source" text file and randomly selects
 * a "prefix" of a given number of words. The program then uses this prefix as the "current state" of the Markov process to generate the next
 * word until the given number of words is reached.
 * 
 * Calling it from the command line:
 * 
 * java GenText [-d] prefixLength numWords sourceFile outFile 
 * 
 * where:
 * -d: (Optional) Enters debug mode for the program
 * prefixLength: Number of words used as the prefix (must be greater than or equal to 1)
 * numWords: Number of total words to be generated including the initial prefix (must be greater than 0)
 * sourceFile: Source file for determining the prefix and next word
 * outFile: Output file
 */

public class GenText {

	public static void main(String[] args) throws ProgramArgumentException, FileNotFoundException {

		try{
			
			boolean debugMode = false;
			int argsLength = checkProgArgs(args);
			if(argsLength == 5) {
				debugMode = true;
			}
			
			int prefixLength = Integer.parseInt(args[argsLength-4]);
			int numWords = Integer.parseInt(args[argsLength-3]);
			String sourceFile = args[argsLength-2];
			String outFile = args[argsLength-1];

			PrintWriter writer = new PrintWriter(outFile);		
			
			RandomTextGenerator generator = new RandomTextGenerator(readSource(sourceFile, prefixLength), numWords, prefixLength, debugMode);
			
			if(debugMode) { 
				generator.genTextDebug(writer);
			}
			else {
				generator.genText(writer);
			}
			
			writer.close();
			
		}
		catch(NumberFormatException except) {
			System.out.println("ERROR: Arguments for prefixLength and numWords must be integers. Please re-run program with correct arguments.");
			System.out.println("Expected command-line arguments: [-d](Optional) [prefixLength](Integer) [numWords](Integer) [sourceFile](String) [outFile](String)");
		}
		catch(FileNotFoundException except) {
			System.out.println("ERROR: Cannot write to output file.");
		}
		catch(ProgramArgumentException except) {
			System.out.println("ERROR: Improper program arguments. Please re-run program with correct arguments.");
			System.out.println("Expected command-line arguments: [-d](Optional) [prefixLength](int >= 1) [numWords](int >= 0) [sourceFile](String) [outFile](String)");
		}
		catch(BadDataException except) {
			System.out.println("ERROR: Source file must contain more words than the prefix length.");
		}
		
	}//end main method
	
	
	/**
	 * Checks to make sure that the program command line arguments are valid.
	 * @param args: String array of command line arguments
	 * @return: The number of arguments
	 * @throws ProgramArgumentException, NumberFormatException
	 */
	private static int checkProgArgs(String[] args) throws ProgramArgumentException, NumberFormatException {
		
		if(args.length == 0) {
			throw new ProgramArgumentException();
		}

		int expectedLength = 4; //minimum 4 command-line arguments expected
		if (args[0].equals("-d")) {
			expectedLength = 5; //5 arguments expected with optional debug mode
		}

		if(args.length != expectedLength) {
			throw new ProgramArgumentException();
		}
		
		int prefixLength = Integer.parseInt(args[expectedLength-4]);
		if(prefixLength < 1) {
			throw new ProgramArgumentException();
		}
		
		int numWords = Integer.parseInt(args[expectedLength-3]);
		if(numWords < 1) {
			throw new ProgramArgumentException();
		}
		
		return expectedLength;
		
	}//end method
	
	
	
	/**
	 * Reads the source file into an arraylist of strings and returns the arraylist
	 * @param sourceFile: name of source file
	 * @return the arraylist of strings
	 * @throws FileNotFoundException, BadDataException
	 */
	private static ArrayList<String> readSource(String sourceFile, int prefixLength) throws FileNotFoundException, BadDataException {
		
		ArrayList<String> source = new ArrayList<String>();
		
		try {			
			Scanner reader = new Scanner(new File(sourceFile));
			while(reader.hasNext()) {
				source.add(reader.next());
			}
			reader.close();
			
			if(source.size() <= prefixLength) { //source must be at least 1 word longer than the prefix
				throw new BadDataException();
			}
			
		}
		catch(FileNotFoundException except) {
			System.out.println("ERROR: Sourcefile not found. Please provide a valid source file.");
		}
		
		return source;
		
	}//end method
	
	
	/**
	 * FOR TESTING PURPOSES. Prints out array list.
	 * @param textArray
	 */
	private static void printArrayList(ArrayList<String> textArray) {
		
		ListIterator<String> iter = textArray.listIterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
		
	}//end method


}//end GenText class





/**
 * New IOException and RuntimeException subclasses for handling errors in the program command line
 * @author Dennis
 *
 */

class ProgramArgumentException extends IOException
{
   public ProgramArgumentException() {}
   public ProgramArgumentException(String message)
   {
      super(message);
   }
}


class BadDataException extends RuntimeException
{
   public BadDataException() {}
   public BadDataException(String message)
   {
      super(message);
   }
}
