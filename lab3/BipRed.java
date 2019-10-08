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
	int x, y;
	int e;
	ArrayList<ArrayList<Integer>> listLista = new ArrayList<>();
	ArrayList<ArrayList<Integer>> grannlista = new ArrayList<>();
	ArrayList<Integer> lista;
	int maxMatch = 0;
	

	void readBipartiteGraph() {
// Läs antal hörn och kanter
		x = io.getInt();
		y = io.getInt();
		e = io.getInt();

// Läs in kanterna i grannlista. X = {1, 2,..., a} och Y = {a+1, a+2,..., a+b}. 
		for(int i = 0; i < e; i++){
			int a = io.getInt();
			int b = io.getInt();
			lista = new ArrayList<>();
			lista.add(a);
			lista.add(b);
			grannlista.add(lista);
		}
		//s
		for(int i = 1; i <= x; i++){
			lista = new ArrayList<>();
			lista.add(x+y+1);
			lista.add(i);
			grannlista.add(lista);
		}
		//t
		for(int i = x+1; i <= x+y; i++){
			lista = new ArrayList<>();
			lista.add(i);
			lista.add(x+y+2);
			grannlista.add(lista);
		}
	}
    
	void writeFlowGraph() {
		int v = x + y;
		int s = x + y + 1;
		int t = x + y + 2;
		e = e + x + y;
		// Skriv ut antal hörn och kanter samt källa och sänka
		io.println(v);
		io.println(s + " " + t);
		io.println(e);
		for (int i = 0; i < grannlista.size(); i++) {
			ArrayList<Integer> lista = grannlista.get(i);
			io.println(lista.get(0) + " " + lista.get(1) + " " + 1);
		}
		io.flush();
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

			if(a != s || b != t){
				lista.add(a);
				lista.add(b);
				listLista.add(lista);
				maxMatch++;
			}
		}
		io.flush();
  }
    
	void writeBipMatchSolution(){
		// Skriv ut antal hörn och storleken på matchningen
		io.println(x + " " + y);
		io.println(maxMatch);
		for(int i = 0; i < listLista.size(); i++){
			ArrayList<Integer> lista = listLista.get(i);
			io.println(lista.get(0) + " " + lista.get(1));
		}
		io.flush();
  }
    
	BipRed(){
		io = new Kattio(System.in, System.out);

		readBipartiteGraph();

		writeFlowGraph();

		readMaxFlowSolution();

		writeBipMatchSolution();

		io.close();
  }
    
	public static void main(String args[]) {
		new BipRed();
  }
}

