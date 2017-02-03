package hr.fer.zemris.java.tecaj.hw5.db.conditional;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.getters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;

/**
 * Models a conditional expression.
 * 
 * @author labramusic
 *
 */
public class ConditionalExpression implements IConditional {

	/** The field getter. */
	IFieldValueGetter fieldGetter;

	/** The string literal. */
	String stringLiteral;

	/** The comparison operator. */
	IComparisonOperator comparisonOperator;

	/**
	 * Instantiates a new conditional expression.
	 *
	 * @param fieldGetter
	 *            the field getter
	 * @param stringLiteral
	 *            the string literal
	 * @param comparisonOperator
	 *            the comparison operator
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	@Override
	public boolean evaluate(StudentRecord record) {
		return comparisonOperator.satisfied(fieldGetter.get(record), stringLiteral);
	}

	/**
	 * Gets the field getter.
	 *
	 * @return the field getter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Gets the string literal.
	 *
	 * @return the string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Gets the comparison operator.
	 *
	 * @return the comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
