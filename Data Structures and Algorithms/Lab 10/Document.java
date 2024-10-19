package lab10;

import java.util.Scanner;
import java.util.*;

public class Document implements IWithName{
    public String name;
    public SortedMap<String, Link> link;

    public Document(String name) {
        this.name=name.toLowerCase();
        link=new TreeMap<>();
    }

    public Document(String name, Scanner scan) {
        this.name=name.toLowerCase();
        link=new TreeMap<>();
        load(scan);
    }
    public void load(Scanner scan) {
        while (scan.hasNext()) {
            String next = scan.next();
            if (next.equals("eod")) {
                return;
            }
            if (correctLink(next)) {
                Link linkToAdd = createLink(next);
                link.put(linkToAdd.ref, linkToAdd);
            }
        }
    }

    public static boolean isCorrectId(String id) {
        return id.matches("[a-zA-Z][a-zA-Z0-9_]*");
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    public static Link createLink(String link) {
        Link linkToAdd;
        if (link.split("=").length == 1) {
            if (link.contains("(") && link.contains(")")) {
                int weight = Integer.parseInt(link.split("\\(")[1].split("\\)")[0]);
                linkToAdd = new Link(link.split("\\(")[0], weight);
            } else {
                linkToAdd = new Link(link);
            }
        } else {

            if (link.contains("(") && link.contains(")")) {
                int weight = Integer.parseInt(link.split("\\(")[1].split("\\)")[0]);
                linkToAdd = new Link(link.split("=")[1].split("\\(")[0], weight);
            } else {
                linkToAdd = new Link(link.substring(5));
            }
        }
        return linkToAdd;
    }

    public static boolean correctLink(String link) {
        if (link.contains("(") && link.contains(")")) {
            try {
                int weight = Integer.parseInt(link.split("\\(")[1].split("\\)")[0]);
                if (weight < 1) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
            return link.substring(5).matches("[a-zA-Z][a-zA-Z0-9_\\(\\)]*");
        } else {
            return link.startsWith("link=") && link.substring(5).matches("[a-zA-Z][a-zA-Z0-9_]*");
        }
    }

    @Override
    public String toString() {
        String retStr="Document: "+name+"\n";
        //TODO?
        retStr+=link;
        return retStr;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String getName() {
        return name;
    }
}
