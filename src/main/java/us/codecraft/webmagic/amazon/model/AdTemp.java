package us.codecraft.webmagic.amazon.model;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lizeyu on 2016/5/26.
 */
public class AdTemp{

    private Set<AdVo> ads;
    private static Integer MAX_SIZE = 100;
    private static String FILE_PATH_DIR = "e:\\amazonaddetail\\";

    private static AdTemp adTemp;

    private AdTemp(){};

    public static AdTemp getInstance(){
        if(null == adTemp){
            adTemp = new AdTemp();

            Set<AdVo> ads = new HashSet<AdVo>();
            adTemp.setAds(ads);
        }
        return adTemp;
    }

    public void addOne(AdVo ad){

        if(ads.size() == MAX_SIZE){
            writeAll();
        }

        ads.add(ad);
    }

    public void writeAll(){
        FileWriter writer = null;
        try{
            writer = new FileWriter(getFileName(), true);

            for(AdVo s : ads){
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
        ads.clear();
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

    public Set<AdVo> getAds(){
        return ads;
    }

    public void setAds(Set<AdVo> ads){
        this.ads = ads;
    }


}
