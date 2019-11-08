import java.util.*;

public class MaxFlow{
  //grannlistan(a,b)
  
  private class Node {
    int granne;
    int capacity;
    public Node(int granne, int capacity) {
      this.granne = granne;
      this.capacity = c;
  }
  
  void readInput(){
  
  //ArrayList<Edge> grannLista = new ArrayList<>();
  
  //grannLista.add(new ArrayList<Node>());
  int v = io.getInt();
  int s = io.getInt();
  int t = io.getInt();
  int e = io.getInt();
  //hashmap
  ArrayList<ArrayList<Node>> grannLista = new ArrayList<ArrayList<Integer>>(v);
  
  for(int i = 0; i < e; i++){
    int a = io.getInt();
    int b = io.getInt();
    int capacity = io.getInt();

    if(grannLista.get(a) == null){
      grannLista.set(a,new ArrayList<>());
      ArrayList<Node> temp = grannLista.get(a);
      temp.add(new Node(b, capacity));
    }
    else{
      grannLista.get(a).add(new Node(b, capacity));
    }
    
  }
}
  //BFS()
  //


  
}