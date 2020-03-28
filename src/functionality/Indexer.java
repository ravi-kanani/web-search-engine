package functionality;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.nodes.Document;

import model.DocFrequency;
import model.DocInfo;
import model.DocRank;
import textprocessing.TST;

public class Indexer {

	Crawler crawler = new Crawler();

	private static final Logger logger = Logger.getLogger(Indexer.class.getName());

	private TST<List<DocFrequency>> indexedTerms = new TST<List<DocFrequency>>();
	private HashMap<Integer, DocInfo> documentIdNameMap = new HashMap<Integer, DocInfo>();
	String stemmedWord = "";
	int id = 0;

	private static Indexer indexer;

	private Indexer() {
	}

	public static Indexer getInstance() {
		if (indexer == null) {
			indexer = new Indexer();
		}
		return indexer;
	}

	public void indexDocuments() throws IOException {
		if (crawler.getWebPageList().size() <= 0) {
			String[] urls = { "https://colorlib.com/wp/",
					"https://www.forbes.com/sites/robertadams/2017/03/02/top-income-earning-blogs",
					"https://firstsiteguide.com/examples-of-blogs",
					"https://www.lifehack.org/articles/communication/top-10-most-inspirational-bloggers-the-world.html",
					"https://www.theguardian.com/technology/2008/mar/09/blogs",
					"https://www.rankxl.com/examples-successful-blogs", "https://makeawebsitehub.com/examples-of-blogs",
					"https://detailed.com/50", "https://www.sparringmind.com/successful-blogs",
					"https://www.wpbeginner.com/beginners-guide/how-to-choose-the-best-blogging-platform",
					"https://www.bluleadz.com/blog/8-of-the-most-interesting-blogs-out-there" };
			logger.log(Level.INFO, "Crawling Started");
			for (int i = 0; i < urls.length; i++) {
//				crawler.startCrawler(urls[i], 0);
			}
			logger.log(Level.INFO, "Pages Crawled:" + crawler.getWebPageList().size());
		}

		List<String> tokenWords;
		logger.log(Level.INFO, "Indexing Started");
//		for (Document doc : crawler.getWebPageList()) {
		for (Document doc : Parser.getWebPageListLocal()) {
			double documentLength = doc.body().text().toLowerCase().split("[^a-zA-Z0-9'-]").length;
			documentIdNameMap.put(id, new DocInfo(doc.head().getElementsByTag("title").text(), doc.baseUri()));
			tokenWords = Parser.parse(doc);

			tokenWords.stream().filter(word1 -> word1.trim().length() > 1 || word1.length() > 1).forEach(word -> {
				stemmedWord = word;
				if (null == indexedTerms.get(stemmedWord)) {
					indexedTerms.put(stemmedWord, new ArrayList<DocFrequency>());
					indexedTerms.get(stemmedWord).add(new DocFrequency(id, 1, documentLength));
				} else {
					List<DocFrequency> docList = indexedTerms.get(stemmedWord);
					if (docList.contains(new DocFrequency(id))) {
						DocFrequency docFreqObj = docList.get(docList.indexOf((new DocFrequency(id, documentLength))));
						docFreqObj.addOccurrence();
					} else {
						DocFrequency newDoc = new DocFrequency(id, documentLength);
						newDoc.addOccurrence();
						docList.add(newDoc);
					}
				}
			});
			id++;
		}
		logger.log(Level.INFO, "Indexing Completed");
	}

	public List<DocRank> tfIdf(String term) {
		List<DocRank> docRankList = new ArrayList<DocRank>();
		if (indexedTerms.get(term) != null) {
			double docListLength = indexedTerms.get(term).size();
			for (DocFrequency doc : indexedTerms.get(term)) {
				docRankList
						.add(new DocRank(doc.getDocumentId(), documentIdNameMap.get(doc.getDocumentId()).getDocTitle(),
								documentIdNameMap.get(doc.getDocumentId()).getDocLink(),
								doc.getTermFrequency() * (docListLength / 100)));
			}
		}
		return docRankList;
	}

	public List<DocRank> getFilteredDocuments(String query) {
		String[] queryTokens = query.split(" ");

		List<DocRank> filteredDocumentsList = tfIdf(queryTokens[0]);
		for (int i = 1; i < queryTokens.length; i++) {
			for (DocRank doc : tfIdf(queryTokens[i])) {
				if (filteredDocumentsList.contains(doc)) {
					filteredDocumentsList.get(filteredDocumentsList.indexOf(doc)).addTfIdf(doc.getTfIdf());
				}
			}
		}
		filteredDocumentsList.sort((c1, c2) -> Double.compare(c2.getTfIdf(), c1.getTfIdf()));
		return filteredDocumentsList;
	}

	public TST<List<DocFrequency>> getIndexedTerms() {
		return indexedTerms;
	}

}
