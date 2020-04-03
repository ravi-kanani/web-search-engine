import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import functionality.Indexer;
import functionality.SpellChecker;
import model.DocRank;
import model.SearchResults;

/**
 * The Class WebSearchEngineApplication.
 */
public class WebSearchEngineApplication {

	static Indexer indexer = Indexer.getInstance();
	static SpellChecker spellChecker = new SpellChecker();

	/**
	 * Initialize crawler and indexer.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void initializeCrawlerAndIndexer() throws IOException {
		indexer.startIndexer();
	}

	/**
	 * Gets the search results.
	 *
	 * @param query the query
	 * @return the search results
	 * @throws IOException
	 */
	private static SearchResults getSearchResults(String query) throws IOException {
		String finalQuery = spellChecker.spellCheck(query);
		SearchResults searchResults = new SearchResults();
		searchResults.setQuery(finalQuery.trim());
		double startTime = System.nanoTime();
		searchResults.setResults(indexer.getFilteredDocuments(finalQuery));
		searchResults.setTimeTaken((System.nanoTime() - startTime) / 1000000.0);
		return searchResults;
	}

	/**
	 * Gets the suggestions.
	 *
	 * @param query the query
	 * @return the suggestions
	 */
	private static List<String> getSuggestions(String query) {

		String[] tokens = query.split(" ");
		String suggestedQueryPrefix = "";
		if (tokens.length > 1)
			suggestedQueryPrefix = tokens[0];

		ArrayList<String> array = new ArrayList<String>();
		for (String key : indexer.getIndexedTerms().keys()) {
			if (key.startsWith(tokens[tokens.length - 1])) {
				array.add(suggestedQueryPrefix + " " + key);
			}
		}
		return array;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		initializeCrawlerAndIndexer();
		Scanner s = new Scanner(System.in);
		String choice = "y";
		do {
			System.out.print("Please enter query to search: ");
			String query = s.nextLine();
			System.out.println();
			SearchResults result = getSearchResults(query);
			if (!query.equalsIgnoreCase(result.getQuery())) {
				System.out.println("Showing results for '" + result.getQuery() + "' instead of '" + query + "'");
			}
			List<String> suggestions = getSuggestions(result.getQuery());
			suggestions.remove(0);
			suggestions.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));
			System.out.print("You may also try: ");

			for (String word : suggestions) {
				System.out.print(word + ", ");
			}
			System.out.println("\nAbout " + result.getResults().size() + " results ("
					+ String.format("%.5f", result.getTimeTaken() / 1000.0) + " seconds)");
			if (result.getResults().size() > 10)
				System.out.println("\nTop 10 results...");
			int count = 0;
			for (DocRank res : result.getResults()) {
				if (++count > 10)
					break;
				System.out.println(count + "\t" + res.getDocTitle());
				System.out.println("\t" + res.getDocLink());
				System.out.println("\ttf-idf: " + res.getTfIdf());
				System.out.println();
			}
			System.out.print("Do you want to search?(y/n): ");
			choice = s.nextLine();
		} while (!choice.equalsIgnoreCase("n"));
		System.out.println("Bye. Have a good day!");
		s.close();
	}

}
