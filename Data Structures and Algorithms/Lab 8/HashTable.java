package lab.eight;

import java.util.ArrayList;
import java.util.LinkedList;

public class HashTable {
    LinkedList arr[];
    private final static int defaultInitSize = 8;
    private final static double defaultMaxLoadFactor = 0.7;
    private int size;
    private final double maxLoadFactor;

    public HashTable() {
        this(defaultInitSize);
        arr = new LinkedList[defaultInitSize];
    }

    public HashTable(int size) {
        this(size, defaultMaxLoadFactor);
        arr = new LinkedList[size];
    }


    public HashTable(int initCapacity, double maxLF) {
        this.size = initCapacity;
        this.maxLoadFactor = maxLF;
        arr = new LinkedList[initCapacity];
    }

    public boolean add(Object elem) {
        for (Object obj : allElements()) {
            if (((Document) elem).name.equals(((Document) obj).name)) {
                return false;
            }
        }
        String str = ((Document) elem).name;
        int idx = hashCode(str);
        if (idx < 0) {
            idx = (hashCode(str) + size) % size;
        }
        if (arr[idx] == null) {
            arr[idx] = new LinkedList();
        }
        arr[idx].add(elem);
        if ((double) allElements().size() / size > maxLoadFactor) {
            doubleArray();
        }
        return true;
    }

    final static int[] sequence = {7, 11, 13, 17, 19};

    public int hashCode(String elem) {
        int[] chars = new int[elem.length()];
        for (int i = 0; i < elem.length(); i++) {
            chars[i] = elem.charAt(i);
        }
        int n = 1;
        if (chars.length == 1) {
            return chars[0] % size;
        }
        int key = chars[0] * sequence[0] + chars[1];
        double key2 = chars[0] * sequence[0] + chars[1];
        for (int i = 1; i < chars.length - 1; i++) {
            key = key * sequence[n] + chars[i + 1];
            key2 = key2 * sequence[n] + chars[i + 1];
            if (n < 4) {
                n++;
            } else {
                n = 0;
            }
        }
        return key % size;
    }

    private ArrayList<Object> allElements() {
        ArrayList<Object> all = new ArrayList<>();
        for (LinkedList list : arr) {
            if (list != null) {
                for (Object obj : list) {
                    all.add(obj);
                }
            }
        }
        return all;
    }

    private void doubleArray() {
        ArrayList<Object> all = allElements();
        size *= 2;
        arr = new LinkedList[size];
        for (Object obj : all) {
            add(obj);
        }
    }

    @Override
    public String toString() {
        String output = "";
        int idx = 0;
        for (LinkedList list : arr) {
            String line = "";
            line += idx + ": ";
            if (list != null) {
                for (Object obj : list) {
                    line += ((Document) obj).name + ", ";
                }
            }
            line = line.trim();
            if (line.endsWith(",")) {
                line = line.substring(0, line.length() - 1);
            }
            output = output + line + "\n";
            idx++;
        }
        return output;
    }

    public Object get(Object toFind) {
        for (LinkedList list : arr) {
            if (list != null) {
                for (Object obj : list) {
                    if (((Document) toFind).name.equals(((Document) obj).name)) {
                        return obj;
                    }
                }
            }
        }
        return null;
    }

}