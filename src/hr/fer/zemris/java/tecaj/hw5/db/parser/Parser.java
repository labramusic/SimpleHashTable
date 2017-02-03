package hr.fer.zemris.java.tecaj.hw5.db.parser;

import hr.fer.zemris.java.tecaj.hw5.db.conditional.CompositeConditional;
import hr.fer.zemris.java.tecaj.hw5.db.conditional.ConditionalExpression;
import hr.fer.zemris.java.tecaj.hw5.db.getters.FirstNameGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.JmbagGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.LastNameGetter;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.Lexer;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.LexerException;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.Token;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.TokenType;
import hr.fer.zemris.java.tecaj.hw5.db.operators.EqualsOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.GreaterThanEqualsOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.GreaterThanOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessThanEqualsOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessThanOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.NotEqualsOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.WildcardEqualsOperator;

/**
 * Parser which constructs a composite conditional expression of
 * conditionals constructed from lexic tokens which are generated
 * from a query.
 * @author labramusic
 *
 */
public class Parser {

	/**
	 * The lexer.
	 */
	private Lexer lexer;

	/**
	 * Composite conditional expression.
	 */
	private CompositeConditional composite;

	/**
	 * Initializes a new Parser with the given query and parses it.
	 * @param query line containing query
	 */
	public Parser(String query) {
		composite = new CompositeConditional();
		lexer = new Lexer(query);
		parse();
	}

	/**
	 * Parses the line and sets the conditional expression from the given tokens.
	 */
	private void parse() {
		try {
			while(true) {
				IFieldValueGetter fieldGetter = getField();
				IComparisonOperator comparisonOperator = getOperator();
				String stringLiteral = getString();
				ConditionalExpression conditional = new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator);
				composite.add(conditional);

				lexer.nextToken();
				if (lexer.getToken().getType().equals(TokenType.EOL)) {
					break;
				} else if (!lexer.getToken().getType().equals(TokenType.LOGIC_AND)) {
					throw new ParserException("Invalid syntax.");
				}
			}

		} catch (LexerException ex) {
			throw new ParserException();
		}
	}

	/**
	 * Returns the field getter type.
	 * @return field type
	 */
	private IFieldValueGetter getField() {
		lexer.nextToken();
		Token token = lexer.getToken();
		if (!token.getType().equals(TokenType.NAME)) {
			throw new ParserException("Invalid Syntax. Attribute expected.");
		}
		String attribute = token.getValue();
		return fieldType(attribute);
	}

	/**
	 * Determines the field getter type and returns it.
	 * @param attribute field value
	 * @return field type
	 */
	private static IFieldValueGetter fieldType(String attribute) {
		if (attribute.equals("firstName")) {
			return new FirstNameGetter();
		} else if (attribute.equals("lastName")) {
			return new LastNameGetter();
		} else if (attribute.equals("jmbag")) {
			return new JmbagGetter();
		} else {
			throw new ParserException("Invalid attribute name.");
		}
	}

	/**
	 * Returns the operator type.
	 * @return operator type
	 */
	private IComparisonOperator getOperator() {
		lexer.nextToken();
		Token token = lexer.getToken();
		if (!token.getType().equals(TokenType.OPERATOR)) {
			throw new ParserException("Invalid Syntax. Operator expected.");
		}
		String operator = token.getValue();
		return operatorType(operator);
	}

	/**
	 * Determines the type of the operator and returns it.
	 * @param operator operator value
	 * @return operator type
	 */
	private static IComparisonOperator operatorType(String operator) {
		if (operator.equals("<")) {
			return new LessThanOperator();
		} else if (operator.equals("<=")) {
			return new LessThanEqualsOperator();
		} else if (operator.equals(">")) {
			return new GreaterThanOperator();
		} else if (operator.equals(">=")) {
			return new GreaterThanEqualsOperator();
		} else if (operator.equals("=")) {
			return new EqualsOperator();
		} else if (operator.equals("!=")) {
			return new NotEqualsOperator();
		} else if (operator.equals("LIKE")) {
			return new WildcardEqualsOperator();
		} else {
			throw new ParserException("Invalid operator.");
		}
	}

	/**
	 * Returns the string value.
	 * @return string token
	 */
	private String getString() {
		lexer.nextToken();
		Token token = lexer.getToken();
		if (!token.getType().equals(TokenType.STRING)) {
			throw new ParserException("Invalid Syntax. String literal expected.");
		}
		return token.getValue();
	}

	/**
	 * Gets the composite conditional.
	 * @return composite conditional
	 */
	public CompositeConditional getComposite() {
		return composite;
	}

}
