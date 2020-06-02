import com.sun.jdi.ArrayReference;

import javax.print.DocFlavor;
import java.awt.*;
import java.util.ArrayList;

public class MasonRules {
    public MasonRules(){

    }
    ArrayList<Point> getNonTouchingLoops(ArrayList<ArrayList<Integer>> allLoops){
        ArrayList<Point> nonTouchingLoops = new ArrayList<>();
        boolean touched;
        for (int i=0;i<allLoops.size()-1;i++){
            for (int j = i+1; j <allLoops.size() ; j++) {
                touched=false;
                for (int k = 0; k < allLoops.get(i).size(); k++) {
                    for (int l = 0; l <allLoops.get(j).size() ; l++) {
                        if(allLoops.get(i).get(k).compareTo(allLoops.get(j).get(l))==0){
                            touched = true;
                            break;
                        }
                    }
                    if(touched){
                        break;
                    }
                }
                if(!touched){
                    nonTouchingLoops.add(new Point(i,j));
                }
            }
        }
        return nonTouchingLoops;
    }

    int getGain(ArrayList<Integer> path,GraphAdjacencyMatrix graph){
        int gain=1;
        for (int i=0;i<path.size()-1;i++){
            gain*= graph.getWeight(path.get(i),path.get(i+1));
        }
        return gain;
    }


    void getAllLoops(ArrayList<ArrayList<Integer>> loops,GraphAdjacencyMatrix graph,int numOfVertices){
        for (int i = 0; i < numOfVertices; i++) {
            graph.printPaths(i,i,true,loops);
        }
    }

    int calculateDelta(ArrayList<ArrayList<Integer>>allLoops, ArrayList<Point> nonTouchingLoops,GraphAdjacencyMatrix graph){
        int delta =1;
        for (ArrayList<Integer> allLoop : allLoops) {
            delta -= getGain(allLoop, graph);
        }
        ArrayList<Integer> nonTouching = new ArrayList<>();
        for (Point nonTouchingLoop : nonTouchingLoops) {
            if (!nonTouching.contains(((int) nonTouchingLoop.getX()))) {
                nonTouching.add((int) nonTouchingLoop.getX());
            }
            if (!nonTouching.contains(((int) nonTouchingLoop.getY()))) {
                nonTouching.add((int) nonTouchingLoop.getY());
            }
        }
        for (int i = 0; i <nonTouching.size()-1 ; i++) {
            for (int j = i+1; j <nonTouching.size() ; j++) {
                delta += ( getGain(allLoops.get(i),graph) * getGain(allLoops.get(j),graph) );
            }
        }
        return delta;
    }
    ArrayList<Integer> calculateDeltaForPaths(ArrayList<ArrayList<Integer>> forwardPaths, ArrayList<ArrayList<Integer>>allLoops , GraphAdjacencyMatrix graph){
        ArrayList<Integer> DeltaForPaths = new ArrayList<>();
        for (ArrayList<Integer> forwardPath : forwardPaths) {
            ArrayList<ArrayList<Integer>> newAllLoops = new ArrayList<>(allLoops);
            for (int j = 0; j < allLoops.size(); j++) {
                for (int k = 0; k < allLoops.get(j).size(); k++) {
                    for (Integer integer : forwardPath) {
                        if (allLoops.get(j).get(k).equals(integer)) {
                            newAllLoops.remove(allLoops.get(j));
                            break;
                        }

                    }
                }
            }
            ArrayList<Point> nonTouching = getNonTouchingLoops(newAllLoops);

            DeltaForPaths.add(calculateDelta(newAllLoops, nonTouching, graph));

        }
        return DeltaForPaths;
    }
}
