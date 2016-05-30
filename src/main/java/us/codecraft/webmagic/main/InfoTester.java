package us.codecraft.webmagic.main;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.samples.Fenlei168;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created by lizeyu on 2016/2/29.
 */
public class InfoTester{

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
        System.out.println("The demo started and will last 20 seconds...");
        //Start spider
        OOSpider.create(Site.me(), Fenlei168.class)
                .addUrl("http://sz.fenlei168.com/ershou/")
                .addPipeline(new ConsolePipeline())
                .runAsync();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The demo stopped!");
        System.out.println("To more usage, try to customize your own Spider!");
        System.exit(0);
    }
}
