public class Search{

    public int lazy_hash(String full_word){
        char[]first_three = {0,0,0};
        first_three[0] = full_word.charAt(0);
        first_three[1] = full_word.charAt(1);
        first_three[2] = full_word.charAt(2);
        return (first_three[0] * 900 + first_three[1] * 30 + first_three[2] * 1);

    }

  
    public void search(String word){
        System.out.println(word);
        String lower_case  = word.toLowerCase();
        System.out.println(lower_case);
        int hash = lazy_hash(lower_case);
        //nästa hash *
        //öppna hashfil
        //läsa pos i indexfil
        //hoppa till indexfilen
        //binärsöka efter rätt ord *
        //plocka ut ordet,förkomsterna,position till ocFil
        //läsa och spar förekomster*sizeof(int/long)
        //läsa ur korpus för varje position +- 30 tecken(check om förekomst > x) *
        //skriva till skärm



    }

   public static void main(String[] args){
       Search searcher = new Search();
       searcher.search(args[0]);
       


   }


}
