package com.zznorth.tianji.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.zznorth.tianji.utils.EncodeUtils;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;
import java.util.Map;

/**
 * coder by 中资北方 on 2015/11/30.
 */
public abstract class BaseLineChart {
    private final Context context;
    private XYMultipleSeriesDataset dataset;
    private XYMultipleSeriesRenderer renderer;

    protected BaseLineChart(Context context) {
        this.context = context;
    }

    protected XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> Xvalues, List<double[]> Yvalues) {
        dataset = new XYMultipleSeriesDataset();
        addXYSeries(dataset, titles, Xvalues, Yvalues, 0);
        return dataset;
    }


    //数据集
    private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues,
                            List<double[]> yValues, int scale) {
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(titles[i], scale);
            double[] xV = xValues.get(i);
            double[] yV = yValues.get(i);
            int seriesLength = yV.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series);
        }
    }

    protected XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles,
                                                     List<Map<String,String>> yLabs,List<Map<String,String>> xLabs) {
        renderer = new XYMultipleSeriesRenderer();
        setRenderer(renderer, colors, styles, yLabs,xLabs);
        return renderer;
    }

    //渲染器
    private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles,
                               List<Map<String,String>> yLabs,List<Map<String,String>> xLabs) {
        //renderer.setChartTitle("总资产变化图");
        //renderer.setChartTitleTextSize(EncodeUtils.Px2Dp(15));
        renderer.setPointSize(0);
        // 设置外边框（上左下右）
        renderer.setMargins(new int[]{50, 80, 50, 50});
        renderer.setXAxisMin(0);         //设置x轴最小值
        if(xLabs.size()<15) {
            renderer.setXAxisMax(15);   //设置x轴最大值
        }
        int length = colors.length;
        //设置曲线相关
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            r.setFillPoints(true);
            r.setLineWidth(EncodeUtils.Px2Dp(1.5f));
            renderer.addSeriesRenderer(r);
        }
        if(xLabs.size()<=1) {
            renderer.setPointSize(EncodeUtils.Px2Dp(1f));//只有一条数据的话显示点
        }
        renderer.setShowGrid(true);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        //坐标轴配置（颜色使用Color.parseColor8位）
        renderer.setYTitle("总资产（元）");
        renderer.setAxisTitleTextSize(EncodeUtils.Px2Dp(10));//坐标轴标题大小
        renderer.setLabelsColor(Color.parseColor("#FF000000"));//标题文字颜色
        //renderer.setLegendTextSize(EncodeUtils.Px2Dp(10));//图例文本大小
        renderer.setShowLegend(false);
        renderer.setLabelsTextSize(20);//轴标签文本大小
        renderer.setApplyBackgroundColor(true);//允许设置背景颜色
       renderer.setMarginsColor(Color.parseColor("#00FFFFFF"));//周围背景颜色
        renderer.setBackgroundColor(Color.parseColor("#00000000"));
        renderer.setGridColor(Color.parseColor("#FFD36C0D"));//设置网格颜色
        renderer.setAxesColor(Color.parseColor("#FFd36c0d"));//设置坐标轴颜色
        //renderer.setYAxisMin(300000);//Y轴最低值

        //自定义Y轴标签
        for (int i=0;i<yLabs.size();i++) {
            Map<String,String> map = yLabs.get(i);
            renderer.addYTextLabel(Double.parseDouble(map.get("double")), map.get("string"));
        }
        //自定义X轴标签
        renderer.setXLabels(0);
        for (int i=0;i<xLabs.size();i++) {
            Map<String,String> map = xLabs.get(i);
            renderer.addXTextLabel(Double.parseDouble(map.get("double")), map.get("string"));
        }
        renderer.setXLabelsAngle(-25);
        //移动，缩放
       // renderer.setPanLimits(new double[]{-2, 30, 0,0});//设置拉动范围
       // renderer.setZoomLimits(new double[]{-300, 300, -300, 300});//设置缩放范围
        //renderer.setZoomRate(0.2f);
        //renderer.setInScroll(true);
       // renderer.setPanEnabled(false);
        renderer.setPanEnabled(false,false);
        renderer.setZoomEnabled(false,false);
        //点击判定范围
        //renderer.setClickEnabled(true);
        //renderer.setSelectableBuffer(20);
    }

    public View getView(){
       return ChartFactory.getCubeLineChartView(context, dataset, renderer, 0.5f);
    }

}
