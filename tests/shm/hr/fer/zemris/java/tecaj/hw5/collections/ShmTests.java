package hr.fer.zemris.java.tecaj.hw5.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Ignore;
import org.junit.Test;

public class ShmTests {

	@Test
	public void testGet() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		examMarks.put("Jasna", 3);
		examMarks.put("Luka", 5);
		examMarks.put("Marin", 5);
		examMarks.put("Josip", 4);

		Integer jasnaGrade = examMarks.get("Jasna");
		Integer josipGrade = examMarks.get("Josip");
		assertEquals(3, jasnaGrade.intValue());
		assertEquals(4, josipGrade.intValue());
		assertEquals(null, examMarks.get("Ana"));
		assertEquals(null, examMarks.get(null));
		assertEquals(7, examMarks.size());
	}

	@Test
	public void testContainsKey() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		examMarks.put("Jasna", 3);
		examMarks.put("Luka", 5);
		examMarks.put("Marin", 5);
		examMarks.put("Josip", 4);

		assertTrue(examMarks.containsKey("Marin"));
		assertFalse(examMarks.containsKey("Marko"));
		assertFalse(examMarks.containsKey(null));
	}

	@Test
	public void testContainsValue() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		examMarks.put("Jasna", 3);
		examMarks.put("Luka", 5);
		examMarks.put("Marin", 5);
		examMarks.put("Josip", 4);

		assertTrue(examMarks.containsValue(3));
		assertFalse(examMarks.containsValue(1));
		assertFalse(examMarks.containsValue(null));
		examMarks.put("Josip", null);
		assertTrue(examMarks.containsValue(null));
	}

	@Test
	public void testRemove() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		examMarks.put("Jasna", 3);
		examMarks.put("Luka", 5);
		examMarks.put("Marin", 5);
		examMarks.put("Josip", 4);

		examMarks.remove("Ana"); //does nothing
		examMarks.remove("Josip");
		assertEquals(6, examMarks.size());
	}

	@Test
	public void testClear() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		examMarks.put("Jasna", 3);
		examMarks.put("Luka", 5);
		examMarks.put("Marin", 5);
		examMarks.put("Josip", 4);

		examMarks.clear();
		assertEquals(0, examMarks.size());
		assertFalse(examMarks.containsKey("Marin"));
	}

	@Ignore
	@Test
	public void testIterator() {
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		// prints the cartesian product of all elements in the hash table
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
			for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n",
						pair1.getKey(), pair1.getValue(),
						pair2.getKey(), pair2.getValue());
			}
		}
	}

	@Test
	public void testIteratorRemove() {
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}

		assertFalse(examMarks.containsKey("Ivana"));

		/*
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
			for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n",
						pair1.getKey(), pair1.getValue(),
						pair2.getKey(), pair2.getValue());
			}
		}
		 */
	}

	@Test(expected=IllegalStateException.class)
	public void testIteratorRemoveIllegalStateException() {
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove();
				iter.remove();
			}
		}
	}

	@Test(expected=ConcurrentModificationException.class)
	public void testIteratorRemoveConcurrentModificationException() {
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) { // throws ConcurrentModificationException
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}
	}

	@Test(expected=NoSuchElementException.class)
	public void testIteratorNoSuchElementException() {
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(true) {
			iter.next();
		}
	}

	@Test(expected=ConcurrentModificationException.class)
	public void testIteratorRemoveConcurrentModificationExceptionInNext() {
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
				iter.next(); // throws ConcurrentModificationException
			}
		}
	}

	@Test(expected=ConcurrentModificationException.class)
	public void testIteratorRemoveConcurrentModificationExceptionInRemove() {
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
				iter.remove(); // throws ConcurrentModificationException
			}
		}
	}

}
