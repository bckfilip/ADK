import java.util.*;
import java.lang.*;
import java.io.*;

public class konkordans {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) throws IOException{
        try{
        String word = args[0];
        String[] bounds = findHash(word);
        String posFound = findWord(bounds);
        if(posFound != null && posFound.length() > 0){
            readTokenized(posFound);

        }else{
            System.out.println("Det angivna ordet förekommer ej i texten");
        }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * 
     * @param word
     * @return
     */
    public static String[] findHash(String word) throws IOException{

        File hashFile = new File("/var/tmp/hash.txt");
        BufferedReader br = new BufferedReader(
                                new InputStreamReader(
                                    new FileInputStream(hashFile)));
      
        int hash = index.getHash(word);
    
        for(int i = 0; i < hash; i++){
            br.readLine();
        }
        String firstPosition = br.readLine(); //Soughted pos1
        String currentLine = br.readLine();
        while( currentLine != null && currentLine.equals("0")){
            currentLine = br.readLine();
        }
        String secondPosition = currentLine; //Soughted pos2

        String pos[] = new String[]{firstPosition, secondPosition, word};
        br.close();
   
        return pos;
    }

    /**
     * Performs a binary search to find a word between two hashes
     * @param pos
     * @return the wordline found, null if nothing was found
     */
    public static String findWord(String[] pos) throws IOException{
        File indexFile = new File("/var/tmp/index.txt");
        RandomAccessFile index = new RandomAccessFile(indexFile, "r");
        long startPos = Long.parseLong(pos[0]);
        long endPos = 0;
        String word = pos[2];
        if (pos[1] != null){
            endPos = Long.parseLong(pos[1]);
        }else{
            index.seek(startPos);
            String taBira = index.readLine();
            String taMat = taBira.split(",")[0];
            if(taMat.equals(word)){
                index.close();
                return taBira;
            }else{
                taBira = index.readLine();
                taMat = taBira.split(",")[0];
                if(taMat.equals(word)){
                    index.close();
                    return taBira;
                }
            }
        }
    
        
        while(startPos < endPos){
    
            String startLine = index.readLine();
            long mid = startPos + (endPos - startPos)/2;
            index.seek(mid);
            
            while (index.readByte() != '\n') {
                index.seek(--mid);
            }
            String currentLine = index.readLine();
           
            String currentWord = currentLine.split(",")[0];
            if(currentWord.equals(word)){
                index.close();
              
                return currentLine;
            }
            //mid > key
            else if(currentWord.compareTo(word) < 0){
                startPos = mid + 1;
                System.out.println("mid +1");
            }
            //mid < key
            else if(currentWord.compareTo(word) > 0){
                endPos = mid - 1;
                System.out.println("mid -1");
            }
            if(startPos + startLine.getBytes().length > endPos){
                System.out.println("mid");
                break;
                
            }

        }
        index.close();
       
        return "";
    }

    public static void readTokenized(String line) throws IOException{
        File tokenized = new File("/var/tmp/tokenized.txt");
        File korp = new File("/afs/nada.kth.se/info/adk19/labb1/korpus");
        RandomAccessFile token = new RandomAccessFile(tokenized, "r");
        RandomAccessFile korpus = new RandomAccessFile(korp, "r");

        String[] input = line.split(",");
        int amount = Integer.parseInt(input[1]);
        long posTokenizer = Long.parseLong(input[2]);
        
        token.seek(posTokenizer);
        for(int i = 0; i < Math.min(25, amount); i++){
            String[] tokenLine = token.readLine().split(" ");
            int posKorpus = Integer.parseInt(tokenLine[1]);
            korpus.seek(posKorpus);
            byte[] b = new byte[1000 + tokenLine[0].getBytes().length + 1];
            korpus.read(b, 0, 30);
            System.out.println(new String(b, "ISO-8859-1"));
        }
        System.out.println("Det fanns " + amount + " förekomster av ordet i korpuset");
        if (amount > 25){
            Scanner sc = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Finns fler än 25 förekomster? Vill du verkligen se alla? J/N");
            String svar = sc.nextLine();
            if(svar.equals("J")){
                for(int i = 25; i < amount; i++){
                    String[] tokenLine = token.readLine().split(" ");
                    int posKorpus = Integer.parseInt(tokenLine[1]);
                    korpus.seek(posKorpus);
                    byte[] b = new byte[1000 + tokenLine[0].getBytes().length + 1];
                    korpus.read(b, 0, 30);
                    System.out.println(new String(b, "ISO-8859-1"));
                }
            }           
        }
           
        token.close();
        korpus.close();
    }
}