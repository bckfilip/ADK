import java.io.*;
import java.util.ArrayList;
public class Main{



/*
HACKER: ./tokenizer < korpus | sort > tokenized_korpus.txt
Hashfunktion: tar en sträng, plockar ut dom första tre bokstäverna och utför latmanshashning.
input:String
output: Long
*/
  public int lazy_hash(String full_word){
            char[]first_three = {0,0,0};
            first_three[0] = full_word.charAt(0);
            first_three[1] = full_word.charAt(1);
            first_three[2] = full_word.charAt(2);
            return (first_three[0] * 900 + first_three[1] * 30 + first_three[2] * 1);

        }


    public static void main(String[] args){
        
        

        try{
            
            Main main = new Main();
            String char_set = "ISO-8859-1";
            String tokenized_korpus_path = "tokenized.txt";
            String ocurrences_file_path = "ocurrences.txt";
            String index_file_path = "index_file.txt";
            String hash_file_path = "hash_file.txt";

            File tokenized_korpus_file = new File(tokenized_korpus_path);
            File index_file = new File(index_file_path);
            File hash_file = new File(hash_file_path);
            File ocurrences_file = new File(ocurrences_file_path); // fil med alla positioner av ordet i korpusfilen
            //File test_file = new File(test);

            RandomAccessFile index_w = new RandomAccessFile(index_file,"rw");
            RandomAccessFile hash_wr = new RandomAccessFile(hash_file,"rw");
            RandomAccessFile occurences_wr = new RandomAccessFile(ocurrences_file,"rw");
            BufferedReader tokenized_korpus_r = new BufferedReader(new InputStreamReader(new FileInputStream(tokenized_korpus_file),char_set));

            String current_word;
            int occurences = 0;
            String old_word = "";
            long pos_in_oc = 0; //hämtar positionen där vi just nu skriver i indexfilen
            long pos_in_indexfile = 0;

            ArrayList<Integer> lista = new ArrayList<Integer>();
            
            
            while((current_word = tokenized_korpus_r.readLine()) != null){
                String[] word_bytePos = current_word.split(" ");
           
                if(old_word == ""){      // true första gången while körs används enbart för att behandla första ordet (a)
                    occurences = 1;
                    pos_in_indexfile = (index_w.getFilePointer()); // hämtar positionen i indexfilen innan man skrivit
                    index_w.writeChars(word_bytePos[0]);    //skriver ordet till indexfilen
                    pos_in_oc = occurences_wr.getFilePointer(); // hämtar positionen t filen med förekomster innan man skrivit
                    occurences_wr.writeLong(Long.valueOf(word_bytePos[1])); //skriver förekomst i korpusfilen

                    old_word = word_bytePos[0];
                   
                }else{
                    if(word_bytePos[0].equals(old_word)){ // om man läser samma ord som tidigare appenda nya förekomsten till String buildern
                        occurences_wr.writeLong(Long.valueOf(word_bytePos[1])); //skriver förekomster i korpusfilen
                        occurences++;
                        

                    }else{
                        index_w.writeInt(occurences);   //skriver antal förekomster till filen med antal förekomster och faktiska värden in  korpusfilen
                        index_w.writeLong(pos_in_oc);  //skriver positionen där förekomsterna för ett ord börjar
                        pos_in_oc = occurences_wr.getFilePointer(); // hämtar den nya positionen för att kunna skriva förekomsterna för nästa ord på rätt plats
                        
            
                        /*
                       
                        */
                        int h_val = main.lazy_hash(current_word);   //hashar ordet
                        hash_wr.seek(h_val);         //Flyttar filpekaren till hashvärdet för ordet
                        hash_wr.writeLong(pos_in_indexfile);//skriver bytepositionen där ordet förekommer i indexfilen
                        
                        /*
                        samma som första if -> oldword = "" borde ge samma resultat
                        */
                        occurences = 1;
                        pos_in_indexfile = (index_w.getFilePointer());
                        
                        index_w.writeChars(word_bytePos[0]);
                        occurences_wr.writeLong(Long.valueOf(word_bytePos[1]));
                        old_word = word_bytePos[0];
                    }
                }

            }
            occurences_wr.close();
            tokenized_korpus_r.close();
            index_w.close();
            hash_wr.close();
        

        }catch(IOException e){
            e.printStackTrace();
        }
     
    }

}
