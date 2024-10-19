package lab.five;

import java.util.ListIterator;
import java.util.Scanner;

public class Document{
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;
    public Document(String name, Scanner scan) {
        this.name=name.toLowerCase();
        link=new TwoWayCycledOrderedListWithSentinel<Link>();
        load(scan);
    }
    public void load(Scanner scan) {
        //TODO
        while (scan.hasNext()) {
            String next = scan.next();
            if (next.equals("eod")) {
                return;
            }
            if (correctLink(next)) {
                Link linkToAdd = createLink(next);
                link.add(linkToAdd);
            }
        }
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)


    public static boolean isCorrectId(String id) {
        return id.matches("[a-zA-Z][a-zA-Z0-9_]*");

    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    public static Link createLink(String link) {
        Link linkToAdd;
        if (link.contains("(") && link.contains(")")) {
            int weight = Integer.parseInt(link.split("\\(")[1].split("\\)")[0]);
            if(link.contains("="))
                linkToAdd = new Link(link.split("=")[1].split("\\(")[0], weight);
            else
                linkToAdd = new Link(link.split("\\(")[0], weight);
        } else {
            linkToAdd = new Link(link.substring(5));
        }
        return linkToAdd;
    }

    private static boolean correctLink(String link) {
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
        if (link.size == 0) {
            return "Document: " + name.toLowerCase();
        }

        String output = "Document: " + name.toLowerCase() + "\n";
        String line = "";

        for (Link l : link) {
            if (line.split(" ").length < 10) {
                line += l.toString() + " ";
            } else {
                line.trim();
                output += line + "\n";
                line = "";
                line += l.toString() + " ";
            }
        }
        output += line;
        return output;
    }

    public String toStringReverse() {
        if (link.size == 0) {
            return "Document: " + name.toLowerCase();
        }
        String output = "Document: " + name.toLowerCase() + "\n";
        String line = "";
        ListIterator<Link> iter = link.listIterator();
        while (iter.hasNext())
            iter.next();
        //TODO
        while (iter.hasPrevious()) {
            if (line.split(" ").length < 10) {
                line += iter.previous().toString() + " ";
            } else {
                line.trim();
                output += line + "\n";
                line = "";
                line += iter.previous().toString() + " ";
            }
        }
        output += line;
        return output;
    }


    public int[] getWeights() {
        int[] weights = new int[link.size];
        for(int i = 0; i<link.size(); i++){
            weights[i] = link.get(i).weight;
        }
        return weights;
    }

    public static void showArray(int[] arr) {
        // TODO
        String result = " ";
        for(int i =0; i<arr.length;i++){
            result += arr[i] + " ";
        }
        result= result.trim();
        System.out.println(result);
    }

    void bubbleSort(int[] arr) {
        int n = arr.length;
        for(int i=0; i<n-1;i++) {
            showArray(arr);
            for (int j = n-1; j >i; j--)
                if (arr[j] < arr[j -1 ]) {
                    int temp = arr[j];
                    arr[j] = arr[j -1];
                    arr[j -1] = temp;
                }
        }
        showArray(arr);
        //TODO
    }

    public void insertSort(int[] arr) {

        int n = arr.length;
        for(int i =n-2; i>-1; --i){
            showArray(arr);
            int key = arr[i];
            int j = i+1;
            while (j<n && arr[j]<key){
                arr[j-1] = arr[j];
                j=j+1;
            }
            arr[j-1] = key;
        }


        showArray(arr);
        //TODO

    }
    public void selectSort(int[] arr) {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = n-1; i >=1; i--)
        {
            showArray(arr);
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i-1; j >= 0; j--)
                if (arr[j] > arr[min_idx])
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
        showArray(arr);
    }
}