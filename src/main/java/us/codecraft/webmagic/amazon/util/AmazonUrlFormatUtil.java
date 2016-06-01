package us.codecraft.webmagic.amazon.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizeyu on 2016/6/1.
 */
public class AmazonUrlFormatUtil{

    /**
     * get ASIN from url
     * @param url
     * @return
     */
    public static String fromUrl(String url){

        if(url.matches("https://www.amazon.com/dp/.*") ||
                url.matches("http://www.amazon.com/dp/.*")){
            String[] strArray = url.split("/");
            return strArray[4];
        }else{
            String[] strArray = url.split("/");
            return strArray[5];
        }
    }

    /**
     * get ASIN from url
     * @param urlList
     * @return
     */
    public static List<String> fromUrl(List<String> urlList){
        List<String> results = new ArrayList<String>();

        for(String s : urlList){
            results.add(fromUrl(s));
        }

        return results;
    }

    /**
     * get url from ASIN
     * @param asin
     * @return
     */
    public static String fromASIN(String asin){
        return "http://www.amazon.com/dp/" + asin;
    }

    /**
     * get url from ASIN
     * @param asinList
     * @return
     */
    public static List<String> fromASIN(List<String> asinList){
        List<String> results = new ArrayList<String>();

        for(String s : asinList){
            results.add(fromASIN(s));
        }

        return results;
    }


}
