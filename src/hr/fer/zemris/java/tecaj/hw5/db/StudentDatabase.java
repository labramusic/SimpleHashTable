package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw5.db.filters.IFilter;

/**
 * Represents a database of student records.
 * 
 * @author labramusic
 *
 */
public class StudentDatabase {

	/**
	 * A list of student records.
	 */
	List<StudentRecord> studentRecords;

	/**
	 * Map of student records. The key is a student's JMBAG and the value is
	 * their associated StudentRecord.
	 */
	private SimpleHashtable<String, StudentRecord> table;

	/**
	 * Instantiates a new StudentDatabase. It accepts a list of Strings, each
	 * containing data for one student(JMBAG, last name, first name, final
	 * grade).
	 * 
	 * @param lines
	 *            list of student records as string values
	 */
	public StudentDatabase(List<String> lines) {
		studentRecords = new ArrayList<>();
		table = new SimpleHashtable<>();
		for (String line : lines) {
			if (line == null)
				break;
			line = line.trim();
			if (line.isEmpty())
				continue;
			String[] elements = line.split("\t");
			String jmbag = elements[0];
			String lastName = elements[1];
			String firstName = elements[2];
			int finalGrade = Integer.parseInt(elements[3]);
			StudentRecord record = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			studentRecords.add(record);
			table.put(jmbag, record);
		}
	}

	/**
	 * Obtains requested student record from the student's JMBAG in O(1). If the
	 * student is not in the database, null is returned.
	 * 
	 * @param jmbag
	 *            student's JMBAG
	 * @return student record of the student with the given JMBAG
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return table.get(jmbag);
	}

	/**
	 * Filters the student records list with the given filter and returns the
	 * filtered list.
	 * 
	 * @param filter
	 *            list filter
	 * @return filtered list
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredList = new ArrayList<>();
		for (StudentRecord record : studentRecords) {
			if (filter.accepts(record)) {
				filteredList.add(record);
			}
		}
		return filteredList;
	}

}
