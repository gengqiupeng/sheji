package com.zznorth.tianji.bean;

/**
 * coder by 中资北方 on 2015/12/5.
 */
public class HistoryRemarkBean {

    private String OperTime;
    /**
     * 操作方向
     */
    private String Operation;
    private String Price;
    private String Remark;
    /**
     * 证券代码
     */
    private String StockId;
    /**
     * 名称
     */
    private String StockName;
    /**
     * 数量
     */
    private String Volume;

    /** 操作时间 已格式化 yy/MM/dd HH:mm:ss
     * @return
     */
    public String getOperTime() {
        return OperTime;
    }

    public void setOperTime(String operTime) {
        OperTime = operTime;
    }

    /** 操作方向
     * @return
     */
    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    /**
     * 证券代码
     */
    public String getStockId() {
        return StockId;
    }

    public void setStockId(String stockId) {
        StockId = stockId;
    }

    /** 名称
     * @return
     */
    public String getStockName() {
        return StockName;
    }

    public void setStockName(String stockName) {
        StockName = stockName;
    }

    /** 数量
     * @return
     */
    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }


    //=============持仓历史中新增字段===============

    /**
     * 持仓历史中追踪账号
     */
    private String AccountId;
    /**
     * 持仓历史中金额
     */
    private String Amount;

    /**
     * 持仓历史中追踪账号
     */
    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    /**
     * 持仓历史中金额
     */
    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
