package us.codecraft.webmagic.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lizeyu on 2016/5/27.
 */
public class DateUtils{

    public static SimpleDateFormat date_normal_format = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat time_normal_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String dateToStr(Date date){
        String str = date_normal_format.format(date);
        return str;
    }

    public static Date strToDate(String str){
        Date date = null;
        try{
            date = date_normal_format.parse(str);

        }catch(ParseException e){
            e.printStackTrace();
        }

        return date;
    }

    public static String getNowDateStr(){
        return dateToStr(new Date());
    }

}
