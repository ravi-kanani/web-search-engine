package functionality;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	private Set<String> pagesCrawledList = new HashSet<String>();
	private List<Document> webPageList = new ArrayList<Document>();

	public List<Document> getWebPageList() {
		return webPageList;
	}

	public void setWebPageList(List<Document> webPageList) {
		this.webPageList = webPageList;
	}

	private int maxDepth = 3;

	public void crawl() {
		String[] urls = { "https://www.forbes.com/sites/robertadams/2017/03/02/top-income-earning-blogs",
				"https://firstsiteguide.com/examples-of-blogs",
				"https://www.lifehack.org/articles/communication/top-10-most-inspirational-bloggers-the-world.html",
				"https://www.theguardian.com/technology/2008/mar/09/blogs",
				"https://www.rankxl.com/examples-successful-blogs", "https://makeawebsitehub.com/examples-of-blogs",
				"https://detailed.com/50", "https://www.sparringmind.com/successful-blogs",
				"https://www.wpbeginner.com/beginners-guide/how-to-choose-the-best-blogging-platform",
				"https://www.bluleadz.com/blog/8-of-the-most-interesting-blogs-out-there" };
		for (String url : urls) {
			startCrawler(url, 0);
		}
	}

	/**
	 * This method search for links in the web page and stores html in list
	 * 
	 * @param url   [web page url to be searched for urls]
	 * @param depth [depth of the urls to be crawled]
	 * @throws IOException
	 */
	public void startCrawler(String url, int depth) {
		if (depth <= maxDepth) {
			try {
				Document document = Jsoup.connect(url).get();
				Parser.saveDoc(document);
				webPageList.add(document);
				depth++;
				if (depth < maxDepth) {
					Elements links = document.select("a[href]");
					for (Element page : links) {
						if (shouldCrawlUrl(page.attr("abs:href"))) {
							System.out.println(webPageList.size() + page.attr("abs:href"));
							startCrawler(page.attr("abs:href"), depth);
							pagesCrawledList.add(page.attr("abs:href"));
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Error fetching url - " + url);
			}
		}
	}

	private boolean shouldCrawlUrl(String nextUrl) {
		if (this.pagesCrawledList.contains(nextUrl)) {
			return false;
		}
		if (nextUrl.startsWith("javascript:")) {
			return false;
		}
		if (nextUrl.contains("mailto:")) {
			return false;
		}
		if (nextUrl.contains("#") || nextUrl.contains("?")) {
			return false;
		}
		if (nextUrl.endsWith(".swf")) {
			return false;
		}
		if (nextUrl.endsWith(".pdf")) {
			return false;
		}
		if (nextUrl.endsWith(".png")) {
			return false;
		}
		if (nextUrl.endsWith(".gif")) {
			return false;
		}
		if (nextUrl.endsWith(".jpg")) {
			return false;
		}
		if (nextUrl.endsWith(".jpeg")) {
			return false;
		}
		return true;
	}
}
