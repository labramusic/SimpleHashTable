package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

/**
 * A hash table with variable type of key and value. Contains methods to add,
 * remove and search for an entry.
 * 
 * @author labramusic
 *
 * @param <K>
 *            entry key type
 * @param <V>
 *            entry value type
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Minimum table capacity.
	 */
	private final static int MIN_CAPACITY = 1;

	/**
	 * Default table capacity.
	 */
	private final static int DEF_CAPACITY = 16;

	/**
	 * An array of parameterized slots representing a hash table.
	 */
	private TableEntry<K, V>[] table;

	/**
	 * Number of entries in table.
	 */
	private int size;

	/**
	 * Counts each modification made on the collection.
	 */
	private int modificationCount;

	/**
	 * Initializes the capacity of the hash table to 16.
	 */
	public SimpleHashtable() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] array = new TableEntry[DEF_CAPACITY];
		table = array;
	}

	/**
	 * Initializes the capacity of the hash table to the first power of 2
	 * greater or equal to the given capacity.
	 * 
	 * @param capacity
	 *            given capacity
	 */
	public SimpleHashtable(int capacity) {
		if (capacity < MIN_CAPACITY) {
			throw new IllegalArgumentException("Capacity must be at least " + MIN_CAPACITY + ".");
		}
		int initialCapacity = 1;
		while (true) {
			if (initialCapacity >= capacity) {
				break;
			} else {
				initialCapacity *= 2;
			}
		}
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] array = new TableEntry[initialCapacity];
		table = array;
	}

	/**
	 * Static nested class representing a hash table entry.
	 * 
	 * @author labramusic
	 *
	 * @param <K>
	 *            entry key type
	 * @param <V>
	 *            entry value type
	 */
	public static class TableEntry<K, V> {
		/**
		 * Entry key.
		 */
		private K key;
		/**
		 * Entry value.
		 */
		private V value;
		/**
		 * Next table entry in the overflow list.
		 */
		private TableEntry<K, V> next;

		/**
		 * Constructor which initializes a new table entry.
		 * 
		 * @param key
		 *            entry key
		 * @param value
		 *            entry value
		 * @param next
		 *            next entry in the overflow list
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns the entry key.
		 * 
		 * @return entry key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Returns the entry value.
		 * 
		 * @return entry value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the entry value-
		 * 
		 * @param value
		 *            entry value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}

	/**
	 * Generates hash table index of element using the absolute value of the
	 * hash code of its key, given by the existing hashCode method, evenly
	 * distributing between slots of the hash table.
	 * 
	 * @param key
	 *            provided key
	 * @param array
	 *            array representing table
	 * @return generated index
	 */
	private int generateIndex(Object key, TableEntry<K, V>[] array) {
		return Math.abs(key.hashCode()) % array.length;
	}

	/**
	 * Determines the slot in which to place the given element using the
	 * generateIndex method, finds the next available empty space and places the
	 * given entry into the table.
	 * 
	 * @param key
	 *            provided key
	 * @param value
	 *            provided value
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("The key cannot be null.");
		}

		if (!containsKey(key)) {
			++size;
			++modificationCount;
		}
		table = putInArray(key, value, table);

		if (size >= 0.75 * table.length) {
			increaseCapacity();
			++modificationCount;
		}
	}

	/**
	 * Finds the value associated with the given key and returns it. If null is
	 * given as the key, the returned value is null. If key doesn't exist in the
	 * table, the returned value is null.
	 * 
	 * @param key
	 *            given key
	 * @return associated value
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}
		int index = generateIndex(key, table);
		TableEntry<K, V> entry = table[index];
		while (entry != null) {
			if (entry.key.equals(key)) {
				return entry.value;
			}
			entry = entry.next;
		}
		return null;
	}

	/**
	 * Returns the size of the hash table, i.e. number of added elements.
	 * 
	 * @return size of hash table
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks if hash table contains the given key. Returns false if the given
	 * key is null.
	 * 
	 * @param key
	 *            given key
	 * @return true if table contains key
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		int index = generateIndex(key, table);
		TableEntry<K, V> entry = table[index];
		while (entry != null) {
			if (entry.key.equals(key)) {
				return true;
			}
			entry = entry.next;
		}
		return false;
	}

	/**
	 * Checks if hash table contains the given value.
	 * 
	 * @param value
	 *            given value
	 * @return true if table contains given value
	 */
	public boolean containsValue(Object value) {
		for (TableEntry<K, V> entry : table) {
			while (entry != null) {
				if (entry.value == null) {
					if (value == null) {
						return true;
					} else {
						break;
					}
				}

				if (entry.value.equals(value)) {
					return true;
				}
				entry = entry.next;
			}
		}
		return false;
	}

	/**
	 * Removes an element from the hash table with the given key.
	 * 
	 * @param key
	 *            key of element to be deleted.
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}
		int index = generateIndex(key, table);
		TableEntry<K, V> entry = table[index];
		if (entry == null) {
			return;
		} else {
			if (entry.key.equals(key)) {
				table[index] = entry.next;
				--size;
				++modificationCount;
				return;
			}
			while (entry.next != null) {
				if (entry.next.key.equals(key)) {
					entry.next = entry.next.next;
					--size;
					++modificationCount;
					return;
				}
				entry = entry.next;
			}
		}
	}

	/**
	 * Checks if table is empty.
	 * 
	 * @return true if table contains no elements
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Removes all entries from the table. Leaves the capacity unchanged.
	 */
	public void clear() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] array = new TableEntry[table.length];
		table = array;
		size = 0;
		++modificationCount;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringJoiner joiner = new StringJoiner(", ");
		sb.append("[");
		for (int slot = 0; slot < table.length; ++slot) {
			TableEntry<K, V> entry = table[slot];
			while (entry != null) {
				joiner.add(entry.toString());
				entry = entry.next;
			}
		}
		sb.append(joiner);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Places the entry in the given array.
	 * 
	 * @param key
	 *            entry key
	 * @param value
	 *            entry value
	 * @param array
	 *            array to put in entry
	 * @return array with put entry
	 */
	private TableEntry<K, V>[] putInArray(K key, V value, TableEntry<K, V>[] array) {
		int index = generateIndex(key, array);
		TableEntry<K, V> entry = array[index];
		if (entry == null) {
			array[index] = new TableEntry<K, V>(key, value, null);
		} else {
			if (entry.key.equals(key)) {
				entry.value = value;
				return array;
			}
			while (entry.next != null) {
				entry = entry.next;
				if (entry.key.equals(key)) {
					entry.value = value;
					return array;
				}
			}
			entry.next = new TableEntry<K, V>(key, value, null);
		}
		return array;
	}

	/**
	 * Increases the hash table capacity to double its value.
	 */
	private void increaseCapacity() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] array = new TableEntry[2 * table.length];

		for (int i = 0; i < table.length; ++i) {
			TableEntry<K, V> entry = table[i];
			while (entry != null) {
				array = putInArray(entry.key, entry.value, array);
				entry = entry.next;
			}
		}
		table = array;
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * An iterator over hash table entries.
	 * 
	 * @author labramusic
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Index of current iterated element.
		 */
		private int currentIndex;

		/**
		 * Previously returned entry.
		 */
		private TableEntry<K, V> currentEntry;

		/**
		 * Next table entry to be iterated through.
		 */
		private TableEntry<K, V> nextEntry = table[currentIndex];

		/**
		 * Current modification count.
		 */
		private int currentModCount = modificationCount;

		@Override
		public boolean hasNext() {
			if (currentModCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			while (currentIndex < table.length) {
				if (nextEntry != null) {
					return true;
				}
				++currentIndex;
				if (currentIndex < table.length) {
					nextEntry = table[currentIndex];
				}
			}
			return false;
		}

		@Override
		public TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			currentEntry = nextEntry;
			while (currentEntry == null) {
				++currentIndex;
				currentEntry = table[currentIndex];
			}
			nextEntry = currentEntry.next;
			return currentEntry;
		}

		@Override
		public void remove() {
			if (currentModCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (currentEntry == null) {
				throw new IllegalStateException();
			}

			if (table[currentIndex].equals(currentEntry)) {
				table[currentIndex] = nextEntry;

			} else {
				TableEntry<K, V> prev = table[currentIndex];
				while (!prev.next.equals(currentEntry)) {
					prev = prev.next;
				}
				prev.next = nextEntry;
			}
			currentEntry = null;
			--size;
			++modificationCount;
			++currentModCount;
		}

	}

}
