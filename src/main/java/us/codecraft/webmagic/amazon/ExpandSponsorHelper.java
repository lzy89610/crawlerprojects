package us.codecraft.webmagic.amazon;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.utils.http.HttpResponse;
import us.codecraft.webmagic.utils.http.HttpUtils;
import us.codecraft.webmagic.utils.log.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizeyu on 2016/5/27.
 */
public class ExpandSponsorHelper{

    private static String basicStr = "http://www.amazon.com/gp/nemo/spd/handlers/spd-shov.html";

    public static List<String> getExpandSponsor(String productAsin){

        List<String> resultList = new ArrayList<String>();

        Map<String, String> params = new HashMap<String, String>();
        params.put("ASIN", productAsin);
        params.put("ie", "UTF8");
        params.put("searchTerms", "");
        params.put("referringSearchEngine", "");
        params.put("wName", "sp_detail");
        params.put("pg", "2");     //页数，设为2
        params.put("start", "7");  //初始值设为7，因为在加载页面的时候，7以后的都是动态加载的
        params.put("cc", "7");
        params.put("offset", "7");
        params.put("count", "200"); //共加载200个
        params.put("num", "200");
        try{
            HttpResponse response = HttpUtils.get(basicStr, params);
            if(response != null && response.getCode() == 200){
                String content = response.getContent();
                List<String> expandSponsorStrs  = new JsonPathSelector("$.data[*].content").selectList(content);

                for(String s : expandSponsorStrs){
                    Page page = new Page();
                    page.setHtml(new Html(s));
                    String asin = page.getHtml().xpath("//div[1]/@data-asin").toString();
                    resultList.add(asin);
                }
            }else{
                System.out.println("warn: 获取扩展sponsor请求时出错，code:" + response.getCode());
                LogUtil.info("warn: 获取扩展sponsor请求时出错，code:" + response.getCode());
            }
        }catch(Exception e){
            System.out.println("warn: 获取扩展sponsor请求时出错" + e.getMessage());
            LogUtil.info("warn: 获取扩展sponsor请求时出错" + e.getMessage());
        }

        return resultList;
    }





}
