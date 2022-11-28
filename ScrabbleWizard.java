import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Collections;

public class ScrabbleWizard {
	private static String letters;
	private static ArrayList<String> words;
	private static ArrayList<String> tempList;

	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<Character> seq = new ArrayList<Character>();
		ArrayList<Character> univ = new ArrayList<Character>();
		words = readWords();

		Scanner in = new Scanner(System.in);

		System.out.print("Scrabble Wizard 2 - Tiffany Kawamura: ");
		letters = in.next().toLowerCase();
		addToLetters(letters, univ);

		TestClass<Character> tc = new TestClass<Character>();

		for (int l = 2; l <= letters.length(); l++) {
			PuzzleSolve.<Character>solve(l, seq, univ, tc);
			//if (l==letters.length()) {
				//System.out.printf("Best %d-letter word: %s, score %d\n", l, tc.getBestWord(), tc.getBestScore());
				//System.out.println("Best "+l+"-letter word: "+ tc.getList());
				tempList = tc.getList();
				for (int i = 0; i<tempList.size(); i++) {
					String item = tempList.get(i);
					int itemLength = item.length();
					if (itemLength==letters.length()) 
						System.out.printf("%s\n",item);
				}
			//}
			tc.reset();
		}
		
		in.close();
	}

	private static class TestClass<C> implements PuzzleTest<C> {
		private static ArrayList<String> list = new ArrayList<String>();
		private String bestWord = "";
		private int bestScore = -1;
		private static int[] letterScore = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4,
				10 };
		private StringBuilder builder;

		public boolean test(ArrayList<C> candidate) {
			return true;
		}

		public void foundSolution(ArrayList<C> solution) {
			int score = calcScore(solution);
			if (score > 0) {
				bestWord = builder.toString();
				list.add(bestWord); //add all the other words??
				if (score > bestScore) {
					bestScore = score;
					bestWord = builder.toString();
				}
			}
		}

		public int calcScore(ArrayList<C> solution) {
			int score = 0;

			builder = new StringBuilder(solution.size());
			for (C ch : solution) {
				builder.append(ch);
				score += val(ch);
			}
			if (Collections.binarySearch(words, builder.toString()) > 0) {
				return score;
			}
			return 0;
		}

		private int val(C c) {
			Character ch = (Character) c;
			return letterScore[(int) ch.charValue() - 97];
		}

		public String getBestWord() {
			return bestWord;
		}
		
		public ArrayList<String> getList() {
			return list;
		}

		public int getBestScore() {
			return bestScore;
		}

		public void reset() {
			bestWord = "";
			bestScore = -1;
		}
	}

	private static void addToLetters(String n, ArrayList<Character> letters) {
		for (int i = 0; i < n.length(); i++) {
			letters.add(n.charAt(i));
		}
	}

	private static ArrayList<String> readWords() throws FileNotFoundException {
		ArrayList<String> words = new ArrayList<String>();

		File inputFile = new File("words.txt");
		Scanner in = new Scanner(inputFile);
		while (in.hasNext()) {
			words.add(in.next());
		}
		Collections.sort(words);

		in.close();

		return words;
	}
}