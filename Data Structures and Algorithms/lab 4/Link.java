package labfour;


public class Link implements Comparable<Link> {
	public String ref;
	public int weight;

	public Link(String ref) {
		this.ref = ref;
		weight = 1;
	}

	public Link(String ref, int weight) {
		this.ref = ref.toLowerCase();
		this.weight = weight;
	}

	public String getRef() {
		return ref;
	}

	@Override
	public boolean equals(Object obj) {
		if (ref != null && obj instanceof Link) {
			Link link = (Link) obj;
			if (link.ref == null)
				return false;
			return ref.equals(link.ref);
		}
		return false;
	}

	   @Override
	    public String toString() {
	        return ref + " (" + weight + ")";
	    }

	@Override
	public int compareTo(Link another) {
		String thisRef = this.getRef();
		String thatRef = another.getRef();
		int sizeDiff = Math.abs(thisRef.length() - thatRef.length());
		int bound = Math.max(thisRef.length(), thatRef.length()) - sizeDiff;
		for (int index = 0; index < bound; index++) {
			int charA = (int) thisRef.charAt(index);
			int charB = (int) thatRef.charAt(index);
			if (charA > charB || charA < charB) {
				return charA - charB;
			}
		}
		return thisRef.length() - thatRef.length();
	}
}