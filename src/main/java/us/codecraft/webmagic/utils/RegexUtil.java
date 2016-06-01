package us.codecraft.webmagic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lizeyu on 2016/6/1.
 */
public class RegexUtil{

    public static boolean regex(String content, String patternStr){
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(content);
        boolean isMatch = matcher.matches(); //当条件满足时，将返回true，否则返回false

        return isMatch;
    }


}
