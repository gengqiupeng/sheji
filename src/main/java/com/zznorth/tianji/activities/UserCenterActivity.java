package com.zznorth.tianji.activities;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.ZZNHApplication;
import com.zznorth.tianji.base.BaseSwipeActivity;
import com.zznorth.tianji.bean.Context;
import com.zznorth.tianji.utils.Config;
import com.zznorth.tianji.utils.FileUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * coder by 中资北方 on 2015/12/8.
 */
public class UserCenterActivity extends BaseSwipeActivity {
    @ViewInject(R.id.text_user_account)
    private TextView account;
    @ViewInject(R.id.text_user_name)
    private TextView name;
    @ViewInject(R.id.text_user_out_date)
    private TextView date;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    public void initView() {
        setSwipeAnyWhere(true);
        Context user = FileUtils.ReadUser();
        account.setText(user.getAccountId());
        name.setText(user.getAccountName());
        date.setText(user.getExpiredTime());
    }

    @Event(value = R.id.relative_user_change_pwd,type = View.OnClickListener.class)
    private void changePwd(View view){
        Intent intent = new Intent(UserCenterActivity.this,ChangePwdActivity.class);
        startActivity(intent);
    }

    @Event(value = R.id.relative_user_logout,type = View.OnClickListener.class)
    private void logout(View view){
        FileUtils.DeleteByTag(Config.SPAccountAuto);
        Intent intent1 = new Intent();
        intent1.setAction("com.zznorth.exitService");
        ZZNHApplication.getInstance().sendBroadcast(intent1);
        Intent intent = new Intent(UserCenterActivity.this,LoginActivity.class);
        startActivity(intent);
        ZZNHApplication.getInstance().ExitApp();
    }

    @Event(value = R.id.image_back,type = View.OnClickListener.class)
    private void finish(View view){
        finish();
    }
    @Event(value = R.id.text_top_title,type = View.OnClickListener.class)
    private void finish2(View view){
        finish();
    }

    @Event(value = R.id.relative_user_about,type = View.OnClickListener.class)
    private void about(View view){
        Intent intent = new Intent(this,AboutUsActivity.class);
        startActivity(intent);
    }




}
