
public class Edge {

    final int from;
    final int to;
    int capacity;
    int flow;
    int resCap;
    Edge reverseEdge;
    boolean direction;

    Edge(int from, int to, int c, int f, boolean dir) {
        this.from = from;
        this.to = to;
        this.capacity = c;
        this.flow = f;
        this.resCap = this.capacity - this.flow;
        this.direction = dir;
    }

    void setReverseEdge(Edge reverseEdge) {
        this.reverseEdge = reverseEdge;
    }

    void updateFlow(int newFlow) {
        this.flow += newFlow;
        this.resCap = this.capacity - this.flow;
        this.reverseEdge.flow -= newFlow;
        this.reverseEdge.resCap = this.reverseEdge.capacity - this.reverseEdge.flow;
    }

    public Edge getReverseEdge() {
        return reverseEdge;
    }

    public int[] getVertices() {
        return new int[]{from, to};
    }

    public int getResCap() {
        return resCap;
    }
}