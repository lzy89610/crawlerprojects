package us.codecraft.webmagic.amazon.model;

/**
 * Created by lizeyu on 2016/5/26.
 */
public class AdVo{

    private String inches;
    private String weight;
    private String country;
    private String asin;
    private String unkowncode;
    private String date;

    public String getInches(){
        return inches;
    }

    public void setInches(String inches){
        this.inches = inches;
    }

    public String getWeight(){
        return weight;
    }

    public void setWeight(String weight){
        this.weight = weight;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getAsin(){
        return asin;
    }

    public void setAsin(String asin){
        this.asin = asin;
    }

    public String getUnkowncode(){
        return unkowncode;
    }

    public void setUnkowncode(String unkowncode){
        this.unkowncode = unkowncode;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    @Override
    public String toString(){
        return "" +
                inches + '\t' +
                weight + '\t' +
                country + '\t' +
                asin + '\t' +
                unkowncode + '\t' +
                date + '\t';
    }
}
