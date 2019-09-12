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
            File file = new File("/mnt/c/Users/filip/Desktop/ADKLab1/testing.txt");
            File write = new File("/mnt/c/Users/filip/Desktop/ADKLab1/tom.txt");
            FileWriter fw = new FileWriter(write);
            ArrayList<String> index = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new FileReader(file));
            //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("testing.txt"), "ISO-8859-1"));
            StringBuilder builder = new StringBuilder();
            String oldWord = ""; 
            String currentWord;
            //String[] wordAndPos = {"",""};

            while((currentWord = br.readLine()) != null){ 
                String[] wordAndPos = currentWord.split(" ");
                //System.out.println(wordAndPos[0]+ " hgghgh");
                if(oldWord == ""){
                    builder.append(wordAndPos[0]);
                    builder.append(",");
                    builder.append(wordAndPos[1]);
                    oldWord = wordAndPos[0];
                }else{
                     if(wordAndPos[0].equals(oldWord)){
                        builder.append(",");
                        builder.append(wordAndPos[1]);
                    }else{
                        index.add(builder.toString());
                        builder = new StringBuilder();
                        
                        builder.append(wordAndPos[0]);
                        builder.append(",");
                        builder.append(wordAndPos[1]);
                        oldWord = wordAndPos[0];
                    }
                }

            }
           
            br.close();

            for(int i = 0; i < index.size(); i++){ // i <= index.size()
                fw.write(index.get(i) + "\n"); 
                fw.flush();   
            }
            fw.close();



        }catch(IOException e){
            e.printStackTrace();
        }
    }
}