/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

public class ClosestWords {

  LinkedList<String> closestWords = null;
  int closestDistance = Integer.MAX_VALUE;
  String oldWord = "";
  private static boolean initialized = false;
  private static int[][] bigMatrix = new int[41][41];

  /**
   *
   * @param w Word from input.
   * @param wordList Iterate over wordlist to compare with input-word(w).
   *
   *                 Optimeringar som gjorts i denna metod är på rad #33 där vi hoppar till nästa ord för
   *                 för jämförelse så fort vi vet att skillnaden mellan orden redan är större än vår closestDist.
   *                 'init' initialiserar matrisen med värden 0->40 för respektive rad & kolmumn 0. Detta behövs
   *                 endast göras en gång då vi senare i programmet sparar matrisen när vi går igenom ord.
   */

  public ClosestWords(String w, List<String> wordList) {

    init();
    int wlen = w.length();
    closestWords = new LinkedList<String>();
    for (String s : wordList) {
      if (Math.abs(s.length() - wlen) > closestDistance) {
        continue;
      }

      int dist = Distance(w, s, wlen, s.length());
      if (dist < closestDistance) {
        closestDistance = dist;
        closestWords.clear();
        closestWords.add(s);
      } else if (dist == closestDistance) {
        closestWords.add(s);
      }
    }
  }

  /**
   * Körs första gången för att instansiera matrisen där
   * rad & och kolumn 0 fylls på med värden 0->40.
   */

  void init (){
    if (!initialized) {
      for (int i = 0; i < 41; i++) {
        bigMatrix[i][0] = i;
        bigMatrix[0][i] = i;
      }
      initialized = true;
    }
  }

  /**
   *
   * @param w1 Given word from input.
   * @param w2 Current word from wordlist to compare to input(w1).
   * @param w1len Length of w1.
   * @param w2len Length of w2.
   * @return returns value in matrix with position bigMatrix[w2len][w1len].
   *
   *                            När vi itererar över ord kommer matrisen se likadan ut för ord med samma p bokstäver.
   *                            Därför kollar vi vid varje nytt ord hur det ter sig mot det gamla ordet för se hur
   *                            mycket av den föregående matrisen vi kan spara.
   *                            Matrisen kommer växa nedåt(givet att diagonalvärdet är definierat som längst ned till
   *                            höger), givet detta vet vi att värdet(val) aldrig kommer bli mindre ju längre ned i
   *                            matrisen vi kommer, så om val redan är större än currentMin kan vi avbryta.
   */

  int Distance(String w1, String w2, int w1len, int w2len) {
    int savedIndex = 1;
    while((savedIndex < w2len) &&   //Optimering#2: spara delen av matrisen som är likadan från föregående ord.
            (savedIndex < oldWord.length()) &&
            (w2.charAt(savedIndex-1) == oldWord.charAt(savedIndex-1))) {
      savedIndex++;
    }
    for (int y = savedIndex; y <= w2len; y++) {
      int currentMin = 50;
      for (int x = 1; x <= w1len; x++) {
        int val = Math.min(
                bigMatrix[y - 1][x - 1] + (w1.charAt(x - 1) == w2.charAt(y - 1) ? 0 : 1),
                Math.min(bigMatrix[y-1][x] + 1,
                        bigMatrix[y][x-1] + 1));
        currentMin = Math.min(currentMin, val);
        bigMatrix[y][x] = val;
      }
      if (currentMin > closestDistance)
        return currentMin;
    }
    oldWord = w2;
    return bigMatrix[w2len][w1len];
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
}