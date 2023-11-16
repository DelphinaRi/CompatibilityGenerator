package com.example;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.example.MarkovChainGenerator.SortTTOutput;

public class MarkovChainGenerator<E> extends ProbabilityGenerator<E> {
	// to declare a 2D ArrayList, initializing transition table
	ArrayList<ArrayList<Float>> transitionTable = new ArrayList<ArrayList<Float>>();
	ArrayList<E> alpha = new ArrayList<E>();

	// ArrayList<Float> tokenCounts = new ArrayList<Float>();
	ArrayList<E> copy = new ArrayList<E>();
	double tokenCount = 0;

	void trainM(ArrayList<E> data) {
		// System.out.println("Starting train");
		// training markov chain generation
		int lastIndex = -1;
		int tokenIndex = 0;
		// temp = data;
		for (int i = 0; i < data.size(); i++) {
			// System.out.print(data.get(i) + " ");
			// get index of the the element in the alphabet array
			tokenIndex = alphabet.indexOf(data.get(i));
			copy.add(data.get(i)); 
			// alpha.add(data.get(i));
			if (tokenIndex == -1) {
				// if the value is not in the data arraay
				tokenIndex = alphabet.size();
				alpha.add(data.get(i));

				// add new row and add to transition table
				ArrayList<Float> myRow = new ArrayList<>();
				for (int j = 0; j < alphabet.size(); j++) {
					float z = 0;
					myRow.add(z);
				}
				transitionTable.add(myRow);

				// add 0 0to all the columns
				for (int j = 0; j < transitionTable.size(); j++) {
					float z = 0;
					transitionTable.get(j).add(z);
				}
				alphabet.add(data.get(i));

			}
			if (lastIndex > -1) {
				// System.out.println("token found: " + data.get(i));
				float x = transitionTable.get(lastIndex).get(tokenIndex);
				x++;
				transitionTable.get(lastIndex).set(tokenIndex, x);
			}
			lastIndex = tokenIndex;
		}

	}

	void div() {
		// add up all the values i neach array and get the decimals
		// int avg = 0; dont really think i need this functtion after all oops
		// printProbabilityDistribution(false, alphabet, transitionTable);

		for (int i = 0; i < transitionTable.size(); i++) {
			int avg = 0;
			for (int j = 0; j < transitionTable.get(i).size(); j++) {
				avg += transitionTable.get(i).get(j);
				// System.out.println(transitionTable.get(i).get(j));

			}
			// System.out.println("average is: "+avg);
			for (int j = 0; j < transitionTable.get(i).size(); j++) {
				if (transitionTable.get(i).get(j) != 0) {
					float newVal = transitionTable.get(i).get(j) / avg;
					transitionTable.get(i).set(j, newVal);
				}
			}
		}
		// printProbabilityDistribution(false, alphabet, transitionTable);

	}



	ArrayList<E> generateM(E initToken, int numberOfTokensToGenerate) {

		initToken = generateToken();
		ArrayList<E> newMelody = new ArrayList<E>();
		for (int i = 0; i < numberOfTokensToGenerate; i++) {
			// gets the row of the init token
			ArrayList<Float> row = transitionTable.get(alpha.indexOf(initToken));
			
			//calls genrate each time 
			generate(1, row, true, copy);

			// gets the 1 token that generate provides and puts it into new melody
			newMelody.add(i, getGen());

			// init token now becomes the note that was just added to new melody
			initToken = newMelody.get(i);
			}


		return newMelody;
	} // this calls the above.

	ArrayList<E> generateM(int numberOfTokensToGenerate) {
		// this generate tokens makes the first init token
		return generateM(generateToken(), numberOfTokensToGenerate);
	} // this calls the above with a random initToken

	E generateToken() {
		// get random starting token from prob gen 
		ProbabilityGenerator<E> d = new ProbabilityGenerator<E>();
		d.train(copy);
		E initToken = d.generate(1).get(0);
		return initToken;
	}

	protected class SortTTOutput {
		public ArrayList<E> symbolsListSorted;
		ArrayList<ArrayList<Float>> ttSorted;
	}

	// sort the symbols list and the counts list, so that we can easily print the
	// probability distribution for testing
	// symbols -- your alphabet or list of symbols (input)
	// tt -- the unsorted transition table (input)
	// symbolsListSorted -- your SORTED alphabet or list of symbols (output)
	// ttSorted -- the transition table that changes reflecting the symbols sorting
	// to remain accurate (output)
	public SortTTOutput sortTT(ArrayList<E> symbols, ArrayList<ArrayList<Float>> tt) {

		SortTTOutput sortArraysOutput = new SortTTOutput();

		sortArraysOutput.symbolsListSorted = new ArrayList<E>(symbols);
		sortArraysOutput.ttSorted = new ArrayList<ArrayList<Float>>();

		// sort the symbols list
		Collections.sort(sortArraysOutput.symbolsListSorted, new Comparator<E>() {
			@Override
			public int compare(E o1, E o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});

		// use the current sorted list to reference the counts and get the sorted counts
		for (int i = 0; i < sortArraysOutput.symbolsListSorted.size(); i++) {
			int index = symbols.indexOf(sortArraysOutput.symbolsListSorted.get(i));
			sortArraysOutput.ttSorted.add(new ArrayList<Float>());

			for (int j = 0; j < tt.get(index).size(); j++) {
				int index2 = symbols.indexOf(sortArraysOutput.symbolsListSorted.get(j));
				sortArraysOutput.ttSorted.get(i).add(tt.get(index).get(index2));

			}
		}

		return sortArraysOutput;

	}

	// this prints the transition table
	// symbols - the alphabet or list of symbols found in the data
	// tt -- the transition table of probabilities (not COUNTS!) for each symbol
	// coming after another
	public void printProbabilityDistribution(boolean round, ArrayList<E> symbols, ArrayList<ArrayList<Float>> tt) {
		// sort the transition table
		SortTTOutput sorted = sortTT(symbols, tt);
		// System.out.println("sorted");
		symbols = sorted.symbolsListSorted;
		tt = sorted.ttSorted;

		System.out.println("-----Transition Table -----");

		System.out.println(symbols);

		for (int i = 0; i < tt.size(); i++) {
			System.out.print("[" + symbols.get(i) + "] ");
			for (int j = 0; j < tt.get(i).size(); j++) {
				if (round) {
					DecimalFormat df = new DecimalFormat("#.##");
					System.out.print(df.format((double) tt.get(i).get(j)) + " ");
				} else {
					System.out.print((double) tt.get(i).get(j) + " ");
				}

			}
			System.out.println();

		}
		System.out.println();

		System.out.println("------------");
	}

	public void printProbabilityDistribution(boolean round) {
		printProbabilityDistribution(round, alphabet, transitionTable);
		
	}

	public static void print2D(ArrayList<ArrayList<Float>> mat) {
		// Loop through all rows
		for (int i = 0; i < mat.size(); i++) {
			// System.out.println(" ");
			// Loop through all elements of current row
			for (int j = 0; j < mat.size(); j++)
				System.out.print(mat.get(i).get(j) + " ");
			System.out.println(" ");

		}
	}
}
