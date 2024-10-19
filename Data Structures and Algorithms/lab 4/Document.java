package labfour;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

public class Document {

	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;

	public Document(String name, Scanner scanner) {
		this.name = name.toLowerCase();
		link = new TwoWayCycledOrderedListWithSentinel<Link>();
		load(scanner);
	}

	public void load(Scanner scan) {
		String input;
		while (!(input = scan.nextLine()).equalsIgnoreCase("eod")) {
			String[] arr = input.split("\\s+");
			for (String word : arr) {
				if (word.equalsIgnoreCase("eod")) {
					return;
				}
				if (isCorrectLink(word.toLowerCase())) {
					link.add(createLink(word.toLowerCase().substring(5)));
				}
			}
		}
	}

	public boolean isCorrectLink(String link) {
		return link.toLowerCase().matches("link=[a-z]\\w*") || (link.matches("link=[a-z0-9]*\\([0-9]*\\)") && !link.matches("link=[a-z0-9]*\\(0\\)"));
	}

	public static boolean isCorrectId(String id) {
		return id.toLowerCase().matches("[a-z]\\w*");
	}


	public static Link createLink(String link) {
		if (link.matches("[a-z0-9]*\\([0-9]*\\)")) {
			int start = link.indexOf("(") + 1;
			String key = link.toLowerCase().substring(0, start - 1);
			int weight = Integer.parseInt(link.substring(start, link.length() - 1));
			return new Link(key, weight);
		} else {
			return new Link(link.toLowerCase());
		}
	}

	 @Override
    public String toString() {
        StringBuilder outputBuilder = new StringBuilder();
        Iterator<Link> linkIterator = link.iterator();
        outputBuilder.append("TextDocument: " + name);
        int lineCounter = 10;
        while (linkIterator.hasNext()) {
            String separator = "";
            if (lineCounter == 10) {
                separator = "";
                outputBuilder.append("\n");
            } else if (lineCounter != 0) {
                separator = " ";
            } else {
                separator = "\n";
            }
            lineCounter--;
            outputBuilder.append(separator).append(linkIterator.next().toString());
        }
        return outputBuilder.toString();
    }
	
	    public String toStringReverse() {
	        String resultStr = "TextDocument: " + name;
	        return resultStr + link.toStringReverse();
	    }
	}