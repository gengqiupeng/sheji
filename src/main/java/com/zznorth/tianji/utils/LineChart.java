package com.zznorth.tianji.utils;

import android.content.Context;
import android.graphics.Color;

import com.zznorth.tianji.base.BaseLineChart;
import com.zznorth.tianji.bean.ChartDataBean;

import org.achartengine.chart.PointStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * coder by 中资北方 on 2015/11/30.
 */
public class LineChart extends BaseLineChart {
    private final List<List<ChartDataBean>> lists;
    public LineChart(Context context,List<List<ChartDataBean>> lists) {
        super(context);
        this.lists=lists;
        initRender();
        initData();
    }

    private void initData() {
        String[] titles = new String[]{"总资产","上证指数","深证指数"};
        List<ChartDataBean> total = lists.get(0);
        int size = total.size();
        List<double[]> xValues = new ArrayList<>();
        List<double[]> yValues = new ArrayList<>();
        for(int i=0;i<titles.length;i++) {
            //初始化x轴数据
            double[] dx = new double[size];
            for(int j=0;j<size;j++){
                dx[j]=j;
            }
            xValues.add(dx);
        }
        //初始化y轴数据
        List<ChartDataBean> shzs=lists.get(1);
        List<ChartDataBean> szzs = lists.get(2);

        double[] dyt = new double[size];
        for (int k = 0;k<size;k++){
            dyt[k]= Double.parseDouble(total.get(k).getAssets());
        }
        yValues.add(dyt);
        double[] dys = new double[size];
        for (int k = 0;k<size;k++){
            dys[k]= Double.parseDouble(shzs.get(k).getAssets());
        }
        yValues.add(dys);
        double[] dyz = new double[size];
        for (int k = 0;k<size;k++){
            dyz[k]= Double.parseDouble(szzs.get(k).getAssets());
        }
        yValues.add(dyz);
        buildDataset(titles, xValues, yValues);
    }

    private void initRender() {
        int[] colors = new int[]{Color.parseColor("#FF0000"),Color.parseColor("#000000"),Color.parseColor("#0000FF")};
        PointStyle[] styles = new PointStyle[]{PointStyle.CIRCLE,PointStyle.CIRCLE,PointStyle.CIRCLE};
        List<ChartDataBean> list = lists.get(0);
        int size = list.size();
        List<Map<String, String>> yLabs= new ArrayList<>();
        for(int i=0;i<50;i++){
            Map<String,String> map = new HashMap<>();
            map.put("double",(40+i)*5*1000+"");
            map.put("string",(40+i)*5+"k");
            yLabs.add(map);
        }
        List<Map<String,String>> xLabs = new ArrayList<>();
        for(int i=0;i<size;i++){
            Map<String,String> map = new HashMap<>();
            map.put("double",i+"");
            map.put("string",list.get(i).getOperDate());
            xLabs.add(map);
        }
        buildRenderer(colors,styles,yLabs,xLabs);
    }
}
