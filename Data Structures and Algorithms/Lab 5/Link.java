package lab.five;

public class Link implements Comparable<Link>{
    public String ref;
    public int weight;

    public Link(String ref) {
        this.ref = ref;
        weight = 1;
    }

    public Link(String ref, int weight) {
        this.ref = ref;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object another) {
        return this.ref.equals(((Link) another).ref);
    }

    @Override
    public String toString() {
        return ref.toLowerCase() + "(" + weight + ")";
    }

    @Override
    public int compareTo(Link another) {
        return this.ref.compareToIgnoreCase(another.ref);
    }
}