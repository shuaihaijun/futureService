package com.future.common.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description 微服务请求
 * @Date 2017/12/13
 */
public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    //请求方式
    public static String GET = "get";
    public static String PUT = "put";
    public static String POST = "post";

    /****
     * post请求(用于key-value格式的参数)  不带签名
	 * @param url
	 * @param params
	 * @return
     */
    public static String doPost(String url, Map params){

        BufferedReader in = null;
        try {
            // 定义HttpClient
            org.apache.http.client.HttpClient client = new DefaultHttpClient();
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);//连接时间
            // 实例化HTTP方法
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));
            if(params!=null){
                //设置参数
                List<BasicNameValuePair> nvps = new ArrayList<>();
                for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
                    String name = (String) iter.next();
                    String value = String.valueOf(params.get(name));
                    nvps.add(new BasicNameValuePair(name, value));
                }
                request.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            }
            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){	//请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent(),"utf-8"));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
                return sb.toString();
            }else{
                logger.error("请求失败，状态码code："+code);
                return null;
            }
        }catch(ConnectTimeoutException e){
            /*超时异常特殊处理*/
            logger.error("请求超时！");
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }catch(Exception e){
            logger.error("请求失败！");
            logger.error(e.getMessage(),e);
            return null;
        }
    }

}
