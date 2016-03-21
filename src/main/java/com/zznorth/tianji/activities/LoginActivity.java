package com.zznorth.tianji.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.zznorth.tianji.R;
import com.zznorth.tianji.base.BaseActivity;
import com.zznorth.tianji.bean.LoginInfo;
import com.zznorth.tianji.callBack.CallInt;
import com.zznorth.tianji.callBack.CallString;
import com.zznorth.tianji.callBack.NetCall;
import com.zznorth.tianji.utils.APIUtils;
import com.zznorth.tianji.utils.Config;
import com.zznorth.tianji.utils.EncodeUtils;
import com.zznorth.tianji.utils.FileUtils;
import com.zznorth.tianji.utils.JsonParser;
import com.zznorth.tianji.utils.LogUtil;
import com.zznorth.tianji.utils.NetUtil;
import com.zznorth.tianji.utils.UIHelper;
import com.zznorth.tianji.utils.Update;
import com.zznorth.tianji.view.PullDoorView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.lang.ref.WeakReference;

/**
 * coder by 中资北方 on 2015/12/1.
 */
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.edit_login_username)
    private EditText name;
    @ViewInject(R.id.edit_login_pwd)
    private EditText pwd;
    @ViewInject(R.id.check_login_pwd)
    private CheckBox rememberPwd;
    @ViewInject(R.id.check_login_auto_login)
    private CheckBox autoLogin;
    @ViewInject(R.id.view_login_pullDoor)
    private PullDoorView pullDoorView;
    @ViewInject(R.id.button_login_login)
    private Button buttonLogin;
    private boolean isLoginRun = false;
    private Update update;
    private int updateStatus = Config.UpdateNone;

    @Override
    public void initView() {
        update = new Update(this, new CallInt() {
            @Override
            public void getInt(int code) {
                updateStatus = code;
            }
        });
        //记住密码,自动登陆复选框逻辑判断
        rememberPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    autoLogin.setChecked(false);
                }
            }
        });
        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rememberPwd.setChecked(true);
                }
            }
        });
        //箭头动画
        /*Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.login_up);
        up.startAnimation(animation);*/

        //账号密码相关,如果保存的有密码，初始化输入,如果自动登陆，则直接登陆
        String userName = FileUtils.ReadName();
        String userPwd = FileUtils.ReadPwd();
        if (null != userName) {
            name.setText(userName);
            if (null != userPwd) {
                pwd.setText(userPwd);
                rememberPwd.setChecked(true);
            }
        }

        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FileUtils.RemovePwd();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //根据回掉接口获得是否滑动半屏
        pullDoorView.getLocation(new CallString() {
            @Override
            public void getString(String str) {
                AutoLogin();
                isLoginRun = true;
                buttonLogin.setClickable(true);
            }
        });

        //判断当前SDK版本号，如果是4.4以上，就是支持沉浸式状态栏的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void initStatusBar() {
        //不调用父类状态栏设置
    }

    @Event(value = R.id.button_login_login, type = View.OnClickListener.class)
    private void buttonClick(View view) {
        //在显示toast之前显示更新框
        if (updateStatus == Config.UpdateEnforce) {
            buttonLogin.setClickable(true);
            update.ShowInfo();
            return;
        }
        if (updateStatus == Config.UpdateDefault) {
            buttonLogin.setClickable(true);
            update.ShowInfo();
            updateStatus = Config.UpdateNone;
            return;
        }
        String userName = name.getText().toString().trim();
        String rawPwd = pwd.getText().toString().trim();
        //非空判断
        if (userName.isEmpty()) {
            UIHelper.ToastUtil("请输入账号");
            return;
        }
        if (rawPwd.isEmpty()) {
            UIHelper.ToastUtil("请输入密码");
            return;
        }
        //密码加密
        String userPwd;
        if (null == FileUtils.ReadPwd()) {
            userPwd = EncodeUtils.getMD5Encode(rawPwd);
        } else {
            userPwd = rawPwd;
        }

        boolean isRemember = rememberPwd.isChecked();
        boolean isAutoLogin = autoLogin.isChecked();
        if (isRemember) {
            FileUtils.StoreNamePwd(userName, userPwd, isAutoLogin);
        } else {
            FileUtils.RemoveNamePwd();
        }
        buttonLogin.setClickable(false);
        Login(userName, userPwd);
    }

    /**
     * 是否可以自动登陆
     */
    private void AutoLogin() {
        if (FileUtils.ReadIsAuto() && !isLoginRun) {
            //在显示toast之前显示更新框
            if (updateStatus == Config.UpdateEnforce) {
                buttonLogin.setClickable(true);
                update.ShowInfo();
                return;
            }
            if (updateStatus == Config.UpdateDefault) {
                buttonLogin.setClickable(true);
                update.ShowInfo();
                updateStatus = Config.UpdateNone;
                return;
            }
            buttonLogin.setClickable(false);
            Login(FileUtils.ReadName(), FileUtils.ReadPwd());
            autoLogin.setChecked(true);
        }
    }

    //登陆
    private void Login(final String userName, String userPwd) {
        NetUtil.isNetWork();

        if (updateStatus == Config.UpdateEnforce) {
            buttonLogin.setClickable(true);
            update.ShowInfo();
            return;
        }
        if (updateStatus == Config.UpdateDefault) {
            buttonLogin.setClickable(true);
            update.ShowInfo();
            updateStatus = Config.UpdateNone;
            return;
        }

        buttonLogin.setText("登陆中...");
        String url = APIUtils.LoginLink(userName, userPwd, Config.AccountId);
        LogUtil.i(TAG, url);
        UIHelper.GetDataFromNet(url, new NetCall<String>() {
            @Override
            public void onSuccess(String result) {
                // TxtLog.setInfo("logsuccess " + result);
                LoginInfo info = JsonParser.ParserLoginInfo(result);
                LogUtil.i(TAG, result);
                LogUtil.i(TAG, info.isResult());
                if (info.isResult()) {//登陆成功
                    buttonLogin.setText("登陆成功");
                    FileUtils.StoreName(userName);

                    FileUtils.StoreUser(info.getContext());
                    FileUtils.StoredSessionId(info.getContext().getSessionId());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {//登陆失败
                    buttonLogin.setText("登陆");
                    UIHelper.ToastUtil("账号或密码错误");
                    pwd.setText(null);
                    FileUtils.RemovePwd();
                    buttonLogin.setClickable(true);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                // TxtLog.setInfo("logerror " + ex.toString());
                LogUtil.i(TAG, ex.toString());
                buttonLogin.setText("登陆");
                buttonLogin.setClickable(true);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                buttonLogin.setText("登陆");
                buttonLogin.setClickable(true);
            }

            @Override
            public void onFinished() {
                buttonLogin.setText("登陆");
                buttonLogin.setClickable(true);
            }
        });
    }

    private static class MyHandler extends Handler {
        WeakReference<LoginActivity> loginActivityWeak;

        public MyHandler(LoginActivity loginActivity) {
            loginActivityWeak = new WeakReference<>(loginActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoginActivity sampleActivity = loginActivityWeak.get();
            AlphaAnimation animation = new AlphaAnimation(1, 0);
            animation.setDuration(1000);
            sampleActivity.pullDoorView.startAnimation(animation);
            sampleActivity.AutoLogin();
            sampleActivity.isLoginRun = true;
            sampleActivity.pullDoorView.mCloseFlag = true;
        }
    }

    public MyHandler handler = new MyHandler(LoginActivity.this);

    @Override
    protected void onStart() {
        super.onStart();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public int getLayoutId() {
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_login_no_back;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
