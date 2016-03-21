package com.zznorth.tianji.activities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zznorth.tianji.R;
import com.zznorth.tianji.ZZNHApplication;
import com.zznorth.tianji.base.BaseSwipeActivity;
import com.zznorth.tianji.callBack.NetCall;
import com.zznorth.tianji.utils.APIUtils;
import com.zznorth.tianji.utils.EncodeUtils;
import com.zznorth.tianji.utils.FileUtils;
import com.zznorth.tianji.utils.LogUtil;
import com.zznorth.tianji.utils.NetUtil;
import com.zznorth.tianji.utils.UIHelper;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * coder by 中资北方 on 2015/12/11.
 */
public class ChangePwdActivity extends BaseSwipeActivity {

    @ViewInject(R.id.edit_pwd_old)
    private EditText editOld;
    @ViewInject(R.id.edit_pwd_new)
    private EditText editNew;
    @ViewInject(R.id.edit_pwd_confirm)
    private EditText editConfirm;
    @ViewInject(R.id.button_pwd_change)
    private Button button;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    public void initView() {
        setSwipeAnyWhere(true);
    }

    @Event(value = R.id.button_pwd_change, type = View.OnClickListener.class)
    private void change(View view) {
        String oldPwd = editOld.getText().toString();
        String newPwd = editNew.getText().toString();
        String confirm = editConfirm.getText().toString();

        if (oldPwd.isEmpty()) {
            UIHelper.ToastUtil("现密码不能为空");
            return;
        }
        if (newPwd.isEmpty()) {
            UIHelper.ToastUtil("新密码不能为空");
            return;
        }
        if (confirm.isEmpty()) {
            UIHelper.ToastUtil("确认密码不能为空");
            return;
        }
        if (!newPwd.equals(confirm)) {
            UIHelper.ToastUtil("两次输入密码不一致");
            return;
        }
        if (!NetUtil.isNetWork()) {
            return;
        }
        button.setText("修改中...");
        //更改密码
        final String name = FileUtils.ReadName();
        UIHelper.GetDataFromNet(APIUtils.ChangePwdLink(name, EncodeUtils.getMD5Encode(oldPwd), EncodeUtils.getMD5Encode(newPwd)), new NetCall<String>() {
            @Override
            public void onSuccess(String json) {
                LogUtil.i(TAG, json);
                boolean success = false;
                String message = "";
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    success = jsonObject.getBoolean("Result");
                    message = jsonObject.getString("Message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                UIHelper.ToastUtil(message);
                if (success) {
                    FileUtils.RemoveNamePwd();
                    FileUtils.StoreName(name);
                    Intent intent = new Intent(ChangePwdActivity.this, LoginActivity.class);
                    startActivity(intent);
                    ZZNHApplication.getInstance().ExitApp();
                }else {
                    button.setText("修改密码");
                }
            }
        });
    }

    @Event(value = R.id.image_back, type = View.OnClickListener.class)
    private void back(View view) {
        finish();
    }

    @Event(value = R.id.text_top_title, type = View.OnClickListener.class)
    private void finish2(View view) {
        finish();
    }

}
