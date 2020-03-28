package functionality;

/**
 * The Class SpellChecker.
 */
public class SpellChecker {
	Indexer indexer = Indexer.getInstance();

	/**
	 * Spell check.
	 *
	 * @param query the query
	 * @return the string
	 */
	public String spellCheck(String query) {
		String[] tokens = query.split(" ");
		String finalQuery = "";

		for (String token : tokens) {
			if (indexer.getIndexedTerms().contains(token)) {
				finalQuery += token + " ";
			} else {
				for (String key : indexer.getIndexedTerms().keys()) {
					if (editDistance(token, key) < 2) {
						finalQuery += key + " ";
						break;
					}
				}
			}

		}

		return finalQuery.trim().length() > 0 ? finalQuery : query;
	}

	/**
	 * Edits the distance.
	 *
	 * @param word1 the word 1
	 * @param word2 the word 2
	 * @return the int
	 */
	public static int editDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();

		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}

		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);

				// if last two chars equal
				if (c1 == c2) {
					// update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}

		return dp[len1][len2];
	}

}
