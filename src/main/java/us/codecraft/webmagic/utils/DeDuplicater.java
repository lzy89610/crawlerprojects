package us.codecraft.webmagic.utils;

import us.codecraft.webmagic.utils.file.ReaderHelper;
import us.codecraft.webmagic.utils.file.WriterHelper;

import java.util.Set;

/**
 * Created by lizeyu on 2016/5/26.
 */
public class DeDuplicater{

    public static void deDepblicate(String inputFile, String outputFile){

        Set<String> set = ReaderHelper.getAdListFromFileToSet(inputFile);
        WriterHelper.write(outputFile, set);
    }

    public static void main(String[] args){
        DeDuplicater.deDepblicate("E:\\amazonad\\zhubao.txt", "E:\\amazonad\\newzhubao.txt");

    }

}
