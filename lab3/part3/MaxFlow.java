import java.util.*;
import java.io.*;

public class MaxFlow {

    /**
     * Skapar en en Map utifrån input i readBipartiteGraph.
     */
    HashMap<Integer, ArrayList<Edge>> createFlowgraph(ArrayList<Edge> list, int v){
       HashMap<Integer, ArrayList<Edge>> grannlista;
        grannlista = new HashMap<>(v + 1);
        for (int i = 0; i < v + 1; i++) {
            grannlista.put(i, new ArrayList<Edge>());
        }
        
        for (Edge e:list) {
            int from = e.from;
            int to = e.to;
            int capacity = e.capacity;

            //Skapa kant åt båda håll, sätt referens till varandra
            Edge forward = new Edge(from, to, capacity, 0, true);
            Edge backward = new Edge(to, from, 0, 0, false);
            forward.setReverseEdge(backward);
            backward.setReverseEdge(forward);

            //Placera kanter i lista
            ArrayList<Edge> edgesFrom = grannlista.get(from);
            edgesFrom.add(forward);
            grannlista.put(from, edgesFrom);

            ArrayList<Edge> edgesTo = grannlista.get(to);
            edgesTo.add(backward);
            grannlista.put(to, edgesTo);

        }
        return grannlista;
    }

    void edmondsKarp(HashMap<Integer, ArrayList<Edge>> grannlista,int s,int t) {
        ArrayList<Edge> parents;
        int maxFlow = 0;
        do {
            Queue<Integer> que = new LinkedList<>();
            que.add(s);
            parents = new ArrayList<Edge>(grannlista.size());
            for(int i = 0; i < grannlista.size(); i++){
                parents.add(null);
            }
            while(!que.isEmpty()){
                int current = que.remove();
                for(Edge edge : grannlista.get(current)){
                    if(parents.get(edge.to) == null && edge.to != s && edge.capacity > edge.flow){
                        parents.set(edge.to, edge);
                        que.add(edge.to);
                    }
                }
            }
            //här har vi nu en stig. Dags att öka flow i alla kanter med minFlow
            if(parents.get(t) != null){
                int pathflow = Integer.MAX_VALUE;
                //Initialization, termination, incrementation
                for(Edge edge = parents.get(t); edge != null; edge = parents.get(edge.from)){
                    pathflow = Math.min(pathflow, edge.capacity - edge.flow); //öka med minsta rest cap
                }
                for(Edge edge = parents.get(t); edge != null; edge = parents.get(edge.from)){
                    edge.updateFlow(pathflow);
                }
                maxFlow += pathflow;
            }
        }
        while(parents.get(t) != null);
    }
    //Skapar en lista av kanterna som löser matchningsproblemet
    ArrayList<Edge> solution(int v, int s, int t, int e, HashMap<Integer, ArrayList<Edge>> grannlista ){
        ArrayList<Edge> posFlowEdges = new ArrayList<>();

        for(int i = 0; i <= grannlista.size(); i++){
            if(!grannlista.containsKey(i)){
                continue;
            }
            for(Edge edge: grannlista.get(i)){
                //tar med alla kanter som har flow, och som inte är kopplat till s eller t
                if(edge.flow > 0 && edge.direction == true && edge.from != s && edge.to != t){
                    posFlowEdges.add(edge);
                }
            }
        }
        return posFlowEdges;

    }
}
