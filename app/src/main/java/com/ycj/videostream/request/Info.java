package com.ycj.videostream.request;

import org.apache.http.client.CookieStore;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class Info {
    private static int maxId = -1;

    public static String testInfo() {
        Random r = new Random();
        int a = r.nextInt(2);
        if (a == 0) {
            return null;
        } else if (a == 1) {
            return "1";
        } else {
            return "2";
        }
    }

    public static String getInfo(String url, String userName, String password) {
        try {
            try {
                CookieStore cookie = LoginAuth.getCookie(url, userName, password);
                JSONObject jsonObject = Event.events(cookie, url, 0, 1);
                if (jsonObject != null) {
                    JSONArray array = jsonObject.getJSONArray("data");
                    if (array.length() == 0) {
                        return null;
                    }
                    JSONObject info = (JSONObject) array.get(0);
                    int id = info.getInt("id");
                    if (maxId == -1) {
                        maxId = id;
                        return null;
                    }
                    if (id > maxId) {
                        double gender = info.getDouble("gender");
                        if (gender > 0.5) {
                            return "1";
                        } else {
                            return "2";
                        }
                    } else {
                        return null;
                    }

                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Error error) {
            error.printStackTrace();
            return "3";
        }
        return null;
    }
}
