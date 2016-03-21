package com.zznorth.tianji.callBack;

import com.zznorth.tianji.utils.LogUtil;

import org.xutils.common.Callback;

/**
 * coder by 中资北方 on 2015/12/28.
 */
public abstract class ProgressCall<T> implements Callback.ProgressCallback<T> {
    @Override
    public void onWaiting() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {

    }

    @Override
    public abstract void onSuccess(T result);

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtil.i("Progress",ex.toString());
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
