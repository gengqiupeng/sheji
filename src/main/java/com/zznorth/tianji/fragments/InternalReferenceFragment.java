package com.zznorth.tianji.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zznorth.tianji.R;
import com.zznorth.tianji.adapter.ViewPagerAdapter;
import com.zznorth.tianji.base.BaseFragment;
import com.zznorth.tianji.utils.Config;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/** 独家内参
 * coder by 中资北方 on 2015/12/3.
 */
public class InternalReferenceFragment extends BaseFragment {
    //不要实现懒加载。该Fragment是容器，里面包含的HotPointFragment已经实现过懒加载了。
    private static final String TAG = "Internal";
    @ViewInject(R.id.viewPager_internal)
    private ViewPager viewPager;
    @ViewInject(R.id.radioG_internal_top)
    private RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_internal_reference, container,false);
        x.view().inject(this, view);
        //不实现懒加载卡顿，延时加载
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                initSelector();
                initFragments();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable,400);
        isPrepared=true;
        lazyLoad();
        return view;
    }

    @Override
    protected void lazyLoad() {
    }

    /**
     * ViewPager与RadioGruop绑定
     */
    private void initSelector() {
        ((RadioButton)(radioGroup.getChildAt(0))).setChecked(true);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                checkedRadioButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setViewPager(checkedId);
            }
        });
    }

    private void setViewPager(int checkedId) {
        switch (checkedId) {
            case R.id.radio_internal_zzview:
                viewPager.setCurrentItem(0);
                break;
            case R.id.radio_internal_theme:
                viewPager.setCurrentItem(1);
                break;
            case R.id.radio_internal_interpretation:
                viewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    private void checkedRadioButton(int position) {
        RadioButton rb = (RadioButton) radioGroup.getChildAt(position);
        rb.setChecked(true);
    }

    private void initFragments() {
        List<Fragment> fragments = new ArrayList<>();
        InternalReferenceItem fragment = new InternalReferenceItem();
        Bundle bundle = new Bundle();
        bundle.putInt("type", Config.TypeZZView);
        fragment.setArguments(bundle);
        InternalReferenceItem fragment1 = new InternalReferenceItem();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", Config.TypeTheme);
        fragment1.setArguments(bundle1);
        InternalReferenceItem fragment2 = new InternalReferenceItem();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", Config.TypeInterpretation);
        fragment2.setArguments(bundle2);
        fragments.add(fragment);
        fragments.add(fragment1);
        fragments.add(fragment2);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

}
