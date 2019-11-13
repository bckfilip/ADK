
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

  public static void main(String[] args) throws FileNotFoundException {
    System.setIn(new FileInputStream("test1.txt"));
    System.out.println("first line in main");
    MaxFlow mf = new MaxFlow();
    Kattio io;
    io = new Kattio(System.in, System.out);
    HashMap<Integer, ArrayList<Edge>> grannMap = new HashMap<Integer, ArrayList<Edge>>(); // kan gå sanbbare att ge fixed sized
    HashMap<Integer, ArrayList<Edge>> residualGraf = new HashMap<Integer, ArrayList<Edge>>();
    System.out.println("before input");
    // läser input
    int v = io.getInt();
    int s = io.getInt();
    int t = io.getInt();
    int e = io.getInt();
    for (int i = 0; i < e; i++) {
      System.out.println("creating maps");
      int a = io.getInt();
      int b = io.getInt();
      int capacity = io.getInt();
      // a -> b
      if (!(grannMap.containsKey(a))) {
        ArrayList<Edge> value = new ArrayList<Edge>();
        grannMap.put(a, value);
        residualGraf.put(a, value);
        Edge tmpEdge = mf.new Edge(b, capacity);
        grannMap.get(a).add(tmpEdge);
        residualGraf.get(a).add(tmpEdge);
        if(!(grannMap.containsKey(b))){
          ArrayList<Edge> val = new ArrayList<Edge>();
          grannMap.put(a, val);
          residualGraf.put(a, val);
          Edge tmp = mf.new Edge(b, capacity);
          grannMap.get(a).add(tmp);
          residualGraf.get(a).add(tmp);
        }else{
          Edge tEdge = mf.new Edge(b, capacity);
          grannMap.get(a).add(tEdge);
          residualGraf.get(a).add(tEdge);
          tmpEdge = mf.new Edge(a, capacity);
          grannMap.get(b).add(tmpEdge);
          residualGraf.get(b).add(tmpEdge);
        }
      } // b -> a
      else {
        System.out.println("else");
        Edge tmpEdge = mf.new Edge(b, capacity);
        grannMap.get(a).add(tmpEdge);
        residualGraf.get(a).add(tmpEdge);
        tmpEdge = mf.new Edge(a, capacity);
        grannMap.get(b).add(tmpEdge); // nullpointer om b inte finns
        residualGraf.get(b).add(tmpEdge);
        // check for B
      }
    }
    // här börjar fordfulkersson
    ArrayList<Integer> parentList = new ArrayList<Integer>(Collections.nCopies(v + 1,Integer.MAX_VALUE));
    int maxFlow = 0;
    System.out.println("calling bfs");
    while (mf.bfs(residualGraf, s, t, parentList, v)) {
      System.out.println("rad 73");
      int pathFlow = Integer.MAX_VALUE;
      for (int node = t; node != s; node = parentList.get(node)) {
        System.out.println("rad 76");
        int u = parentList.get(node);
        pathFlow = Math.min(pathFlow, residualGraf.get(u).get(node).capacity);
      }
      for (int node = t; node != s; node = parentList.get(node)) {
        System.out.println("rad 81");
        int u = parentList.get(node);
        residualGraf.get(u).get(node).flow = residualGraf.get(u).get(node).capacity - pathFlow;
        residualGraf.get(node).get(u).flow = residualGraf.get(node).get(u).capacity + pathFlow;
      }
    }
    io.close();
  }
  // residualgraf,Edge s, Edge t, parentList<Edges>
  boolean bfs(HashMap<Integer, ArrayList<Edge>> residualGraf, int source, int sink, ArrayList<Integer> parentList, int v) {
    System.out.println("in bfs");
    ArrayList<Boolean> visited = new ArrayList<Boolean>(Collections.nCopies(v + 1,Boolean.FALSE));
    LinkedList<Integer> queue = new LinkedList<Integer>();

    queue.add(source);
    visited.set(source, true);
    parentList.set(source, -1);
    while (queue.size() != 0) {
      System.out.println("in while");
      int pop = queue.poll();
      for (int i = 0; i <= v; i++) {
        if(visited.get(i) == false ){
          if (residualGraf.get(pop).get(i).capacity > 0) {
            queue.add(i);
            parentList.set(i, pop);
            visited.set(i, true);
          }
        }
      
      }
    }
    // Om vi når sink från källa return true, else false
    return (visited.get(sink));
  }
}
