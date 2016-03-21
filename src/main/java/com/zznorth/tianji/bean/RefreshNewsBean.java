package com.zznorth.tianji.bean;

/**
 * coder by 中资北方 on 2015/12/21.
 */
public class RefreshNewsBean {
    private String type ;
    private String Context;
    private String Title;
    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
