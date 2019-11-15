
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MaxFlow {

  public class Edge {

    int from;
    int to;
    int capacity;
    int flow;
    Edge reverseEdge;
    Boolean direction; //true::=forward

    public Edge(int from, int to, int capacity){
      this.from = from;
      this.to = to;
      this.capacity = capacity;
      this.flow = 0;
      this.reverseEdge = null;
      this.direction = null;
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
    // se om ge fixed size till map.
    HashMap<Integer, ArrayList<Edge>> grannMap = new HashMap<Integer, ArrayList<Edge>>();
    HashMap<Integer, ArrayList<Edge>> residualGraf = new HashMap<Integer, ArrayList<Edge>>();
    System.out.println("before input");
    /*
     * skapar två identiska grafer som inehpller kanter och bakåtkanter.
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
      backward.reverseEdge = forward;
      backward.direction = false;
      forward.reverseEdge = backward;
      forward.direction = true;

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
    ArrayList<Edge> parents = new ArrayList<Edge>();
    int maxFlow = 0;
    while (mf.bfs(residualGraf, s, t, parents, v)) {
      int pathFlow = Integer.MAX_VALUE;
      for(Edge edge : parents){
        if(edge.flow > 0){
          pathFlow = Math.min(pathFlow, edge.capacity - edge.flow);
        }
        else{
          continue;
        }
      }
      for(Edge edge : parents){
        edge.flow += pathFlow;
        edge.reverseEdge.capacity = edge.reverseEdge.capacity - edge.flow;

      }
      maxFlow += pathFlow;
    }
    io.close();
  }

  // residualgraf,Edge s, Edge t, parentList<Edges>
  boolean bfs(HashMap<Integer, ArrayList<Edge>> residualGraf, int source, int sink, ArrayList<Edge> parents, int v) {
    boolean[] visited = new boolean[v];
    Arrays.fill(visited, false);
    LinkedList<Integer> queue = new LinkedList<Integer>();

    queue.add(source);
    visited[source] = true;
    parents.add(null);

    while (queue.size() != 0) {
      int currentNode = queue.poll();
      if (currentNode == sink) {
        return false;
      }
      for (Edge edge : residualGraf.get(currentNode)) {
        if (visited[edge.to] = false) {
          visited[edge.to] = true;
          parents.add(edge);
          queue.add(edge.to);
        }
      }
    }
    return true;
  }
}
