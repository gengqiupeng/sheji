package com.zznorth.tianji.bean;

/**
 * coder by 中资北方 on 2015/12/11.
 */
public class Context {
    /**
     * 登陆的用户名
     */
    private String AccountId;
    /**
     * 账户名
     */
    private String AccountName;
    /**
     * 访问口令
     */
    private String SessionId;
    /**
     * 过期时间
     */
    private String ExpiredTime;
    /**
     * 登陆的用户名
     */
    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    /**
     * 账户名
     */
    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    /**
     * 访问口令
     */
    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    /**
     * 过期时间yyyy-MM-dd
     */
    public String getExpiredTime() {
        return ExpiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        ExpiredTime = expiredTime;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}