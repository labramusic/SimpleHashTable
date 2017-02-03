package hr.fer.zemris.java.tecaj.hw5.db.conditional;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Represents a conditional expression evaluator component.
 * 
 * @author labramusic
 *
 */
public interface IConditional {

	/**
	 * Returns true if the student record satisfies the conditional expression.
	 * 
	 * @param record
	 *            student record being evaluated
	 * @return true if record satisfies the conditional expression
	 */
	public boolean evaluate(StudentRecord record);

}
