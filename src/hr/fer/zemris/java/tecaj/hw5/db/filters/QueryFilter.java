package hr.fer.zemris.java.tecaj.hw5.db.filters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.conditional.CompositeConditional;
import hr.fer.zemris.java.tecaj.hw5.db.parser.Parser;

/**
 * Filter of student records which filters based on the given query.
 * 
 * @author labramusic
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * Composite conditional expression.
	 */
	private CompositeConditional composite;

	/**
	 * Instantiates a new QueryFilter from the given query (everything the user
	 * entered after the "query" keyword)
	 * 
	 * @param query
	 *            given query
	 */
	public QueryFilter(String query) {
		Parser parser = new Parser(query);
		composite = parser.getComposite();

	}

	@Override
	public boolean accepts(StudentRecord record) {
		return composite.evaluate(record);
	}

}
