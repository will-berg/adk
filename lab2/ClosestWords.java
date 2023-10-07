/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;


public class ClosestWords {

	/* Dynamic programming matrix: will consist of the edit distances of substrings of w1 and w2.
	The edit distance between the two input words will then be in the final entry of M. */
	private static final int MAX_WORD_LEN = 40;
	private static final int[][] M = new int[MAX_WORD_LEN][MAX_WORD_LEN];

	LinkedList<String> closestWords = null;
	int closestDistance = -1;


	public int partDist(String w1, String w2, int w1len, int w2len, int i) {
		for (; i < w1len+1; i++) {
			for (int j = 1; j < w2len+1; j++) {
				/* If the new characters match then the edit distance between the substrings is the same as the substrings
				without that character, since the exact same operations can be performed for a match. */
				if (w1.charAt(i-1) == w2.charAt(j-1)) {
					M[i][j] = M[i-1][j-1];
				/* If the characters don't match, then we need to perform an additional operation. */
				} else {
					M[i][j] = Math.min(M[i-1][j], Math.min(M[i-1][j-1], M[i][j-1])) + 1;
				}
			}
		}
		return M[w1len][w2len];
	}


	int distance(String w1, String w2) {
		return partDist(w1, w2, w1.length(), w2.length(), 1);
	}


	// Constructor
	public ClosestWords(String w, List<String> wordList) {
		/* When either substring is empty the number of operations (deletions or insertions) necessary will
		be equal to the amount of characters in the other substring. */
		for (int i = 0; i < MAX_WORD_LEN; i++) M[0][i] = i;
		for (int i = 0; i < MAX_WORD_LEN; i++) M[i][0] = i;

		int dist;
		String sPrev = "";
		// Calculates edit distance between input word w and all of the words in wordList
		for (String s : wordList) {
			if (sPrev != "") {
				int matches = numberOfMatchingChars(sPrev, s);
				int startingRow = 1 + matches;
				dist = partDist(s, w, s.length(), w.length(), startingRow);
			} else {
				dist = distance(s, w);
			}
			sPrev = s;

			// System.out.println("d(" + w + "," + s + ")=" + dist);
			if (dist < closestDistance || closestDistance == -1) {
				closestDistance = dist;
				closestWords = new LinkedList<String>();
				closestWords.add(s);
			}
			else if (dist == closestDistance) {
				closestWords.add(s);
			}
		}
	}


	int numberOfMatchingChars(String w1, String w2) {
		int range = Math.min(w1.length(), w2.length());
		int i;
		for (i = 0; i < range; i++) {
			if (w1.charAt(i) == w2.charAt(i)) continue;
			else break;
		}
		return i;
	}


	int getMinDistance() {
		return closestDistance;
	}


	List<String> getClosestWords() {
		return closestWords;
	}
}
