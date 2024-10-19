package lab.seven;
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

        // add element e after this
        public void addAfter(Element elem) {
            elem.setNext(this.getNext());
            elem.setPrev(this);
            this.getNext().setPrev(elem);
            this.setNext(elem);
        }

        public void addBefore(Element elem) {
            elem.setNext(this);
            elem.setPrev(this.getPrev());
            this.getPrev().setNext(elem);
            this.setPrev(elem);
        }

        // assert it is NOT a sentinel
        public void remove() {
            this.getNext().setPrev(this.getPrev());
            this.getPrev().setNext(this.getNext());
        }

        E object;
        Element next = null;
        Element prev = null;

        public E getObject() {
            return object;
        }

        public void setObject(E object) {
            this.object = object;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }

        public Element getPrev() {
            return prev;
        }

        public void setPrev(Element prev) {
            this.prev = prev;
        }


    }


    Element sentinel;
    int size = 0;

    private class InnerIterator implements Iterator<E> {
        Element current;

        public InnerIterator() {
            current = sentinel;
        }

        @Override
        public boolean hasNext() {
            return current.getNext() != sentinel;
        }

        @Override
        public E next() {
            current = current.getNext();
            return current.getObject();
        }
    }

    private class InnerListIterator implements ListIterator<E> {
        boolean wasNext;
        boolean wasPrevious;
        Element current;

        public InnerListIterator() {
            wasNext = false;
            wasPrevious = false;
            current = sentinel;
        }

        @Override
        public boolean hasNext() {
            return current.getNext() != sentinel;
        }

        @Override
        public E next() {
            wasNext = true;
            wasPrevious = false;
            current = current.getNext();
            return current.getObject();
        }

        @Override
        public void add(E arg0) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasPrevious() {
            return current != sentinel;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            wasNext = false;
            wasPrevious = true;
            E retValue = current.getObject();
            current = current.getPrev();
            return retValue;
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
    }


    public boolean add(E e) {
        Element newElem = new Element(e);
        Element curElem = sentinel.getNext();

        while (curElem != sentinel) {
            if (((Comparable<E>)newElem.object).compareTo(curElem.object) < 0) {
                curElem.addBefore(newElem);
                size++;
                return true;
            }
            curElem = curElem.getNext();
        }

        sentinel.addBefore(newElem);
        size++;
        return true;
    }

    private Element getElement(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        Element elem = sentinel.getNext();
        int cnt = 0;

        while (elem != sentinel && cnt < index) {
            cnt++;
            elem = elem.getNext();
        }
        if (elem == sentinel) {
            throw new NoSuchElementException();
        }
        return elem;
    }

    private Element getElement(E obj) {
        Element elem = sentinel.getNext();
        while (elem != sentinel && !obj.equals(elem.getObject())) {
            elem = elem.getNext();
        }
        if (elem == sentinel) {
            return null;
        }
        return elem;
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        sentinel.setPrev(sentinel);
        sentinel.setNext(sentinel);
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public E get(int index) {
        Element elem = getElement(index);
        return elem.getObject();
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(E element) {
        Element elem = sentinel.getNext();
        int cnt = 0;

        while (elem != sentinel && !elem.getObject().equals(element)) {
            cnt++;
            elem = elem.getNext();
        }
        if (elem == sentinel) {
            return -1;
        }
        return cnt;
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
        Element elem = getElement(index);
        elem.remove();
        size--;
        return elem.getObject();
    }

    @Override
    public boolean remove(E e) {
        if (indexOf(e) == -1) {
            return false;
        }
        Element elem = getElement(e);
        elem.remove();
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    //@SuppressWarnings("unchecked")
    public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
        if (other == null || other == this || other.isEmpty())
            return;
        Element thisIt = this.sentinel.next;
        Element otherIt = other.sentinel.next;
        while(otherIt != other.sentinel && thisIt != this.sentinel) {
            if(((Comparable<E>)thisIt.object).compareTo(otherIt.object) < 0) {
                Element tmp = otherIt.next;
                otherIt.next.addBefore(thisIt.prev);
                otherIt = tmp;
            }
            else if (thisIt != this.sentinel) {
                thisIt = thisIt.next;
            }
        }

        while(otherIt != other.sentinel) {
            Element tmp = otherIt.next;
            otherIt.addAfter(thisIt.prev);
            otherIt = tmp;
        }
        this.size += other.size;
        other.clear();
    }

    //@SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeAll(E e) {
        Element copy = this.sentinel;
        while(copy.next !=sentinel){
            copy = copy.next;
            if(copy.object.equals(e)){
                copy.remove();
                size--;
            }
        }
//        while (contains(e)) {
//            remove(e);
//        }
    }


}