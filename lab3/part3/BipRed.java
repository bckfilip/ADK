/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */
import java.util.*;

public class BipRed {
	Kattio io;
	int x, y, e, v, s, t, maxFlow;
	ArrayList<Edge> grannLista = new ArrayList<Edge>();
	
	/**
	 * x,y hörn
	 * läser in noder och skapar en Edge mellan dessa 
	 * 
	 */
//x hörn
//y hörn
	void readBipartiteGraph() {
	// Läs antal hörn och Edgeer
		x = io.getInt();
		y = io.getInt();
		e = io.getInt();

		//Läs in Edgeerna i grannlista. X = {1, 2,..., a} och Y = {a+1, a+2,..., a+b}. 
		for(int i = 0; i < e; i++){
			int from = io.getInt();
			int to = io.getInt();
			grannLista.add(new Edge(from, to));
		}
		//s
		for(int i = 1; i <= x; i++){
      grannLista.add(new Edge(x+y+1, i));
		}
		//t
		for(int i = x+1; i <= x+y; i++){
      grannLista.add(new Edge(i, x+y+2));
		}
		
	}
    
	void writeFlowGraph() {
		v = x + y + 2;
		e = e + x + y;
		s = x + y + 1;
		t = x + y + 2;
		
		
  }
	 
	
	//Skriver ut lösningen på matchningsproblemet
	//output ger alla kanter som bildar matchningen
	void writeBipMatchSolution(ArrayList<Edge> posFlowEdges){
		io.println(x + " " + y);
		io.println(posFlowEdges.size());
		for(Edge e : posFlowEdges){
			io.println(e.toString());
		}
		io.flush();
  }
    
	BipRed(){
		io = new Kattio(System.in, System.out);

		readBipartiteGraph();

		writeFlowGraph();

		MaxFlow mf = new MaxFlow();
		//skapar en flowgraph
		HashMap<Integer, ArrayList<Edge>> flowGraph = mf.createFlowgraph(grannLista,v);
		mf.edmondsKarp(flowGraph,s,t);
		ArrayList<Edge> posFlowEdges = mf.solution(v,s,t,e,flowGraph);
		writeBipMatchSolution(posFlowEdges);

		io.close();
  }
    
	public static void main(String args[]) {
		new BipRed();
  }
}

