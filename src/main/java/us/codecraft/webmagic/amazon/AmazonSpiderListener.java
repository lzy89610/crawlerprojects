package us.codecraft.webmagic.amazon;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.SpiderListener;

/**
 * Created by lizeyu on 2016/6/2.
 */
public class AmazonSpiderListener implements SpiderListener{


    @Override
    public void onSuccess(Request request){

    }

    @Override
    public void onError(Request request, Exception e){
        e.printStackTrace();
    }


}
