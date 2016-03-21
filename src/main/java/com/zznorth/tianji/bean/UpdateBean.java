package com.zznorth.tianji.bean;

/**
 * coder by 中资北方 on 2015/12/25.
 */
public class UpdateBean {

    /**
     * CurrentVerson : 1.0.3
     * IsForceUpdate : true
     * UpdateUrl : http://61.190.90.244:9009/Download/GetLastAppVersion?Version=1.0.3&Type=android
     * Description : 1.修复若干bug。\n2.新增若干bug。\n
     */

    private String CurrentVerson;
    private boolean IsForceUpdate;
    private String UpdateUrl;
    private String Description;

    public void setCurrentVerson(String CurrentVerson) {
        this.CurrentVerson = CurrentVerson;
    }

    public void setIsForceUpdate(boolean IsForceUpdate) {
        this.IsForceUpdate = IsForceUpdate;
    }

    public void setUpdateUrl(String UpdateUrl) {
        this.UpdateUrl = UpdateUrl;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCurrentVerson() {
        return CurrentVerson;
    }

    public boolean getIsForceUpdate() {
        return IsForceUpdate;
    }

    public String getUpdateUrl() {
        return UpdateUrl;
    }

    public String getDescription() {
        return Description;
    }
}
