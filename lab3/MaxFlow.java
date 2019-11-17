import java.util.*;
import java.io.*;

public class MaxFlow {
    private static Kattio io;
    private static int s;
    private static int t;
    private static int v;
    private static HashMap<Integer, ArrayList<Edge>> grannlista;

    /**
     * Create a flowgraph with input from matchproblem.
     */

    private static void createFlowgraph() throws FileNotFoundException {
        System.setIn(new FileInputStream("test1.txt"));
        io = new Kattio(System.in, System.out);

        v = io.getInt();
        grannlista = new HashMap<>(v + 1);
        for (int i = 0; i < v + 1; i++) {
            grannlista.put(i, new ArrayList<Edge>());
        }
        s = io.getInt();
        t = io.getInt();
        int e = io.getInt();
        for (int i = 0; i < e; i++) {
            int from = io.getInt();
            int to = io.getInt();
            int capacity = io.getInt();

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
    }

    private static int edmondsKarp() {
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
            if(parents.get(t) != null){
                int pathflow = Integer.MAX_VALUE;
                //Initialization, termination, incrementation
                for(Edge edge = parents.get(t); edge != null; edge = parents.get(edge.from)){
                    pathflow = Math.min(pathflow, edge.capacity - edge.flow);
                }
                for(Edge edge = parents.get(t); edge != null; edge = parents.get(edge.from)){
                    edge.updateFlow(pathflow);
                }
                maxFlow += pathflow;
            }
        }
        while(parents.get(t) != null);
        return maxFlow;
    }

    public static void main(String [] args) throws FileNotFoundException {
        createFlowgraph();
        int maxflow = edmondsKarp();
        System.out.println(maxflow);
    }
}
