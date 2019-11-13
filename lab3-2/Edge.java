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