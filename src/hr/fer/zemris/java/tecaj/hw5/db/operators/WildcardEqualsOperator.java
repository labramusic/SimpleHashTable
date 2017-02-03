package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * Represents an "equals" operator with enabled use of wildcard character "*".
 * 
 * @author labramusic
 *
 */
public class WildcardEqualsOperator implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		if (value2.length() > value1.length()) {
			return false;
		}

		String[] split = value2.split("\\*", 3);
		if (split.length > 2) {
			throw new IllegalArgumentException("String literal cannot contain more than 1 wildcard.");
		}

		if (split.length == 1) {
			if (value2.equals(value1)) {
				return true;
			}

		} else if (value1.startsWith(split[0]) && value1.endsWith(split[1])) {
			return true;
		}
		return false;
	}

}
