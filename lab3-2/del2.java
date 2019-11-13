import java.util.*;
public class del2{

  Kattio io;
	int x;
	int y; 
	int e;
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


void readMaxFlowSolution() {
		// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
		// (Antal hörn, källa och sänka borde vara samma som i grafen vi
		// skickade iväg)
		int v = io.getInt();
		int s = io.getInt();
		int t = io.getInt();
		int totflow = io.getInt();
		int e = io.getInt();
		ArrayList<Integer> lista;

		for (int i = 0; i < e; i++) {
			// Flöde f från a till b
			int a = io.getInt();
			int b = io.getInt();
			int f = io.getInt();
			lista = new ArrayList<>();

			if(a != s && a != t && b != s && b != t){
        grannLista.add(new Edge(a, b));
			}
			if(a == s || b == s){
				x++;
			}
			if(a == t || b == t){
				y++;
			}
		}
  }
    
	void writeBipMatchSolution(){
		// Skriv ut antal hörn och storleken på matchningen
		io.println(x + " " + y);
		io.println(grannLista.size());
		for(int i = 0; i < grannLista.size(); i++){
			io.println(grannLista.get(i));
		}
		io.flush();
  }
  del2(){
		io = new Kattio(System.in, System.out);

		readMaxFlowSolution();

		writeBipMatchSolution();

		io.close();
  }
    
	public static void main(String args[]) {
		new del2();
  }
}