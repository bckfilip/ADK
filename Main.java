import java.io.*;

public class Main{

/*
    public Hash_N_letters lazy_hash(String full_word){

        char first_three[] = {0,0,0};
        first_three[0] = full_word.charAt(0);
        first_three[1] = full_word.charAt(1);
        first_three[2] = full_word.charAt(2);
                
        Hash_N_letters h_l = new Hash_N_letters((first_three[0] * 900 + first_three[1] * 30 + first_three[2] * 1),first_three);
        return h_l;
                //return (first_three[0] * 900 + first_three[1] * 30 + first_three[2] * 1);
                
    }
*/  

/*
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
            String tokenized_korpus_path = "tokenized_korpus.txt";
            String test = "test_file.txt";
            String index_file_path = "index_file.txt";
            String hash_file_path = "hash_file.txt";

            File tokenized_korpus_file = new File(tokenized_korpus_path);
            File index_file = new File(index_file_path);
            File hash_file = new File(hash_file_path);
            File test_file = new File(test);

            RandomAccessFile index_w = new RandomAccessFile(index_file,"rw");
            RandomAccessFile hash_wr = new RandomAccessFile(hash_file,"rw");
           
            BufferedReader tokenized_korpus_r = new BufferedReader(new InputStreamReader(new FileInputStream(tokenized_korpus_file),char_set));

            String current_word;
            int occurences = 0;
            String old_word = "";
            
            
            
            while((current_word = tokenized_korpus_r.readLine()) != null){
                String[] word_bytePos = current_word.split(" ");
                if(old_word == ""){      // true första gången while körs används enbart för att behandla första ordet (a)   
                    index_w.writeBytes(word_bytePos[0]);
                    index_w.writeBytes(" ");
                    index_w.writeBytes(word_bytePos[1]);
                    index_w.writeBytes(" ");
                    old_word = word_bytePos[0];
                    occurences = 1;
                }else{
                    if(word_bytePos[0].equals(old_word)){ // om man läser samma ord som tidigare appenda nya förekomsten till String buildern
                        index_w.writeBytes(word_bytePos[1]);
                        index_w.writeBytes(" ");
                        occurences++;

                    }else{
                        byte[] w_b = new byte[10]; 
                        w_b = (current_word.substring(0,2)).getBytes(); //här kan det säkertstrula till sig
                        
                        int h_val = main.lazy_hash(current_word);   //hashar ordet
                        long pos_in_indexfile = (index_w.getFilePointer()); //hämtar positionen där vi just nu skriver i indexfilen

                        /*
                        skriver till hashfil:
                        [Tre första bokstäverna, förekomster, position in till indexfilen].
                        */
                        hash_wr.seek(h_val);         //Flyttar filpekaren till hashvärdet för ordet
                        hash_wr.write(w_b,0,2);      //skriver tre första bokstäverna av ordet(onödigt)
                        hash_wr.writeInt(occurences);//#förekomster
                        hash_wr.writeLong(pos_in_indexfile);//skriver bytepositionen där ordet förekommer i indexfilen
                        
                        /*
                        Se över om man behöver köra whitespace, kasnke är bättre att skriva ints
                        gör samma sak som tidigare
                        */
                        occurences = 1;
                        index_w.writeBytes("\n");
                        index_w.writeBytes(word_bytePos[0]);
                        index_w.writeBytes(" ");
                        index_w.writeBytes(word_bytePos[1]);
                        index_w.writeBytes(" ");
                        old_word = word_bytePos[0];
                    }
                }

            }
            index_w.close();
            hash_wr.close();
        

        }catch(IOException e){e.printStackTrace();}
     
    }

}
