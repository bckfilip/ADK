import java.util.*;
import java.lang.*;
import java.io.*;

public class konkordans {


    public static void Main(String[] args){
        String word = args[0];
        String[] bounds = findHash(word);
        String wordFound = findWord(bounds);
        if(wordFound != null && !wordFound.equals(0)){

        }else{
            System.out.println("Det angivna ordet förekommer ej i texten");
        }
    }

    public static String[] findHash(String word){

        File hashFile = new File("/var/tmp/hash.txt");
        BufferedReader br = new BufferedReader(
                                new InputStreamReader(
                                    new FileInputStream(hashFile)));
        String currentLine;
        int hash = getHash(word);
        for(int i = 0; i < hash; i++){
            br.readLine();
        }
        int firstPosition = br.readLine(); //Soughted pos1
        while((currentLine = br.readLine()) == 0){
        }
        int secondPosition = currentLine; //Soughted pos2
        String pos[] = new String[]{firstPosition, secondPosition, word};
        findIndex(pos);
        return pos;
    }

    /**
     * Performs a binary search to find a word between two hashes
     * @param pos
     * @return the word found, null if nothing was found
     */
    public static String findWord(String[] pos){
        File indexFile = new File("/var/tmp/index.txt");
        RandomAccessFile index = new RandomAccessFile(indexFile, "r");

        long startPos = valueOf(pos[0]);
        long endPos = valueOf(pos[1]);
        String word = pos[2];
        long mid = startPos + (endPos - startPos)/2;
        index.seek(mid);
        index.readLine();
        String currentLine = index.readLine();
        String currentWord = currentLine.split(",")[0];
        if(currentWord == word){
            return mid;
        }
        if(index.seek(mid) > word){
            return binSearch()
        }
        
        index.close();
    }
}