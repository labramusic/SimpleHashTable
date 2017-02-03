package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Exception thrown in case of error while generating a new lexic token.
 * 
 * @author labramusic
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor which initializes a new LexerException.
	 */
	public LexerException() {

	}

	/**
	 * Constructor which delegates the given message to the super class.
	 * 
	 * @param message
	 *            error message
	 */
	public LexerException(String message) {
		super(message);
	}

}