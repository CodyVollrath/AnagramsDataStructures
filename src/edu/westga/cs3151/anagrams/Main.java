package edu.westga.cs3151.anagrams;

import edu.westga.cs3151.anagrams.controller.AnagramController;

/**
 * Entry point for application.
 * 
 * @author CS3151
 * @version Spring 2021
 */
public class Main {

	/**
	 * Entry point in to the application
	 * 
	 * @pre none
	 * @post none
	 * @param args not used
	 */
	public static void main(String[] args) {
		AnagramController controller = new AnagramController("dictionary_large.txt");
		controller.run();
	}
}
