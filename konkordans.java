import java.util.*;
import java.lang.*;
import java.io.*;

public class konkordans {


    public static void Main(String[] args){
        String word = args[0];
        findHash(word);
        if(!hash.equals(0)){

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
        String pos[];
        pos[0] = firstPosition; //startpos i index
        pos[1] = secondPosition; // slutpos i index
        return pos;
    }

    public static void findIndex(String[] pos){
        File indexFile = new File("/var/tmp/index.txt");
        RandomAccessFile index = new RandomAccessFile(indexFile, "r");

        long startPos = valueOf(pos[0]);
        long endPos = valueOf(pos[1]);
        long mid = startPos + (endPos - startPos)/2;
        if(index.seek(mid) == word){
            return mid;
        }
        if(index.seek(mid) > word){
            return binSearch()
        }
        
        index.close();
    }

    public static binSearch()
}