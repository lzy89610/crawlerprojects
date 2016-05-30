package us.codecraft.webmagic.utils.file;

import java.io.*;
import java.util.List;
import java.util.Set;

/**
 * Created by lizeyu on 2016/5/26.
 */
public class WriterHelper{

    public static void writeAfter(String file, String content){
        FileWriter writer = null;
        try{
            writer = new FileWriter(file, true);
            writer.write(content);

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(writer != null){
                try{
                    writer.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void write(String fileName, String content){
        File file = new File(fileName);
        PrintWriter output = null;
        try{
            output = new PrintWriter(file);
            output.println(content);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }finally{
            output.close();
        }
    }

    public static void write(String fileName, Set<String> contents){
        File file = new File(fileName);
        PrintWriter output = null;
        try{
            output = new PrintWriter(file);
            for(String s : contents){
                output.println(s);
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }finally{
            output.close();
        }
    }

    public static void write(String fileName, List<String> contents){
        File file = new File(fileName);
        PrintWriter output = null;
        try{
            output = new PrintWriter(file);
            for(String s : contents){
                output.println(s);
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }finally{
            output.close();
        }
    }



}
