import java.io.*;
import java.util.*;
import java.lang.*;

public class index{
    // /afs/nada.kth.se/info/adk19/labb1/tokenizer < /afs/nada.kth.se/info/adk19/labb1/korpus | sort > /var/tmp/tokenized.txt

    public static void main(String[] args) throws IOException{
        long startTime = System.currentTimeMillis();
        try{

            File tokenized = new File("/var/tmp/tokenized.txt");
            File indexFile = new File("/var/tmp/index.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(tokenized), "ISO-8859-1"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(indexFile), "ISO-8859-1"));

            String firstLine = reader.readLine();
            String[] currentLine = firstLine.split(" ");
            String nextLine = reader.readLine();
            int occurences = 1;
            int positionFirst = 0;
            int positionNext = firstLine.getBytes("ISO-8859-1").length +2;

            while(nextLine != null){
                String currentWord = currentLine[0];
                String[] newLineWords = nextLine.split(" ");
                String nextWord = newLineWords[0];

                if(!currentWord.equals(nextWord)){
                    writer.write(currentWord + "," + occurences + "," + positionFirst + "\n");
                    occurences = 0;
                    positionFirst = positionNext;
                }
                positionNext += nextLine.getBytes("ISO-8859-1").length + 1;
                occurences++;
                currentLine = newLineWords;
                nextLine = reader.readLine();
            }
            String currentWord = currentLine[0];
            writer.write(currentWord + "," + occurences + "," + positionFirst);

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
        for(int i = 0; i < 30*30*30; i++){
            hashList.add(0);
        }

        RandomAccessFile index = new RandomAccessFile(new File("/var/tmp/index.txt"), "r");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("/var/tmp/hash.txt")), "ISO-8859-1"));

        String currentWord;

        int lastHash = 0;
        int currentHash = 0;

        int pos = 0;
        String currentLine;
        while((currentLine = index.readLine()) != null){
            lastHash = currentHash;
            currentWord = currentLine.split(",")[0];
            currentHash = getHash (currentWord);
            if (lastHash == currentHash && currentHash != 0) {
                pos = Math.toIntExact(index.getFilePointer());
                continue;
            }
            
            if(hashList.get(currentHash) == 0){
                hashList.set(currentHash, pos);
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

    public static int getHash(String word){

        int hashIndex = 0;
        for (int l = 0; l < Math.min(3, word.length()); l++) {
            hashIndex += charValue(word.charAt(l)) * Math.pow(30, (2 - l));
        }
        return hashIndex;
    }
}
