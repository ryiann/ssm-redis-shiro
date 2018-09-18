package com.ryan.vo;

import javax.validation.constraints.NotBlank;

/**
 * @author YoriChen
 * @date 2018/5/21
 */
public class UserVO {

    /**用户名 */
    @NotBlank(message="用户名不能为空")
    private String userName;

    /**密码 */
    @NotBlank(message="密码不能为空")
    private String passWord;

    /**记住我 */
    private Boolean remember;

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

    public Boolean getRemember() {
        return remember == null ? false : true;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", remember=" + remember +
                '}';
    }
}