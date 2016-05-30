package us.codecraft.webmagic.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;


/**
 * HTTP请求对象
 * @author xixi
 */
public class HttpRequester {
    private static final String TAG = HttpRequester.class.getSimpleName();
    private String defaultContentEncoding;
 
    public HttpRequester() {
        this.defaultContentEncoding = Charset.defaultCharset().name();
    }
 
    /**
     * 发送GET请求
     * @param urlString - URL地址
     * @return 响应对象
     * @throws java.io.IOException
     */
    public HttpResponse sendGet(String urlString) throws IOException{
        return this.send(urlString, "GET", null, null);
    }

    /**
     * 发送GET请求
     * @param urlString - URL地址
     * @param params - 参数集合
     * @return 响应对象
     * @throws java.io.IOException
     */
    public HttpResponse sendGet(String urlString, Map<String, String> params) throws IOException{
        return this.send(urlString, "GET", params, null);
    }

    /**
     * 发送GET请求
     * @param urlString - URL地址
     * @param params - 参数集合
     * @param propertys - 请求属性
     * @return 响应对象
     * @throws java.io.IOException
     */
    public HttpResponse sendGet(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException{
        return this.send(urlString, "GET", params, propertys);
    }

    /**
     * 发送POST请求
     * @param urlString - URL地址
     * @return 响应对象
     * @throws java.io.IOException
     */
    public HttpResponse sendPost(String urlString) throws IOException{
        return this.send(urlString, "POST", null, null);
    }

    /**
     * 发送POST请求
     * @param urlString - URL地址
     * @param params - 参数集合
     * @return 响应对象
     * @throws java.io.IOException
     */
    public HttpResponse sendPost(String urlString, Map<String, String> params) throws IOException{
        return this.send(urlString, "POST", params, null);
    }

    /**
     * 发送POST请求
     * @param urlString - URL地址
     * @param params - 参数集合
     * @param propertys - 请求属性
     * @return 响应对象
     * @throws java.io.IOException
     */
    public HttpResponse sendPost(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException{
        return this.send(urlString, "POST", params, propertys);
    }

    /**
     * 发送HTTP请求
     * @param urlString
     * @return 响映对象
     * @throws java.io.IOException
     */
    private HttpResponse send(String urlString, String method, Map<String, String> parameters, Map<String, String> propertys) throws IOException{
        HttpURLConnection urlConnection = null;
        if ("GET".equalsIgnoreCase(method) && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                param.append((i == 0)?"?":"&");
                param.append(key).append("=").append(parameters.get(key));
                i++;
            }
            urlString += param;
        }
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_8; en-US) AppleWebKit/532.5 (KHTML, like Gecko) Chrome/4.0.249.0 Safari/532.5");

        if (propertys != null)
            for (String key : propertys.keySet()) {
                urlConnection.addRequestProperty(key, propertys.get(key));
            }

        if (method.equalsIgnoreCase("POST") && parameters != null) {
            StringBuffer param = new StringBuffer();
            for (String key : parameters.keySet()) {
                param.append("&");
                param.append(key).append("=").append(parameters.get(key));
            }
            urlConnection.getOutputStream().write(param.toString().getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }

        return this.makeContent(urlString, urlConnection);
    }

    /**
     * 得到响应对象
     * @param urlConnection
     * @return 响应对象
     * @throws java.io.IOException
     */
    private HttpResponse makeContent(String urlString, HttpURLConnection urlConnection) throws IOException{
        HttpResponse httpResponser = new HttpResponse();
        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(in));
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            Vector<String> contentCollection = httpResponser.getContentCollection();
            while (line != null) {
                contentCollection.add(line);
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
 
            String ecod = urlConnection.getContentEncoding();
            if (ecod == null)
                ecod = getDefaultContentEncoding();
 
            httpResponser.setUrlString(urlString);
 
            httpResponser.setDefaultPort(urlConnection.getURL().getDefaultPort());
            httpResponser.setFile(urlConnection.getURL().getFile());
            httpResponser.setHost(urlConnection.getURL().getHost());
            httpResponser.setPath(urlConnection.getURL().getPath());
            httpResponser.setPort(urlConnection.getURL().getPort());
            httpResponser.setProtocol(urlConnection.getURL().getProtocol());
            httpResponser.setQuery(urlConnection.getURL().getQuery());
            httpResponser.setRef(urlConnection.getURL().getRef());
            httpResponser.setUserInfo(urlConnection.getURL().getUserInfo());
 
            httpResponser.setContent(new String(temp.toString().getBytes(), ecod));
            httpResponser.setContentEncoding(ecod);
            httpResponser.setCode(urlConnection.getResponseCode());
            httpResponser.setMessage(urlConnection.getResponseMessage());
            httpResponser.setContentType(urlConnection.getContentType());
            httpResponser.setMethod(urlConnection.getRequestMethod());
            httpResponser.setConnectTimeout(urlConnection.getConnectTimeout());
            httpResponser.setReadTimeout(urlConnection.getReadTimeout());
 
            return httpResponser;
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }
 
    /**
     * 默认的响应字符集
     */
    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }
 
    /**
     * 设置默认的响应字符集
     */
    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }
}