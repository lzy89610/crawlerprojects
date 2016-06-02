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

        Integer statusCode = (Integer)request.getExtra("statusCode");

        if(e instanceof ConnectTimeoutException){
            logger.error(e.getMessage());
        }

        if(e instanceof RuntimeException){
            if(404 == statusCode){
                //todo:
                logger.error("404: ", request.getUrl());
            }else if(403 == statusCode){
                logger.error("403:", request.getUrl());
            }else if(0 == statusCode){
                logger.error("0:", request.getUrl());
            }else{
                logger.error(e.getMessage());
            }

            logger.error(e.getMessage());
        }

    }


}
