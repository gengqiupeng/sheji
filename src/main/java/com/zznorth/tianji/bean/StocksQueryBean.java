package com.zznorth.tianji.bean;

/**
 * coder by 中资北方 on 2015/12/7.
 */
public class StocksQueryBean {

    /**
     * 追踪账号
     */
    private String AccountId;
    /**
     * 成本价
     */
    private String CostPrice;
    /**
     * 参考当前价
     */
    private String CurrPrice;
    /**
     * 最新市值
     */
    private String MarketAssets;
    /**
     * 参考浮动盈亏
     */
    private String Profit;
    /**
     * 盈亏率
     */
    private String ProfitRate;
    /**
     * 股票代码
     */
    private String StockId;
    /**
     * 股票名称
     */
    private String StockName;
    /**
     * 数量
     */
    private String Volume;

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    /**
     * 成本价
     */
    public String getCostPrice() {
        return CostPrice;
    }

    public void setCostPrice(String costPrice) {
        CostPrice = costPrice;
    }

    /**
     * 参考当前价
     */
    public String getCurrPrice() {
        return CurrPrice;
    }

    public void setCurrPrice(String currPrice) {
        CurrPrice = currPrice;
    }

    /**
     * 最新市值
     */
    public String getMarketAssets() {
        return MarketAssets;
    }

    public void setMarketAssets(String marketAssets) {
        MarketAssets = marketAssets;
    }

    /**
     * 参考浮动盈亏
     */
    public String getProfit() {
        return Profit;
    }

    public void setProfit(String profit) {
        Profit = profit;
    }

    /**
     * 盈亏率
     */
    public String getProfitRate() {
        return ProfitRate;
    }

    public void setProfitRate(String profitRate) {
        ProfitRate = profitRate;
    }

    /**
     * 股票代码
     */
    public String getStockId() {
        return StockId;
    }

    public void setStockId(String stockId) {
        StockId = stockId;
    }

    /**
     * 股票名称
     */
    public String getStockName() {
        return StockName;
    }

    public void setStockName(String stockName) {
        StockName = stockName;
    }

    /**
     * 数量
     */
    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }
}
