import java.util.*;

public class MaxFlow {
  Kattio io;

  // grannlistan(a,b)

  private class Edge {
    int granne;
    int capacity;
    int flow;

    public Edge(int granne, int capacity) {
      this.granne = granne;
      this.capacity = capacity;
      this.flow = 0;
    }
  }

  void fordFulkersson() {
        io = new Kattio(System.in, System.out);
        // läser input
        int v = io.getInt();
        io.println(v);
        // System.out.println(v);
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
            grannLista.set(a, new ArrayList<>());
            ArrayList<Edge> temp = grannLista.get(a);
            temp.add(new Edge(b, capacity));
          } else {
            grannLista.get(a).add(new Edge(b, capacity));
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

    while (bfs(residualGraf, s, t, parentList, v)) {
      int path_flow = 9999999;
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
    return (visited.set(sink, true));

    // int visited[v];
    // memset(visited,0,sizeof(visited));
    // visited list
    // queue Edges

  }

  public static void main(String[] args) {
    MaxFlow mf = new MaxFlow();


    
  }

}
