package com.zznorth.tianji.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.bean.RateRankBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * coder by 中资北方 on 2015/12/10.
 */
public class RateRankAdapter extends BaseAdapter{

    private final Context context;
    private final List<RateRankBean> list ;
    private final String[] colors;

    public RateRankAdapter(Context context, List<RateRankBean> list, String[] colors) {
        this.context = context;
        this.list = list;
        this.colors = colors;
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
        ViewHolder holder ;
        if(convertView==null){
            holder= new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_raterank,parent,false);
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        RateRankBean bean = list.get(position);
        if(null==bean){
            return convertView;
        }
        if(position<4){
            holder.rank.setText(position+1+"");
            holder.rank.setBackgroundColor(Color.parseColor(colors[position]));
        }else {
            holder.rank.setText(position+1+"");
            holder.rank.setBackgroundColor(Color.parseColor(colors[colors.length-1]));
        }
        if(null!=bean.getStockId()) {
            holder.id.setText(bean.getStockId());
        }
        if(null!=bean.getStockName()) {
            holder.name.setText(bean.getStockName());
        }
        if(null!=bean.getRate()) {
            holder.rate.setText(bean.getRate());
        }
        return convertView;
    }

    class ViewHolder{
        @ViewInject(R.id.text_item_raterank_id)
        TextView id;
        @ViewInject(R.id.text_item_raterank_name)
        TextView name;
        @ViewInject(R.id.text_item_raterank_rank)
        TextView rank;
        @ViewInject(R.id.text_item_raterank_rate)
        TextView rate;
    }
}
