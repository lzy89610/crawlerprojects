package us.codecraft.webmagic.amazon;

import us.codecraft.webmagic.amazon.model.ASINTemp;

import java.util.TimerTask;

/**
 * Created by lizeyu on 2016/5/30.
 */
public class SaverTask extends TimerTask{

    private ASINTemp asinTemp;

    public SaverTask(ASINTemp asinTemp){
        this.asinTemp = asinTemp;
    }


    @Override
    public void run(){
        asinTemp.writeAll();
    }
}
