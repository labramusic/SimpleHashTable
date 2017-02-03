package hr.fer.zemris.java.tecaj.hw5.db.filters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Models a filter of student records.
 * 
 * @author labramusic
 *
 */
public interface IFilter {

	/**
	 * Returns true if the filter accepts the student record.
	 * 
	 * @param record
	 *            student record to be filtered
	 * @return true if record can be filtered through the list
	 */
	public boolean accepts(StudentRecord record);

}
