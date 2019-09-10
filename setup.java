import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;


public class setup {

    public static void main(String[] args){
        if(args[0] == null){
            system.out.println("Inget sökord inskrivet");
        }
        else{//sök

            String toHash = args[0];   
            setup setup = new setup();
            setup.search(toHash);      

        }
        
    }

    public void search(String toHash){
        int hashed = hash(toHash);
        byte bytePos[];

        
        try{   
            File file = new File("korpus"); 
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            String line;
            while (line = br.readLine() != null) {

            }
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }




    }

    //formaterar korpus till ord följt av dess positioner i korpus: a[bytepos1,..,byteposn]
    public void format() {
        try{    
            //String test = "";
            File file = new File("tokenized.txt");
            RandomAccessFile rFile = new RandomAccessFile(file,"rw");

            byte byteArray[] = test.getBytes();
            for(int i = 0; i <10; i++){
                rFile.write(byteArray);
            }
            rFile.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }



    public void converter(String s){
        /*gemener till versaler 
        */
    }

    public void replaceSpace(String s){

    }

    public int hash(string word){

        int hashed = 0;
        byte byteArrayWord[] = word.getBytes();
        if(byteArrayWord.length >= 3){
            hashed = byteArrayWord[0] * 900 + byteArrayWord[1] *30 + byteArrayWord[2] * 1;
        }
        if(byteArrayWord.length == 2){
            hashed = byteArrayWord[0] * 900 + byteArrayWord[1] *30;

        }
        if(byteArrayWord.length == 1){
            hashed = byteArrayWord[0] * 900;
        }
        return hashed;
    }


}