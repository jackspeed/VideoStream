package com.ycj.videostream.request;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * 登录
 *
 * @author Wangshutao
 *         2016年7月4日
 */
public class LoginAuth {

    private static CookieStore cookie;
    private static long loginTime = -1L;

    public synchronized static CookieStore getCookie(String serverUrl, String userName, String password) {
        if (System.currentTimeMillis() - loginTime > 1000 * 60 * 60 * 12) {
            cookie = null;
        }
        if (cookie == null) {
            try {
                //cookie = authLogin(serverUrl, "megvii@megvii.com", "Stayhungry2015");
                cookie = authLogin(serverUrl, userName, password);
                loginTime = System.currentTimeMillis();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cookie;
    }

    /**
     * 登录 获取 Cookie
     *
     * @param url      API地址
     * @param username 账号, 注意不要使用admin@megvii.com
     * @param password 密码
     * @return cookie CookieStore
     * @throws Exception
     */
    public static CookieStore authLogin(String url, String username, String password) throws Exception {
        try {
            System.out.println("Start /auth/login to ...");
            url += "/auth/login";
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost request = new HttpPost(url);

            //设置user-agent为 "Koala Admin"
            //设置Content-Type为 "application/json"
            request.setHeader("User-Agent", "Koala Admin");
            request.setHeader("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);

            request.setEntity(new StringEntity(json.toString(), "UTF-8"));

            //发起网络请求，获取结果值
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse response = httpclient.execute(request, context);
            String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
            //解析JSON数据
            JSONObject resp = new JSONObject(responseBody);
            int result = resp.optInt("code", -1);
            if (result != 0) {
                System.err.println("Login failed, code:" + result);
            } else {
                System.out.println("Login Success,id:" + resp.getJSONObject("data").getInt("id"));
                return context.getCookieStore();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}