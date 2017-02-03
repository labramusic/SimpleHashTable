package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Enumeration which lists possible token types.
 * 
 * @author labramusic
 *
 */
public enum TokenType {

	/**
	 * End of line token.
	 */
	EOL,
	/**
	 * Attribute name.
	 */
	NAME,
	/**
	 * String literal.
	 */
	STRING,
	/**
	 * Valid operators are arithmetic comparison operators and LIKE(wildcard equals).
	 */
	OPERATOR,
	/**
	 * Logical 'AND' operator which enables multiple conditions.
	 */
	LOGIC_AND;

}
