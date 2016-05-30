package us.codecraft.webmagic.utils.http;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class HttpUtils {
    private static final String TAG = HttpUtils.class.getSimpleName();
    private static final String CHARSET = "UTF-8";

    /**
     * 发送GET请求
     * @param urlString - URL地址
     * @param params - 参数集合
     * @return 响应对象
     * @throws java.io.IOException
     */
    public static final HttpResponse get(String urlString, Map<String, String> params) throws IOException{
        return get(urlString, params, null);
    }

    /**
     * 发送GET请求
     * @param urlString - URL地址
     * @param params - 参数集合
     * @param propertys - 请求属性
     * @return 响应对象
     * @throws java.io.IOException
     */
    public static final HttpResponse get(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException{
        HttpRequester request = new HttpRequester();
        HttpResponse hr = request.sendGet(urlString, params, propertys);
        return hr;
    }

    /**
     * 发送POST请求
     * @param urlString - URL地址
     * @param params - 参数集合
     * @param propertys - 请求属性
     * @return 响应对象
     * @throws java.io.IOException
     */
    public static final HttpResponse post(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException{
        HttpRequester request = new HttpRequester();   
        HttpResponse hr = request.sendPost(urlString, params, propertys);
        return hr;
    }
    
//    /**
//     * 用HttpClient方式GET
//     * @param urlString
//     * @param params
//     * @return
//     */
//    public static final org.apache.http.HttpResponse get(String urlString, Map<String, String> params) {
//        try {
//            StringBuffer param = new StringBuffer();
//            if (params != null && params.size() > 0) {
//                int i = 0;
//                for (String key : params.keySet()) {
//                    param.append((i == 0) ? "?" : "&");
//                      param.append(key).append("=").append(params.get(key));
//                    i++;
//                }
//            }
//            urlString += param;
//            HttpClient httpClient = new DefaultHttpClient();
//            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
//            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
//            HttpGet httpGet = new HttpGet(urlString);
//
//            return httpClient.execute(httpGet);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    /**
     * 用HttpClient方式POST
     * @param urlString
     * @param params
     * @return
     */
    public static final org.apache.http.HttpResponse post(String urlString, Map<String, String> params) {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10*1000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10*1000);
        HttpPost httpPost = new HttpPost(urlString);
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                parameters.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(parameters, CHARSET);
            httpPost.setEntity(entity);
            return httpClient.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	 /**
     * 用HttpClient方式POST
     * @param urlString
     * @param params
     * @return
     */
    public static final org.apache.http.HttpResponse postByCookie(String urlString, Map<String, String> params,Map<String,String> cookies)throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10*1000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10*1000);
        HttpPost httpPost = new HttpPost(urlString);
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                parameters.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        StringBuilder cookieSb=new StringBuilder();
        if(cookies!=null){
        	Set<String> keySet=cookies.keySet();
        	Iterator<String> it=keySet.iterator();
        	while(it.hasNext()){
        		String key=it.next();
        		String value=cookies.get(key);
        		cookieSb.append(";").append(key).append("=").append(value);
        	}
        }
        httpPost.addHeader("Cookie", cookieSb.toString());
        UrlEncodedFormEntity entity = null;
        entity = new UrlEncodedFormEntity(parameters);
        httpPost.setEntity(entity);
        return httpClient.execute(httpPost);
    }
    /**
	 * 获取allCookieStr中key对应的值，allCookieStr格式：VERIFY_KEY=54D79CF25C1BFC381B347D15BECF2841; PATH=/; DOMAIN=xunlei.com;
	 * @param allCookieStr
	 * @param key
	 * @return
	 */
	public  static final String getCookieKeyValue(String allCookieStr,String key){
		if(allCookieStr==null||"".equals(allCookieStr)){
			return "";
		}
		String[] strArray=allCookieStr.split(";");
		if(strArray.length==0){
			return "";
		}
		for(String str:strArray){
			if("".equals(str)){
				continue;
			}
			String[] temp=str.split("=");
			if(!key.equals(temp[0])){
				continue;
			}
			return temp.length==2?temp[1]:"";
		}
		return "";
	}

    /**
     * 拼接urlstring
     * @param urlString
     * @param parameters
     * @return
     */
    public static String spliceUrlStr(String urlString, Map<String, String> parameters){
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : parameters.keySet()) {
            param.append((i == 0)?"?":"&");
            param.append(key).append("=").append(parameters.get(key));
            i++;
        }
        urlString += param;

        return urlString;
    }


    public static void main(String[] args){
        String basicUrl = "http://www.amazon.com/gp/nemo/spd/handlers/spd-shov.html";
        Map<String, String> params = new HashMap<String, String>();
        params.put("ASIN", "B00CPPQI9A");
        params.put("ie", "");
        params.put("searchTerms", "");
        params.put("referringSearchEngine", "");
        params.put("wName", "sp_detail");
        params.put("start", "7");
        params.put("cc", "7");
        params.put("count", "200");
        params.put("offset", "7");
        params.put("pg", "2");
        params.put("num", "200");

        String url = spliceUrlStr(basicUrl, params);
        System.out.println(url);
    }


}
