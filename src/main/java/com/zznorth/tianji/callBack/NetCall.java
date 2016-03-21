package com.zznorth.tianji.callBack;

import com.zznorth.tianji.utils.LogUtil;

import org.xutils.common.Callback;

/**
 * coder by 中资北方 on 2015/12/28.
 */
public abstract class NetCall<T> implements Callback.CommonCallback<T> {
    @Override
    public abstract void onSuccess(T result);

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtil.i("CallBack",ex.toString());
    }

    @Override
    public void onCancelled(CancelledException cex) {
        LogUtil.i("CallBack","Cancel");
    }

    @Override
    public void onFinished() {
    }
}
