package com.zznorth.tianji.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zznorth.tianji.bean.ChartDataBean;
import com.zznorth.tianji.bean.ContextNewsListBean;
import com.zznorth.tianji.bean.HistoryRemarkBean;
import com.zznorth.tianji.bean.IndexBean;
import com.zznorth.tianji.bean.LoginInfo;
import com.zznorth.tianji.bean.NewsBean;
import com.zznorth.tianji.bean.NewsListBean;
import com.zznorth.tianji.bean.RateRankBean;
import com.zznorth.tianji.bean.StocksQueryBean;
import com.zznorth.tianji.bean.TotalAssetsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * coder by 中资北方 on 2015/12/3.
 */
public class JsonParser {

    /**
     * 解析登陆成功之后返回的json对象
     *
     * @param json
     * @return
     */
    public static LoginInfo ParserLoginInfo(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, LoginInfo.class);
    }

    /**
     * 解析最近操作的json数据
     *
     * @param json
     * @return
     */
    public static HistoryRemarkBean ParserHistoryRemark(String json) {
        return new Gson().fromJson(json, HistoryRemarkBean.class);
    }

    /**
     * 解析操作历史列表数据
     *
     * @param json
     * @return
     */
    public static List<HistoryRemarkBean> ParserHistoryRemarkList(String json) {
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray("rows");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(jsonArray!=null) {
            return new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HistoryRemarkBean>>() {
            }.getType());
        }else {
            return new ArrayList<>();
        }
    }

    /**
     * 解析图标的json数据
     *
     * @param json
     * @return
     */
    public static List<List<ChartDataBean>> ParserChartDataBean(String json) {
        List<List<ChartDataBean>> lists = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray rows = jsonObject.getJSONArray("rows");
            JSONArray shzs = jsonObject.getJSONArray("shzs");
            JSONArray szzs = jsonObject.getJSONArray("szzs");
            Gson gson = new Gson();
            List<ChartDataBean> Lrows = gson.fromJson(rows.toString(), new TypeToken<List<ChartDataBean>>() {
            }.getType());
            List<ChartDataBean> Lshzs = gson.fromJson(shzs.toString(), new TypeToken<List<ChartDataBean>>() {
            }.getType());
            List<ChartDataBean> Lszzs = gson.fromJson(szzs.toString(), new TypeToken<List<ChartDataBean>>() {
            }.getType());
            lists.add(Lrows);
            lists.add(Lshzs);
            lists.add(Lszzs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 解析收益排行榜数据
     *
     * @param json
     * @return
     */
    public static List<RateRankBean> ParserRateRankList(String json) {
        List<RateRankBean> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("rank");
            Gson gson = new Gson();
            list = gson.fromJson(jsonArray.toString(), new TypeToken<List<RateRankBean>>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析总资产
     *
     * @param json
     * @return
     */
    public static TotalAssetsBean ParserTotalAssets(String json) {
        return new Gson().fromJson(json, TotalAssetsBean.class);
    }

    public static List<StocksQueryBean> ParserStocksQuery(String json) {
        List<StocksQueryBean> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("rows");
            Gson gson = new Gson();
            list = gson.fromJson(jsonArray.toString(), new TypeToken<List<StocksQueryBean>>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析新闻列表
     *
     * @param json
     * @return
     */
    public static List<NewsListBean> ParserNewsList(String json) {
        List<NewsListBean> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("Article");
            Gson gson = new Gson();
            list = gson.fromJson(jsonArray.toString(), new TypeToken<List<NewsListBean>>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析含有内容的新闻列表
     *
     * @param json
     * @return
     */
    public static List<ContextNewsListBean> ParserContextNewsList(String json) {
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray("rows");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(null!=jsonArray) {
            return new Gson().fromJson(jsonArray.toString(), new TypeToken<List<ContextNewsListBean>>() {
            }.getType());
        }else {
            return new ArrayList<>();
        }
    }

    /**
     * 解析新闻数据
     *
     * @param json
     * @return
     */
    public static NewsBean ParserNews(String json) {
        return new Gson().fromJson(json, NewsBean.class);
    }

    /**
     * 解析大盘走势
     *
     * @param string
     * @return
     */
    public static List<IndexBean> ParserIndexList(String string) {
        List<IndexBean> list = new ArrayList<>();
        String[] temp = string.split(",");
        if (temp.length == 11) {
            IndexBean shzs = new IndexBean();
            shzs.setName(temp[0].split("\"")[1]);
            shzs.setPoint(temp[1]);
            shzs.setPrice(temp[2]);
            shzs.setPercent(temp[3]);
            shzs.setVolume(temp[4]);
            String[] sh_sz = temp[5].split("\"");
            shzs.setFinalPrice(sh_sz[0]);
            IndexBean szzs = new IndexBean();
            szzs.setName(sh_sz[2]);
            szzs.setPoint(temp[6]);
            szzs.setPrice(temp[7]);
            szzs.setPercent(temp[8]);
            szzs.setVolume(temp[9]);
            szzs.setFinalPrice(temp[10]);
            list.add(shzs);
            list.add(szzs);
            return list;
        } else {
            return null;
        }
    }


}
