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

public class del1 {
	Kattio io;
	int x, y, e;
  ArrayList<Edge> grannLista = new ArrayList<>();
  
  private class Edge {
    int a;
    int b;
    public Edge (int a, int b) {
      this.a = a;
      this.b = b;
    }

    @Override public String toString() {
      return a + " " + b;
    }
  }
	
	void readBipartiteGraph() {
	// Läs antal hörn och kanter
		x = io.getInt();
		y = io.getInt();
		e = io.getInt();

		// Läs in kanterna i grannlista. X = {1, 2,..., a} och Y = {a+1, a+2,..., a+b}. 
		for(int i = 0; i < e; i++){
			int a = io.getInt();
			int b = io.getInt();
			grannLista.add(new Edge(a, b));
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
		int v = x + y + 2;
		e = e + x + y;
		int s = x + y + 1;
		int t = x + y + 2;
		
		// Skriv ut antal hörn och kanter samt källa och sänka
		io.println(v);
		io.println(s + " " + t);
		io.println(e);
		for (int i = 0; i < grannLista.size(); i++) {
			io.println(grannLista.get(i) + " " + 1);
		}
		io.flush();
  }
    
	
    
	del1(){
		io = new Kattio(System.in, System.out);

		readBipartiteGraph();

		writeFlowGraph();

		io.close();
  }
    
	public static void main(String args[]) {
		new del1();
  }
}

