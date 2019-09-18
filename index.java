import java.io.*;

public class index{

    public static void main(String[] args){
        try{
            
            File tokenized = new File("tokenized.txt");
            File indexfile = new File("index.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tokenized), "ISO-8859-1"));
            BufferedWriter index = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(indexfile), "ISO-8859-1"));

            String[] currentLine = br.readLine().split(" ");
            String nextLine = br.readLine();
            int occurences = 0;
            int positionFirst = 0;
            int positionNext = 0;
            int i = 0;

            long startTime = System.currentTimeMillis();
            
            while(nextLine != null){
                String currentWord = currentLine[0];
                String[] newLineWords = nextLine.split(" ");
                String nextWord = newLineWords[0];
                positionNext += nextLine.getBytes("ISO-8859-1").length;
                occurences++;
                if(!currentWord.equals(nextWord)){
                    index.write(currentWord + "," + occurences + "," + positionFirst + "\n");
                    if(i % 10000 == 0)
                        System.out.println(currentWord + "," + occurences + "," + positionFirst);
                    occurences = 0;
                    positionFirst = positionNext;
                    i++;
                }
                currentLine = newLineWords;
                nextLine = br.readLine();
            }
            br.close();
            index.close();
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println(elapsedTime/1000);

        }catch(IOException e){
            e.printStackTrace();
        }
     
    }
}