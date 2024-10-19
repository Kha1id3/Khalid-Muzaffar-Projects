package labthree;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayUnorderedListWithHeadAndTail<E> implements IList<E> {
	
	private class Element {
		public Element(E e) {
			this.object = e;
		}
		public Element(E e, Element next, Element prev) {
			this.object = e;
			this.next = next;
			this.prev = prev;
		}
		E object;
		Element next = null;
		Element prev = null;
	}
	
	Element head;
	Element tail;
	int size;
	
	private class InnerIterator implements Iterator<E> {
		Element pos;

		public InnerIterator() {
			pos = head;
		}
		@Override
		public boolean hasNext() {
			return pos != null;
		}
		
		@Override
		public E next() {
			if (pos == null) {
				throw new NoSuchElementException();
			}
			E result = pos.object;
			pos = pos.next;
			return result;
		}
	}
	
	private class InnerListIterator implements ListIterator<E> {
		Element p;

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasNext() {
			return p != null;
		}

		@Override
		public boolean hasPrevious() {
			return p != null && p.prev != null;
		}

		@Override
		public E next() {
			if (p == null) {
				throw new NoSuchElementException();
			}
			E result = p.object;
			p = p.next;
			return result;
		}

		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public E previous() {
			if (p == null || p.prev == null) {
				throw new NoSuchElementException();
			}
			p = p.prev;
			return p.object;
		}

		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(E e) {
			if (p == null) {
				throw new NoSuchElementException();
			}
			p.object = e;
		}
	}
	
	public TwoWayUnorderedListWithHeadAndTail() {
		head = null;
		tail = null;
		size = 0;
	}
	
	@Override
	public boolean add(E e) {
		Element newElement = new Element(e);
		if (isEmpty()) {
			head = tail = newElement;
		} else {
			tail.next = newElement;
			newElement.prev = tail;
			tail = newElement;
		}
		size++;
		return true;
	}

	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size) {
			throw new NoSuchElementException();
		}
		if (index == size) {
			add(element);
			return;
		}
		Element current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		Element newElement = new Element(element, current, current.prev);
		if (current.prev != null) {
			current.prev.next = newElement;
		} else {
			head = newElement;
		}
		current.prev = newElement;
		size++;
	}


	@Override
	public void clear() {
		head = tail = null;
		size = 0;
	}

	@Override
	public boolean contains(E element) {
		Element current = head;
		while (current != null) {
			if (current.object.equals(element)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new NoSuchElementException();
		}
		Element current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.object;
	}

	@Override
	public E set(int index, E element) {
		if (index < 0 || index >= size) {
			throw new NoSuchElementException();
		}
		Element current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		E oldValue = current.object;
		current.object = element;
		return oldValue;
	}

	@Override
	public int indexOf(E element) {
		int index = 0;
		Element current = head;
		while (current != null) {
			if (current.object.equals(element)) {
				return index;
			}
			index++;
			current = current.next;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new NoSuchElementException();
		}
		Element current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		if (current.prev != null) {
			current.prev.next = current.next;
		} else {
			head = current.next;
		}
		if (current.next != null) {
			current.next.prev = current.prev;
		} else {
			tail = current.prev;
		}
		size--;
		return current.object;
	}

	@Override
	public boolean remove(E e) {
		Element current = head;
		while (current != null) {
			if (current.object.equals(e)) {
				if (current.prev != null) {
					current.prev.next = current.next;
				} else {
					head = current.next;
				}
				if (current.next != null) {
					current.next.prev = current.prev;
				} else {
					tail = current.prev;
				}
				size--;
				return true;
			}
			current = current.next;
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}
	
    public String toStringReverse() {

        ListIterator<E> iter = new InnerListIterator();
        while (iter.hasNext())
            iter.next();
        String retStr = "";
        return retStr;
    }

    
    	public void add(TwoWayUnorderedListWithHeadAndTail<E> other) {
    		if (other == null || other.isEmpty()) {
    			return;
    		}
    		if (isEmpty()) {
    			head = other.head;
    			tail = other.tail;
    			size = other.size;
    		} else {
    			tail.next = other.head;
    			other.head.prev = tail;
    			tail = other.tail;
    			size += other.size;
    		}
    		other.clear();
    	}


}