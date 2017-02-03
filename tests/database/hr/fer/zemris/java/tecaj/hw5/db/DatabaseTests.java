package hr.fer.zemris.java.tecaj.hw5.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.db.filters.IFilter;
import hr.fer.zemris.java.tecaj.hw5.db.filters.QueryFilter;

public class DatabaseTests {

	StudentDatabase sdb;

	@Before
	public void initialize() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Unable to read file!");
			System.exit(1);
		}
		sdb = new StudentDatabase(lines);
	}

	@Test
	public void testIndexquery() {
		String query = "jmbag=\"0000000003\"";
		StudentRecord rec = sdb.forJMBAG(query.split("=")[1].trim().replaceAll("\"", "").trim());

		assertEquals(new StudentRecord("0000000003", "Bosnić", "Andrea", 3), rec);
	}

	@Test
	public void testQueryAnd() {
		String query = "firstName>\"Ao\" and lastName LIKE \"B*ć\"";
		IFilter filter = new QueryFilter(query);
		List<StudentRecord> filtered = sdb.filter(filter);

		assertTrue(filtered.contains(new StudentRecord("0000000002", "Bakamović", "Petra", 3)));
		assertTrue(filtered.contains(new StudentRecord("0000000004", "Božić", "Marin", 5)));
		assertTrue(filtered.contains(new StudentRecord("0000000005", "Brezović", "Jusufadis", 2)));
		assertEquals(3, filtered.size());
	}

	@Test
	public void testQueryLikeWildcard() {
		String query = "firstName LIKE \"M*\" aNd lastName LIKE \"*ć\"and jmbag< \"0000000010\"";
		IFilter filter = new QueryFilter(query);
		List<StudentRecord> filtered = sdb.filter(filter);

		assertTrue(filtered.contains(new StudentRecord("0000000001", "Akšamović", "Marin", 2)));
		assertTrue(filtered.contains(new StudentRecord("0000000004", "Božić", "Marin", 5)));
		assertTrue(filtered.contains(new StudentRecord("0000000008", "Ćurić", "Marko", 5)));
		assertEquals(3, filtered.size());
	}

	@Test
	public void testQueryLikeDoubleWildcard() {
		String query = "lastName LIKE \"*ć\" and lastName LIKE \"K*\"";
		IFilter filter = new QueryFilter(query);
		List<StudentRecord> filtered = sdb.filter(filter);

		assertTrue(filtered.contains(new StudentRecord("0000000024", "Karlović", "Đive", 5)));
		assertTrue(filtered.contains(new StudentRecord("0000000025", "Katanić", "Dino", 2)));
		assertTrue(filtered.contains(new StudentRecord("0000000026", "Katunarić", "Zoran", 3)));
		assertTrue(filtered.contains(new StudentRecord("0000000028", "Kosanović", "Nenad", 5)));
		assertTrue(filtered.contains(new StudentRecord("0000000030", "Kovačević", "Boris", 3)));
		assertEquals(5, filtered.size());
	}

	@Test
	public void testQueryBetween() {
		String query = "jmbag >= \"0000000051\" and jmbag < \"0000000054\"";
		IFilter filter = new QueryFilter(query);
		List<StudentRecord> filtered = sdb.filter(filter);

		assertTrue(filtered.contains(new StudentRecord("0000000051", "Skočir", "Andro", 4)));
		assertTrue(filtered.contains(new StudentRecord("0000000052", "Slijepčević", "Josip", 5)));
		assertTrue(filtered.contains(new StudentRecord("0000000053", "Srdarević", "Dario", 2)));
		assertEquals(3, filtered.size());
	}

	@Test
	public void testQueryEquals() {
		String query = "jmbag=\"0000000031\"";
		IFilter filter = new QueryFilter(query);
		List<StudentRecord> filtered = sdb.filter(filter);

		assertTrue(filtered.contains(new StudentRecord("0000000031", "Krušelj Posavec", "Bojan", 4)));
		assertEquals(1, filtered.size());
	}

}
