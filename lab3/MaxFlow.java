
import java.util.*;

public class MaxFlow {

  public class Edge {
    int granne;
    int capacity;
    int flow;
  
    public Edge(int granne, int capacity) {
      this.granne = granne;
      this.capacity = capacity;
      this.flow = 0;
    }
  
    public int restCapacity() {
      return capacity - flow;
    }
  }

  // grannlistan(a,b)
  

  public static void main(String[] args) {
    MaxFlow mf = new MaxFlow();
    Kattio io;
    io = new Kattio(System.in, System.out);
  
    
    // läser input
    int v = io.getInt();
    int s = io.getInt();
    int t = io.getInt();
    int e = io.getInt();
    // hashmap
    ArrayList<ArrayList<Edge>> grannLista = new ArrayList<ArrayList<Edge>>(v);
    for (int i = 0; i < e; i++) {
      int a = io.getInt();
      int b = io.getInt();
      int capacity = io.getInt();
      if (grannLista.get(a) == null) {
        // a -> b
        grannLista.set(a, new ArrayList<>());
        ArrayList<Edge> tempA = grannLista.get(a);
        tempA.add(mf.new Edge(b, capacity));
      }
      // b -> a
      if (grannLista.get(b) == null) {
        grannLista.set(a, new ArrayList<>());
        ArrayList<Edge> tempB = grannLista.get(b);
        tempB.add(mf.new Edge(a, capacity));
      } else {
        // a <--> b
        grannLista.get(a).add(mf.new Edge(b, capacity));
        grannLista.get(b).add(mf.new Edge(a, capacity));
      }
    }
    // här börjar fordfulkersson
    // skapar residualgraf
    ArrayList<ArrayList<Edge>> residualGraf = new ArrayList<ArrayList<Edge>>(grannLista);
    ArrayList<Integer> parentList = new ArrayList<Integer>(v);
    int maxFlow = 0;

    // skapar residual graf.
    for (int i = 0; i <= grannLista.size(); i++) {
      for (int j = 0; j <= grannLista.get(i).size(); j++) {
        residualGraf.get(i).get(j).capacity = grannLista.get(i).get(j).capacity;
      }
    }
    while (mf.bfs(residualGraf, s, t, parentList, v)) {
      int pathFlow = Integer.MAX_VALUE;
      for (int node = t; node != s; node = parentList.get(node)) {
        int u = parentList.get(node);
        pathFlow = Math.min(pathFlow, residualGraf.get(u).get(node).capacity);
      }
      for (int node = t; node != s; node = parentList.get(node)) {
        int u = parentList.get(node);
        residualGraf.get(u).get(node).flow = residualGraf.get(u).get(node).capacity - pathFlow;
        residualGraf.get(node).get(u).flow = residualGraf.get(node).get(u).capacity + pathFlow;
      }
    }
  }

  // residualgraf,Edge s, Edge t, parentList<Edges>
  boolean bfs(ArrayList<ArrayList<Edge>> residualGraf, int source, int sink, ArrayList<Integer> parentList, int v) {

    ArrayList<Boolean> visited = new ArrayList<Boolean>(v);
    Collections.fill(visited, Boolean.FALSE);
    LinkedList<Integer> queue = new LinkedList<Integer>();
    queue.add(source);
    visited.set(source, true);
    parentList.set(source, -1);
    while (queue.size() != 0) {
      int pop = queue.poll();
      for (int i = 0; i <= v; i++) {
        if (visited.get(i) == false && residualGraf.get(pop).get(i).capacity > 0) {
          queue.add(i);
          parentList.set(i, pop);
          visited.set(i, true);
        }
      }
    }
    // Om vi når sink från källa return true, else false
    return (visited.get(sink));
  }
}
