package hr.fer.zemris.java.tecaj.hw5.db.parser;

/**
 * Exception thrown in case of error while parsing a text document.
 * 
 * @author labramusic
 *
 */
public class ParserException extends RuntimeException {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor which initializes a new ParserException.
	 */
	public ParserException() {

	}

	/**
	 * Constructor which delegates the given message to the super class.
	 * 
	 * @param message
	 *            error message
	 */
	public ParserException(String message) {
		super(message);
	}

}
