package com.zznorth.tianji.base;

import android.support.v4.app.Fragment;

/**
 * coder by 中资北方 on 2015/12/21.
 */
public abstract class BaseFragment extends Fragment{
    protected boolean isPrepared = false;

    /**
     * 当前Fragment是否可见
     */
    protected boolean isVisiable;
    protected boolean isFirstTimeVisiable = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisiable=true;
            onVisible();
        }else {
            isVisiable=false;
            onInvisible();
        }
    }

    protected void onInvisible(){

    }

    /**
     * 可见
     */
    private void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

}
