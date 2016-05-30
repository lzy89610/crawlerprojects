package us.codecraft.webmagic.main;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

class MyStarter implements PageProcessor{

    private Site site = Site.me().setDomain("my.oschina.net")
            .addStartUrl("http://sz.fenlei168.com")
            .addStartUrl("http://bj.fenlei168.com");

    @Override
    public void process(Page page) {
        List<String> links = page.getHtml().links().regex("http://sz.fenlei168.com/\\w+/\\d+.html").all();
        page.addTargetRequests(links);
        page.putField("telephone", page.getHtml().xpath("//font[@class='tel']/tidyText()"));
        page.putField("QQ", page.getHtml().xpath("//div[@class='contact']/ul/li[4]/tidyText()"));
        page.putField("email",page.getHtml().xpath("//div[@class='contact']/ul/li[5]/tidyText()"));
        page.putField("name",page.getHtml().xpath("//div[@class='contact']/ul/li[2]/tidyText()"));
    }


    @Override
    public Site getSite() {
        return site;

    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
        Spider.create(new MyStarter())
                .pipeline(new ConsolePipeline())
//                .pipeline(new ClassifyPipeLine("d:\\test\\userphoneinfo.txt"))
                .run();
    }
}