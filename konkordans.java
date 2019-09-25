import java.util.*;
import java.lang.*;
import java.io.*;

public class konkordans {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) throws IOException{
        long startTime = System.currentTimeMillis();
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
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
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
        String secondPosition = br.readLine();
        while( secondPosition != null && secondPosition.equals("0")){
            secondPosition = br.readLine();
        }

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
        long endPos;
        if (pos[1] != null)
            endPos = Long.parseLong(pos[1]);
        else 
            endPos = index.length();

        String word = pos[2];

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
            }
            //mid < key
            else if(currentWord.compareTo(word) > 0){
                endPos = mid - 1;
            }
            if(startPos + startLine.getBytes().length > endPos){
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
        StringBuilder str;
        token.seek(posTokenizer);
        System.out.println("DET FINNS " + amount + " FÖREKOMSTER I KORPUS.");
        int printedCount = amount <= 25 ? amount : 25;

        printWords (0, printedCount, token, korpus);

        if (amount > 25){
            Scanner sc = new Scanner(System.in);  // Create a Scanner object
            System.out.println("ORDET FÖREKOMMER FLER ÄN 25 GÅNGER. VILL DU SE ALLA? J/N");
            String svar = sc.nextLine();
            if(svar.equals("J")){
                printWords (printedCount, amount, token, korpus);
            }           
        }  
        token.close();
        korpus.close();
    }

    private static void printWords(int start, int end, RandomAccessFile token, RandomAccessFile korpus) throws IOException {
        for(int i = start; i < end; i++){
            String[] tokenLine = token.readLine().split(" ");
            int posKorpus = Integer.parseInt(tokenLine[1]);
            int korpusStart = posKorpus - 30 < 0 ? 0 : posKorpus - 30;
            int korpusEnd = posKorpus + 30 > korpus.length() ? ((int) korpus.length()): posKorpus + 30; 
            korpus.seek(korpusStart);
            byte[] b = new byte[1000 + tokenLine[0].getBytes().length + 1];
            korpus.read(b, 0, korpusEnd - korpusStart);
            String result = new String(b, "ISO-8859-1").replace("\n", " ");
            System.out.println(result);
        }
    }
}

