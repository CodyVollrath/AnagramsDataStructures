package edu.westga.cs3151.anagrams.controller;

import java.util.ArrayList;
import java.util.Scanner;

import edu.westga.cs3151.anagrams.solver.AnagramFinder;

/**
 * The Class AnagramController
 * 
 * @author CS3151
 * @version Spring 2021
 */
public class AnagramController {
	private AnagramFinder anagramFinder;
	
	/**
	 * Instantiates a new controller.
	 * 
	 * @pre none
	 * @post none
	 * @param filename the name of the dictionary file
	 */
	public AnagramController(String filename) {
		this.anagramFinder = new AnagramFinder(filename);
	}

	/**
	 * Runs the anagram finder
	 */
	public void run() {
		System.out.println("Welcome to the Anagram Finder!");
		try (Scanner console = new Scanner(System.in)) {
			boolean done = false;
			while (!done) {
				String phrase = this.getLettersOfPhrase(console);
				ArrayList<ArrayList<String>> anagrams = this.anagramFinder.findAnagrams(phrase);
				this.printAnagrams(console, anagrams);
				done = this.checkIfDone(console);
			}
		}
		System.out.println("Thanks for using the Anagram Finder!");
	}

	private String getLettersOfPhrase(Scanner console) {
		System.out.println();
		String phrase = "";
		while (phrase.isEmpty()) {
			System.out.print("Enter a word or phrase: ");
			phrase = console.nextLine().toLowerCase();
			phrase = phrase.replaceAll("[^a-zA-Z]", "");
		}
		return phrase;
	}

	private void printAnagrams(Scanner console, ArrayList<ArrayList<String>> anagrams) {
		if (anagrams.isEmpty()) {
			System.out.println("No anagrams have been found.");
		} else {
			System.out.println("The anagrams of the entered phrase:");
			for (ArrayList<String> phrase : anagrams) {
				for (String word : phrase) {
					System.out.print(" " + word);
				}
				System.out.println();
			}
		}
	}

	private boolean checkIfDone(Scanner console) {
		System.out.println();
		System.out.print("Do you want to try another phrase? (y/n) ");
		String answer = console.nextLine().toLowerCase();
		while (!answer.equals("n") && !answer.equals("y")) {
			System.out.println("Please enter either y or n.");
			System.out.print("Do you want to try another phrase? ");
			answer = console.nextLine();
		}
		return !answer.equals("y");
	}
}
