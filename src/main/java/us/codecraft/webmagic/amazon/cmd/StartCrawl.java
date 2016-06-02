package us.codecraft.webmagic.amazon.cmd;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.amazon.ASINPipeLine;
import us.codecraft.webmagic.amazon.AmazonProductProcessor;
import us.codecraft.webmagic.amazon.AmazonSpiderListener;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.utils.date.DateUtils;
import us.codecraft.webmagic.utils.file.WriterHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizeyu on 2016/5/27.
 */
public class StartCrawl{

    public static void main(String[] args) throws Exception{
        WriterHelper.write(CmdConstant.config_file_path, "on");

        //site定义抽取配置，以及开始url等
         Site site = Site.me().setDomain("www.amazon.com")
                    .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
                    .setRetryTimes(2)
                    .addCookie("amznacsleftnav-458791882", "1")
                    .addCookie("amznacsleftnav-463302482", "1")
                    .addCookie("csm-hit", "0P4Q53Z7RSBMA8H98N0P+s-0P4Q53Z7RSBMA8H98N0P|1464328858866")
                    .addCookie("lc-main", "en_US")
                    .addCookie("s_dslv", "1459924376707")
                    .addCookie("s_nr", "1459924376705-New")
                    .addCookie("s_pers", "%20s_fid%3D79B7F0E741B7FE96-0309B0A345BA74A2%7C1522996381824%3B%20s_dl%3D1%7C1459926181825%3B%20gpv_page%3DUS%253AAZ%253ASOA-overview-sell%7C1459926181828%3B%20s_ev15%3D%255B%255B%2527AZUSSOA-sell%2527%252C%25271459924381846%2527%255D%255D%7C1617690781846%3B")
                    .addCookie("session-token", "FFhlXMMg8csc/iCb6sOmCxFde/UOp5q3V+7zk/LrSDxJcZU3Cgy//Fdg1chsT3E/xbdVnV5pQQWyzw4JG8DxnEUGn2g3lyPYfAxsi5gYo6nQhhSqnhM6rHm71FB2OgMsjC4Ak06Ays3DmQpVy22+o413ZCGde0Kac39Jf1sw6kpoRZQJCKq02UMKnhUGiYnKNntSTpgZNOCnwp+QtzuHIim2WoNQ8ZbFSpYtNSUASdhGWEqiIwkoUm0cm0sPo6k7")
                    .addCookie("skin", "noskin")
                    .addCookie("x-amz-captcha-1", "1464272540847892")
                    .addCookie("x-amz-captcha-2", "oSWzs1Vg17Tq6feu9xzTEA==")
                    .addCookie("session-id-time", "2082787201l")
                    .addCookie("session-id", "188-0121876-7999171")
                    .addCookie("s_vnum", "1891924376706%26vn%3D1")
                    .addCookie("x-wl-uid", "1Tb1iVjeTxc6qyw8cVk6+dQjs+mCPqfgtxBVrhEXd5BybuEEs7iTEeOrakHOfyKPlN6XDYPUZv2c=")
                    .addCookie("ubid-main", "191-4028859-2701517")
                    .setSleepTime(3000)
                    .setTimeOut(5000)
                    .setRetryTimes(2);
//                    .addHeader("Upgrade-Insecure-Requests", "1")

//        site = init(site);
        String date = DateUtils.getNowDateStr();

        List<SpiderListener> spiderListeners = new ArrayList<SpiderListener>();
        spiderListeners.add(new AmazonSpiderListener());

        Spider.create(new AmazonProductProcessor(site))
                .setSpiderListeners(spiderListeners)
                .setScheduler(new FileCacheQueueScheduler("E:\\amazonad\\config"))
                .addPipeline(new ASINPipeLine("E:\\amazonad\\rawasin\\" + date + ".txt"))
                .thread(5)
                .run();

    }

//    /**
//     * 装载上一次运行时中的所有的url
//     * @param site
//     * @return
//     */
//    private static Site init(Site site){
//        List<String> requestUrls = ReaderHelper.getAdListFromFile(CmdConstant.shutdownenv_file_path);
//
//        for(String s : requestUrls){
//            site.addStartUrl(s);
//        }
//
//        return site;
//    }

}
