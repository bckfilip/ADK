import java.io.*;
import java.util.*;
import java.lang.*;

public class index{

    public static void main(String[] args) throws IOException{
        long startTime = System.currentTimeMillis();
        try{
            
            File tokenized = new File("/var/tmp/tokenized_korpus.txt");
            File indexFile = new File("/var/tmp/index.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(tokenized), "ISO-8859-1"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(indexFile), "ISO-8859-1"));

            String[] currentLine = reader.readLine().split(" ");
            String nextLine = reader.readLine();
            int occurences = 0;
            int positionFirst = 0;
            int positionNext = 0;

            
            
            while(nextLine != null){
                String currentWord = currentLine[0];
                String[] newLineWords = nextLine.split(" ");
                String nextWord = newLineWords[0];

                positionNext += nextLine.getBytes("ISO-8859-1").length;
                occurences++;

                if(!currentWord.equals(nextWord)){
                    writer.write(currentWord + "," + occurences + "," + positionFirst + "\n");
                    occurences = 0;
                    positionFirst = positionNext;
                }
                currentLine = newLineWords;
                nextLine = reader.readLine();
            }
            occurences++;
            String currentWord = currentLine[0];
            writer.write(currentWord + "," + occurences + "," + positionFirst + "\n");
            
            reader.close();
            writer.close();

            

        }catch(IOException e){
            e.printStackTrace();
        }
        createHash();

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
     
    }
    public static void createHash() throws IOException{

        List<Integer> hashList = new ArrayList<Integer>();
        for(int i = 0; i < 30*30*30+1; i++){
            hashList.add(0);
        }

        File indexFile = new File("/var/tmp/index.txt");
        File hashFile = new File("/var/tmp/hash.txt");
        RandomAccessFile index = new RandomAccessFile(indexFile, "r");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(hashFile), "ISO-8859-1"));
        int pos = 0;
        String currentLine;
      
        while((currentLine = index.readLine()) != null){
            String currentWord = currentLine.split(",")[0];
            int hashCurrent = getHash(currentWord);

            if(hashList.get(hashCurrent) == 0){
                hashList.set(hashCurrent, pos);
                pos = Math.toIntExact(index.getFilePointer());
            }
        }
        for(int i:hashList){
            writer.write(i + "\n");
        }
        writer.close();
        index.close();
    }
        
    private static int charValue(char chr) {
        if (chr == 229) { // 'a
            return 28;
        } else if (chr == 228) { // 'a'
            return 27;
        } else if (chr == 246) { // 'o'
            return 29;
        }
        if (chr >= 'a' && chr <= 'z') {
            return chr - 'a' + 1;
        } else {
            return 0;
        }
    }

    public static Integer getHash(String word){
       
        int hashIndex = 0;
        for (int l = 0; l < Math.min(3, word.length()); l++) {
            hashIndex += charValue(word.charAt(l)) * Math.pow(30, (2 - l));
        }
        return hashIndex;
    }
}
