package com.ycj.videostream.request;

import java.io.Serializable;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-17 11:08
 */

public class InputData implements Serializable {
    private String serverUrl;
    private String userName;
    private String password;
    private String streamUrl;

    public InputData() {
    }

    public InputData(String serverUrl, String userName, String password, String streamUrl) {
        this.serverUrl = serverUrl;
        this.userName = userName;
        this.password = password;
        this.streamUrl = streamUrl;
    }


    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }


    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
