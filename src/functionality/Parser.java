package functionality;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import model.Path;

public class Parser {

	private static List<String> stopWords = new ArrayList<String>();
	private static List<Document> webPageListLocal = new ArrayList<Document>();

	public static List<Document> getWebPageListLocal() {
		if (webPageListLocal.isEmpty()) {
			generateDocuments();
		}
		return webPageListLocal;
	}

	public static void generateDocuments() {
		try {
			File directory = new File(Path.htmlDirectoryPath);
			File[] f1 = directory.listFiles();
			for (int i = 0; i < Path.pagesToCrawl && i < f1.length; i++) {
				System.out.println(i + " " + f1[i].getName());
				if (f1[i].isFile()) {
					Document doc = Jsoup.parse(f1[i], "UTF-8");
					webPageListLocal.add(doc);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}

	public static void buildStopWordsList() {
		stopWords.add("a");
		stopWords.add("the");
		stopWords.add("and");
		stopWords.add("are");
		stopWords.add("an");
		stopWords.add("as");
		stopWords.add("at");
		stopWords.add("be");
		stopWords.add("by");
		stopWords.add("for");
		stopWords.add("from");
		stopWords.add("has");
		stopWords.add("he");
		stopWords.add("is");
		stopWords.add("its");
		stopWords.add("of");
		stopWords.add("on");
		stopWords.add("it");
		stopWords.add("that");
		stopWords.add("was");
		stopWords.add("to");
		stopWords.add("were");
		stopWords.add("will");
		stopWords.add("with");

	}

	// removing the stop words from the tokens
	public static void removeStopWords(List<String> inputList) {
		inputList.removeIf(ip -> stopWords.contains(ip));
	}

	public static List<String> parse(Document doc) throws IOException {
		List<String> tokenList = new ArrayList<String>();
		if (stopWords.isEmpty())
			buildStopWordsList();

		String bodyTextStr = new String(doc.body().text().toLowerCase());
		String[] tokens = bodyTextStr.toString().split("[^a-zA-Z0-9'-]");
		for (String token : tokens)
			tokenList.add(token);
		removeStopWords(tokenList);
		return tokenList;
	}

	public static void saveDoc(Document doc) {
		try {
			BufferedWriter html = new BufferedWriter(
					new FileWriter(Path.htmlDirectoryPath + doc.title().replace('/', '-') + ".html"));
			html.write(doc.toString());
			html.close();

			BufferedWriter txt = new BufferedWriter(
					new FileWriter(Path.txtDirectoryPath + doc.title().replace('/', '-') + ".txt"));
			txt.write(doc.body().text().toLowerCase());
			txt.close();

		} catch (Exception e) {
			System.out.println(
					"Exception in saving file: " + Path.txtDirectoryPath + doc.title().replace('/', '-') + ".txt");
		}
	}
}
