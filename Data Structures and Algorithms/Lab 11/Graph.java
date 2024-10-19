package lab.eleven;

import java.util.*;

public class Graph {
    int arr[][];
    HashMap<String, Integer> name2Int;
    @SuppressWarnings("unchecked")
    Map.Entry<String, Document>[] arrDoc = (Map.Entry<String, Document>[]) new Map.Entry[0];

    public Graph(SortedMap<String, Document> internet) {
        int size = internet.size();
        arr = new int[size][size];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (i == j) {
                    arr[i][j] = 0;
                } else {
                    arr[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        int counter = 0;
        this.name2Int = new HashMap<String, Integer>();
        for (Map.Entry<String, Document> entry : internet.entrySet()) {
            name2Int.put(entry.getValue().getName(), counter);
            counter++;
        }
        for (Map.Entry<String, Document> document : internet.entrySet()) {
            SortedMap<String, Link> links = document.getValue().link;
            for (Map.Entry<String, Link> link : links.entrySet()) {
                if (internet.containsKey(link.getValue().ref)) {
                    int node = name2Int.get(document.getValue().getName());
                    int edge = name2Int.get(link.getValue().ref);
                    if (node != edge) {
                        arr[node][edge] = link.getValue().weight;
                    }
                }
            }
        }
    }

    public String bfs(String start) {
        if (!name2Int.containsKey(start)) {
            return null;
        }
        int first = name2Int.get(start);
        String result = "";
        int white = 0;
        int grey = 1;
        int black = 2;
        HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();
        for (int i = 0; i < arr.length; i++) {
            colors.put(i, white);
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(first);
        colors.put(first, grey);
        int temp;
        while (!queue.isEmpty()) {
            temp = queue.poll();
            ArrayList<Integer> neighbors = new ArrayList<Integer>();
            for (int i = 0; i < arr[temp].length; i++) {
                if (arr[temp][i] != 0 && arr[temp][i] != Integer.MAX_VALUE) {
                    neighbors.add(i);
                }
            }
            for (int node : neighbors) {
                if (colors.get(node) == white) {
                    colors.put(node, grey);
                    queue.offer(node);
                }
            }
            colors.put(temp, black);
            for (Map.Entry<String, Integer> entry : name2Int.entrySet()) {
                if (entry.getValue().equals(temp)) {
                    result += entry.getKey() + ", ";
                    break;
                }
            }
        }
        return result.substring(0, result.length() - 2);
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
            result.append(this.arrDoc).append(", ");
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

    public String DijkstraSSSP(String startVertexStr) {
        //check if contains such a node
        if(!name2Int.containsKey(startVertexStr)) {
            return null;
        }
        int size = name2Int.size();
        //initialize fill distances array
        int[] distArr = new int[size];
        for(int i = 0; i< distArr.length; i++){
            distArr[i] = Integer.MAX_VALUE;
        }
        int white = 0;
        int grey = 1;
        int black = 2;
        //initialize and fill colours array
        int[] colours = new int[size];
        for(int i = 0; i< colours.length; i++){
            colours[i] = white;
        }
        //here will be sotred paths to nodes
        @SuppressWarnings("unchecked")
		ArrayList<Integer>[] path = new ArrayList[size];
        for(int i = 0; i < path.length; i++) {
            path[i] = new ArrayList<>();
        }
        int start = name2Int.get(startVertexStr);
        int current = start;
        //find neighbours of first node and set their paths
        ArrayList<Integer> neighbours = new ArrayList<>();
        for(int i = 0; i < arr[start].length; i++) {
            if(arr[start][i] != 0 && arr[start][i] != Integer.MAX_VALUE) {
                neighbours.add(i);
                path[i].add(start);
                path[i].add(i);
            }
        }
        //colour the starting node
        distArr[start] = 0;
        colours[start] = black;
        if(neighbours.size() == 0) {
            String paths = "";
            for(int i = 0; i < size; i++) {
                //for starting node
                if(colours[i] == black) {
                    String temp = "";
                    temp+=getKey(name2Int, i)+"=0";
                    paths+=temp+"\n";
                }
                else {
                    paths+="no path to "+getKey(name2Int, i)+"\n";
                }
            }
            return paths;
        }
        int minNum = Integer.MAX_VALUE;
        int minIndex = -1;
        //change color of new visited nodes to gray
        for(int i = 0; i < size; i++) {
            if(neighbours.contains(i)) {
                distArr[i] = arr[start][i];
                colours[i] = grey;
                if(distArr[i] < minNum) {
                    minNum = distArr[i];
                    minIndex = i;
                }
            }
        }
        //remove current node from neighbours
        neighbours.remove(Integer.valueOf(current));


        current = minIndex;
        while(neighbours.size() > 0) {
            for(int i = 0; i < arr[current].length; i++) {
                //check if has connection and if hasnt been visited
                if(arr[current][i] != 0 && arr[current][i] != Integer.MAX_VALUE && colours[i] == white) {
                    neighbours.add(i);
                    colours[i] = grey;
                }
            }
            for(int i = 0; i < size; i++) {
                //update distances and paths
                if(arr[current][i] != Integer.MAX_VALUE && distArr[i] > distArr[current] + arr[current][i]) {
                    distArr[i] = distArr[current] + arr[current][i];
                    colours[i] = grey;
                    path[i] = new ArrayList<>(path[current]);
                    path[i].add(i);
                }
            }
            minNum = Integer.MAX_VALUE;
            minIndex = -1;
            //update neighbours and pick minimum one
            for(int i = 0; i < size; i++) {
                if(neighbours.contains(i)) {
                    colours[i] = grey;
                    if(distArr[i] < minNum) {
                        minNum = distArr[i];
                        minIndex = i;
                    }
                }
            }
            //remove current node from neighbours

            neighbours.remove(Integer.valueOf(current));


            colours[current] = black;
            current = minIndex;

        }

        String result = "";
        for(int i = 0; i < size; i++) {
            if(colours[i] == black) {
                String temp = "";
                if(distArr[i] == 0) {
                    temp+=getKey(name2Int, i)+"=0";
                }
                else {
                    for(Integer node: path[i]) {
                        temp+=(getKey(name2Int, node))+"->";
                    }
                    temp = temp.substring(0, temp.length()-2);
                    temp+="="+distArr[i];
                }
                result+=temp+"\n";
            }
            else {
                result+="no path to "+getKey(name2Int, i)+"\n";
            }
        }
        return result;
    }

    private String getKey(HashMap<String, Integer> map, Integer key) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(key)) {
                return entry.getKey();
            }
        }
        return null;
    }
}