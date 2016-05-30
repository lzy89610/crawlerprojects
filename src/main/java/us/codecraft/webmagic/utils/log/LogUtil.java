package us.codecraft.webmagic.utils.log;

import us.codecraft.webmagic.utils.date.DateUtils;
import us.codecraft.webmagic.utils.file.WriterHelper;

/**
 * Created by lizeyu on 2016/5/27.
 */
public class LogUtil{

    public static String logFile = "E:\\amazonad\\log\\";

    public static void info(String msg){
        WriterHelper.writeAfter(logFile + DateUtils.getNowDateStr(), msg + "\n");

    }




}
