import java.util.*;

/**
 * Reducera Graffärgningsproblemet.
Indataformat: 
Rad ett: tal V (antal hörn, 1 <= V <= 300) 
Rad två: tal E (antal kanter, 0 <= E <= 25000) 
Rad tre: mål m (max antal färger, 1 <= m <= 2^30) 
En rad för varje kant (E stycken) med kantens ändpunkter(hörnen numreras från 1 till V).
 */

public class Reduction {
  
  
/**
 * Skapa grannlista.
 */
  static void reduce(){
    int v, e, m;
    Kattio io;
    HashMap<Integer, ArrayList<Integer>> grannlista = new HashMap<>();

    io = new Kattio(System.in, System.out);
    v = io.getInt() + 2; //Roller
    e = io.getInt() + v-1; //Scener
    m = Math.min(v,io.getInt()) + 2; //Skådespelare

    for(int i = 1; i <= v; i++){
      grannlista.put(i, new ArrayList<Integer>());
    }
    for(int i = 0; i < e-v+1; i++){
      int from = io.getInt();
      int to = io.getInt();
      ArrayList<Integer> edgeFrom = grannlista.get(from);
      edgeFrom.add(to);
    }
    //Det är även möjligt att lägga till 5 i varje lista i tidigare steg.
    //Tillverka kant från diva1 till samtliga noder
    ArrayList<Integer> edgesToDiva1 = grannlista.get(v-1);
    for(int i = 1; i < v - 1 ; i++){
      edgesToDiva1.add(i);
    }
    //Tillverka kant från diva2 till nod 1 eftersom nod 1 är den enda som garanterat existerar(!= diva1)
    ArrayList<Integer> edgesToDiva2 = grannlista.get(v);
    edgesToDiva2.add(1);

    //v,e,m antal roller, antal scener och antal skådespelare
    io.println(v);
    io.println(e);
    io.println(m);

    //skapar personer
    //min av m och v

    StringBuilder personer = new StringBuilder();
    for(int i = 3; i < m; i++){
      personer.append(i + " ");
    }
    personer.append(m);

    //skriver ut roller för alla utom divorna
    for(int i = 1;i <= v-2; i++){
      if(!grannlista.containsKey(i)){
        continue;
      }
      else{
        io.println((m -2) + " " + personer.toString());
        
      }
    }
    //tilldelar divorna varsin roll
    io.println("1 1");
    io.println("1 2");

    //Skriver ut scener
    //3 1-2-6
    for(int i = 1; i <= v; i++){
      if(!grannlista.containsKey(i)){
        continue;
      }
      else{
        for(int granne : grannlista.get(i)){
          io.println("2 " + i + " " + granne);
        }
      }
    }



    io.close();
  }

  public static void main(String args[]){
    reduce();

    
  }
}