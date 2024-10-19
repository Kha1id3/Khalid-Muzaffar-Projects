package lab.seven;

import java.util.LinkedList;

public class HashTable{
    LinkedList arr[]; // use pure array
    private final static int defaultInitSize=8;
    private final static double defaultMaxLoadFactor=0.7;
    private int size;
    private final double maxLoadFactor;
    public HashTable() {
        this(defaultInitSize);
    }
    public HashTable(int size) {
        this(size,defaultMaxLoadFactor);
    }


    public HashTable(int initCapacity, double maxLF) {
        //TODO
        this.maxLoadFactor=maxLF;
        this.arr = new LinkedList[initCapacity];
        int i = 0;
        while(i< this.arr.length){
            this.arr[i]=new LinkedList<Object>();
            i++;
        }
        this.size=0;
    }

    public boolean add(Object elem) {
        //TODO
        if (elem == null || this.get(elem) != null)
            return false;
        this.arr[elem.hashCode() % this.arr.length].add(elem);
        this.size++;
        if ((double)this.size / this.arr.length > this.maxLoadFactor)
            doubleArray();
        return true;
    }


    private void doubleArray() {
        //TODO
        LinkedList[] doubleArr = new LinkedList[this.arr.length*2];
        int i=0;
        while (i<doubleArr.length) {
            doubleArr[i] = new LinkedList<Object>();
            i++;
        }
        for (int k=0; k < this.arr.length; k++) {
            for (Object objectFromList : this.arr[k])
                doubleArr[objectFromList.hashCode() % doubleArr.length].add(objectFromList);
        }

        this.arr = doubleArr;
    }


    @Override
    public String toString() {
        //TODO
        // use	IWithName x=(IWithName)elem;
        String output = "";
        boolean firstList = true;
        for (int i=0; i < this.arr.length; i++) {
            if (firstList) {
                output = output.concat(i + ":");
                firstList = false;
            } else
                output = output.concat("\n" + i + ":");

            boolean firstObject = true;
            for (Object objectFromList : this.arr[i]) {
                IWithName x = (IWithName)objectFromList;
                if (firstObject) {
                    output = output.concat(" " + x.getName());
                    firstObject = false;
                }
                else
                    output = output.concat(", " + x.getName());
            }
        }
        return output + "\n";
    }

    public Object get(Object toFind) {
        //TODO
        LinkedList listWithObject = this.arr[toFind.hashCode() % this.arr.length];
        for (Object o : listWithObject) {
            if (o.equals(toFind))
                return o;
        }
        return null;
    }

}
