package us.codecraft.webmagic.amazon;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.*;

/**
 * Created by lizeyu on 2016/6/1.
 */
public class ASINPipeLine extends FilePersistentBase implements Pipeline{

    public ASINPipeLine(String path) throws Exception{
        setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task){
        FileWriter writer = null;
        try{
            writer = new FileWriter(path, true);
            writer.write(resultItems.get("asin").toString() + "\n");

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(writer != null){
                try{
                    writer.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }


    }
}
