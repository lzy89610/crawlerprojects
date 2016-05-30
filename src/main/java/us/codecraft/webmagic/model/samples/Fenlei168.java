package us.codecraft.webmagic.model.samples;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.samples.pipeline.ClassifyPipeLine;

import javax.management.JMException;
import java.io.IOException;

/**
 * @author code4crafter@gmail.com <br>
 */
@TargetUrl("http://sz.fenlei168.com/\\w+/\\d+.html")
@HelpUrl("http://sz.fenlei168.com/\\w+/")
public class Fenlei168 {

    @ExtractBy("//font[@class='tel']/tidyText()")
    private String telephone;

    @ExtractBy("//div[@class='contact']/ul/li[4]/tidyText()")
    private String QQ;

    @ExtractByUrl("//div[@class='contact']/ul/li[5]/tidyText()")
    private String email;

    @ExtractByUrl("//div[@class='contact']/ul/li[2]/tidyText()")
    private String name;

    public static void main(String[] args) throws IOException, JMException{
        //Just for benchmark
//        Spider thread = OOSpider.create(Site.me().addStartUrl("http://sz.fenlei168.com/ershou/").setSleepTime(0), new ClassifyPipeLine(), Fenlei168.class).thread(20);
        Spider thread = OOSpider.create(Site.me().addStartUrl("http://sz.fenlei168.com/ershou/").setSleepTime(0), new ClassifyPipeLine("d:\\test\\classfiy.txt"),  Fenlei168.class).thread(20);
        thread.start();
//        SpiderMonitor spiderMonitor = SpiderMonitor.instance();
//        spiderMonitor.register(thread);
    }


    public String getTelephone(){
        return telephone;
    }

    public String getQQ(){
        return QQ;
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }
}
