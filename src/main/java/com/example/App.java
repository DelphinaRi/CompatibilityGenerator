/*
 * c2017-2023 Courtney Brown 
 * Edited by: Delphina Rivas 
 * Class: Project 2 Template
 * Description: This is a template for the project 2 code, which is an implementation of a Markov chain of order 1
 */

package com.example;

//importing the JMusic stuff
import jm.music.data.*;
import jm.util.*;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import processing.core.*;

//make sure this class name matches your file name, if not fix.
public class App extends PApplet {

	static MelodyPlayer player; // play a midi sequence
	static MidiFileToNotes midiNotes; // read a midi file
	static int noteCount = 0;
	// ArrayList<String> dates = new ArrayList<String>();
	String text1 = "";
	String finalName = "d";
	String text2 = "";
	String finalDate = "d";
	boolean name1Entered = false;
	boolean name2Entered = false;

	boolean date1entered = false;
	boolean date2entered = false;
	GUI g = new GUI(this);
	// button b = new button(this, 0, 0, 200, 100, "click me");

	// int whichTest = MAX_INT;

	// make cross-platform
	static FileSystem sys = FileSystems.getDefault();

	// the getSeperator() creates the appropriate back or forward slash based on the
	// OS in which it is running -- OS X & Windows use same code :)
	static String filePath = "mid" + sys.getSeparator() + "MaryHadALittleLamb.mid"; // path to the midi file -- you
																					// can
																					// change this to your file
																					// ABBA_-_Super_Trouper
																					// MaryHadALittleLamb
	// location/name

	public static void main(String[] args) {
		// setup();
		// int whichTest = Integer.parseInt(args[0]);
		// println("this is whichtest = " + whichTest);
		// unitTests(whichTest);
		int whichArg = Integer.parseInt(args[0]);

		final int COMMANDLINE_ARG_FOR_UNIT_TEST = 2; // or whatever number you want to use, just tell me in the assign

		if (whichArg != 2) {
			PApplet.main("com.example.App");
			println("Hola!");
		} else if (whichArg == 2) {
			testAndTrainProbGen();
			generateMelody();
			unitTest3();

			testAndTrainProbGenMar();
			generateMelodyM();
			unitTest2();
		}
		// PApplet.main("com.example.App");
		// TODO Auto-generated method stub

	}

	public void settings() {
		// set size of canvas, have it at 500,500 for testing purposes
		fullScreen();
		// background(255);
		// size(500, 500);

	}

	public void draw() {
		// display basically runs the whole program
		g.display();
		if (g.getReset() == true) {
			text1 = "";
			finalName = "d";
			text2 = "";
			finalDate = "d";
			name1Entered = false;
			name2Entered = false;

			date1entered = false;
			date2entered = false;
		}
		// b.display();
		// int s = g.getState();

	}

	public void keyPressed() {

		int s = g.getState();
		// if its on the start screen pressing any button except enter moves to the next
		// screen
		if (s == 1) {
			if (key != ENTER) {
				g.changeState();
			}
		} else if (s == 2) {
			String topPart = "Name your child";
			String bottomPart = "Do NOT input uppercase letters";

			// draw background
			// draw outerSquare
			noStroke();
			fill(247, 121, 233);
			rect(0, 0, width, height);

			// draw inner square
			fill(252, 235, 250);
			rect(50, 50, width - 100, height - 100);

			stroke(0);
			fill(0);
			textSize(50);
			text(topPart, width / 2 - 200, 100);
			textSize(10);
			text(bottomPart, width / 2 - 75, 150);

			// println(text1);
			if (key == BACKSPACE) {
				// makes it so you can actually use the backspace button
				if (text1.length() > 0) {
					text1 = text1.substring(0, text1.length() - 1);
				} // if
			} else if (key == ENTER) {
				// if key =enter takes the input and gives it to the gui class
				if (name1Entered == false && name2Entered == false) {
					println("Enter Pressed = " + text1);
					finalName = text1;
					println("Final = " + finalName);
					name1Entered = true;
					g.getName(finalName);
					text1 = "";
				} else if (name1Entered == true && name2Entered == false) {
					finalName = text1;
					name2Entered = true;
					g.getName2(finalName);
					g.changeState();
				}
			} else if (key != ENTER) {
				// if its not enter adds another letter to the word
				String k = String.valueOf(key);
				text1 = text1.concat(k);
				// text1 = temp;
			}

			// priints the letters
			textSize(50);
			if (name1Entered == false) {
				fill(0);
				textSize(20);
				text("Player 1 Input Your First Name: " + text1, width / 4, height / 3 + 100);
			}
			if (name1Entered == true) {
				fill(0);
				textSize(20);
				text("Person 2 Input your First name: " + text1, width / 4, height / 3 + 100);
			}
		} else if (s == 4) {
			String topPart = "What will your child's birthday be";
			String bottomPart = "Please input it in MM/DD/YYYY format";

			// draw background
			// draw outerSquare
			noStroke();
			fill(247, 121, 233);
			rect(0, 0, width, height);

			// draw inner square
			fill(252, 235, 250);
			rect(50, 50, width - 100, height - 100);

			stroke(0);
			fill(0);
			textSize(50);
			text(topPart, 250, 100);
			textSize(10);
			text(bottomPart, width / 2 - 75, 150);

			// println(text1);
			if (key == BACKSPACE) {
				// makes it so you can actually use the backspace button
				if (text2.length() > 0) {
					text2 = text2.substring(0, text2.length() - 1);
				} // if
			} else if (key == ENTER) {
				// if key =enter takes the input and gives it to the gui class
				if (date1entered == false && date2entered == false) {
					println("Enter Pressed = " + text2);
					finalDate = text2;
					println("Final = " + finalDate);
					date1entered = true;
					g.getDate(finalDate);
					text2 = "";
				} else if (date1entered == true && date2entered == false) {
					finalDate = text2;
					date2entered = true;
					g.getDate(finalDate);
					g.changeState();
				}
			} else if (key != ENTER) {
				// if its not enter adds another letter to the word
				String k = String.valueOf(key);
				text2 = text2.concat(k);
				// text1 = temp;
			} else

				// prints the letters
				textSize(50);
			if (date1entered == false) {
				fill(0);
				textSize(20);
				text("Person 1 Input your Birthday: " + text2, width / 4, height / 3 + 100);
			}
			if (date1entered == true) {
				fill(0);
				textSize(20);
				text("Person 2 Input your Birthday: " + text2, width / 4, height / 3 + 100);
			}
		}

	}

	// doing all the setup stuff
	public void setup() {

		// playMidiFile(filePath); //use to debug -- this will play the ENTIRE file --
		// use ONLY to check if you have a valid path & file & it plays
		// it will NOT let you know whether you have opened file to get the data in the
		// form you need for the assignment

		midiSetup(filePath);
	}

	// public static void unitTests(int whichTest) {
	// // setup();
	// testAndTrainProbGenMar();
	// generateMelodyM();
	// // // // unitTest2();
	// if (whichTest == 0) {
	// testAndTrainProbGen();
	// } else if (whichTest == 1) {
	// testAndTrainProbGen();
	// generateMelody();
	// unitTest3();
	// } else if (whichTest == 2) {
	// // testAndTrainProbGen();
	// generateMelody();
	// unitTest3();
	// } else if (whichTest == 3) {
	// testAndTrainProbGenMar();
	// } else if (whichTest == 4) {
	// // testAndTrainProbGenMar();
	// generateMelodyM();
	// unitTest2();
	// } else if (whichTest == 5) {
	// testAndTrainProbGenMar();
	// generateMelodyM();
	// unitTest2();
	// }

	// }

	public static void testAndTrainProbGen() {
		// declare and test prob gen
		ProbabilityGenerator<Integer> pitchGen = new ProbabilityGenerator<Integer>();
		ProbabilityGenerator<Double> rythemGen = new ProbabilityGenerator<Double>();

		pitchGen.train(midiNotes.getPitchArray());
		rythemGen.train(midiNotes.getRhythmArray());
		// System.out.println("Starting pre print");

		pitchGen.printProbabilityDistribution(false);

		rythemGen.printProbabilityDistribution(false);

	}

	public static void testAndTrainProbGenMar() {
		// declare and test prob gen
		MarkovChainGenerator<Integer> pitchGen = new MarkovChainGenerator<Integer>();
		MarkovChainGenerator<Double> rythemGen = new MarkovChainGenerator<Double>();

		pitchGen.trainM(midiNotes.getPitchArray());
		rythemGen.trainM(midiNotes.getRhythmArray());

		pitchGen.div();
		rythemGen.div();

		// pitchGen.printProbabilityDistribution(false);
		// rythemGen.printProbabilityDistribution(false);

	}

	public static void generateMelody() {
		// generate melody from probability distribution
		ProbabilityGenerator<Integer> pitchGen = new ProbabilityGenerator<>();
		ProbabilityGenerator<Double> rythemGen = new ProbabilityGenerator<>();

		pitchGen.train(midiNotes.getPitchArray());
		rythemGen.train(midiNotes.getRhythmArray());

		ArrayList<Integer> pitches = pitchGen.generate(20);
		ArrayList<Double> rythem = rythemGen.generate(20);

		player.setMelody(pitches);
		player.setRhythm(rythem);

	}

	public static void generateMelodyM() {
		// generate melody from probability distribution
		MarkovChainGenerator<Integer> pitchGen = new MarkovChainGenerator<Integer>();
		MarkovChainGenerator<Double> rythemGen = new MarkovChainGenerator<Double>();

		pitchGen.trainM(midiNotes.getPitchArray());
		rythemGen.trainM(midiNotes.getRhythmArray());

		ArrayList<Integer> pitches = pitchGen.generateM(20);
		ArrayList<Double> rythem = rythemGen.generateM(20);

		// rythemGen.div();
		// rythemGen.printProbabil ityDistribution(true);

		player.setMelody(pitches);
		player.setRhythm(rythem);

		//
	}

	public static void unitTest2() {
		// unit test for markov generator
		MarkovChainGenerator<Integer> melodyGen = new MarkovChainGenerator<Integer>();
		MarkovChainGenerator<Double> rythemGen = new MarkovChainGenerator<Double>();

		// creates prob gen disribution arrays
		MarkovChainGenerator<Integer> probDistGen = new MarkovChainGenerator<Integer>();
		MarkovChainGenerator<Double> probDistGenr = new MarkovChainGenerator<Double>();

		// trains rythem and pitch array
		melodyGen.trainM(midiNotes.getPitchArray());
		rythemGen.trainM(midiNotes.getRhythmArray());

		for (int i = 0; i < 1000; i++) {
			// loops through 10000 different creates songs
			ArrayList<Integer> mel = melodyGen.generateM(20);
			probDistGen.trainM(mel);

			ArrayList<Double> ry = rythemGen.generateM(20);
			probDistGenr.trainM(ry);
		}
		// does the division of the rows
		probDistGen.div();
		probDistGenr.div();

		// prints new distributions
		probDistGen.printProbabilityDistribution(true);
		probDistGenr.printProbabilityDistribution(true);

	}

	public static void unitTest3() {
		// unit test for prob gen I named it wrong oops
		// creates initial arrays for prob gens
		ProbabilityGenerator<Integer> melodyGen = new ProbabilityGenerator<Integer>();
		ProbabilityGenerator<Double> rythemGen = new ProbabilityGenerator<Double>();

		// creates prob gen disribution arrays
		ProbabilityGenerator<Integer> probDistGen = new ProbabilityGenerator<Integer>();
		ProbabilityGenerator<Double> probDistGenr = new ProbabilityGenerator<Double>();

		// trains rythem and pitch array
		melodyGen.train(midiNotes.getPitchArray());
		rythemGen.train(midiNotes.getRhythmArray());

		for (int i = 0; i < 100000; i++) {
			// loops through 10000 different creates songs
			ArrayList<Integer> mel = melodyGen.generate(20);
			probDistGen.train(mel);

			ArrayList<Double> ry = rythemGen.generate(20);
			probDistGenr.train(ry);
		}
		// prints new distributions
		probDistGen.printProbabilityDistribution(true);
		probDistGenr.printProbabilityDistribution(true);
	}

	// plays the midi file using the player -- so sends the midi to an external
	// synth such as Kontakt or a DAW like Ableton or Logic
	static public void playMelody() {

		assert (player != null); // this will throw an error if player is null -- eg. if you haven't called
									// setup() first

		while (!player.atEndOfMelody()) {
			player.play(); // play each note in the sequence -- the player will determine whether is time
							// for a note onset
		}

	}

	// opens the midi file, extracts a voice, then initializes a melody player with
	// that midi voice (e.g. the melody)
	// filePath -- the name of the midi file to play
	static void midiSetup(String filePath) {

		// Change the bus to the relevant port -- if you have named it something
		// different OR you are using Windows
		player = new MelodyPlayer(100, "loopMIDI Port"); // sets up the player with your bus.

		midiNotes = new MidiFileToNotes(filePath); // creates a new MidiFileToNotes -- reminder -- ALL objects in Java
													// must
													// be created with "new". Note how every object is a pointer or
													// reference. Every. single. one.

		// // which line to read in --> this object only reads one line (or ie, voice or
		// ie, one instrument)'s worth of data from the file
		midiNotes.setWhichLine(0); // this assumes the melody is midi channel 0 -- this is usually but not ALWAYS
									// the case, so you can try other channels as well, if 0 is not working out for
									// you.

		noteCount = midiNotes.getPitchArray().size(); // get the number of notes in the midi file

		assert (noteCount > 0); // make sure it got some notes (throw an error to alert you, the coder, if not)

		// sets the player to the melody to play the voice grabbed from the midi file
		// above
		player.setMelody(midiNotes.getPitchArray());
		player.setRhythm(midiNotes.getRhythmArray());
	}

	static void resetMelody() {
		player.reset();

	}

	// this function is not currently called. you may call this from setup() if you
	// want to test
	// this just plays the midi file -- all of it via your software synth. You will
	// not use this function in upcoming projects
	// but it could be a good debug tool.
	// filename -- the name of the midi file to play
	static void playMidiFileDebugTest(String filename) {
		Score theScore = new Score("Temporary score");
		Read.midi(theScore, filename);
		Play.midi(theScore);
	}

}
