package com.zznorth.tianji.utils;

/**
 * 接口方法类，拼接请求数据用的接口
 * coder by 中资北方 on 2015/12/3.
 */
public class APIUtils {

    private static final String TAG = "APIUtils";
    private final static String Host = "http://122.144.130.253:9009/";
    //public static final String Host="http://61.190.90.244:9009/";

    /**
     * 登陆接口
     *
     * @param userName
     * @param pwd
     * @param AnalystId
     * @return
     */
    public static String LoginLink(String userName, String pwd, String AnalystId) {
        String urlStr = Host + "Account/Verification?AccountId=" + userName + "&Password=" + pwd + "&AnalystId=" + AnalystId + "&Type=android";
        return EncodeUtils.URLEncode(urlStr);
    }

    /**
     * 验证单点登录功能
     *
     * @return
     */
    public static String CheckSessionLink() {
        return EncodeUtils.URLEncode(Host + "Account/CheckSessionId?AccountId=" + FileUtils.ReadName() + "&SessionId=" + FileUtils.ReadSessionId() + "&Type=android");
    }

    /**
     * 最近操作链接
     *
     * @return
     */
    public static String HistoryRemarkLink() {
        return EncodeUtils.URLEncode(Host + "TodayOper/GetHistoryRemark?AccountId=" + Config.AccountId);
    }

    /**
     * 操作历史链接
     *
     * @param time
     * @param page
     * @param rows
     * @return
     */
    public static String PurchaseHistoryLink(String time, int page, int rows) {
        return EncodeUtils.URLEncode(Host + "Home/GetPurchaseHistory?AccountId=" + Config.AccountId + "&OperTime=" + time + "&page=" + page + "&rows=" + rows);
    }

    /**
     * 操作历史链接
     *
     * @param page
     * @return
     */
    public static String PurchaseHistoryLink(int page) {
        return EncodeUtils.URLEncode(Host + "Home/GetPurchaseHistory?AccountId=" + Config.AccountId + "&page=" + page + "&rows=20");
    }

    /**
     * 收益对比图数据连接
     *
     * @return
     */
    public static String ChartsDataSourceLink() {
        return EncodeUtils.URLEncode(Host + "HoldStatus/getChartsDataSource?AccountId=" + Config.AccountId);
    }

    /**
     * 收益排行数据链接
     *
     * @return
     */
    public static String StockRateRankLink() {
        return EncodeUtils.URLEncode(Host + "HoldStatus/getStockRateRank?AccountId=" + Config.AccountId);
    }

    /**
     * 总资产链接
     *
     * @return
     */
    public static String TotalAssetsLink() {
        return EncodeUtils.URLEncode(Host + "Home/GetTotalAssets?AccountId=" + Config.AccountId);
    }

    /**
     * 持仓状态
     *
     * @return
     */
    public static String StocksQueryLink(int page) {
        return EncodeUtils.URLEncode(Host + "Home/GetStocksQuery?AccountId=" + Config.AccountId + "&OperTime=2015-12-03&page=" + page + "&rows=20");
    }

    /**
     * 持仓状态链接
     *
     * @param OperTime
     * @param page
     * @param rows
     * @return
     */
    public static String StocksQueryLink(String OperTime, int page, int rows) {
        return EncodeUtils.URLEncode(Host + "Home/GetStocksQuery?AccountId=" + Config.AccountId + "&OperTime=" + OperTime + "&page=" + page + "&rows=" + rows);
    }

    /**
     * 文章列表链接
     *
     * @param page
     * @param num  每页条数
     * @param type Config.Type*
     * @return
     */
    public static String ArticleListLink(int page, int num, int type) {
        return EncodeUtils.URLEncode(Host + "Article/GetArticleList?type=" + type + "&page=" + page + "&size=" + num);
    }

    /**
     * 获取带有内容的新闻列表链接
     *
     * @param page
     * @param type
     * @return
     */
    public static String ContextArticleListLink(int page, int type) {
        return EncodeUtils.URLEncode(Host + "Article/GetArticleContextByType?type=" + type + "&page=" + page + "&size=10");
    }

    /**
     * 通过id获取文章详情页的链接
     *
     * @param ArticleId
     * @return
     */
    public static String ArticleContextLinkById(String ArticleId) {
        return EncodeUtils.URLEncode(Host + "Article/GetArticleContext?Id=" + ArticleId);
    }

    /**
     * 更改密码接口
     *
     * @param AccountId
     * @param OldPwd
     * @param NewPwd
     * @return
     */
    public static String ChangePwdLink(String AccountId, String OldPwd, String NewPwd) {
        return EncodeUtils.URLEncode(Host + "Account/ChangePassword?AccountId=" + AccountId + "&OldPwd=" + OldPwd + "&NewPwd=" + NewPwd);
    }

    /**
     * 刷新天玑提示和实时热点的链接
     *
     * @return
     */
    public static String RefreshNewsLink(String OperTime) {
        return EncodeUtils.URLEncode(Host + "StockPoll/QueryArticleContext?OperTime=" + OperTime);
    }

    /**
     * 刷新成交历史的链接
     *
     * @param OperTime
     * @return
     */
    public static String RefreshSoldHistory(String OperTime) {
        return EncodeUtils.URLEncode(Host + "Home/GetHistoryRecordByTime?OperTime=" + OperTime);
    }

    /**
     * 版本更新链接
     *
     * @return
     */
    public static String CheckVersionLink() {
        return EncodeUtils.URLEncode(Host + "CheckVersion/GetCurrentVersionInfo?Type=android&ProductId=" + Config.ProductId);
    }

    /**
     * 上证指数，深圳成指
     *
     * @return
     */
    public static String SZZSLink() {
        return "http://hq.sinajs.cn/list=s_sh000001,s_sz399001";
    }

    public static String ServicesTimeLink() {
        return EncodeUtils.URLEncode(Host + "Home/GetBaselineDateTime");
    }

    /**
     * 首页ViewPager图片下载链接
     */
    String[] imgUrl = new String[]{"","","","",""};
    public static String mainViewPagerUrl() {
        return "";
    }

}
