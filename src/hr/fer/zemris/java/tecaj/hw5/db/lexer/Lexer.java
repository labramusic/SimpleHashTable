package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * A lexer which can generate tokens on demand.
 * 
 * @author labramusic
 *
 */
public class Lexer {

	/**
	 * Input text.
	 */
	private char[] data;

	/**
	 * The current token.
	 */
	private Token token;

	/**
	 * Index of the first unevaluated character.
	 */
	private int currentIndex;

	/**
	 * Current state of the lexer.
	 */
	private LexerState state;

	/**
	 * Constructor which accepts the input text for tokenization.
	 * 
	 * @param text
	 *            text to be tokenized
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		data = text.toCharArray();
		state = LexerState.INIT;
	}

	/**
	 * Generates and returns the next token.
	 * 
	 * @throws LexerException
	 *             in case of error while generating tokens
	 * @return next token
	 */
	public Token nextToken() throws LexerException {
		if (token != null && token.getType().equals(TokenType.EOL)) {
			throw new LexerException("EOL token already generated.");
		}
		skipWhitespaces();

		if (currentIndex == data.length) {
			token = new Token(TokenType.EOL, null);
			return token;
		}

		StringBuilder buffer = new StringBuilder();
		char ch;

		while (true) {
			if (currentIndex < data.length) {
				ch = data[currentIndex];
			} else {
				if (state.equals(LexerState.STRING)) {
					throw new LexerException("Invalid syntax. String quotes must be closed.");
				}
				ch = '\n';
			}

			switch (state) {
			case WORD:
				Token wordToken = wordState(buffer, ch);
				if (wordToken == null) {
					break;
				}
				return wordToken;

			case STRING:
				Token stringToken = stringState(buffer, ch);
				if (stringToken == null) {
					break;
				}
				return stringToken;

			case OPERATOR:
				return operatorState(buffer, ch);

			case INIT:
				initState(buffer, ch);
				break;
			}

		}
	}

	/**
	 * Skips all whitespaces until the next character in the given text.
	 */
	private void skipWhitespaces() {
		while (currentIndex < data.length) {
			char ch = data[currentIndex];
			if (Character.isWhitespace(ch)) {
				++currentIndex;
				continue;
			}
			break;
		}
	}

	/**
	 * Defines the lexer behavior in init state.
	 * 
	 * @param buffer
	 *            character buffer
	 * @param ch
	 *            current character
	 */
	private void initState(StringBuilder buffer, char ch) {
		if (Character.isLetter(ch)) {
			state = LexerState.WORD;
		} else if (ch == '\"') {
			state = LexerState.STRING;
			++currentIndex;
		} else if (ch == '>' || ch == '<' || ch == '=' || ch == '!') {
			state = LexerState.OPERATOR;
			buffer.append(ch);
			++currentIndex;
		} else {
			throw new LexerException("Invalid character read.");
		}
	}

	/**
	 * Defines the lexer behavior in word state.
	 * 
	 * @param buffer
	 *            character buffer
	 * @param ch
	 *            current character
	 * @return generated token
	 */
	private Token wordState(StringBuilder buffer, char ch) {
		if (Character.isLetter(ch)) {
			buffer.append(ch);
			++currentIndex;
			return null;
		} else {
			state = LexerState.INIT;
			String value = buffer.toString();
			if (value.equalsIgnoreCase("AND")) {
				token = new Token(TokenType.LOGIC_AND, value);
			} else if (value.equals("LIKE")) {
				token = new Token(TokenType.OPERATOR, value);
			} else {
				token = new Token(TokenType.NAME, value);
			}
			return token;
		}
	}

	/**
	 * Defines the lexer behavior in string state.
	 * 
	 * @param buffer
	 *            character buffer
	 * @param ch
	 *            current character
	 * @return generated token
	 */
	private Token stringState(StringBuilder buffer, char ch) {
		if (ch == '\"') {
			if (buffer.length() == 0) {
				throw new LexerException("String cannot be empty.");
			}
			state = LexerState.INIT;
			++currentIndex;
			token = new Token(TokenType.STRING, buffer.toString());
			return token;
		} else {
			buffer.append(ch);
			++currentIndex;
			return null;
		}
	}

	/**
	 * Defines the lexer behavior in operator state.
	 * 
	 * @param buffer
	 *            character buffer
	 * @param ch
	 *            current character
	 * @return generated token
	 */
	private Token operatorState(StringBuilder buffer, char ch) {
		char symbol = buffer.charAt(0);
		if ((symbol == '>' || symbol == '<' || symbol == '!') && ch == '=') {
			buffer.append(ch);
			++currentIndex;
		}
		state = LexerState.INIT;
		token = new Token(TokenType.OPERATOR, buffer.toString());
		return token;
	}

	/**
	 * Returns the previously generated token, without generating the next one.
	 * 
	 * @return previously generated token
	 */
	public Token getToken() {
		return token;
	}

}
