package com.ryan.pojo;

import java.io.Serializable;

/**
 * @author YoriChen
 * @date 2018/6/21
 */
public class UserDO implements Serializable {

    /**用户编号 */
    private Integer userId;

    /**用户姓名 */
    private String userName;

    /**用户密码 */
    private String passWord;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}