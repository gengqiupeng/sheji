package com.zznorth.tianji.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.bean.StocksQueryBean;
import com.zznorth.tianji.utils.EncodeUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * coder by 中资北方 on 2015/12/8.
 */
public class OpenInterestAdapter extends BaseAdapter {
    private final Context context;
    private final List<StocksQueryBean> list;
    private int color;

    public OpenInterestAdapter(Context context, List<StocksQueryBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_interest, parent,false);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StocksQueryBean bean = list.get(position);
        String color = "#000000";
        //非空判断，并设置数据
        if (null == bean) {
            return convertView;
        }
        if (null != bean.getProfit()) {
            String num = EncodeUtils.SplitStrNum(bean.getProfit(), 2);
            color = getColor(num);
            holder.profit.setText(num);
            holder.profit.setTextColor(Color.parseColor(color));
        }
        if (null != bean.getStockId()) {
            holder.id.setText(bean.getStockId());
        }
        if (null != bean.getStockName()) {
            holder.name.setText(bean.getStockName());
        }
        if (null != bean.getVolume()) {
            holder.volume.setText(bean.getVolume());
        }
        if (null != bean.getMarketAssets()) {
            holder.price.setText(bean.getMarketAssets());
        }
        if (null != bean.getCostPrice()) {
            holder.cost.setText(bean.getCostPrice());
            holder.cost.setTextColor(Color.parseColor(color));
        }
        if (null != bean.getCurrPrice()) {
            holder.current.setText(bean.getCurrPrice());
            holder.current.setTextColor(Color.parseColor(color));
        }
        if (null != bean.getProfitRate()) {
            holder.percent.setText(EncodeUtils.SplitStrNum(bean.getProfitRate(), 2));
            holder.percent.setTextColor(Color.parseColor(color));
        }
        return convertView;
    }

    private  String getColor(String num) {
        String format = num.split("\\.")[0];
        int number = Integer.parseInt(format);
        if(number>0){
            return "#ff0000";
        }else {
            return "#76b672";
        }
    }

    class ViewHolder {
        @ViewInject(R.id.text_item_stock_id)
        TextView id;
        @ViewInject(R.id.text_item_stock_name)
        TextView name;
        @ViewInject(R.id.text_item_interest_volume)
        TextView volume;
        @ViewInject(R.id.text_item_interest_price)
        TextView price;
        @ViewInject(R.id.text_item_interest_cost_price)
        TextView cost;
        @ViewInject(R.id.text_item_interest_current_price)
        TextView current;
        @ViewInject(R.id.text_item_interest_profit_loss)
        TextView profit;
        @ViewInject(R.id.text_item_interest_loss_percent)
        TextView percent;
    }
}
