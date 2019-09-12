import java.util.ArrayList;
import java.io.*;
import java.lang.StringBuilder;


public class kladd {
    /*
    index is sorted list with each word + the first position of said word in ordlista(LMH points here)
    ordlista is sorted list with all words + its position in korpus(index points here)
    */
    
    
    public static void main(String[] args){
        try{    
            File read = new File("tokenized.txt"); // skapar filen med ord och alla förekomster(sorterade filen från tokenized <- korpus )
            File write = new File("index_file.txt"); // Skapar filen som vi skriver den berabetade read till
            FileWriter fw = new FileWriter(write);  // objekt för att skriva till index_file
            ArrayList<String> index = new ArrayList<String>(); // Lista för att apppenda alla förekomster av ordens position
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(read), "ISO-8859-1")); // Bufferedreader för att läsa från tokenized.txt 
            StringBuilder builder = new StringBuilder();
            String oldWord = ""; 
            String currentWord;
            int occurences = 0;
            

            while((currentWord = br.readLine()) != null){ 
                String[] wordAndPos = currentWord.split(" ");
                if(oldWord == ""){      // true första gången while körs används enbart för att behandla första ordet (a)   
                    builder.append(wordAndPos[0]);
                    builder.append(",");
                    builder.append(wordAndPos[1]);
                    oldWord = wordAndPos[0];
                    occurences = 1;
                }else{
                    if(wordAndPos[0].equals(oldWord)){ // om man läser samma ord som tidigare appenda nya förekomsten till String buildern
                    builder.append(",");
                    builder.append(wordAndPos[1]);
                    occurences++;
                    }else{

                    
                    builder.insert(0,occurences); // insertar #förekomster i början av strängen
                    int length = String.valueOf(occurences).length(); //math
                    builder.insert(length,",");
                    occurences = 1;
                    index.add(builder.toString()); // om ett nytt ord dyker upp lägg till ordet och förekomster i listan med ord fölt av förekomst a,bytepos,bytepos
                    
                    builder = new StringBuilder();  // ny string builder(fungerar som flush)
                    builder.append(wordAndPos[0]); //lägg till nytt ord
                    builder.append(",");          
                    builder.append(wordAndPos[1]);  //första bytepos
                    oldWord = wordAndPos[0];
                    }
                }
            }
            index.add(builder.toString());      //lägger till sista ordet
            br.close();                         //stänger buffered reader

            //Skriver resultat till index_file
            for(int i = 0; i < index.size(); i++){
                fw.write((index.get(i) + "\n")); 
                fw.flush();   
            }
            fw.close();



        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
