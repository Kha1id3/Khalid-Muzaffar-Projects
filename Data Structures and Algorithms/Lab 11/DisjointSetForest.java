package lab.eleven;


import java.util.HashSet;


public class DisjointSetForest implements DisjointSetDataStructure {

    private class Element{
        int rank;
        int parent;
    }

    Element []arr;

    public DisjointSetForest(int size) {
        this.arr = new Element[size];
    }

    @Override
    public void makeSet(int item) {
        Element element = new Element();
        element.parent=item;
        element.rank=0;
        this.arr[item]=element;
    }

    @Override
    public int findSet(int item) { //return element who is its own parent
        if(arr[item] != null) {
            if(item != this.arr[item].parent) {
                this.arr[item].parent = findSet(this.arr[item].parent);
            }
            return this.arr[item].parent;
        }
        return -1;
    }

    @Override
    public boolean union(int itemA, int itemB) {
        int repA = findSet(itemA);
        int repB = findSet(itemB);
        if(repA != -1 && repB != -1 && repA != repB ) {
            if (this.arr[repA].rank > this.arr[repB].rank)
                this.arr[repB].parent = repA;
            else {
                this.arr[repA].parent = repB;
                if (this.arr[repA].rank == this.arr[repB].rank){
                    this.arr[repB].rank += 1;
                }
            }
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        String outcome="Disjoint sets as forest:";
        for (int i = 0; i<this.arr.length; i++){
            outcome+="\n"+ i +" -> " + this.arr[i].parent;
        }
        return outcome;
    }

    @Override
    public int countSets() {
        HashSet<Integer> h = new HashSet<>();
        for (int i=0; i<arr.length; i++)
            h.add(findSet(i));
        return h.size();
    }


}