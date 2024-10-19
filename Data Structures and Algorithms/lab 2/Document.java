package dsalab2;

import dsalab2.Link;
import dsalab2.OneWayLinkedList;

import java.util.Scanner;

public class Document{
    public String name;
    public OneWayLinkedList<Link> links;

    public Document(String name, Scanner scan) {
        // TODO
        this.name=name;
        links = new OneWayLinkedList<>();
        load(scan);
    }

    public void load(Scanner scan) {
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] words = line.split(" ");
            for (String s : words) {
                if (s.equals("eod"))
                    return;
                if (s.length() > 5 && s.substring(0, 5).toLowerCase().equals("link=")) {
                    String link = s.substring(5);
                    if (correctLink(link)) {
                        for (String c : link.split("")) {
                            links.add(new Link(c));
                        }
                    }
                }
            }
        }
    }
    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    private static boolean correctLink(String link) {

        if(Character.isLetter(link.charAt(0))|| Character.isDigit(link.charAt(0))){
            String[] letters = link.split("");
            for(String s: letters){
                if(Character.isLetter(s.charAt(0)) || Character.isDigit(s.charAt(0)) || s.equals("_"))
                    return true;
            }
            return false;
        }
        return false;


    }

    @Override
    
    public String toString() {
        String s = "Document: " + name;
        for (Link link : links) {
            s += "\n" + link.ref;
        }
        return s;
    }

}