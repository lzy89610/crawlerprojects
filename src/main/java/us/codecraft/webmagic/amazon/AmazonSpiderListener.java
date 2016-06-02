package us.codecraft.webmagic.amazon;

import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.SpiderListener;

/**
 * Created by lizeyu on 2016/6/2.
 */
public class AmazonSpiderListener implements SpiderListener{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onSuccess(Request request){

    }

    @Override
    public void onError(Request request, Exception e){

        String statusCode = (String) request.getExtra("statusCode");

        if("404".equals(statusCode)){
            //todo:
            logger.error("can't get :", statusCode);
        }else if("403".equals(statusCode)){
            logger.error("can't get :", statusCode);
        }else{
            logger.error("can't get :", statusCode);
        }

        if(e instanceof ConnectTimeoutException){
            logger.error(e.getMessage());
        }

        if(e instanceof RuntimeException){
            logger.error(e.getMessage());
        }

    }


}
