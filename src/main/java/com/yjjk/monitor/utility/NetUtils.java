/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: NetUtils
 * Author:   CentreS
 * Date:     2019-06-24 14:10
 * Description: 网络工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.utility;

/**
 * @Description: 网络工具
 * @author CentreS
 * @create 2019-06-24
 */

import com.alibaba.fastjson.JSON;
import com.yjjk.monitor.entity.TokenResultEntity.TokenResult;
import com.yjjk.monitor.entity.properties.AreaSign;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.Map.Entry;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2016/11/28.
 * 基于HttpClient提供网络访问工具
 */
public final class NetUtils {
    public static CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    /**
     * get请求获取String类型数据
     *
     * @param url 请求链接
     * @return
     */
    public static String get(String url) {
        StringBuffer sb = new StringBuffer();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();
            InputStreamReader reader = new InputStreamReader(entity.getContent(), "utf-8");
            char[] charbufer;
            while (0 < reader.read(charbufer = new char[10])) {
                sb.append(charbufer);
            }
        } catch (IOException e) {//1
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
        return sb.toString();
    }

    /**
     * post方式请求数据
     *
     * @param url  请求链接
     * @param data post数据体
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String post(String url, Map<String, String> data) {
        StringBuffer sb = new StringBuffer();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
        if (null != data) {
            for (String key : data.keySet()) {
                valuePairs.addAll((Collection<? extends NameValuePair>) new BasicNameValuePair(key, data.get(key)));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity((List<? extends org.apache.http.NameValuePair>) valuePairs));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            BufferedInputStream bis = new BufferedInputStream(httpEntity.getContent());
            byte[] buffer;
            while (0 < bis.read(buffer = new byte[128])) {
                sb.append(new String(buffer, "utf-8"));
            }
        } catch (UnsupportedEncodingException e) {//数据格式有误
            e.printStackTrace();
        } catch (IOException e) {//请求出错
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return sb.toString();
    }

    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url    地址
     * @param params json格式的参数
     * @return
     */
    public static String doPost(String url, String params) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            } else {
            }
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String doPost(String url, Object data) throws Exception {
        return doPost(url, JSON.toJSONString(data));
    }

    public static TokenResult getAccessToken(AreaSign areaSign) throws IOException {
        // 输入服务网址
        HttpClient client = new HttpClient();
        // HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        // GetMethod
        PostMethod post = new PostMethod(areaSign.getTokenArea());
        // 设置参数
        post.setParameter("username", areaSign.getUsername());
        post.setParameter("password", areaSign.getPassword());
        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        // 执行,返回一个结果码
        int code = client.executeMethod(post);
        // 获取xml结果
        String result = post.getResponseBodyAsString();
        System.out.println(result);
        // 释放连接
        post.releaseConnection();
        // 关闭连接
        ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
        return JSON.parseObject(result, TokenResult.class);
    }

    public static String test() throws IOException {
        // 输入服务网址
        HttpClient client = new HttpClient();
        // HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        // GetMethod
        PostMethod post = new PostMethod("http://123.57.226.94:8181/query.json?");
        // 设置参数
//        AreaSign areaSign = new AreaSign();
//        areaSign.setTokenArea("http://123.57.226.94:8181/api/auth/login.json?");
//        areaSign.setUsername("yj001");
//        areaSign.setPassword("e10adc3949ba59abbe56e057f20f883e");
//        TokenResult accessToken = NetUtils.getAccessToken(areaSign);
//        post.setRequestHeader("authToken",accessToken.getToken());
//        post.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
//        post.setParameter("command", "savetempdata");
//        post.setParameter("dept", "");
        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        // 执行,返回一个结果码
        int code = client.executeMethod(post);
        // 获取xml结果
        String result = post.getResponseBodyAsString();
        System.out.println(result);
        // 释放连接
        post.releaseConnection();
        // 关闭连接
        ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
        return post.getResponseBodyAsString().toString();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(test());
    }



    public static String postMap(String url, Map<String, String> headerMap, Map<String, String> contentMap) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> content = new ArrayList<NameValuePair>();
        Iterator iterator = contentMap.entrySet().iterator();           //将content生成entity
        while (iterator.hasNext()) {
            Entry<String, String> elem = (Entry<String, String>) iterator.next();
            content.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
        }
        CloseableHttpResponse response = null;
        try {
            Iterator headerIterator = headerMap.entrySet().iterator();          //循环增加header
            while (headerIterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) headerIterator.next();
                post.addHeader(elem.getKey(),elem.getValue());
            }
            if (content.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(content, "UTF-8");
                post.setEntity(entity);
            }
            response = httpClient.execute(post);            //发送请求并接收返回数据
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();       //获取response的body部分
                result = EntityUtils.toString(entity);          //读取reponse的body部分并转化成字符串
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

}
