import java.io.*;

public class Main{

    static String char_set = "ISO-8859-1";
    static String tokenized_korpus_path = "tokenized.txt";
    static String ocurrences_file_path = "ocurrences.txt";
    static String index_file_path = "index_file.txt";
    static String hash_file_path = "hash_file.txt";

    static File tokenized_korpus_file = new File(tokenized_korpus_path);
    static File index_file = new File(index_file_path);
    static File hash_file = new File(hash_file_path);
    static File ocurrences_file = new File(ocurrences_file_path); // fil med alla positioner av ordet i korpusfilen

    public static void main(String[] args){
        try{
            RandomAccessFile index_w = new RandomAccessFile(index_file,"rw");
            RandomAccessFile hash_wr = new RandomAccessFile(hash_file,"rw");
            RandomAccessFile occurences_wr = new RandomAccessFile(ocurrences_file,"rw");
            BufferedReader tokenized_korpus_r = new BufferedReader(new InputStreamReader(new FileInputStream(tokenized_korpus_file),char_set));
            String line;
            int occurences = 0;
            String old_word = "x";
            long pos_in_oc = 0; //hämtar positionen där vi just nu skriver i indexfilen
            long pos_in_indexfile = 0;
            
            while((line = tokenized_korpus_r.readLine()) != null){
                String[] wordPos = line.split(" ");
                String word = wordPos[0];
                long pos = Long.parseLong(wordPos[1]);
        
                if(word.equals(old_word)){ 
                    occurences_wr.writeLong(pos); //Räknar förekomster av samma ord
                    occurences++;
                }else{  
                    index_w.writeLong(pos_in_oc);  //skriver positionen där förekomsterna för ett ord börjar
                    pos_in_oc = occurences_wr.getFilePointer(); // hämtar den nya positionen för att kunna skriva förekomsterna för nästa ord på rätt plats
                    int hash = getHash(word);   //hashar ordet
                    hash_wr.seek(hash);         //Flyttar filpekaren till hashvärdet för ordet
                    hash_wr.writeLong(pos_in_indexfile);//skriver bytepositionen där ordet förekommer i indexfilen
                    occurences = 1;
                    pos_in_indexfile = (index_w.getFilePointer());
                    index_w.writeChars(word);
                    occurences_wr.writeLong(pos);
                    old_word = word;
                }
                index_w.writeInt(occurences);
                occurences = 0;
            }
            occurences_wr.close();
            tokenized_korpus_r.close();
            index_w.close();
            hash_wr.close();
        

        }catch(IOException e){
            e.printStackTrace();
        }
     
    }




/*
HACKER: ./tokenizer < korpus | sort > tokenized_korpus.txt
Hashfunktion: tar en sträng, plockar ut dom första tre bokstäverna och utför latmanshashning.
input:String
output: Long
*/
    public static int getHash(String full_word){
        char[]first_three = {0,0,0};
        first_three[0] = full_word.charAt(0);
        first_three[1] = full_word.charAt(1);
        first_three[2] = full_word.charAt(2);
        return (first_three[0] * 900 + first_three[1] * 30 + first_three[2] * 1);

    }
}