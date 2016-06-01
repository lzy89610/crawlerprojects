package us.codecraft.webmagic.amazon.model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lizeyu on 2016/5/25.
 */
@Deprecated
public class ASINTemp{

    private Set<String> asinSet;
    private static Integer MAX_SIZE = 100;
    private static String FILE_PATH_DIR = "e:\\amazonad\\";

    private static ASINTemp asinTemp;

    private ASINTemp(){};

    public static ASINTemp getInstance(){
        if(null == asinTemp){
            asinTemp = new ASINTemp();

            Set<String> ASINS = new HashSet<String>();
            asinTemp.setAsinSet(ASINS);
        }
        return asinTemp;
    }

    public void addAsin(String asin){

        if(asinSet.size() == MAX_SIZE){
            writeAll();
        }

        asinSet.add(asin);
    }

    public void writeAll(){
        FileWriter writer = null;
        try{
            writer = new FileWriter(getFileName(), true);

            for(String s : asinSet){
                writer.write(s + "\n");
            }

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

        flush();

    }

    public void flush(){
        asinSet.clear();
    }


    public Set<String> getAsinSet(){
        return asinSet;
    }

    public void setAsinSet(Set<String> asinSet){
        this.asinSet = asinSet;
    }

    public static void main(String[] args){
        ASINTemp asinTemp1 = ASINTemp.getInstance();
        asinTemp1.writeAll();
    }

    private String getFileName(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String str=sdf.format(date);

        return FILE_PATH_DIR + str + ".txt";
    }

}
