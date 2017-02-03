package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Represents a student record. Every student can only have one student record.
 * 
 * @author labramusic
 *
 */
public class StudentRecord {

	/**
	 * Sutdent's JMBAG.
	 */
	private String jmbag;

	/**
	 * Student's last name.
	 */
	private String lastName;

	/**
	 * Student's first name.
	 */
	private String firstName;

	/**
	 * Student's final grade.
	 */
	private int finalGrade;

	/**
	 * Instantiates a new student record.
	 * 
	 * @param jmbag
	 *            student's JMBAG
	 * @param lastName
	 *            student's last name
	 * @param firstName
	 *            student's first name
	 * @param finalGrade
	 *            student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/**
	 * Gets the student's JMBAG.
	 * 
	 * @return JMBAG
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Gets the last name.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the first name.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the final grade.
	 * 
	 * @return the final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

}
