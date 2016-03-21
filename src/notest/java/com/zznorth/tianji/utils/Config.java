package com.zznorth.tianji.utils;

import android.os.Build;

import java.lang.String;

/**
 * coder by 中资北方 on 2015/12/3.
 */
public class Config {

    //=========================系统相关==============================

    /**
     * 系统API版本号
     */
    public static final int SYSVER = Build.VERSION.SDK_INT;
    /**
     * 包名
     */
    public static final String PackageName = "com.zznorth.tianji001";

    //=========================新闻type============================

    /**
     * 白银百科
     */
    public static final int TypeSilver = 3;
    /**
     * 财经快讯
     */
    public static final int TypeFinanceNews = 4;
    /**
     * 财经要闻
     */
    public static final int TypeFinanceNotice = 2;
    /**
     * 独家内参
     */
    public static final int TypeInternal = 5;
    /**
     * 股票池策略
     */
    public static final int TypeStockPool = 6;
    /**
     * 实时热点
     */
    public static final int TypeHotPoint = 6;
    /**
     * 中资观点
     */
    public static final int TypeZZView = 8;
    /**
     * 政策解读
     */
    public static final int TypeInterpretation = 10;
    /**
     * 题材风向标
     */
    public static final int TypeTheme = 9;
    /**
     * 天玑提示
     */
    public static final int TypeNotice = 11;
    /**
     * 通知公告
     */
    public static final int TypeAnnouncement = 7;
    /**
     * 研究院
     */
    public static final int TypeInstitute = 1;

    public static final int TypeHistory = 10086;

    //==========================SharedPreference相关===============

    /**
     * 存储账号信息的SP文件名
     */
    public final static String SPFileName = "account";
    /**
     * 键名--name；
     */
    public final static String SPAccountName = "name";
    /**
     * 键值--用户名
     */
    public final static String SPScreenName = "sname";
    /**
     * 键值--过期时间
     */
    public final static String SPOutDate = "outDate";
    /**
     * 键名--pwd；
     */
    public final static String SPAccountPwd = "pwd";
    /**
     * 键名--auto
     */
    public final static String SPAccountAuto = "auto";
    /**
     * 键名--SessionId
     */
    public final static String SPAccountSessionId = "SessionId";
    /**
     * 键名--Time
     */
    public final static String SPTime = "time";
    /**
     * 键名--Resume MainActivity是否可见
     */
    public final static String SPResume = "resume";
    /**
     * 键名--Resume MainActivity是否可见
     */
    public final static String SPDestroy = "destroy";

    /**
     * 追踪账户，接口中要添加的AccountId参数
     */
    public final static String AccountId = "tianji001";

    /**
     * 产品ID（天玑壹号，天玑贰号）
     */
    public final static String ProductId = "tianji001";
    /**
     * 键名--是否点击了更新提示框
     */
    public final static String SPIsClickUpdate = "clickUpdate";
    //==================更新的int类型===============
    public static final int UpdateNone = -1;
    public static final int UpdateDefault = 0;
    public static final int UpdateEnforce = 1;
    public static final int UdateDone = 2;


}
