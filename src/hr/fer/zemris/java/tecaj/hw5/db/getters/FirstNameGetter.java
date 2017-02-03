package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Implements a student's first name getter from their student record.
 * 
 * @author labramusic
 *
 */
public class FirstNameGetter implements IFieldValueGetter {

	@Override
	public String get(StudentRecord record) {
		return record.getFirstName();
	}
}
