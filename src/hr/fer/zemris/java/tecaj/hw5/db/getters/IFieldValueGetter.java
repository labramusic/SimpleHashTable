package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Represents a strategy responsible for obtaining a requested field value from
 * the given student record.
 * 
 * @author labramusic
 *
 */
public interface IFieldValueGetter {

	/**
	 * Gets the requested field value from the given StudentRecord.
	 * 
	 * @param record
	 *            a student record
	 * @return requested field value
	 */
	public String get(StudentRecord record);

}
