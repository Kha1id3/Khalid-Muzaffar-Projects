package dsalab2;

public class Link{
    public String ref;
    public Link(String ref) {
        this.ref=ref;
    }
    @Override
    public boolean equals(Object o){
        return ref.equals(((Link)o).ref);
    }

}

