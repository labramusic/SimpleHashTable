package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * A token generated by the lexer, defined by its type and value.
 * 
 * @author labramusic
 *
 */
public class Token {

	/**
	 * Token value;
	 */
	private String value;

	/**
	 * Token type.
	 */
	private TokenType type;

	/**
	 * Constructor which instantiates a new Token with its type and value.
	 * 
	 * @param type
	 *            token type
	 * @param value
	 *            token value
	 */
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns the value of the token.
	 * 
	 * @return token value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns the type of the token.
	 * 
	 * @return token type
	 */
	public TokenType getType() {
		return type;
	}

}
