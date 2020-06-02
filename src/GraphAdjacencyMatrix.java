import javax.print.DocFlavor;
import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.List;

public class GraphAdjacencyMatrix {
    int vertex;
    int matrix[][];



    public  GraphAdjacencyMatrix(int vertex) {
        this.vertex = vertex;
        matrix = new int[vertex][vertex];
    }

    public void addEdge(int source, int destination) {
        //add edge
        matrix[source][destination]=1;

        //add bak edge for undirected graph
        //matrix[destination][source] = 1;
    }
    public void addEdge(int source,int destination,int weight){
        matrix[source][destination]=weight;
    }
    public long getWeight(int source,int destination){
        return matrix[source][destination];
    }

    public void printGraph() {
        System.out.println("Graph: (Adjacency Matrix)");
        for (int i = 0; i < vertex; i++) {
            for (int j = 0; j <vertex ; j++) {
                System.out.print(matrix[i][j]+ " ");
            }
            System.out.println();
        }
        for (int i = 0; i < vertex; i++) {
            System.out.print("Vertex " + (i+1) + " is connected to:");
            for (int j = 0; j <vertex ; j++) {
                if(matrix[i][j]!=0){
                    System.out.print((j+1) + " ");
                }
            }
            System.out.println();
        }
    }

    void printPaths(int source,int destination,boolean cycles,ArrayList<ArrayList<Integer>> list){
        boolean visited[]=new boolean[vertex];
        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(source);
        printPathsRec(source,visited,destination,path,cycles,list);

    }
    void printPathsRec(int v,boolean visited[],int destination , List<Integer> path,boolean cycles,ArrayList<ArrayList<Integer>> test)
    {
        // Mark the current node as visited and print it
        if(cycles){
            cycles=false;
           for (int i =0; i<vertex ; i++){
               if(matrix[v][i]!=0){

                   path.add(i);
                   printPathsRec(i,visited,destination,path,cycles,test);
                   path.remove((Object)i);
               }
           }
        }
        else {
            visited[v] = true;
            //if you reach destination node
            if (v == destination) {
            //    System.out.println(path);
                ArrayList<Integer>  temp = new ArrayList<Integer>();
                temp.addAll(path);
                int count=0;
                for (int i=0;i<test.size();i++){
                    if(count==temp.size()){
                        break;
                    }
                    count=0;

                    for(int j=0;j<temp.size();j++){
                        if(temp.size()==test.get(i).size()){
                            for (int k = 0; k < test.get(i).size(); k++) {
                                if (temp.get(j).compareTo(test.get(i).get(k)) == 0) {
                                    count++;
                                    if(count==temp.size()){
                                        break;
                                    }
                                }
                            }
                        }
                        if(count==temp.size()){
                            break;
                        }
                    }
                }
                if(count==temp.size()){
                    visited[v] = false;
                    return;
                }
                test.add((ArrayList<Integer>) temp);
                count=0;
                visited[v] = false;
                return;
            }

            // Recur for all the vertices adjacent to this vertex
            for (int i = 0; i < vertex; i++) {
                if (matrix[v][i] != 0 && !visited[i]) {
                    path.add(i);
                    printPathsRec(i, visited, destination, path, cycles,test);
                    path.remove(path.lastIndexOf((Object)i));
                    //path.remove((Object) i);

                }
            }
            visited[v] = false;
        }
    }

}