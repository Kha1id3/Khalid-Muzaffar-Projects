package labthree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

// Document class

public class Document {
    public String name;
    public TwoWayUnorderedListWithHeadAndTail<Link> link;

    public Document(String name, Scanner scan) {
        this.name = name;
        link = new TwoWayUnorderedListWithHeadAndTail<Link>();
        load(scan);
    }

    public void load(Scanner scan) {
        while (scan.hasNext()) {
            String next = scan.next();
            if (next.equals("eod")) {
                return;
            }
            if (next.contains("=") && correctLink(next.split("=")[1])) {
                Link linkToAdd = new Link(next.split("=")[1]);
                link.add(linkToAdd);
            }
        }
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    public static boolean correctLink(String link) {
        if (link.length() == 0 || !Character.isLetter(link.charAt(0))) {
            return false;
        }
        for (char c : link.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        String output = "Document: " + name;
        for (Link link : link) {
            output += "\n" + link.ref;
        }
        return output;
    }

    public String toStringReverse() {
        ArrayList<Link> revLink = new ArrayList<>();
        for (Link link : link) {
            revLink.add(link);
        }
        Collections.reverse(revLink);
        String output = "Document: " + name;
        for (Link link : revLink) {
            output += "\n" + link.ref;
        }
        return output;
    }
}
