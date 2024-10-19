package labfour;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E> {

	private class Element {
		public Element(E e) {
			this.object = e;
		}

		public Element(E e, Element next, Element prev) {
			this.object = e;
			this.next = next;
			this.prev = prev;
		}

		public void setNext(Element next) {
			this.next = next;
		}

		public void setPrev(Element prev) {
			this.prev = prev;
		}

		public void setValue(E object) {
			this.object = object;
		}

		public Element getNext() {
			return next;
		}

		public Element getPrev() {
			return prev;
		}

		public E getValue() {
			return object;
		}

		// add element e after this
		public void addAfter(Element elem) {
			elem.setNext(this.getNext());
			elem.setPrev(this);
			this.getNext().setPrev(elem);
			this.setNext(elem);
		}

		// assert it is NOT a sentinel
		public void remove() {
			if (object != null) {
				this.getNext().setPrev(this.getPrev());
				this.getPrev().setNext(this.getNext());
			}
		}

		E object;
		Element next = null;
		Element prev = null;
	}


	Element sentinel;
	int size;

	private class InnerIterator implements Iterator<E> {
		Element pos;

		public InnerIterator() {
			pos = sentinel;
		}

		@Override
		public boolean hasNext() {
			return pos.getNext() != sentinel;
		}

		@Override
		public E next() {
			pos = pos.getNext();
			return pos.getValue();
		}
	}

	private class InnerListIterator implements ListIterator<E> {
		Element p;

		public InnerListIterator() {
			p = sentinel;
		}

		@Override
		public boolean hasNext() {
			return p.getNext() != sentinel;
		}

		@Override
		public E next() {
			p = p.getNext();
			return p.getValue();
		}

		@Override
		public void add(E arg0) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasPrevious() {
			return p != sentinel;
		}

		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public E previous() {
			E value = p.getValue();
			p = p.getPrev();
			return value;
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
		public void set(E arg0) {
			throw new UnsupportedOperationException();
		}
	}

	public TwoWayCycledOrderedListWithSentinel() {
		sentinel = new Element(null);
		sentinel.setNext(sentinel);
		sentinel.setPrev(sentinel);
		size = 0;
	}

	//@SuppressWarnings("unchecked")
	@Override
	public boolean add(E e) {
		Element newElem = new Element(e);
		Link newValue = (Link) newElem.getValue();
		if (!newValue.getRef().matches("[a-z]\\w*") || newValue.weight <= 0) {
			return false;
		}
		Element actElem = sentinel.getNext();
		if (size == 1) {
			if (newValue.compareTo((Link) actElem.getValue()) >= 0) {
				actElem.addAfter(newElem);
			} else {
				sentinel.addAfter(newElem);
			}
			size++;
			return true;
		}

		if (size > 1 && newValue.compareTo((Link) sentinel.getNext().getValue()) < 0) {
			sentinel.addAfter(newElem);
			size++;
			return true;
		}

		while (actElem != sentinel) {
			Link actValue = (Link) actElem.getValue();
			if (newValue.compareTo(actValue) == 0) {
				while (actElem.getNext() != sentinel && ((Link) actElem.getNext().getValue()).compareTo(newValue) == 0) {
					actElem = actElem.getNext();
				}
				actElem.addAfter(newElem);
				size++;
				return true;
			}
			if (actElem.getNext() != sentinel && newValue.compareTo(actValue) > 0) {
				actValue = (Link) actElem.getNext().getValue();
				if (newValue.compareTo(actValue) < 0) {
					actElem.addAfter(newElem);
					size++;
					return true;
				}
			}
			actElem = actElem.getNext();
		}
		actElem = sentinel.getPrev();
		actElem.addAfter(newElem);
		size++;
		return true;
	}

	private Element getElement(int index) {
		if (index >= size || index < 0) {
			throw new NoSuchElementException();
		}
		Element currElem = sentinel.getNext();
		while (index > 0 && currElem != sentinel) {
			index--;
			currElem = currElem.getNext();
		}
		if (currElem == sentinel) {
			throw new NoSuchElementException();
		}
		return currElem;
	}

	private Element getElement(E obj) {
		//TODO
		return null;
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void clear() {
		sentinel.setNext(sentinel);
		sentinel.setPrev(sentinel);
		size = 0;
	}

	@Override
	public boolean contains(E element) {
		return indexOf(element) >= 0;
	}

	@Override
	public E get(int index) {
		Element actElem = getElement(index);
		return actElem.getValue();
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(E element) {
		int pos = 0;
		Element actElem = sentinel.getNext();
		while (actElem != sentinel) {
			if (actElem.getValue().equals(element)) {
				return pos;
			}
			pos++;
			actElem = actElem.getNext();
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new InnerListIterator();
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new NoSuchElementException();
		}
		Element actElem = sentinel.getNext();
		if (actElem == sentinel) {
			throw new NoSuchElementException();
		}
		actElem = getElement(index);
		E value = actElem.getValue();
		actElem.remove();
		size--;
		return value;
	}

	@Override
	public boolean remove(E e) {
		Element actElem = sentinel.getNext();
		if (actElem == sentinel) {
			return false;
		}
		while (actElem != sentinel) {
			if (actElem.getValue().equals(e)) {
				actElem.remove();
				size--;
				return true;
			}
			actElem = actElem.getNext();
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}


	public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
		Element actElem = other.sentinel.getNext();
		while (actElem != other.sentinel) {
			add(actElem.getValue());
			actElem = actElem.getNext();
		}
		other.clear();
	}


	public void removeAll(E e) {
		Element aElement = sentinel.getNext();
		while (aElement != sentinel) {
			if (aElement.getValue().equals(e)) {
				aElement.remove();
			}
			aElement = aElement.getNext();
		}
	}

	public String toStringReverse() {
		ListIterator<E> it = new InnerListIterator();
		if (!it.hasNext()) {
			return "";
		}
		StringBuilder reString = new StringBuilder();
		while (it.hasNext()) {
			it.next();
		}
		int counter = 10;
		while (it.hasPrevious()) {
			String seperator = "";
			if (counter == 10) {
				seperator = "";
				reString.append("\n");
			} else if (counter != 0) {
				seperator = " ";
			} else {
				seperator = "\n";
			}
			counter--;
			reString.append(seperator).append(it.previous().toString());
		}
		return reString.toString();

	}
}