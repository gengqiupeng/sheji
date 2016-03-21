package com.zznorth.tianji.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.bean.ContextNewsListBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * coder by 中资北方 on 2015/12/9.
 */
public class NotificationAdapter extends BaseAdapter {

    private final List<ContextNewsListBean> list;
    private final Context context;
    private boolean isFirstTime=true;

    public NotificationAdapter(Context context, List<ContextNewsListBean> list) {
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
        ViewHolder holder ;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_notification, parent,false);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
       /* if(Config.SYSVER<19){
            holder.web.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        }*/
        final ContextNewsListBean bean = list.get(position);
        if(null==bean){
            return convertView;
        }
        //设置标题，日期
        if(null!=bean.getTitle()) {
            holder.title.setText(bean.getTitle());
        }
        if(null!=bean.getFbrq()) {
            holder.time.setText(bean.getFbrq());
        }
        //设置webView
        //holder.web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
       /* holder.web.setBackgroundColor(0);
        if(null!=bean.getContext()) {
            holder.web.loadDataWithBaseURL(null, bean.getContext(), "text/html", "utf-8", null);
        }*/
        holder.text.setText(Html.fromHtml(bean.getContext()));
        //添加点击事件
        final ViewHolder finalHolder = holder;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalHolder.contain.getVisibility()==View.VISIBLE){
                    finalHolder.contain.setVisibility(View.GONE);
                }else {
                    finalHolder.contain.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.contain.setVisibility(View.GONE);
            }
        });
        if(position==0&&isFirstTime){
            holder.contain.setVisibility(View.VISIBLE);
            isFirstTime = false;
        }
        return convertView;
    }

    class ViewHolder {
        @ViewInject(R.id.text_notification_title)
        TextView title;
        @ViewInject(R.id.text_notification_time)
        TextView time;
        @ViewInject(R.id.web_notification)
        TextView text;
        @ViewInject(R.id.image_notification_up)
        ImageView up;
        @ViewInject(R.id.relative_notification_contain)
        RelativeLayout contain;
    }
}
