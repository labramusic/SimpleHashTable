package hr.fer.zemris.java.tecaj.hw5.db.operators;

import java.text.Collator;
import java.util.Locale;

/**
 * Represents a "less than" operator.
 * 
 * @author labramusic
 *
 */
public class LessThanOperator implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		Collator hrCollator = Collator.getInstance(Locale.forLanguageTag("hr"));
		return hrCollator.compare(value1, value2) < 0;
	}

}
