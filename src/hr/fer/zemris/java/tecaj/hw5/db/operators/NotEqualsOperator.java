package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * Represents a "not equals" operator.
 * 
 * @author labramusic
 *
 */
public class NotEqualsOperator implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		return !value1.equals(value2);
	}

}
