package us.codecraft.webmagic.samples.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.model.samples.Fenlei168;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.*;
import java.util.Map;

/**
 * Created by lizeyu on 2016/2/29.
 */
public class ClassifyPipeLine extends FilePersistentBase implements PageModelPipeline<Fenlei168>{

    private Logger logger = LoggerFactory.getLogger(getClass());

    private PrintWriter printWriter;

    /**
     * create a FilePipeline with default path"/data/webmagic/"
     */
    public ClassifyPipeLine() throws FileNotFoundException, UnsupportedEncodingException{
        this("/data/webmagic/");
    }

    public ClassifyPipeLine(String path) throws FileNotFoundException, UnsupportedEncodingException {
        setPath(path);
        printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path)), "UTF-8"));
    }

//    @Override
//    public synchronized void process(ResultItems resultItems, Task task){
//
//    }

    @Override
    public void process(Fenlei168 fenlei168, Task task){
//        String url = fenlei168.get;

//        //如果不是item项的链接，就不解析
//        if(!isDetailItem(url)){
//            return;
//        }
//
//        String city = getCity(url);     //获取城市信息
//        String classifyType = getType(url);     //获取分类信息
//        String resultStr = city + "\t" + classifyType + "\t";
        String resultStr = fenlei168.getEmail() + fenlei168.getName();

//        boolean isEmpty = false;
//        for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet()){
//            if(entry.getValue() == null){
//                isEmpty = true;
//                break;
//            }
//            resultStr += cleanText(entry) + "\t";
//        }
//
//        if(!isEmpty){
//            printWriter.println(resultStr);
//            printWriter.flush();
//        }
        printWriter.println(resultStr);
            printWriter.flush();

    }

    private boolean isDetailItem(String url){
        String temp = url.substring(url.indexOf("//")+1, url.length());
        if(temp.split("/").length != 4){
            return false;
        }else{
            return true;
        }
    }

    private String getCity(String url){
        String result = url.substring(url.indexOf('/') + 2, url.indexOf('.'));

        return result;
    }

    private String getType(String url){
        String temp = url.substring(url.indexOf("//")+1, url.length());
        String result = temp.split("/")[2];

        return result;
    }

    public String cleanText(Map.Entry<String, Object> entry){
        String result = "";

        if(null == entry){
            return result;
        }

        String key = entry.getKey();
        String value = ((PlainText) entry.getValue()).toString();
        if("telephone".equals(key)){
            result = value.substring(0, 11);
        }else if("QQ".equals(key)){
            result = value.substring(value.indexOf('：')+1, value.length());
        }else if("email".equals(key)){
            result = value.substring(value.indexOf('：')+1, value.length());
        }else if("name".equals(key)){
            result = value.substring(value.indexOf('：')+1, value.indexOf('：')+4);
        }else{
            result = "";
        }

        return result;


    }


}
