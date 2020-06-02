import java.awt.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
       MasonRules solver = new MasonRules();
        System.out.println("Enter the number of vertices: ");
        int v = sc.nextInt();
        GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(v);
        System.out.println("Enter the number of edges: ");
        int e = sc.nextInt();
        int start,end;
        System.out.println("Enter the start node: ");
        start = sc.nextInt();
        System.out.println("Enter the end node: ");
        end = sc.nextInt();
        System.out.println("Enter the edges: <to> <from> <weight>");
        int count=1,to,from,weight;
        while (count <= e) {
            to = sc.nextInt();
            from = sc.nextInt();
            weight=sc.nextInt();
            graph.addEdge(to-1, from-1,weight );
            count++;
        }
        graph.printGraph();
       ArrayList<ArrayList<Integer>> forwardPaths = new ArrayList<ArrayList<Integer>>();
       graph.printPaths(start-1,end-1,false,forwardPaths);

        ArrayList<ArrayList<Integer>> allLoops = new ArrayList<ArrayList<Integer>>();
        solver.getAllLoops(allLoops,graph,v);

        ArrayList<Point> nonTouchingLoops =new ArrayList<Point>();
        nonTouchingLoops= solver.getNonTouchingLoops(allLoops);
        ArrayList<Integer> forwardPathsGain = new ArrayList<Integer>();
        for (int i = 0; i <forwardPaths.size() ; i++) {
            forwardPathsGain.add(solver.getGain(forwardPaths.get(i),graph));
        }
        ArrayList<Integer> DeltaForPaths = solver.calculateDeltaForPaths(forwardPaths,allLoops,graph);


        double overallTF=0.0;
        for (int i = 0; i <forwardPaths.size() ; i++) {
            overallTF +=  (forwardPathsGain.get(i) * DeltaForPaths.get(i));
        }
        float Delta = solver.calculateDelta(allLoops,nonTouchingLoops,graph);
        overallTF = overallTF / Delta;
        for(ArrayList<Integer> path : forwardPaths){
            for(int i =0;i<path.size();i++){
                path.set(i,path.get(i)+1);
            }
        }
        System.out.print("Forward paths :\n"+forwardPaths + "\n");
        for(ArrayList<Integer> path : allLoops){
            for(int i =0;i<path.size();i++){
                path.set(i,path.get(i)+1);
            }
        }
        System.out.print("All loop paths :\n"+allLoops + "\n");
        System.out.print("answer :\n"+overallTF + "\n");    }



}