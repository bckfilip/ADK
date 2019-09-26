/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {

  public static List<String> readWordList(Kattio input) throws IOException {  // Oanvänd
    LinkedList<String> list = new LinkedList<String>();
    while (true) {
      String s = input.getWord();
      if (s.equals("#"))
        break;
      list.add(s);
    }
    return list;
  }

  public static void main(String args[]) throws IOException {
    Kattio io = new Kattio(System.in, System.out);
    String word;
    LinkedList<String> wordList = new LinkedList<String>();
    while (true) {
      String s = io.getWord();
      if (s.equals("#"))
        break;
      wordList.add(s);
    }
    
    while ((word = io.getWord()) != null) {
      ClosestWords closestWords = new ClosestWords(word, wordList);
      io.print(word + " (" + closestWords.getMinDistance() + ")");
      for (String w : closestWords.getClosestWords())
        io.print(" " + w);
      io.println();
    }
    io.close();

  }
}
