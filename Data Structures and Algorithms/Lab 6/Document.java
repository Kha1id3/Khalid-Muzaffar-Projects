package lab.six;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.Scanner;

public class Document {
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

    public void iterativeMergeSort(int[] arr) {

        int n = arr.length;
        int actSize;
        int startIndex;
        for(actSize = 1; actSize<=n-1;actSize *=2)
        {
            showArray(arr);
            for(startIndex=0; startIndex < n-1; startIndex += 2*actSize)
            {
                int mid = Math.min(startIndex + actSize -1,n-1);
                int endIndex = Math.min(startIndex + 2*actSize-1,n-1);
                merge(arr,startIndex,mid, endIndex);
            }
        }
        showArray(arr);
        //TODO
    }

    public static void merge(int[] arr ,int startIndex, int mid, int endIndex){

        int i,j,k;
        int n1= mid - startIndex +1;
        int n2 = endIndex - mid;

        int left[] = new int[n1];
        int right[] = new int[n2];

        for (i = 0; i < n1; i++)
            left[i] = arr[startIndex + i];
        for (j = 0; j < n2; j++)
            right[j] = arr[mid + 1+ j];

        i=0;
        j=0;
        k=startIndex;
        while (i<n1 && j<n2)
        {
            if(left[i] <= right[j])
            {
                arr[k] = left[i];
                i++;
            }
            else
            {
                arr[k] = right[j];
                j++;
            }
            k++;
        }
        //copying remaining elements
        while (i < n1)
        {
            arr[k] = left[i];
            i++;
            k++;
        }
        //copying remaining elements
        while (j < n2)
        {
            arr[k] = right[j];
            j++;
            k++;
        }
    }


//    public void radixSort(int[] arr){
//        int n = arr.length;
//        for (int d = 0; d<3; d++) {
//            showArray(arr);
//            countingSort(arr,10);
//        }
//        showArray(arr);
//        //TODO
//    }
//
//    public static void countingSort(int arr[], int k){
//        k++;
//        int n = arr.length;
//        int pos[] = new int[k];
//        int result[] = new int [n];
//        int i,j;
//        for(i = 0; i< k ; i++)
//            pos[i]=0;
//        for(j=0;j<n;j++)
//            pos[arr[j]]++;
//        pos[0]--;
//        for(i=1;i<k;i++)
//            pos[i]+=pos[i-1];
//        for( j=n-1;j>=0;j--){
//            result[pos[arr[j]]] = arr[j];
//            pos[arr[j]]--;
//        }
//        for(j=0; j<n; j++)
//            arr[j] = result[j];
//    }


    public void radixSort(int[] arr) {
        showArray(arr);
        int n = arr.length;
        for (int exp = 1; exp<1000; exp *= 10) {
            countSort(arr, n, exp);
            showArray(arr);
        }
    }

    static void countSort(int arr[], int n, int exp)
    {
        int output[] = new int[n]; // output array
        int i;
        int count[] = new int[10];

        for(int x=0;x<10;x++)
            count[x]=0;

        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;

        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        for (i = 0; i < n; i++)
            arr[i] = output[i];
    }

}