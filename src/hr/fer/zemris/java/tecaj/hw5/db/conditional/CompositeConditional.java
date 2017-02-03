package hr.fer.zemris.java.tecaj.hw5.db.conditional;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Composite conditional collection.
 * 
 * @author labramusic
 *
 */
public class CompositeConditional implements IConditional {

	/**
	 * Collection of conditional expressions.
	 */
	private List<ConditionalExpression> conditionals = new ArrayList<>();

	@Override
	public boolean evaluate(StudentRecord record) {
		for (ConditionalExpression conditional : conditionals) {
			if (!conditional.evaluate(record)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds the conditional expression to the composition.
	 * 
	 * @param conditional
	 *            conditional expression
	 */
	public void add(ConditionalExpression conditional) {
		conditionals.add(conditional);
	}

	/**
	 * Removes the conditional expression from the composition.
	 * 
	 * @param conditional
	 *            conditional expression
	 */
	public void remove(ConditionalExpression conditional) {
		conditionals.remove(conditional);
	}

}
