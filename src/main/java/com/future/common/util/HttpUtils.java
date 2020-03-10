package com.future.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Description 微服务请求
 * @Date 2017/12/13
 */
public class HttpUtils {

    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * get请求
     * @param url
     * @return
     */
    public static String httpGet(String url){
        RestTemplate restTemplate=new RestTemplate();
        String result=restTemplate.exchange(url, HttpMethod.GET,null,String.class).getBody();
        return result;
    }

    /**
     * post请求
     * @param url
     * @param param
     * @return
     */
    public static String httpPost(String url,Map param){
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(param, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        return response.getBody();
    }

}
