package us.codecraft.webmagic.amazon;

import us.codecraft.webmagic.amazon.cmd.CmdConstant;
import us.codecraft.webmagic.utils.file.ReaderHelper;
import us.codecraft.webmagic.utils.file.WriterHelper;
import us.codecraft.webmagic.utils.http.HttpResponse;
import us.codecraft.webmagic.utils.http.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by lizeyu on 2016/5/26.
 */
public class CheckerTask implements Callable{

    private String file;
    private int offset;

    private String file404RecordPath = "E:\\amazonad\\thecode.txt";

    private static String adurl_prefix =  "http://www.amazon.com/dp/";

    public CheckerTask(String file, String offset){
        this.file = file;
        this.offset = Integer.parseInt(offset);
    }

    @Override
    public Object call(){
        List<String> adList = ReaderHelper.getAdListFromFile(file);

        if(null == adList){
            return null;
        }

        HttpResponse response = null;
        while(true){
            for(int i=offset; i<adList.size(); i++){
                String adUrl = adurl_prefix + adList.get(i);
                try{
                    response = HttpUtils.get(adUrl, new HashMap<String, String>());
                    System.out.println(adUrl);
                }catch(IOException e){
                    WriterHelper.writeAfter(file404RecordPath, adUrl);
                    e.printStackTrace();
                }

                if(response != null && response.getCode() == 404){
                    WriterHelper.writeAfter(file404RecordPath, adUrl);
                }

                if(checkStopCmd()){
                    stop(file, i);
                }

                sleep();
            }


        }


    }

    /**
     * 保存结束时的运行时环境
     */
    private void stop(String file, int offset){

        String content = file + "\t" + offset;
        WriterHelper.write(CmdConstant.checker_shutdownenv_file_path, content);
        System.exit(1);
    }

    private boolean checkStopCmd(){
        String turnFlag = ReaderHelper.read(CmdConstant.checker_config_file_path);
        if("off".equals(turnFlag)){
            return true;
        }else{
            return false;
        }
    }

    private void sleep(){
        try{
            Thread.sleep(2500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }
}
