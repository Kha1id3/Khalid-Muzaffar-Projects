package lab10;


import java.util.*;

public class Graph {
    int[][] arr;
    //TODO? Collection to map Document to index of vertex
    // You can change it
    HashMap<String, Integer> name2Int;
    @SuppressWarnings("unchecked")
    //TODO? Collection to map index of vertex to Document
    // You can change it

    HashMap<Integer, String> int2Name;

    // The argument type depend on a selected collection in the Main class
    public Graph(SortedMap<String,Document> internet){
        int size = internet.size();
        this.arr = new int[size][size]; //create adjacency matrix
        for(int i  = 0; i < this.arr.length; i++ ) {
            for(int j = 0; j < this.arr[i].length; j++) {
                if(i == j) {
                    this.arr[i][j] = 0;  //put 0 on diagonal
                }
                else {
                    this.arr[i][j] = -1; //put infinity (-1) on other places
                }
            }
        }

        this.name2Int = new HashMap<>();
        this.int2Name = new HashMap<>();

        int counter = 0;
        for (String internetString : internet.keySet()) { //put keys from the Internet to the HashMaps
            this.name2Int.put(internetString, counter); //string as a key
            this.int2Name.put(counter, internetString); //counter as a key
            counter++;
        }

        for (Document internetDocument : internet.values()) //put proper weights in adjacency matrix
            for (Link l : internetDocument.link.values())
                if (internet.containsKey(l.ref))
                    if (!this.name2Int.get(internetDocument.name).equals(this.name2Int.get(l.ref)))
                        this.arr[this.name2Int.get(internetDocument.name)][this.name2Int.get(l.ref)] = l.weight;
    }

    // used colors:
    // 0 - white, 1 - gray, 2 - black
    public String bfs(String start) {
        if (!this.name2Int.containsKey(start))
            return null;
        String result = "";
        int[] arrayOfColors = new int[this.arr.length];
        int counter = 0;
        while (counter < arrayOfColors.length) {
            arrayOfColors[counter] = 0;
            counter++;
        }
        arrayOfColors[this.name2Int.get(start)] = 1;
        Queue<Integer> q = new LinkedList<>();
        q.add(this.name2Int.get(start));

        while (!q.isEmpty()) {
            int current = q.poll();
            counter = 0;
            while (counter < this.arr.length) {
                if (this.arr[current][counter] > 0)
                    if (arrayOfColors[counter] == 0) {
                        q.add(counter);
                        arrayOfColors[counter] = 1;
                    }
                counter++;
            }
            arrayOfColors[current] = 2;
            result += this.int2Name.get(current) + ", ";
        }
        return result.substring(0, result.length()-2);
    }

    public String dfs(String start) { //recursive version
        if (!this.name2Int.containsKey(start))
            return null;
        StringBuilder result = new StringBuilder();
        boolean[] wasVisited = new boolean[this.arr.length];
        dfsVisit(this.name2Int.get(start), wasVisited, result);
        return result.substring(0, result.length()-2);
    }

    private void dfsVisit(int k, boolean[] visited, StringBuilder result) {
        if (!visited[k]) {
            visited[k] = true;
            result.append(this.int2Name.get(k)).append(", ");
            int counter=0;
            while (counter < this.arr.length) {
                if (this.arr[k][counter] >=0 && !visited[counter])
                    dfsVisit(counter, visited, result);
                counter++;
            }
        }

    }

    public int connectedComponents() {
        if(arr.length == 0) {
            return 0;
        }
        DisjointSetForest components = new DisjointSetForest(this.arr.length);
        for(int i = 0; i < this.arr.length; i++) {
            components.makeSet(i);
        }
        for(int i = 0; i < this.arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                if(arr[i][j] > 0) {
                    components.union(i, j);
                }
            }
        }
        return components.countSets();
    }
}