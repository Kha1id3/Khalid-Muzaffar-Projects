package labthree;



public class Link{
    public String ref;
    // in the future there will be more fields
    public Link(String ref) {
        this.ref=ref;
    }
    @Override
    public boolean equals(Object obj) {
        return ref.equals(((Link) obj).ref);
    }

}

