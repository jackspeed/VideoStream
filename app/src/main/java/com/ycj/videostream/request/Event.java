package com.ycj.videostream.request;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * 历史记录
 *
 * @author Wangshutao 2016年7月5日
 */
public class Event {

    /**
     * 历史识别记录 某个角色
     *
     * @param cookieStore cookie值
     * @param user_role   角色 不传: 所有, 0: 员工(默认)， 1: 访客, 2: VIP
     * @param url
     */
    public static JSONObject events(CookieStore cookieStore, String url, Integer user_role, int page) throws Exception {
        url += "/event/events";
        System.out.println("Start GET /event/events 历史识别记录 ...");
        // 自定义HttpClient 设置CookieStore
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        // 设置参数 user_role
        HttpGet request = new HttpGet(url + "?page=" + page);

        CloseableHttpResponse response = httpclient.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");

        // 解析JSON
        JSONObject resp = new JSONObject(responseBody);
        int result = resp.optInt("code", -1);
        if (result != 0) {
            return null;
        } else {
            return resp;
        }
    }


}