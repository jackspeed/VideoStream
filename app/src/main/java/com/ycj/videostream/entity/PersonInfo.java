package com.ycj.videostream.entity;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-17 14:31
 */

public class PersonInfo {

    private String headIcon;
    private String name;
    private String nickName;

    public PersonInfo() {
    }

    public PersonInfo(String headIcon, String name, String nickName) {
        this.headIcon = headIcon;
        this.name = name;
        this.nickName = nickName;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "headIcon='" + headIcon + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
