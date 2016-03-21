package com.zznorth.tianji.bean;

/**
 * coder by 中资北方 on 2015/12/5.
 */
public class ChartDataBean {

    /**
     * 总资产，Y轴
     */
    private String Assets;
    /**
     * 日期
     */
    private String OperDate;
    /**
     * 指数
     */
    private String Point;

    /**
     * 总资产，Y轴
     */
    public String getAssets() {
        return Assets;
    }

    public void setAssets(String assets) {
        Assets = assets;
    }

    /**
     * 日期
     */
    public String getOperDate() {
        return OperDate;
    }

    public void setOperDate(String operDate) {
        OperDate = operDate;
    }

    /**
     * 指数
     */
    public String getPoint() {
        return Point;
    }

    public void setPoint(String point) {
        Point = point;
    }
}
