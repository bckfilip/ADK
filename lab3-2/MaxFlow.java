
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MaxFlow {

  public class Edge {

    int from;
    int to;
    int capacity;
    int flow;

    public Edge(int from, int to, int capacity) {
      this.from = from;
      this.to = to;
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
    //se om ge fixed size till map.
    HashMap<Integer, ArrayList<Edge>> grannMap = new HashMap<Integer, ArrayList<Edge>>();
    HashMap<Integer, ArrayList<Edge>> residualGraf = new HashMap<Integer, ArrayList<Edge>>();
    System.out.println("before input");
    /*
      skapar två identiska grafer som inehpller kanter och bakåtkanter.    
    */
    int v = io.getInt();
    int s = io.getInt();
    int t = io.getInt();
    int e = io.getInt();
    for (int i = 0; i < e; i++) {
      System.out.println("creating maps");
      int from = io.getInt();
      int to = io.getInt();
      int capacity = io.getInt();
      Edge forward = mf.new Edge(from, to, capacity);
      Edge backward = mf.new Edge(to, from, capacity);
      if (!grannMap.containsKey(from)) {
        ArrayList<Edge> edgeFrom = new ArrayList<Edge>();
        ArrayList<Edge> edgeFromR = new ArrayList<Edge>();
        edgeFrom.add(forward);
        edgeFromR.add(forward);
        grannMap.put(from, edgeFrom);
        residualGraf.put(from, edgeFromR);
        if (!grannMap.containsKey(to)) {
          ArrayList<Edge> edgeTo = new ArrayList<Edge>();
          ArrayList<Edge> edgeToR = new ArrayList<Edge>();
          edgeTo.add(backward);
          edgeToR.add(backward);
          grannMap.put(to, edgeTo);
          residualGraf.put(to, edgeToR);
        } else {
          grannMap.get(to).add(backward);
          residualGraf.get(to).add(backward);
        }
      } else {
        grannMap.get(from).add(forward);
        residualGraf.get(from).add(forward);
        if (!grannMap.containsKey(to)) {
          ArrayList<Edge> edgeTo = new ArrayList<Edge>();
          ArrayList<Edge> edgeToR = new ArrayList<Edge>();
          edgeTo.add(backward);
          edgeToR.add(backward);
          grannMap.put(to, edgeTo);
          residualGraf.put(to, edgeToR);
        } else {
          grannMap.get(to).add(backward);
          residualGraf.get(to).add(backward);
        }
      }

    }

    // här börjar fordfulkersson
    ArrayList<Integer> parentList = new ArrayList<Integer>(Collections.nCopies(v + 1, Integer.MAX_VALUE));
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
  boolean bfs(HashMap<Integer, ArrayList<Edge>> residualGraf, int source, int sink, ArrayList<Integer> parentList,
      int v) {
    System.out.println("in bfs");
    ArrayList<Boolean> visited = new ArrayList<Boolean>(Collections.nCopies(v + 1, Boolean.FALSE));
    LinkedList<Integer> queue = new LinkedList<Integer>();

    queue.add(source);
    visited.set(source, true);
    parentList.set(source, -1);
    while (queue.size() != 0) {
      System.out.println("in while");
      int pop = queue.poll();
      for (int i = 0; i <= v; i++) {
        if (visited.get(i) == false) {
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
