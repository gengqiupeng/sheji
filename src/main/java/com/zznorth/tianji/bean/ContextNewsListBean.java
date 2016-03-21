package com.zznorth.tianji.bean;

/** 通知列表的每条新闻对象类
 * coder by 中资北方 on 2015/12/7.
 */
public class ContextNewsListBean {

    /**
     * 日期
     */
    private String Fbrq;
    /**
     * 通知id
     */
    private String Id;
    /**
     * 通知标题
     */
    private String Title;
    /**
     * 通知内容
     */
    private String Context;

    /** 通知内容
     * @return
     */
    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    /** 通知日期
     * @return
     */
    public String getFbrq() {
        return Fbrq;
    }

    public void setFbrq(String fbrq) {
        Fbrq = fbrq;
    }

    /** 通知id
     * @return
     */
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    /** 通知标题
     * @return
     */
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

}
