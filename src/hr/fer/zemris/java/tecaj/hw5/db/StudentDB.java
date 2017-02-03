package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.tecaj.hw5.db.filters.IFilter;
import hr.fer.zemris.java.tecaj.hw5.db.filters.QueryFilter;
import hr.fer.zemris.java.tecaj.hw5.db.parser.ParserException;

/**
 * Program which demonstrates a console with access to a database of student records.
 * Valid commands are:
 * indexquery - Selects the student record with the given JMBAG.
 * query - Selects all student records which satisfy the given conditional expression.
 * 		   Multiple expressions are allowed by combining them with the "AND" operator.
 * exit - Exits the program.
 * 
 * @author labramusic
 *
 */
public class StudentDB {

	/**
	 * The main method
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Unable to read file!");
			System.exit(1);
		}

		StudentDatabase sdb = new StudentDatabase(lines);

		try (Scanner sc = new Scanner(System.in)) {
			while(true) {
				System.out.print("> ");
				String command = sc.next().trim();
				String query = sc.nextLine().trim();

				if (command.equalsIgnoreCase("exit")) {
					System.out.println("Goodbye!");
					break;

				} else if (command.equals("indexquery")) {
					indexqueryCommand(sdb, query);

				} else if (command.equals("query")) {
					queryCommand(sdb, query);

				} else {
					System.out.println("Unknown command.");
				}
				System.out.println();
			}

		} catch (InputMismatchException e) {
			System.err.println("Error while reading from scanner.");
			System.exit(1);
		}
	}

	/**
	 * Prints the result of the indexquery command on the screen.
	 * @param sdb the student database
	 * @param query query expression
	 */
	private static void indexqueryCommand(StudentDatabase sdb, String query) {
		String[] elems = query.split("=");
		if (elems.length != 2 || !elems[0].trim().equalsIgnoreCase("jmbag") 
				|| !elems[1].trim().matches("\"\\w+\"")) {
			System.out.println("Unable to parse line.");
			System.out.println();
			return;
		}
		String jmbag = elems[1].trim().replaceAll("\"", "").trim();
		List<StudentRecord> record = new ArrayList<>();
		StudentRecord rec = sdb.forJMBAG(jmbag);
		if (rec != null) {
			record.add(rec);
		}
		System.out.println("Using index for record retrieval.");
		print(record);
	}

	/**
	 * Prints the result of the query command on the screen.
	 * @param sdb the student database
	 * @param query query expression
	 */
	private static void queryCommand(StudentDatabase sdb, String query) {
		try {
			IFilter filter = new QueryFilter(query);
			List<StudentRecord> filtered = sdb.filter(filter);
			print(filtered);
		} catch (ParserException|IllegalArgumentException ex) {
			System.out.println("Unable to parse line.");
		}
	}

	/**
	 * Prints a formatted table of student records in the given list.
	 * @param records list of records
	 */
	private static void print(List<StudentRecord> records) {
		if (!records.isEmpty()) {
			int maxJmbag = 0;
			int maxLastName = 0;
			int maxFirstName = 0;
			for (StudentRecord record : records) {
				String jmbag = record.getJmbag();
				String lastName = record.getLastName();
				String firstName = record.getFirstName();

				if (jmbag.length() > maxJmbag) {
					maxJmbag = jmbag.length();
				}
				if (lastName.length() > maxLastName) {
					maxLastName = lastName.length();
				}
				if (firstName.length() > maxFirstName) {
					maxFirstName = firstName.length();
				}
			}

			printBorder(maxJmbag + 2, maxLastName + 2, maxFirstName + 2);
			printValues(records, maxJmbag, maxLastName, maxFirstName);
			printBorder(maxJmbag + 2, maxLastName + 2, maxFirstName + 2);
		}
		System.out.println("Records selected: " + records.size());
	}

	/**
	 * Prints the border of the table.
	 * @param jmbagLen length of border above "jmbag" value
	 * @param lastNameLen length of border above "lastName" value
	 * @param firstNameLen length of border above "firstName" value
	 */
	private static void printBorder(int jmbagLen, int lastNameLen, int firstNameLen) {
		StringBuilder sb = new StringBuilder();
		sb.append("+");
		for (int i = 0; i < jmbagLen; ++i) {
			sb.append("=");
		}
		sb.append("+");
		for (int i = 0; i < lastNameLen; ++i) {
			sb.append("=");
		}
		sb.append("+");
		for (int i = 0; i < firstNameLen; ++i) {
			sb.append("=");
		}
		sb.append("+");
		for (int i = 0; i < 3; ++i) {
			sb.append("=");
		}
		sb.append("+");
		System.out.println(sb.toString());
	}

	/**
	 * Prints the rows of the table which contain student records.
	 * @param records list of student records
	 * @param jmbagLen length of "jmbag" field
	 * @param lastNameLen length of "lastName" field
	 * @param firstNameLen length of "firstName" field
	 */
	private static void printValues(List<StudentRecord> records, int jmbagLen, int lastNameLen, int firstNameLen) {
		for (StudentRecord record : records) {
			StringBuilder sb = new StringBuilder();
			int spaces = 0;

			sb.append("| ");
			sb.append(record.getJmbag());
			spaces = jmbagLen - record.getJmbag().length();
			for (int i = 0; i < spaces; ++i) {
				sb.append(" ");
			}
			sb.append(" |");

			sb.append(" ");
			sb.append(record.getLastName());
			spaces = lastNameLen - record.getLastName().length();
			for (int i = 0; i < spaces; ++i) {
				sb.append(" ");
			}
			sb.append(" |");

			sb.append(" ");
			sb.append(record.getFirstName());
			spaces = firstNameLen - record.getFirstName().length();
			for (int i = 0; i < spaces; ++i) {
				sb.append(" ");
			}
			sb.append(" |");

			sb.append(" ");
			sb.append(record.getFinalGrade());
			sb.append(" |");

			System.out.println(sb.toString());
		}
	}

}
