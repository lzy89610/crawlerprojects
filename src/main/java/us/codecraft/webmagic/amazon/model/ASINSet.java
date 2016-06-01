package us.codecraft.webmagic.amazon.model;

import java.util.Set;

/**
 * Created by lizeyu on 2016/6/1.
 */
public class ASINSet{

    private Set<String> ASINSet;

    public ASINSet(Set<String> ASINSet){
        this.ASINSet = ASINSet;
    }

    public Set<String> getASINSet(){
        return ASINSet;
    }

    public void setASINSet(Set<String> ASINSet){
        this.ASINSet = ASINSet;
    }

    @Override
    public String toString(){
        String result = "";

        int i = 0;
        for(String s : ASINSet){
            result += s;
            i++;
            if(i != ASINSet.size()){
                result += "\n";
            }
        }

        return result;
    }
}
