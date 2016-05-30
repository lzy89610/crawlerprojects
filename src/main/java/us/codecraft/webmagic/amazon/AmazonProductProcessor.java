package us.codecraft.webmagic.amazon;

import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.amazon.cmd.CmdConstant;
import us.codecraft.webmagic.amazon.model.ASINTemp;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.file.ReaderHelper;
import us.codecraft.webmagic.utils.file.WriterHelper;
import us.codecraft.webmagic.utils.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizeyu
 */
public class AmazonProductProcessor implements PageProcessor {

    private Site site;

    private ASINTemp asinTemp;



    public AmazonProductProcessor(Site site, ASINTemp asinTemp){
        this.site = site;
        this.asinTemp = asinTemp;
    }

    @Override
    public void process(Page page){
        getProductsInLineStyle(page);
        getProductsInGridStyle(page);

        //把sponsor的ASIN码保存下来
        List<String> asin = getProductsSponsored(page);
        for(String s : asin){
            if(!StringUtils.isEmpty(s)){
                System.out.println(s);
                asinTemp.addAsin(s);
            }
        }

        //保存结束时的运行时环境，并退出程序
        if(checkStopCmd()){
            stop(page);
        }
    }

    private List<String> getProductsSponsored(Page page){
        //把页面中已经展现的sponsor加到target中
        List<String> asin = page.getHtml().xpath("//li[@class='a-carousel-card']/div/@data-asin").all();
        for(String s : asin){
            page.addTargetRequest("http://www.amazon.com/dp/" + s);
        }

        //模拟点击扩展按钮获取更多sponsored商品
        String thisAsin = page.getHtml().xpath("//div[@id='detailBullets_feature_div']//li[4]//span[2]/tidyText()")
                .toString(); // 获取当前页面的ASIN

        if(StringUtils.isEmpty(thisAsin)){
            thisAsin = page.getHtml().xpath("//div[@id='buybox']//form//input[@id='ASIN']//@value")
                    .toString(); // 获取当前页面的ASIN
        }

        if(!StringUtils.isEmpty(thisAsin)){
            List<String> expandAsin = ExpandSponsorHelper.getExpandSponsor(thisAsin);
            asin.addAll(expandAsin);
        }else{
            System.out.println("该页面没有解析出ASIN码，导致无法查询出扩展信息，请核实页面" + page.getUrl());
            LogUtil.info("该页面没有解析出ASIN码，导致无法查询出扩展信息，请核实页面" + page.getUrl());
        }

        return asin;
    }

    private void getProductsInLineStyle(Page page){
        List<String> products = page.getHtml().xpath("//a[@class='a-link-normal']//@href").regex("http://www.amazon.com/.*/dp/.*").all();

        page.addTargetRequests(products);
    }

    private void getProductsInGridStyle(Page page){
        List<String> products = page.getHtml().xpath("//div[@id='srecs-wj-feature']/div/@data-asin").all();

        String asins = page.getHtml().xpath("//div[@id='srecs-wj-feature']/div/@data-asins").toString();
        if(null != asins){
            String[] asinsArr = asins.split(",");
            for(String s : asinsArr){
                products.add(s);
            }
        }

        page.addTargetRequests(products);
    }

    /**
     * 保存结束时的运行时环境
     * @param page
     */
    private void stop(Page page){
        List<Request> requestUrls = page.getTargetRequests();
        List<String> requestUrlStr = new ArrayList<String>();
        for(Request url : requestUrls){
            requestUrlStr.add(url.getUrl());
        }
        WriterHelper.write(CmdConstant.shutdownenv_file_path, requestUrlStr);

        asinTemp.writeAll();

        System.exit(1);
    }

    private boolean checkStopCmd(){

        String turnFlag = ReaderHelper.read(CmdConstant.config_file_path);
        if("off".equals(turnFlag)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Site getSite(){
        return site;
    }


}
