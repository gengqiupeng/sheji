package com.zznorth.tianji.bean;

/**
 * coder by 中资北方 on 2015/12/4.
 */
public class NewsBean {

    /**
     * 文章内容
     */
    private String Context;
    /**
     * 日期
     */
    private String Fbrq;
    /**
     * 文章id
     */
    private String Id;
    /**
     * 文章标题
     */
    private String Title;

    /**文章内容
     * @return
     */
    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    /** 文章日期
     * @return
     */
    public String getFbrq() {
        return Fbrq;
    }

    public void setFbrq(String fbrq) {
        Fbrq = fbrq;
    }

    /** 文章id
     * @return
     */
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    /** 文章标题
     * @return
     */
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
