package us.codecraft.webmagic.amazon;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.amazon.model.AdTemp;
import us.codecraft.webmagic.amazon.model.AdVo;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.file.ReaderHelper;

import java.util.List;

/**
 * @author lizeyu
 */
public class AmazonAdProcessor implements PageProcessor {

    private Site site;

    private static AdTemp adTemp;

    @Override
    public void process(Page page) {


        String inches = page.getHtml().xpath("//div[@id='detailBullets_feature_div']//li[1]//span[2]/tidyText()").toString();

        String weight = page.getHtml().xpath("//div[@id='detailBullets_feature_div']//li[2]//span[2]/tidyText()").toString();

        String country = page.getHtml().xpath("//div[@id='detailBullets_feature_div']//li[3]//span[2]/tidyText()").toString();

        String asin = page.getHtml().xpath("//div[@id='detailBullets_feature_div']//li[4]//span[2]/tidyText()").toString();

        String unkowncode = page.getHtml().xpath("//div[@id='detailBullets_feature_div']//li[5]//span[2]/tidyText()").toString();

        String date = page.getHtml().xpath("//div[@id='detailBullets_feature_div']//li[6]//span[2]/tidyText()").toString();


        AdVo adVo = new AdVo();
        adVo.setInches(inches);
        adVo.setWeight(weight);
        adVo.setCountry(country);
        adVo.setAsin(asin);
        adVo.setUnkowncode(unkowncode);
        adVo.setDate(date);
        adTemp.addOne(adVo);


    }

    @Override
    public Site getSite() {
        //site定义抽取配置，以及开始url等
        if (site == null) {
            site = Site.me().setDomain("www.amazon.com")
                    .addStartUrl("http://www.amazon.com/dp/B01EHXJ9GQ")
                    .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

            List<String> ads = ReaderHelper.getAdListFromFile("E:\\amazonad\\2016-05-26.txt");
            for(String s : ads){
                site.addStartUrl("http://www.amazon.com/dp/" + s);
            }
        }
        return site;
    }

    public static void main(String[] args) {
        adTemp = AdTemp.getInstance();
        Spider.create(new AmazonAdProcessor()).run();
    }
}
