package us.codecraft.webmagic.amazon;

import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.amazon.model.ASINSet;
import us.codecraft.webmagic.amazon.util.AmazonUrlFormatUtil;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.log.LogUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lizeyu
 */
public class AmazonProductProcessor implements PageProcessor {

    private Site site;

    public AmazonProductProcessor(Site site){
        this.site = site;
    }

    @Override
    public void process(Page page){
        getProductsInLineStyle(page);
        getProductsInGridStyle(page);

        //把sponsor的ASIN码保存下来
        Set<String> asin = getProductsSponsored(page);
        page.putField("asin", new ASINSet(asin));
    }

    private Set<String> getProductsSponsored(Page page){
        //把页面中已经展现的sponsor加到target中
        List<String> asins = page.getHtml().xpath("//li[@class='a-carousel-card']/div/@data-asin").all();
        for(String asin : asins){
            page.addTargetRequest(AmazonUrlFormatUtil.fromASIN(asin));
        }

//        //模拟点击扩展按钮获取更多sponsored商品
//        String thisAsin = page.getHtml().xpath("//div[@id='detailBullets_feature_div']//li[4]//span[2]/tidyText()")
//                .toString(); // 获取当前页面的ASIN
//
//        if(StringUtils.isEmpty(thisAsin)){
//            thisAsin = page.getHtml().xpath("//div[@id='buybox']//form//input[@id='ASIN']//@value")
//                    .toString(); // 获取当前页面的ASIN
//        }

        String thisAsin = AmazonUrlFormatUtil.fromUrl(page.getUrl().toString());
        if(!StringUtils.isEmpty(thisAsin)){
            List<String> expandAsins = ExpandSponsorHelper.getExpandSponsor(thisAsin);
            asins.addAll(expandAsins);

            //添加到request中
            List<String> expandUrls = AmazonUrlFormatUtil.fromASIN(expandAsins);
            page.addTargetRequests(expandUrls);
        }else{
            System.out.println("该页面没有解析出ASIN码，导致无法查询出扩展信息，请核实页面" + page.getUrl());
            LogUtil.info("该页面没有解析出ASIN码，导致无法查询出扩展信息，请核实页面" + page.getUrl());
        }

        return new HashSet<String>(asins);
    }

    private void getProductsInLineStyle(Page page){
        List<String> products = page.getHtml().xpath("//a[@class='a-link-normal']//@href").regex("http://www.amazon.com/.*/dp/.*").all();
        List<String> productsUrlAfterFormat = reformatUrls(products);
        page.addTargetRequests(productsUrlAfterFormat);
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

        List<String> productsUrlAfterFormat = reformatUrls(products);
        page.addTargetRequests(productsUrlAfterFormat);
    }

    private List<String> reformatUrls(List<String> products){
        List<String> productsUrlAfter = new ArrayList<String>();
        for(String s : products){
            AmazonUrlFormatUtil.fromUrl(s);
        }

        return productsUrlAfter;
    }

//    /**
//     * 保存结束时的运行时环境
//     * @param page
//     */
//    private void stop(Page page){
//        List<Request> requestUrls = page.getTargetRequests();
//        List<String> requestUrlStr = new ArrayList<String>();
//        for(Request url : requestUrls){
//            requestUrlStr.add(url.getUrl());
//        }
//        WriterHelper.write(CmdConstant.shutdownenv_file_path, requestUrlStr);
//
//        asinTemp.writeAll();
//
//        System.exit(1);
//    }

//    private boolean checkStopCmd(){
//
//        String turnFlag = ReaderHelper.read(CmdConstant.config_file_path);
//        if("off".equals(turnFlag)){
//            return true;
//        }else{
//            return false;
//        }
//    }

    @Override
    public Site getSite(){
        return site;
    }


}
