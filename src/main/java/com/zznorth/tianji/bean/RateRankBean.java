package com.zznorth.tianji.bean;

/**
 * coder by 中资北方 on 2015/12/7.
 */
public class RateRankBean {
    /**
     * 收益率
     */
    private String Rate;
    /**
     * 股票代码
     */
    private String StockId;
    /**
     * 股票名称
     */
    private String StockName;

    /** 收益率
     * @return
     */
    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    /** 股票代码
     * @return
     */
    public String getStockId() {
        return StockId;
    }

    public void setStockId(String stockId) {
        StockId = stockId;
    }

    /** 股票名称
     * @return
     */
    public String getStockName() {
        return StockName;
    }

    public void setStockName(String stockName) {
        StockName = stockName;
    }
}
