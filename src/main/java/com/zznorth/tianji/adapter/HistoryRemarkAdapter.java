package com.zznorth.tianji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.bean.HistoryRemarkBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * coder by 中资北方 on 2015/12/8.
 */
public class HistoryRemarkAdapter extends BaseAdapter {
    private final Context context;
    private final List<HistoryRemarkBean> list;

    public HistoryRemarkAdapter(Context context, List<HistoryRemarkBean> list) {
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
        if(convertView==null){
            holder= new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.listview_item_history,parent,false);
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        HistoryRemarkBean bean = list.get(position);
        if(null==bean){
            return convertView;
        }
        if(null!=bean.getStockId()) {
            holder.id.setText(bean.getStockId());
        }
        if(null!=bean.getStockName()) {
            holder.name.setText(bean.getStockName());
        }
        if(null!=bean.getOperTime()) {
            holder.date.setText(bean.getOperTime().split(" ")[0]);
            holder.time.setText(bean.getOperTime().split(" ")[1]);
        }
        if(null!=bean.getAmount()) {
            holder.sold.setText(bean.getAmount());
        }
        if(null!=bean.getOperation()) {
            //卖买
            if (bean.getOperation().contains("卖")) {
                holder.Sdirection.setVisibility(View.VISIBLE);
                holder.Bdirection.setVisibility(View.GONE);
            } else {
                holder.Bdirection.setVisibility(View.VISIBLE);
                holder.Sdirection.setVisibility(View.GONE);
            }
        }
        if(null!=bean.getPrice()) {
            holder.price.setText(bean.getPrice());
        }
        if(null!=bean.getVolume()) {
            holder.num.setText(bean.getVolume());
        }
        return convertView;
    }

    class ViewHolder{
        @ViewInject(R.id.text_item_stock_id)
        TextView id;
        @ViewInject(R.id.text_item_stock_name)
        TextView name;
        @ViewInject(R.id.text_item_history_date)
        TextView date;
        @ViewInject(R.id.text_item_history_time)
        TextView time;
        @ViewInject(R.id.text_item_history_price)
        TextView price;
        @ViewInject(R.id.text_item_history_num)
        TextView num;
        @ViewInject(R.id.text_item_history_sold)
        TextView sold;
        @ViewInject(R.id.text_item_history_Sdirection)
        TextView Sdirection;
        @ViewInject(R.id.text_item_history_Bdirection)
        TextView Bdirection;
    }
}
