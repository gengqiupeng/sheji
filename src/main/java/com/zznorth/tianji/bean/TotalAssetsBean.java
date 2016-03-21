package com.zznorth.tianji.bean;

/**
 * coder by 中资北方 on 2015/12/7.
 */
public class TotalAssetsBean {

    /**
     * 可用资金
     */
    private float AvailableCapital;
    /**
     * 持仓市值
     */
    private float MarketValue;
    /**
     * 总资产
     */
    private float TotalAssets;

    /** 可用资金
     * @return
     */
    public float getAvailableCapital() {
        return AvailableCapital;
    }

    public void setAvailableCapital(float availableCapital) {
        AvailableCapital = availableCapital;
    }

    /** 持仓市值
     * @return
     */
    public float getMarketValue() {
        return MarketValue;
    }

    public void setMarketValue(float marketValue) {
        MarketValue = marketValue;
    }

    /** 总资产
     * @return
     */
    public float getTotalAssets() {
        return TotalAssets;
    }

    public void setTotalAssets(float totalAssets) {
        TotalAssets = totalAssets;
    }
}
