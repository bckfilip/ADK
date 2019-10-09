import java.util.*;
public class del2{

  Kattio io;
	int x, y, e;
	ArrayList<Edge> listLista = new ArrayList<>();
	ArrayList<ArrayList<Integer>> grannlista = new ArrayList<>();

private class Edge {
  int a;
  int b;
  public Edge (int a, int b) {
    this.a = a;
    this.b = b;
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
        listLista.add(new Edge(a, b));
			}
		}
  }
    
	void writeBipMatchSolution(){
		// Skriv ut antal hörn och storleken på matchningen
		io.println(x + " " + y);
		io.println(listLista.size());
		for(int i = 0; i < listLista.size(); i++){
			Edge kant = listLista.get(i);
			io.println(kant.a + " " + kant.b);
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