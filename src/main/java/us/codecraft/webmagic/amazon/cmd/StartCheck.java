package us.codecraft.webmagic.amazon.cmd;

import us.codecraft.webmagic.amazon.CheckerTask;
import us.codecraft.webmagic.utils.file.ReaderHelper;
import us.codecraft.webmagic.utils.file.WriterHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lizeyu on 2016/5/27.
 */
public class StartCheck{

    public static void main(String[] args){
        WriterHelper.write(CmdConstant.checker_shutdownenv_file_path, "on");

        List<Map<String, String>> envMaps = init();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new CheckerTask(envMaps.get(0).get("file"), envMaps.get(0).get("offset")));

    }

    /**
     * 装载上一次运行时中的所有的url
     * @return
     */
    private static List<Map<String, String>> init(){
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();

        List<String> envList = ReaderHelper.getAdListFromFile(CmdConstant.checker_shutdownenv_file_path);

        for(String env : envList){
            Map<String, String> map = new HashMap<String, String>();
            String[] envArr = env.split("\\t");
            map.put("file", envArr[0]);
            map.put("offset", envArr[1]);

            resultList.add(map);
        }

        return resultList;
    }

}
