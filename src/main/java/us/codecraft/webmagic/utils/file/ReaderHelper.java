package us.codecraft.webmagic.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lizeyu on 2016/5/26.
 */
public class ReaderHelper{

    public static List<String> getAdListFromFile(String fileName){
        List<String> list = null;
        BufferedReader br = null;
        try{
            list = new ArrayList<String>();
            br = new BufferedReader(new FileReader(new File(fileName)));
            String s = null;

            while((s = br.readLine()) != null){
                list.add(s);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(br != null){
                try{
                    br.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    public static Set<String> getAdListFromFileToSet(String fileName){
        Set<String> set = null;
        BufferedReader br = null;
        try{
            set = new HashSet<String>();
            br = new BufferedReader(new FileReader(new File(fileName)));
            String s = null;

            while((s = br.readLine()) != null){
                set.add(s);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(br != null){
                try{
                    br.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        return set;
    }

    public static String read(String fileName){
        String resultStr = null;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(new File(fileName)));
            resultStr = br.readLine();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(br != null){
                try{
                    br.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        return resultStr;
    }




}
