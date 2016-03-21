package com.zznorth.tianji.bean;

/**
 * coder by 中资北方 on 2015/12/3.
 */
public class LoginInfo {
    /**
     * 登陆结果
     */
    private boolean Result;
    /**
     * 登陆失败后返回的信息
     */
    private String Message;
    /**
     * 登陆成功后返回的对象
     */
    private Context Context;


    /**
     * 登陆成功后返回的对象
     */
    public Context getContext() {
        return Context;
    }

    public void setContext(Context context) {
        this.Context = context;
    }

    /**
     * 登陆失败后返回的信息
     */
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    /**
     * 登陆结果
     */
    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean result) {
        Result = result;
    }

}
