package com.zznorth.tianji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.bean.NewsListBean;
import com.zznorth.tianji.utils.EncodeUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * coder by 中资北方 on 2015/12/4.
 */
public class NewsListAdapter extends BaseAdapter{

    private final Context context;
    private final List<NewsListBean> list;
    private final boolean isShowTime;

    public NewsListAdapter(Context context, List<NewsListBean> list,boolean isShowTime) {
        this.context = context;
        this.list = list;
        this.isShowTime=isShowTime;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_newslist,parent,false);
            holder = new ViewHolder();
            if(position==0){
                convertView.setPadding(0, EncodeUtils.dip2px(4),0,0);
            }
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        NewsListBean bean = list.get(position);
        if(null==bean){
            return convertView;
        }
        if(null!=bean.getTitle()) {
            holder.title.setText(bean.getTitle());
        }
        if(null!=bean.getFbrq()) {
            if (isShowTime) {
                holder.time.setText(bean.getFbrq());
            }
        }
        return convertView;
    }


    class ViewHolder{
        @ViewInject(R.id.text_news_list_title)
        TextView title;
        @ViewInject(R.id.text_news_list_time)
        TextView time;
    }

}
