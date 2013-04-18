/* List.java */

package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  A List is a mutable list ADT.  No implementation is provided.
 *
 *  DO NOT CHANGE THIS FILE.
 **/

public abstract class List<T> implements Iterable<T> {

  /**
   *  size is the number of items in the list.
   **/

  protected int size;

  /**
   *  isEmpty() returns true if this List is empty, false otherwise.
   *
   *  @return true if this List is empty, false otherwise. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
   *  length() returns the length of this List. 
   *
   *  @return the length of this List.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int length() {
    return size;
  }

  /**
   *  insertFront() inserts an item at the front of this List.
   *
   *  @param item is the item to be inserted.
   **/
  public abstract void insertFront(T item);

  /**
   *  insertBack() inserts an item at the back of this List.
   *
   *  @param item is the item to be inserted.
   **/
  public abstract void insertBack(T item);

  /**
   *  front() returns the node at the front of this List.  If the List is
   *  empty, return an "invalid" node--a node with the property that any
   *  attempt to use it will cause an exception.
   *
   *  @return a ListNode at the front of this List.
   */
  public abstract ListNode<T> front();

  /**
   *  back() returns the node at the back of this List.  If the List is
   *  empty, return an "invalid" node--a node with the property that any
   *  attempt to use it will cause an exception.
   *
   *  @return a ListNode at the back of this List.
   */
  public abstract ListNode<T> back();

  /**
   *  toString() returns a String representation of this List.
   *
   *  @return a String representation of this List.
   */
  public abstract String toString();
  
	@Override
	public Iterator<T> iterator() {
		return new ListIterator(this);
	}

	private class ListIterator implements Iterator<T> {

		List<T> iList;
		ListNode<T> curr, next;

		private ListIterator(List<T> list) {
			iList = list;
			next = iList.front();
		}

		@Override
		public boolean hasNext() {
			return next.isValidNode();
		}

		@Override
		public T next() {
			try {
				curr = next;
				next = next.next();
				return curr.item();
			} catch (InvalidNodeException e) {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			try {
				curr.remove();
			} catch (InvalidNodeException e) {
				throw new NoSuchElementException();
			}
		}
	}

}
