package com.zznorth.tianji.bean;

/**
 * coder by 中资北方 on 2015/12/14.
 */
public class IndexBean {
    /**
     * 指数名
     */
    private String name;
    /**
     * 当前点数
     */
    private String point;
    /**
     * 当前价格
     */
    private String price;
    /**
     * 涨跌率
     */
    private String percent;
    /**
     * 成交量
     */
    private String volume;
    /**
     * 成交额
     */
    private String finalPrice;

    /**
     * 指数名
     */
    public String getName() {
        return name;
    }

    /**
     * 指数名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**当前点数
     * @return
     */
    public String getPoint() {
        return point;
    }

    /** 当前点数
     * @param point
     */
    public void setPoint(String point) {
        this.point = point;
    }

    /** 当前价格
     * @return
     */
    public String getPrice() {
        return price;
    }

    /** 当前价格
     * @param price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /** 涨跌率
     * @return
     */
    public String getPercent() {
        return percent;
    }

    /** 涨跌率
     * @param percent
     */
    public void setPercent(String percent) {
        this.percent = percent;
    }

    /** 成交量
     * @return
     */
    public String getVolume() {
        return volume;
    }

    /** 成交量
     * @param volume
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /** 成交额
     * @return
     */
    public String getFinalPrice() {
        return finalPrice;
    }

    /** 成交额
     * @param finalPrice
     */
    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }
}
