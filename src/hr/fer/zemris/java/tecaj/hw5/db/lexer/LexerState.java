package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Represents the state in which the lexer operates.
 * 
 * @author labramusic
 *
 */
public enum LexerState {

	/**
	 * A word has been read.
	 */
	WORD,
	/**
	 * A string constant has been read.
	 */
	STRING,
	/**
	 * An operator has been read.
	 */
	OPERATOR,
	/**
	 * Nothing has been read yet.
	 */
	INIT;

}