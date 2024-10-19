package dsalab2;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayLinkedList<E> implements IList<E> {


    private class Element{
        public Element(E e)
        {
            this.object=e;
        }
        E object;
        Element next=null;

        private void setNext(Element elem){
            this.next=elem;
        }
        private void setData(E data){
            this.object=data;
        }
    }

    Element sentinel;

    private class InnerIterator implements Iterator<E>{
        // TODO
        Element actElem;
        public InnerIterator() {
            actElem=sentinel;
            // TODO
        }
        @Override
        public boolean hasNext() {
            // TODO
            return actElem.next!=null;
        }

        @Override
        public E next() {
            // TODO
                if(hasNext()){
                    actElem=actElem.next;
                    return actElem.object;
                }
                else return null;
        }
        private Element nextElement(){
            actElem = actElem.next;
            return actElem;
        }
    }

    public OneWayLinkedList() {
        this.sentinel = new Element(null);
        // TODO
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
    public boolean add(E e){
        InnerIterator iterator = new InnerIterator();
        Element tail = sentinel;
        while(iterator.hasNext()){
            tail=iterator.nextElement();
        }
        tail.setNext(new Element(e));
        return true;
    }

    @Override
    public void add(int index, E element) throws NoSuchElementException {// done
        // TODO Auto-generated method stub
        if(index<0) throw new NoSuchElementException("Index out of bounds");
        InnerIterator iterator = new InnerIterator();
        Element tail = sentinel;
        for(int i =0; i<index;i++){
            if(iterator.hasNext()){
                tail=iterator.nextElement();
            }
            else throw new NoSuchElementException("Index out of bounds");
        }
        Element actElem = new Element(element);
        actElem.setNext(tail.next);
        tail.setNext(actElem);

    }

    @Override
    public void clear() { //dpme
        sentinel.setNext(null);
        // TODO Auto-generated method stub

    }

    @Override
    public boolean contains(E element) { //done
        // TODO Auto-generated method stub
        InnerIterator iterator = new InnerIterator();
        Element tail = sentinel;
        while (iterator.hasNext()){
            tail = iterator.nextElement();
            if(tail.object.equals(element)) return true;
        }
        return false;
    }

    @Override
    public E get(int index) throws NoSuchElementException { //done
        // TODO Auto-generated method stub
        if (index < 0) throw new NoSuchElementException("Index out of bounds");
        InnerIterator iterator = new InnerIterator();
        Element tail = sentinel;
        for (int i = 0; i < index + 1; i++) {
            if (iterator.hasNext()) {
                tail = iterator.nextElement();
            } else throw new NoSuchElementException("Index out of bounds");
        }
        return tail.object;
    }

    @Override
    public E set(int index, E element) throws NoSuchElementException { //done
        if(index<0) throw new NoSuchElementException("Index out of bounds");
        InnerIterator iterator = new InnerIterator();
        Element tail=sentinel;
        for(int i =0; i<index+1;i++){
            if(iterator.hasNext()){
                tail = iterator.nextElement();
            }
            else throw new NoSuchElementException("Index out of bounds");
        }
        E actObject = tail.object;
        tail.setData(element);
        return actObject;
    }

    @Override
    public int indexOf(E element) { //done
        // TODO Auto-generated method stub
        InnerIterator iterator = new InnerIterator();
        Element tail = sentinel;
        int num = -1;
        while(iterator.hasNext()){
            tail = iterator.nextElement();
            num++;
            if(tail.object.equals(element)) return num;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() { //done
        // TODO Auto-generated method stub
        return sentinel.next==null;
    }

    @Override
    public E remove(int index) throws NoSuchElementException { //done
        // TODO Auto-generated method stub
        InnerIterator iterator = new InnerIterator();
        Element tail = sentinel;
        if(index==0 && !iterator.hasNext()) throw new NoSuchElementException("Index out of bounds");
        if(index==size()||index<0) throw new NoSuchElementException("Index out of bounds");
        for(int i=0; i<index; i++){
            if(iterator.hasNext()){
                tail=iterator.nextElement();
            }
            else throw new NoSuchElementException("Index out of bounds");
        }
        Element elem = tail.next;
        tail.setNext(elem.next);
        return elem.object;
    }

    @Override
    public boolean remove(E e) { //done
        // TODO Auto-generated method stub
        InnerIterator iterator = new InnerIterator();
        Element tail = sentinel;
        while (iterator.hasNext()){
            if(tail.next.object.equals(e)){
                tail.setNext(tail.next.next);
                return true;
            }
            tail=iterator.nextElement();
        }
        return false;
    }

    @Override
    public int size() { //done
        InnerIterator iterator = new InnerIterator();
        int num = 0;
        while(iterator.hasNext()){
            iterator.nextElement();
            num++;
        }
        return num;
    }

}
