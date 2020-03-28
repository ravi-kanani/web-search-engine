package model;

/**
 * The Class DocInfo.
 */
public class DocInfo {

	/** The doc title. */
	private String docTitle;

	/** The doc link. */
	private String docLink;

	/**
	 * Instantiates a new doc info.
	 *
	 * @param title   the title
	 * @param docLink the doc link
	 */
	public DocInfo(String title, String docLink) {
		this.docTitle = title;
		this.docLink = docLink;
	}

	/**
	 * Gets the doc title.
	 *
	 * @return the doc title
	 */
	public String getDocTitle() {
		return docTitle;
	}

	/**
	 * Gets the doc link.
	 *
	 * @return the doc link
	 */
	public String getDocLink() {
		return docLink;
	}

}
