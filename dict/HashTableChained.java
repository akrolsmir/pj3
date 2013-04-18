/* HashTableChained.java */

package dict;

import java.util.Iterator;

import list.DList;
import list.List;

/**
 * HashTableChained implements a Dictionary as a hash table with chaining. All
 * objects used as keys must have a valid hashCode() method, which is used to
 * determine which bucket of the hash table an entry is stored in. Each object's
 * hashCode() is presumed to return an int between Integer.MIN_VALUE and
 * Integer.MAX_VALUE. The HashTableChained class implements only the compression
 * function, which maps the hash code to a bucket in the table's range.
 * 
 * DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained<K, V> {

	/**
	 * Place any data fields here.
	 **/
	private List<Entry<K, V>>[] table;
	private int size = 0;

	private class DEntry<K, V> extends Entry<K, V> {
		public DEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public String toString(){
			return "<" + key + ", " + value + ">";
		}
	}

	/**
	 * Construct a new empty hash table intended to hold roughly sizeEstimate
	 * entries. (The precise number of buckets is up to you, but we recommend
	 * you use a prime number, and shoot for a load factor between 0.5 and 1.)
	 **/

	public HashTableChained(int sizeEstimate) {
		table = new List[findPrime(sizeEstimate)];
	}

	/**
	 * Construct a new empty hash table with a default size. Say, a prime in the
	 * neighborhood of 100.
	 **/

	public HashTableChained() {
		table = new List[101];
	}

	// Finds the smallest prime larger than about.
	private int findPrime(int about) {
		for (int p = about; ; p++) {
			for (int i = 2; i < p; i++)
				if (p % i == 0)
					break;
				else if (i * i > p)
					return p;
		}
	}
	
	// Outputs number of collisions
	public int getCollisions()
	{
		int lists = 0;
		for(List<Entry<K, V>> list : table ){
			if(list != null)
				lists++;
		}
		return size - lists;
	}

	/**
	 * Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
	 * to a value in the range 0...(size of hash table) - 1.
	 * 
	 * This function should have package protection (so we can test it), and
	 * should be used by insert, find, and remove.
	 **/

	int compFunction(int code) {
		return ((code % table.length) + table.length) % table.length;
	}

	/**
	 * Returns the number of entries stored in the dictionary. Entries with the
	 * same key (or even the same key and value) each still count as a separate
	 * entry.
	 * 
	 * @return number of entries in the dictionary.
	 **/

	public int size() {
		return size;
	}

	/**
	 * Tests if the dictionary is empty.
	 * 
	 * @return true if the dictionary has no entries; false otherwise.
	 **/

	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Create a new Entry object referencing the input key and associated value,
	 * and insert the entry into the dictionary. Return a reference to the new
	 * entry. Multiple entries with the same key (or even the same key and
	 * value) can coexist in the dictionary.
	 * 
	 * This method should run in O(1) time if the number of collisions is small.
	 * 
	 * @param key
	 *            the key by which the entry can be retrieved.
	 * @param value
	 *            an arbitrary object.
	 * @return an entry containing the key and value.
	 **/

	public Entry<K, V> insert(K key, V value) {
		Entry<K, V> entry = new DEntry<K, V>(key, value);
		int comp = compFunction(key.hashCode());
		if (table[comp] == null)
			table[comp] = new DList<Entry<K, V>>();
		table[comp].insertBack(entry);
		size++;
		return entry;
	}

	/**
	 * Search for an entry with the specified key. If such an entry is found,
	 * return it; otherwise return null. If several entries have the specified
	 * key, choose one arbitrarily and return it.
	 * 
	 * This method should run in O(1) time if the number of collisions is small.
	 * 
	 * @param key
	 *            the search key.
	 * @return an entry containing the key and an associated value, or null if
	 *         no entry contains the specified key.
	 **/

	public V find(K key) {
		try {
			List<Entry<K, V>> list = table[compFunction(key.hashCode())];
			for (Entry<K, V> entry : list)
				if (entry.key().equals(key))
					return entry.value();
		} catch (NullPointerException e) {
		}
		// Either no list, or key not in list
		return null;
	}

	/**
	 * Remove an entry with the specified key. If such an entry is found, remove
	 * it from the table and return it; otherwise return null. If several
	 * entries have the specified key, choose one arbitrarily, then remove and
	 * return it.
	 * 
	 * This method should run in O(1) time if the number of collisions is small.
	 * 
	 * @param key
	 *            the search key.
	 * @return an entry containing the key and an associated value, or null if
	 *         no entry contains the specified key.
	 */

	public V remove(K key) {
		try {
			List<Entry<K, V>> list = table[compFunction(key.hashCode())];
			for (Iterator<Entry<K, V>> i = list.iterator(); i.hasNext();) {
				Entry<K, V> entry = i.next();
				if (entry.key().equals(key)) {
					i.remove();
					size--;
					return entry.value();
				}
			}
		} catch (NullPointerException e) {
		}
		// Either no list, or key not in list
		return null;
	}

	/**
	 * Remove all entries from the dictionary.
	 */
	public void makeEmpty() {
		size = 0;
		table = new List[table.length];
	}
	
	public static void main(String[] args){
		List<String> list = new DList<String>();
		list.insertBack("ONE");
		list.insertBack("TWO");
		list.insertBack("THREE");
		for (Iterator i = list.iterator();i.hasNext();) {
			if (i.next().equals("TWO")) {
				i.remove();
			}
		}
		System.out.println(list.toString());
		
		HashTableChained h = new HashTableChained();
		h.insert("key0", "value0");
		h.insert("key1", "value1");
		System.out.println(h.size());
		h.remove("key0");
		h.remove("key0");
		System.out.println(h.size());
		h.makeEmpty();
		System.out.println(h.isEmpty());
	}

}
