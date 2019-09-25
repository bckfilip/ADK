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
        System.out.println("asdsa");
        String wordFound = findWord(bounds);
        if(wordFound != null && wordFound.length() > 0){
            readTokenized(wordFound);

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

        File hashFile = new File("hash.txt");
        BufferedReader br = new BufferedReader(
                                new InputStreamReader(
                                    new FileInputStream(hashFile)));
        String currentLine;
        int hash = index.getHash(word);

        for(int i = 0; i < hash; i++){
            br.readLine();
        }
        String firstPosition = br.readLine(); //Soughted pos1

        while((currentLine = br.readLine()) == "0"){
        }
        String secondPosition = currentLine; //Soughted pos2
        String pos[] = new String[]{firstPosition, secondPosition, word};
        br.close();
        return pos;
    }

    /**
     * Performs a binary search to find a word between two hashes
     * @param pos
     * @return the word found, null if nothing was found
     */
    public static String findWord(String[] pos) throws IOException{
        File indexFile = new File("index.txt");
        RandomAccessFile index = new RandomAccessFile(indexFile, "r");
        long startPos = Long.parseLong(pos[0]);
        long endPos = Long.parseLong(pos[1]);

        String word = pos[2];
        while(startPos < endPos){
            String startLine = index.readLine();
            long mid = startPos + (endPos - startPos)/2;
            index.seek(mid);
            while (index.readByte() != '\n') {
                index.seek(--mid);
            }
            String currentLine = index.readLine();
            System.out.println("Line: " + currentLine);
            System.exit(0);
            String currentWord = currentLine.split(",")[0];
            if(currentWord.equals(word)){
                index.close();
                return currentLine;
            }
            //mid > key
            else if(currentWord.compareTo(word) < 0){
                startPos = mid;
            }
            //mid < key
            else if(currentWord.compareTo(word) > 0){
                endPos = mid;
            }
            if(startPos + startLine.getBytes().length > endPos){
                break;
            }

        }
        index.close();
        return "";
    }

    public static void readTokenized(String line) throws IOException{
        File tokenized = new File("tokenized");
        File korp = new File("korpus");
        RandomAccessFile token = new RandomAccessFile(tokenized, "r");
        RandomAccessFile korpus = new RandomAccessFile(korp, "r");

        String[] input = line.split(",");
        int amount = Integer.parseInt(input[1]);
        long posTokenizer = Long.parseLong(input[2]);
        System.out.println(input[0]);
        token.seek(posTokenizer);
        for(int i = 0; i < amount; i++){
            String[] tokenLine = token.readLine().split(" ");

            int posKorpus = Integer.parseInt(tokenLine[1]);
            korpus.seek(posKorpus);
            byte[] b = new byte[1000 + tokenLine[0].getBytes().length + 1];
            korpus.read(b, 0, 30);
            System.out.println(new String(b));
        }
        token.close();
        korpus.close();
    }
}
