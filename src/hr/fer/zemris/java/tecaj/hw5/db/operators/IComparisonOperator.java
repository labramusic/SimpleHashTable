package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * Represents a comparison operator strategy.
 * 
 * @author labramusic
 *
 */
public interface IComparisonOperator {

	/**
	 * Returns true if the comparison between two string literals is satisfied.
	 * 
	 * @param value1
	 *            string literal value of left side of the operator
	 * @param value2
	 *            string literal value of right side of the operator
	 * @return true if comparison is satisfied
	 */
	public boolean satisfied(String value1, String value2);

}
