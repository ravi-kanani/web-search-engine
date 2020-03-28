package model;

/**
 * The Class DocFrequency.
 */
public class DocFrequency {

	/** The document id. */
	private int documentId;

	/** The frequency. */
	private double frequency;

	/** The term frequency. */
	private double termFrequency;

	/** The doc length. */
	private double docLength;

	/** The tfidf. */
	private double tfidf;

	/**
	 * Instantiates a new doc frequency.
	 *
	 * @param documentId the document id
	 * @param frequency  the frequency
	 * @param docLength  the doc length
	 */
	public DocFrequency(int documentId, double frequency, double docLength) {
		this.documentId = documentId;
		this.frequency = frequency;
		this.docLength = docLength;
		this.termFrequency = this.frequency / this.docLength;
	}

	/**
	 * Instantiates a new doc frequency.
	 *
	 * @param documentId the document id
	 * @param docLength  the doc length
	 */
	public DocFrequency(int documentId, double docLength) {
		this.documentId = documentId;
		this.frequency = 0;
		this.docLength = docLength;
		this.termFrequency = this.frequency / this.docLength;
	}

	/**
	 * Instantiates a new doc frequency.
	 *
	 * @param documentId the document id
	 */
	public DocFrequency(int documentId) {
		this.documentId = documentId;
		this.frequency = 0;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + documentId;
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocFrequency other = (DocFrequency) obj;
		if (documentId != other.documentId)
			return false;
		return true;
	}

	/**
	 * Gets the document id.
	 *
	 * @return the document id
	 */
	public int getDocumentId() {
		return documentId;
	}

	/**
	 * Gets the frequency.
	 *
	 * @return the frequency
	 */
	public double getFrequency() {
		return frequency;
	}

	/**
	 * Adds the occurrence.
	 */
	public void addOccurrence() {
		this.frequency++;
		this.termFrequency = this.frequency / this.docLength;
	}

	/**
	 * Adds the occurrence.
	 *
	 * @param noOfOccurences the no of occurrences
	 */
	public void addOccurence(double noOfOccurences) {
		this.frequency += noOfOccurences;
		this.termFrequency = this.frequency / this.docLength;
	}

	/**
	 * Gets the term frequency.
	 *
	 * @return the term frequency
	 */
	public double getTermFrequency() {
		return this.termFrequency;
	}

	/**
	 * Gets the tfidf.
	 *
	 * @return the tfidf
	 */
	public double getTfidf() {
		return tfidf;
	}

	/**
	 * Sets the tfidf.
	 *
	 * @param tfidf the new tfidf
	 */
	public void setTfidf(double tfidf) {
		this.tfidf = tfidf;
	}
}
