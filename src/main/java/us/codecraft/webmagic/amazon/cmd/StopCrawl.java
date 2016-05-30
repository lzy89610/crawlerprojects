package us.codecraft.webmagic.amazon.cmd;

import us.codecraft.webmagic.utils.file.WriterHelper;

/**
 * Created by lizeyu on 2016/5/27.
 */
public class StopCrawl{

    public static void main(String[] args){
        WriterHelper.write(CmdConstant.config_file_path, "off");
    }

}
